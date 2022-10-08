package inaugural.soliloquy.graphics.test.display;

import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.rendering.FrameExecutorImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.io.MouseCursor;
import soliloquy.specs.graphics.rendering.FrameExecutor;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

import static inaugural.soliloquy.graphics.api.Constants.WHOLE_SCREEN;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class DisplayTest {
    protected final static float[] MESH_DATA =
            new float[]{0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    protected final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    protected final static FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    protected final static String SHADER_FILENAME_PREFIX = "./src/main/resources/shaders/defaultShader";
    protected final static UUID UUID = java.util.UUID.randomUUID();
    protected final static WindowResolution RESOLUTION = WindowResolution.RES_1920x1080;
    protected final static FakeGlobalClock GLOBAL_CLOCK = new FakeGlobalClock();
    protected final static FakeRenderableStack RENDERING_STACK = new FakeRenderableStack();

    protected static FakeFrameTimer FrameTimer;
    protected static MouseCursor MouseCursor = new FakeMouseCursor();

    /** @noinspection rawtypes */
    protected static void runTest(Function<WindowResolutionManager, List<Renderer>>
                                          generateRenderablesAndRenderersWithMeshAndShader,
                                  Consumer<Long> stackRendererAction,
                                  Runnable graphicsPreloaderLoadAction,
                                  Consumer<GraphicsCoreLoop> closeAfterSomeTime) {
        WindowResolutionManagerImpl windowResolutionManager = new WindowResolutionManagerImpl(
                WindowDisplayMode.WINDOWED, RESOLUTION);

        FrameTimer = new FakeFrameTimer();

        Function<float[], Function<float[], Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = WHOLE_SCREEN;

        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        List<Renderer> renderersWithMeshAndShader =
                generateRenderablesAndRenderersWithMeshAndShader.apply(windowResolutionManager);

        stackRenderer.RenderAction = stackRendererAction;

        FrameExecutor frameExecutor = new FrameExecutorImpl(GLOBAL_CLOCK, stackRenderer, 100);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), FrameTimer, 0, windowResolutionManager,
                frameExecutor, new ShaderFactoryImpl(), renderersWithMeshAndShader,
                SHADER_FILENAME_PREFIX, meshFactory, renderersWithMeshAndShader, MESH_DATA,
                MESH_DATA, graphicsPreloader, MouseCursor);

        graphicsPreloader.LoadAction = graphicsPreloaderLoadAction;

        graphicsCoreLoop.startup(() -> closeAfterSomeTime.accept(graphicsCoreLoop));
    }

    public static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        closeAfterSomeTime(graphicsCoreLoop, 3000);
    }

    public static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop, int totalMs) {
        CheckedExceptionWrapper.sleep(totalMs);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}