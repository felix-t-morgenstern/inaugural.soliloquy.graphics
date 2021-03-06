package inaugural.soliloquy.graphics.test.display.rendering.renderers.textlinerenderer;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.common.test.fakes.FakePair;
import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.assets.FontImpl;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.rendering.renderers.TextLineRendererImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.renderables.TextJustification;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.WindowDisplayMode;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

class TextLineRendererJustificationsTest {
    private final static FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final static float[] MESH_DATA =
            new float[] {0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    private final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    private final static String RELATIVE_LOCATION = "./res/fonts/Trajan Pro Regular.ttf";
    private final static float MAX_LOSSLESS_FONT_SIZE = 100f;
    private final static float ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING = 0.5f;
    private final static Map<Character, Float> GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING =
            new HashMap<>();
    private final static Map<Character, Float> GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT =
            new HashMap<>();
    private final static float ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING = 0.2f;
    private final static float LEADING_ADJUSTMENT = 0.0f;
    private final static FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final static String LINE_TEXT_LEFT = "This is left-aligned";
    private final static String LINE_TEXT_CENTER = "This is center-aligned";
    private final static String LINE_TEXT_RIGHT = "This is right-aligned";
    private static final String SHADER_FILENAME_PREFIX = "./res/shaders/defaultShader";

    private static FakeTextLineRenderable TextLineRenderableLeft;
    private static FakeTextLineRenderable TextLineRenderableCenter;
    private static FakeTextLineRenderable TextLineRenderableRight;

    public static void main(String[] args) {
        WindowResolutionManagerImpl windowResolutionManager =
                new WindowResolutionManagerImpl(WindowDisplayMode.WINDOWED,
                        WindowResolution.RES_1920x1080, COORDINATE_FACTORY);

        FakeFrameTimer frameTimer = new FakeFrameTimer();
        Function<float[], Function<float[], Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING.put('Q', 0.75f);
        GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING.put('q', 0.75f);

        FakeFontStyleDefinition plain = new FakeFontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING);
        FakeFontStyleDefinition italic = new FakeFontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING);
        FakeFontStyleDefinition bold = new FakeFontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING);
        FakeFontStyleDefinition boldItalic = new FakeFontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING);
        FakeFontDefinition fontDefinition = new FakeFontDefinition("id", RELATIVE_LOCATION,
                MAX_LOSSLESS_FONT_SIZE,
                plain, italic, bold, boldItalic,
                LEADING_ADJUSTMENT);

        FakePair<Float,Float> renderingLocationLeft = new FakePair<>(0.05f, 0.225f);
        FakePair<Float,Float> renderingLocationCenter = new FakePair<>(0.5f, 0.475f);
        FakePair<Float,Float> renderingLocationRight = new FakePair<>(0.95f, 0.725f);

        TextLineRenderableLeft = new FakeTextLineRenderable(null, 0.05f, 0f, LINE_TEXT_LEFT,
                new FakeStaticProviderAtTime<>(null), new FakeStaticProviderAtTime<>(null), null,
                null, null, new StaticProviderImpl<>(renderingLocationLeft), new FakeEntityUuid());
        TextLineRenderableCenter = new FakeTextLineRenderable(null, 0.05f, 0f, LINE_TEXT_CENTER,
                new FakeStaticProviderAtTime<>(null), new FakeStaticProviderAtTime<>(null), null,
                null, null, new StaticProviderImpl<>(renderingLocationCenter),
                new FakeEntityUuid());
        TextLineRenderableRight = new FakeTextLineRenderable(null, 0.05f, 0f, LINE_TEXT_RIGHT,
                new FakeStaticProviderAtTime<>(null), new FakeStaticProviderAtTime<>(null), null,
                null, null, new StaticProviderImpl<>(renderingLocationRight),
                new FakeEntityUuid());

        TextLineRenderableLeft.Justification = TextJustification.LEFT;
        TextLineRenderableCenter.Justification = TextJustification.CENTER;
        TextLineRenderableRight.Justification = TextJustification.RIGHT;

        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        Renderer<soliloquy.specs.graphics.renderables.TextLineRenderable> textLineRenderer =
                new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY, Color.WHITE,
                        windowResolutionManager);

        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithMesh =
                new ArrayList<Renderer>() {{
                    add(textLineRenderer);
                }};
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithShader =
                new ArrayList<Renderer>() {{
                    add(textLineRenderer);
                }};

        stackRenderer.RenderAction = timestamp -> {
            textLineRenderer.render(TextLineRenderableLeft, timestamp);
            textLineRenderer.render(TextLineRenderableCenter, timestamp);
            textLineRenderer.render(TextLineRenderableRight, timestamp);
        };

        FakeFrameExecutor frameExecutor = new FakeFrameExecutor(stackRenderer, null);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), frameTimer, 20, windowResolutionManager,
                frameExecutor, new ShaderFactoryImpl(), renderersWithShader,
                SHADER_FILENAME_PREFIX, meshFactory, renderersWithMesh, MESH_DATA, MESH_DATA,
                graphicsPreloader);

        graphicsPreloader.LoadAction = () -> {
            TextLineRenderableLeft.Font =  TextLineRenderableCenter.Font =
                    TextLineRenderableRight.Font =
                                    new FontImpl(fontDefinition, FLOAT_BOX_FACTORY, COORDINATE_FACTORY);
            frameTimer.ShouldExecuteNextFrame = true;
        };

        graphicsCoreLoop.startup(() -> closeAfterSomeTime(graphicsCoreLoop));
    }

    private static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.sleep(8000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}
