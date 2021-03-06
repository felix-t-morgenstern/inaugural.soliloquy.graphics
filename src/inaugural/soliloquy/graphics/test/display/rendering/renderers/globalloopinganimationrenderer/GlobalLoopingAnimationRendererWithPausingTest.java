package inaugural.soliloquy.graphics.test.display.rendering.renderers.globalloopinganimationrenderer;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.bootstrap.assetfactories.AnimationFactory;
import inaugural.soliloquy.graphics.bootstrap.assetfactories.ImageFactoryImpl;
import inaugural.soliloquy.graphics.renderables.GlobalLoopingAnimationRenderableImpl;
import inaugural.soliloquy.graphics.renderables.providers.GlobalLoopingAnimationImpl;
import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.rendering.renderers.GlobalLoopingAnimationRenderer;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.assets.Animation;
import soliloquy.specs.graphics.assets.AnimationFrameSnippet;
import soliloquy.specs.graphics.assets.Image;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.bootstrap.assetfactories.AssetFactory;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.AnimationDefinition;
import soliloquy.specs.graphics.renderables.GlobalLoopingAnimationRenderable;
import soliloquy.specs.graphics.renderables.providers.GlobalLoopingAnimation;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import static inaugural.soliloquy.graphics.api.Constants.MS_PER_SECOND;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a window of 1920x1080 pixels in the middle of the screen for 3000ms
 *    with a titlebar reading "My title bar". The window will contain a looping animation of a
 *    torch, centered in the screen, which will change its frames every 250ms.
 * 2. The animation will persist for 1125ms.
 * 3. The animation will pause for 1000ms.
 * 4. The animation will resume for 1125ms. (A rainbow version of the torch is used to verify
 *    whether animation resumes where it left off.)
 * 5. The window will then close.
 *
 */
public class GlobalLoopingAnimationRendererWithPausingTest {
    private final static FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final static float[] MESH_DATA =
            new float[] {0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    private final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    private final static FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final static String TORCH_RELATIVE_LOCATION =
            "./res/images/fixtures/animated_torch_rainbow.png";
    private static final String SHADER_FILENAME_PREFIX = "./res/shaders/defaultShader";
    private static final FakeGlobalClock GLOBAL_CLOCK = new FakeGlobalClock();

    private static GlobalLoopingAnimation GlobalLoopingAnimation;

    public static void main(String[] args) {
        WindowResolution resolution = WindowResolution.RES_1920x1080;

        WindowResolutionManagerImpl windowManager = new WindowResolutionManagerImpl(
                WindowDisplayMode.WINDOWED, resolution, COORDINATE_FACTORY);

        FakeFrameTimer frameTimer = new FakeFrameTimer();
        Function<float[], Function<float[], Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        AssetFactory<AnimationDefinition, Animation> animationFactory = new AnimationFactory();

        HashMap<Integer, AnimationFrameSnippet> frames = new HashMap<>();

        int numberOfFrames = 9;
        int frameWidth = 32;
        int frameHeight = 64;
        int frameDuration = MS_PER_SECOND / 4;
        int loopsToDisplay = 3;

        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        Renderer<GlobalLoopingAnimationRenderable> globalLoopingAnimationRenderer =
                new GlobalLoopingAnimationRenderer(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY,
                        new FakeColorShiftStackAggregator());
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithMesh =
                new ArrayList<Renderer>() {{
                    add(globalLoopingAnimationRenderer);
                }};
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithShader =
                new ArrayList<Renderer>() {{
                    add(globalLoopingAnimationRenderer);
                }};

        FakeFrameExecutor frameExecutor = new FakeFrameExecutor(stackRenderer, GLOBAL_CLOCK);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), frameTimer, 20, windowManager, frameExecutor,
                new ShaderFactoryImpl(), renderersWithShader, SHADER_FILENAME_PREFIX, meshFactory,
                renderersWithMesh, MESH_DATA, MESH_DATA, graphicsPreloader);

        graphicsPreloader.LoadAction = () -> {
            Image renderableImage = new ImageFactoryImpl(0.5f)
                    .make(TORCH_RELATIVE_LOCATION, false);
            for (int i = 0; i < numberOfFrames; i++) {
                frames.put(frameDuration * i, new FakeAnimationFrameSnippet(renderableImage,
                        frameWidth * i, 0, frameWidth * (i + 1), frameHeight, 0f, 0f));
            }
            // TODO: Ensure that animation starts on the first frame, if possible
            long globalLoopingAnimationStartTimestamp = GLOBAL_CLOCK.globalTimestamp();

            int msDuration = frameDuration * numberOfFrames;

            FakeAnimationDefinition animationDefinition = new FakeAnimationDefinition(msDuration,
                    "torch", frames);

            Animation animation = animationFactory.make(animationDefinition);

            System.out.println("Animation duration = " + animation.msDuration());

            int periodModuloOffset = (int)(globalLoopingAnimationStartTimestamp % (msDuration));

            GlobalLoopingAnimation = new GlobalLoopingAnimationImpl(animation, periodModuloOffset);

            float animationHeight = 0.5f;
            float animationWidth = ((float)frameWidth / (float)frameHeight)
                    / resolution.widthToHeightRatio();
            float midpoint = 0.5f;

            GlobalLoopingAnimationRenderableImpl globalLoopingAnimationRenderable =
                    new GlobalLoopingAnimationRenderableImpl(GlobalLoopingAnimation, null, null,
                            new ArrayList<>(),
                            new StaticProviderImpl<>(new FakeFloatBox(
                                    midpoint - (animationWidth / 2f),
                                    midpoint - (animationHeight / 2f),
                                    midpoint + (animationWidth / 2f),
                                    midpoint + (animationHeight / 2f))),
                            0, new FakeEntityUuid(), renderable -> {}, renderable -> {});
            frameTimer.ShouldExecuteNextFrame = true;

            stackRenderer.RenderAction = timestamp -> globalLoopingAnimationRenderer
                    .render(globalLoopingAnimationRenderable, timestamp);
        };

        graphicsCoreLoop.startup(() ->
                closeAfterSomeTime(graphicsCoreLoop,
                        frameDuration * numberOfFrames * loopsToDisplay));
    }

    private static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop, int ms) {
        CheckedExceptionWrapper.sleep(ms / 3);

        long pauseTimestamp = GLOBAL_CLOCK.globalTimestamp();
        GlobalLoopingAnimation.reportPause(pauseTimestamp);
        System.out.println("Paused at " + pauseTimestamp);
        System.out.println("Period modulo offset = " + GlobalLoopingAnimation.periodModuloOffset());

        CheckedExceptionWrapper.sleep(1000);

        long unpauseTimestamp = GLOBAL_CLOCK.globalTimestamp();
        GlobalLoopingAnimation.reportUnpause(unpauseTimestamp);
        System.out.println("Unpaused at " + unpauseTimestamp);
        System.out.println("Period modulo offset = " + GlobalLoopingAnimation.periodModuloOffset());

        CheckedExceptionWrapper.sleep((ms * 2) / 3);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}
