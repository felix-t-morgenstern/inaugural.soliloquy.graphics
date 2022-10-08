package inaugural.soliloquy.graphics.test.display.rendering.renderers.textlinerenderer;

import inaugural.soliloquy.graphics.assets.FontImpl;
import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.rendering.renderers.TextLineRendererImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeStaticProvider;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeTextLineRenderable;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontDefinition;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontStyleDefinition;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a string of text, "Rainbow", aligned left, near the left edge
 * of the window, and in the vertical center of the window, for 4000ms. The message, "This
 * message is in the colors of the rainbow!", will be displayed, with each color having a
 * different color in order of the rainbow.
 * 2. The window will then close.
 */
class TextLineRendererColorTest extends TextLineRendererTest {
    private final static String LINE_TEXT = "Wow, this message is in the colors of the rainbow!";

    private static FakeTextLineRenderable TextLineRenderable;

    public static void main(String[] args) {
        runTest(
                TextLineRendererColorTest::generateRenderablesAndRenderersWithMeshAndShader,
                timestamp -> TextLineRenderer.render(TextLineRenderable, timestamp),
                () -> {
                    TextLineRenderable.Font =
                            new FontImpl(FontDefinition, FLOAT_BOX_FACTORY, COORDINATE_FACTORY);
                    FrameTimer.ShouldExecuteNextFrame = true;
                },
                TextLineRendererColorTest::closeAfterSomeTime
        );
    }

    /** @noinspection rawtypes */
    private static List<Renderer> generateRenderablesAndRenderersWithMeshAndShader(
            WindowResolutionManager windowResolutionManager) {
        GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT.put('j', 0.000625f);

        FontStyleDefinition plain = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_OSWALD,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_OSWALD);
        FontStyleDefinition italic = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_OSWALD,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_OSWALD);
        FontStyleDefinition bold = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_OSWALD,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_OSWALD);
        FontStyleDefinition boldItalic = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_OSWALD,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_OSWALD);
        FontDefinition = new FontDefinition("id", RELATIVE_LOCATION_OSWALD,
                MAX_LOSSLESS_FONT_SIZE_OSWALD, LEADING_ADJUSTMENT,
                plain, italic, bold, boldItalic);

        Vertex renderingLocation = Vertex.of(0.1f, 0.475f);

        HashMap<Integer, ProviderAtTime<Color>> colorIndices = rainbowGradient(LINE_TEXT);

        TextLineRenderable = new FakeTextLineRenderable(null,
                new FakeStaticProvider<>(0.05f), 0f, LINE_TEXT,
                new FakeStaticProvider<>(null), new FakeStaticProvider<>(null),
                colorIndices, null, null,
                new StaticProviderImpl<>(java.util.UUID.randomUUID(), renderingLocation, null),
                java.util.UUID.randomUUID());

        TextLineRenderer = new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY,
                INTACT_COLOR, windowResolutionManager, null);

        return new ArrayList<Renderer>() {{
            add(TextLineRenderer);
        }};
    }

    public static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.sleep(6000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }

    @SuppressWarnings("SameParameterValue")
    private static HashMap<Integer, ProviderAtTime<Color>> rainbowGradient(String lineText) {
        HashMap<Integer, ProviderAtTime<Color>> rainbowGradient = new HashMap<>();

        float degreePerLetter = 360f / lineText.length();
        for (int i = 0; i < lineText.length(); i++) {
            rainbowGradient.put(i,
                    new StaticProviderImpl<>(java.util.UUID.randomUUID(),
                            colorAtDegree((float) i * degreePerLetter), null));
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
        @SuppressWarnings("UnnecessaryLocalVariable")
        float colorComponent = amountOfSixthOfCircle / sixthOfCircle;
        return colorComponent;
    }
}