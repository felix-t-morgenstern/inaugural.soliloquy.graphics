package inaugural.soliloquy.graphics.rendering;

import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.tools.Check;
import org.lwjgl.glfw.GLFWVidMode;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.*;

// TODO: Clean up all the mess in this code
public class WindowResolutionManagerImpl implements WindowResolutionManager {
    private final CoordinateFactory COORDINATE_FACTORY;
    private final ArrayList<Consumer<Coordinate>> RESOLUTION_CHANGE_SUBSCRIBERS =
            new ArrayList<>();

    private WindowDisplayMode _windowDisplayMode;
    private WindowResolution _windowResolution;

    private WindowDisplayMode _mostRecentlyRenderedWindowDisplayMode;
    private WindowResolution _mostRecentlyRenderedWindowResolution;

    private Integer _windowedFullscreenWidth;
    private Integer _windowedFullscreenHeight;

    private Integer _previouslyPublishedWidth;
    private Integer _previouslyPublishedHeight;

    public WindowResolutionManagerImpl(WindowDisplayMode startingWindowDisplayMode,
                                       WindowResolution startingWindowResolution,
                                       CoordinateFactory coordinateFactory) {
        _windowDisplayMode = Check.ifNull(startingWindowDisplayMode, "startingWindowDisplayMode");
        _windowResolution = Check.ifNull(startingWindowResolution, "startingWindowResolution");

        if (_windowDisplayMode == WindowDisplayMode.WINDOWED_FULLSCREEN &&
                _windowResolution != WindowResolution.RES_WINDOWED_FULLSCREEN) {
            throw new IllegalArgumentException("WindowResolutionManagerImpl: If " +
                    "windowDisplayMode is WINDOWED_FULLSCREEN, windowResolution must be " +
                    "RES_WINDOWED_FULLSCREEN");
        }

        _mostRecentlyRenderedWindowDisplayMode = WindowDisplayMode.UNKNOWN;
        _mostRecentlyRenderedWindowResolution = WindowResolution.RES_INVALID;

        COORDINATE_FACTORY = coordinateFactory;
    }

    @Override
    public WindowDisplayMode getWindowDisplayMode() {
        return _windowDisplayMode;
    }

    @Override
    public void setWindowDisplayMode(WindowDisplayMode windowDisplayMode)
            throws IllegalArgumentException {
        if (Check.ifNull(windowDisplayMode, "windowDisplayMode") ==
                WindowDisplayMode.UNKNOWN) {
            throw new IllegalArgumentException(
                    "WindowResolutionManagerImpl.setWindowDisplayMode: windowDisplayMode cannot " +
                            "be UNKNOWN");
        }
        _windowDisplayMode = windowDisplayMode;
    }

    @Override
    public void setDimensions(int width, int height)
            throws IllegalArgumentException, UnsupportedOperationException {
        if (_windowDisplayMode == WindowDisplayMode.WINDOWED_FULLSCREEN) {
            throw new UnsupportedOperationException(
                    "WindowResolutionManagerImpl.setDimensions: cannot set dimensions while " +
                            "windowed fullscreen");
        }

        if (width < 1) {
            throw new IllegalArgumentException("WindowResolutionManagerImpl.setDimensions: " +
                    "width (" + width + ") cannot be less than 1");
        }
        if (height < 1) {
            throw new IllegalArgumentException("WindowResolutionManagerImpl.setDimensions: " +
                    "height (" + height + ") cannot be less than 1");
        }

        WindowResolution windowResolutionFromInputs =
                WindowResolution.getFromWidthAndHeight(width, height);

        if (windowResolutionFromInputs == WindowResolution.RES_INVALID) {
            throw new IllegalArgumentException("WindowResolutionManagerImpl.setDimensions: inputs " +
                    "(" + width + "," + height + ") do not correspond to valid resolution");
        }

        _windowResolution = WindowResolution.getFromWidthAndHeight(width, height);
    }

    @Override
    public int getWidth() throws UnsupportedOperationException {
        if (_windowResolution == WindowResolution.RES_WINDOWED_FULLSCREEN) {
            return _windowedFullscreenWidth;
        }
        return _windowResolution.WIDTH;
    }

    @Override
    public int getHeight() throws UnsupportedOperationException {
        if (_windowResolution == WindowResolution.RES_WINDOWED_FULLSCREEN) {
            return _windowedFullscreenHeight;
        }
        return _windowResolution.HEIGHT;
    }

    @Override
    public void registerResolutionSubscriber(Consumer<Coordinate> subscriber)
            throws IllegalArgumentException {
        RESOLUTION_CHANGE_SUBSCRIBERS.add(Check.ifNull(subscriber, "subscriber"));
    }

    @Override
    public long updateWindowSizeAndLocation(long windowId, String titlebar) {
        Check.ifNullOrEmpty(titlebar, "titlebar");

        if (_windowResolution != _mostRecentlyRenderedWindowResolution ||
                _windowDisplayMode != _mostRecentlyRenderedWindowDisplayMode) {
            switch(_windowDisplayMode) {
                case WINDOWED:
                    windowId = renderWindowed(windowId, titlebar);
                    break;
                case FULLSCREEN:
                    windowId = renderFullscreen(windowId, titlebar);
                    break;
                case WINDOWED_FULLSCREEN:
                    windowId = renderWindowedFullscreen(windowId, titlebar);
                    break;
                default:
                    throw new IllegalStateException(
                            "WindowResolutionManager.updateWindowSizeAndLocation: display mode " +
                                    "cannot be UNKNOWN");
            }

            _mostRecentlyRenderedWindowResolution = _windowResolution;
            _mostRecentlyRenderedWindowDisplayMode = _windowDisplayMode;

            int width = getWidth();
            int height = getHeight();
            if ((Integer)width != _previouslyPublishedWidth ||
                    (Integer)height != _previouslyPublishedHeight) {
                Coordinate resolution = COORDINATE_FACTORY.make(width, height);
                RESOLUTION_CHANGE_SUBSCRIBERS.forEach(subscriber -> subscriber.accept(resolution));

                _previouslyPublishedWidth = width;
                _previouslyPublishedHeight = height;
            }
        }

        return windowId;
    }

    private long renderWindowed(long windowId, String titlebar) {
        // TODO: Consider enforcing window to be within boundaries of monitor resolution
        return renderWindowForMode(windowId, WindowDisplayMode.WINDOWED,
                currentWindowId -> glfwSetWindowSize(currentWindowId, _windowResolution.WIDTH,
                        _windowResolution.HEIGHT),
                monitor -> glfwVidMode -> {

                    glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);

                    long newWindowId = glfwCreateWindow(_windowResolution.WIDTH,
                            _windowResolution.HEIGHT, titlebar, 0, 0);
                    glfwSetWindowPos(newWindowId,
                            (glfwVidMode.width() - _windowResolution.WIDTH) / 2,
                            (glfwVidMode.height() - _windowResolution.HEIGHT) / 2);

                    _windowedFullscreenWidth = _windowedFullscreenHeight = null;

                    return newWindowId;
                });
    }

    private long renderFullscreen(long windowId, String titlebar) {
        return renderWindowForMode(windowId, WindowDisplayMode.FULLSCREEN,
                currentWindowId -> glfwSetWindowSize(currentWindowId, _windowResolution.WIDTH,
                        _windowResolution.HEIGHT),
                monitor -> glfwVidMode -> {
                    _windowedFullscreenWidth = _windowedFullscreenHeight = null;

                    return glfwCreateWindow(_windowResolution.WIDTH,
                        _windowResolution.HEIGHT, titlebar, monitor, 0);
                });
    }

    private long renderWindowedFullscreen(long windowId, String titlebar) {
        return renderWindowForMode(windowId, WindowDisplayMode.WINDOWED_FULLSCREEN,
                currentWindowId -> {}, monitor -> glfwVidMode -> {

                    glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);

                    _windowedFullscreenWidth = glfwVidMode.width();
                    _windowedFullscreenHeight = glfwVidMode.height();

                    long newWindowId = glfwCreateWindow(
                            _windowedFullscreenWidth,
                            _windowedFullscreenHeight,
                            titlebar,
                            0, 0);
                    glfwSetWindowMonitor(newWindowId, 0,
                            0,
                            0,
                            _windowedFullscreenWidth,
                            _windowedFullscreenHeight,
                            GLFW_DONT_CARE);
                    return newWindowId;
                });
    }

    private long renderWindowForMode(long windowId,
                                     WindowDisplayMode windowDisplayMode,
                                     Consumer<Long> noDisplayModeChangeAction,
                                     Function<Long, Function<GLFWVidMode, Long>> createNewWindow) {
        if (_mostRecentlyRenderedWindowDisplayMode == windowDisplayMode) {
            noDisplayModeChangeAction.accept(windowId);
            return windowId;
        }

        long oldWindowId = windowId;
        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode glfwVidMode = glfwGetVideoMode(monitor);
        assert glfwVidMode != null;

        windowId = createNewWindow.apply(monitor).apply(glfwVidMode);

        if (_mostRecentlyRenderedWindowDisplayMode != WindowDisplayMode.UNKNOWN) {
            glfwDestroyWindow(oldWindowId);
        }

        glfwMakeContextCurrent(windowId);
        glfwShowWindow(windowId);

        return windowId;
    }

    @Override
    public String getInterfaceName() {
        return WindowResolutionManager.class.getCanonicalName();
    }
}
