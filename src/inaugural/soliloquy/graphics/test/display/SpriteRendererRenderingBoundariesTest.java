package inaugural.soliloquy.graphics.test.display;

import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.SpriteRenderer;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.test.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.Renderer;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class SpriteRendererRenderingBoundariesTest {
    private final static float[] MESH_DATA =
            new float[] {0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    private final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    private final static String RPG_WEAPONS_RELATIVE_LOCATION =
            "./res/images/items/RPG_Weapons.png";
    private static final String SHADER_FILENAME_PREFIX = "./res/shaders/defaultShader";

    private static FakeSpriteRenderable SpriteRenderable;

    public static void main(String[] args) {
        WindowResolutionManagerImpl windowManager =
                new WindowResolutionManagerImpl(WindowDisplayMode.WINDOWED,
                        WindowResolution.RES_1920x1080);

        FakeFrameTimer frameTimer = new FakeFrameTimer();
        Function<float[], Function<float[], Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        FakeRenderableImage renderableImage =
                new FakeRenderableImage(RPG_WEAPONS_RELATIVE_LOCATION);
        FakeSprite sprite =
                new FakeSprite(renderableImage, 266, 271, 313, 343);
        SpriteRenderable = new FakeSpriteRenderable(sprite, new ArrayList<>(),
                new FakeFloatBox(0.25f, 0.125f, 0.75f,
                        0.825f));
        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        Renderer<soliloquy.specs.graphics.renderables.SpriteRenderable> spriteRenderer = new SpriteRenderer(RENDERING_BOUNDARIES);
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithMesh =
                new ArrayList<Renderer>() {{
                    add(spriteRenderer);
                }};
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithShader =
                new ArrayList<Renderer>() {{
                    add(spriteRenderer);
                }};

        stackRenderer.RenderAction = () -> spriteRenderer.render(SpriteRenderable);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), frameTimer, windowManager, stackRenderer,
                new ShaderFactoryImpl(), renderersWithShader, SHADER_FILENAME_PREFIX, meshFactory,
                renderersWithMesh, MESH_DATA, MESH_DATA, graphicsPreloader);

        graphicsPreloader.LoadAction = () -> {
            renderableImage.WindowId = graphicsCoreLoop.windowId();
            renderableImage.load();
            frameTimer.ShouldExecuteNextFrame = true;
        };

        graphicsCoreLoop.startup(() -> closeAfterSomeTime(graphicsCoreLoop));
    }

    private static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.Sleep(1000);

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 0.625f, 0.625f);

        CheckedExceptionWrapper.Sleep(1000);

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.375f, 0.0f, 1.0f, 0.625f);

        CheckedExceptionWrapper.Sleep(1000);

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.375f, 0.375f, 1.0f, 1.0f);

        CheckedExceptionWrapper.Sleep(1000);

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.375f, 0.625f, 1.0f);

        CheckedExceptionWrapper.Sleep(1000);

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        CheckedExceptionWrapper.Sleep(1000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}
