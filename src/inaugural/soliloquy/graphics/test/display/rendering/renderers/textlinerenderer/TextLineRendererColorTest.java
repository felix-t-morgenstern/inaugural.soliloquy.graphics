package inaugural.soliloquy.graphics.test.display.rendering.renderers.textlinerenderer;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.common.test.fakes.FakePair;
import inaugural.soliloquy.graphics.api.WindowResolution;
import inaugural.soliloquy.graphics.assets.FontImpl;
import inaugural.soliloquy.graphics.bootstrap.GraphicsCoreLoopImpl;
import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.rendering.MeshImpl;
import inaugural.soliloquy.graphics.rendering.renderers.TextLineRendererImpl;
import inaugural.soliloquy.graphics.rendering.WindowResolutionManagerImpl;
import inaugural.soliloquy.graphics.rendering.factories.ShaderFactoryImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
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

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a string of text, "Rainbow", aligned left, near the left edge
 *    of the window, and in the vertical center of the window, for 4000ms. The message, "This
 *    message is in the colors of the rainbow!", will be displayed, with each color having a
 *    different color in order of the rainbow.
 * 2. The window will then close.
 *
 */
class TextLineRendererColorTest {
    private final static FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();
    private final static float[] MESH_DATA =
            new float[] {0f, 1f, 1f, 1f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
    private final static FakeRenderingBoundaries RENDERING_BOUNDARIES =
            new FakeRenderingBoundaries();
    private final static String RELATIVE_LOCATION = "./res/fonts/Oswald-VariableFont_wght.ttf";
    private final static float MAX_LOSSLESS_FONT_SIZE = 200f;
    private final static float ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING = 0.25f;
    private final static Map<Character, Float> GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING =
            new HashMap<>();
    private final static Map<Character, Float> GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT =
            new HashMap<>();
    private final static float ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING = 0.1f;
    private final static float LEADING_ADJUSTMENT = 0f;
    private final static FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final static Color DEFAULT_COLOR = Color.WHITE;
    private final static String LINE_TEXT = "Wow, this message is in the colors of the rainbow!";
    private static final String SHADER_FILENAME_PREFIX = "./res/shaders/defaultShader";

    private static FakeTextLineRenderable TextLineRenderable;

    public static void main(String[] args) {
        WindowResolutionManagerImpl windowResolutionManager = new WindowResolutionManagerImpl(
                WindowDisplayMode.WINDOWED, WindowResolution.RES_1920x1080, COORDINATE_FACTORY);

        FakeFrameTimer frameTimer = new FakeFrameTimer();
        Function<float[], Function<float[], Mesh>> meshFactory = f1 -> f2 -> new MeshImpl(f1, f2);

        FakeStackRenderer stackRenderer = new FakeStackRenderer();

        RENDERING_BOUNDARIES.CurrentBoundaries = new FakeFloatBox(0.0f, 0.0f, 1.0f, 1.0f);

        GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT.put('j', 0.000625f);

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

        FakePair<Float,Float> renderingLocation = new FakePair<>(0.1f, 0.475f);

        HashMap<Integer, ProviderAtTime<Color>> colorIndices = rainbowGradient(LINE_TEXT);

        TextLineRenderable = new FakeTextLineRenderable(null, 0.05f, 0f, LINE_TEXT,
                new FakeStaticProviderAtTime<>(null), new FakeStaticProviderAtTime<>(null),
                colorIndices, null, null, new StaticProviderImpl<>(renderingLocation),
                new FakeEntityUuid());

        FakeGraphicsPreloader graphicsPreloader = new FakeGraphicsPreloader();

        Renderer<soliloquy.specs.graphics.renderables.TextLineRenderable> textLineRenderer =
                new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY, DEFAULT_COLOR,
                        windowResolutionManager);

        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithMesh =
                new ArrayList<Renderer>() {{
                    add(textLineRenderer);
                }};
        @SuppressWarnings("rawtypes") Collection<Renderer> renderersWithShader =
                new ArrayList<Renderer>() {{
                    add(textLineRenderer);
                }};

        stackRenderer.RenderAction = timestamp ->
                textLineRenderer.render(TextLineRenderable, timestamp);

        FakeFrameExecutor frameExecutor = new FakeFrameExecutor(stackRenderer, null);

        GraphicsCoreLoop graphicsCoreLoop = new GraphicsCoreLoopImpl("My title bar",
                new FakeGLFWMouseButtonCallback(), frameTimer, 20, windowResolutionManager,
                frameExecutor, new ShaderFactoryImpl(), renderersWithShader,
                SHADER_FILENAME_PREFIX, meshFactory, renderersWithMesh, MESH_DATA, MESH_DATA,
                graphicsPreloader);

        graphicsPreloader.LoadAction = () -> {
            TextLineRenderable.Font =
                    new FontImpl(fontDefinition, FLOAT_BOX_FACTORY, COORDINATE_FACTORY);
            frameTimer.ShouldExecuteNextFrame = true;
        };

        graphicsCoreLoop.startup(() -> closeAfterSomeTime(graphicsCoreLoop));
    }

    private static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.sleep(6000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }

    private static HashMap<Integer, ProviderAtTime<Color>> rainbowGradient(String lineText) {
        HashMap<Integer, ProviderAtTime<Color>> rainbowGradient = new HashMap<>();

        float degreePerLetter = 360f / lineText.length();
        for (int i = 0; i < lineText.length(); i++) {
            rainbowGradient.put(i,
                    new StaticProviderImpl<>(colorAtDegree((float)i * degreePerLetter)));
        }
        return rainbowGradient;
    }

    private static Color colorAtDegree(float degree) {
        float red = getColorComponent(0f, degree);
        float green = getColorComponent(120f, degree);
        float blue = getColorComponent(240f, degree);

        return new Color(red, green, blue, 1f);
    }

    private static float getColorComponent(float componentCenter, float degree) {
        float degreesInCircle = 360f;
        float halfOfCircle = 180f;
        float sixthOfCircle = 60f;
        float degreeModulo = degree % degreesInCircle;
        float distance = componentCenter - degreeModulo;
        if (distance < -halfOfCircle) {
            distance += degreesInCircle;
        }
        float absVal = Math.abs(distance);
        if (absVal <= sixthOfCircle) {
            return 1f;
        }
        absVal -= sixthOfCircle;
        float absValWithCeiling = Math.min(sixthOfCircle, absVal);
        float amountOfSixthOfCircle = sixthOfCircle - absValWithCeiling;
        float colorComponent = amountOfSixthOfCircle / sixthOfCircle;
        return colorComponent;
    }
}
