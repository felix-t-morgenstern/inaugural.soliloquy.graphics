package inaugural.soliloquy.graphics.test.display.rendering.renderers.spriterenderer;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.bootstrap.assetfactories.ImageFactoryImpl;
import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.rendering.renderers.SpriteRenderer;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a window of 1920x1080 pixels in the middle of the screen for 3000ms
 *    with a titlebar reading "My title bar". The window will contain a picture of a shield,
 *    centered in the window, taking up half of the width and three-fourths of the height of the
 *    window.
 * 2. The window will then close.
 *
 */
class SpriteRendererSimpleTest {
    private final static FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final static float[] MESH_DATA =
            new float[] {0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    private final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    private final static FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final static String RPG_WEAPONS_RELATIVE_LOCATION =
            "./res/images/items/RPG_Weapons.png";
    private static final String SHADER_FILENAME_PREFIX = "./res/shaders/defaultShader";

    private static FakeSpriteRenderable SpriteRenderable;

    public static void main(String[] args) {
        WindowResolutionManagerImpl windowResolutionManager =
                new WindowResolutionManagerImpl(WindowDisplayMode.WINDOWED,
                        WindowResolution.RES_1920x1080, COORDINATE_FACTORY);

        FakeFrameTimer frameTimer = new FakeFrameTimer();
        Function<float[], Function<float[],Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        FakeSprite sprite =
                new FakeSprite(null, 266, 271, 313, 343);
        SpriteRenderable = new FakeSpriteRenderable(sprite, new ArrayList<>(),
                new StaticProviderImpl<>(
                        new FakeFloatBox(0.25f, 0.125f, 0.75f, 0.875f)),
                new StaticProviderImpl<>(null, 0f), new StaticProviderImpl<>(null, INTACT_COLOR),
                new FakeEntityUuid());
        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        Renderer<SpriteRenderable> spriteRenderer = new SpriteRenderer(RENDERING_BOUNDARIES,
                FLOAT_BOX_FACTORY, windowResolutionManager, new FakeColorShiftStackAggregator());

        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithMesh =
                new ArrayList<Renderer>() {{
                    add(spriteRenderer);
        }};
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithShader =
                new ArrayList<Renderer>() {{
                    add(spriteRenderer);
        }};

        stackRenderer.RenderAction = timestamp ->
                spriteRenderer.render(SpriteRenderable, timestamp);

        FakeFrameExecutor frameExecutor = new FakeFrameExecutor(stackRenderer, null);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), frameTimer, 20, windowResolutionManager,
                frameExecutor, new ShaderFactoryImpl(), renderersWithShader,
                SHADER_FILENAME_PREFIX, meshFactory, renderersWithMesh, MESH_DATA, MESH_DATA,
                graphicsPreloader);

        graphicsPreloader.LoadAction = () -> {
            sprite.Image = new ImageFactoryImpl(0.5f).make(RPG_WEAPONS_RELATIVE_LOCATION, false);
            frameTimer.ShouldExecuteNextFrame = true;
        };

        graphicsCoreLoop.startup(() -> closeAfterSomeTime(graphicsCoreLoop));
    }

    private static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.sleep(3000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}
