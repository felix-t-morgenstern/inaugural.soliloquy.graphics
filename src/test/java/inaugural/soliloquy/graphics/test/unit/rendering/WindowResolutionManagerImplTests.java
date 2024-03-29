package inaugural.soliloquy.graphics.test.unit.rendering;

import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.lwjgl.opengl.GL;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;

import static inaugural.soliloquy.tools.valueobjects.Pair.pairOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.lwjgl.glfw.GLFW.*;

class WindowResolutionManagerImplTests {
    private WindowResolutionManager _windowResolutionManager;

    @BeforeAll
    static void setUpFixture() {
        if (!glfwInit()) {
            throw new RuntimeException("GLFW failed to initialize");
        }

        long window = glfwCreateWindow(1, 1, "", 0, 0);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    @AfterAll
    static void tearDownFixture() {
        glfwTerminate();
    }

    @BeforeEach
    void setUp() {
        _windowResolutionManager = new WindowResolutionManagerImpl(WindowDisplayMode.WINDOWED,
                WindowResolution.RES_1920x1080);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new WindowResolutionManagerImpl(
                null, WindowResolution.RES_1920x1080));
        assertThrows(IllegalArgumentException.class, () -> new WindowResolutionManagerImpl(
                WindowDisplayMode.WINDOWED, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(WindowResolutionManager.class.getCanonicalName(),
                _windowResolutionManager.getInterfaceName());
    }

    @Test
    void testGetAndSetWindowDisplayMode() {
        _windowResolutionManager.setWindowDisplayMode(WindowDisplayMode.FULLSCREEN);

        assertEquals(WindowDisplayMode.FULLSCREEN,
                _windowResolutionManager.getWindowDisplayMode());
    }

    @Test
    void testSetWindowDisplayModeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.setWindowDisplayMode(null));
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.setWindowDisplayMode(WindowDisplayMode.UNKNOWN));
    }

    @Test
    void testUpdateAndGetDimensions() {
        int width = 3840;
        int height = 2160;

        _windowResolutionManager.updateDimensions(width, height);

        assertEquals(pairOf(width, height), _windowResolutionManager.getWindowDimensions());
    }

    @Test
    void testWindowWidthToHeightRatio() {
        int width = 3840;
        int height = 2160;

        _windowResolutionManager.updateDimensions(width, height);

        assertEquals(width / (float) height, _windowResolutionManager.windowWidthToHeightRatio());
    }

    @Test
    void testSetInvalidDimensions() {
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.updateDimensions(0, 600));
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.updateDimensions(800, 0));
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.updateDimensions(800, 601));
    }

    @Test
    void testUpdateWindowSizeAndLocationWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.updateWindowSizeAndLocation(0, null));
        assertThrows(IllegalArgumentException.class,
                () -> _windowResolutionManager.updateWindowSizeAndLocation(0, ""));
    }

    // NB: updateWindowSizeAndLocation is not currently tested here, since its only effects are
    //     visible in the actual display, hence the display tests (e.g.
    //     WindowManagerImplWindowedTest)
}
