package inaugural.soliloquy.graphics.test.display.renderables.providers.looping.linear.location;

import inaugural.soliloquy.graphics.renderables.providers.LoopingLinearMovingVertexProvider;
import inaugural.soliloquy.graphics.rendering.renderers.TextLineRendererImpl;
import inaugural.soliloquy.graphics.test.display.rendering.renderers.textlinerenderer.TextLineRendererTest;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeStaticProvider;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeTextLineRenderable;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontDefinition;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontStyleDefinition;
import soliloquy.specs.graphics.renderables.providers.ResettableProvider;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoopingLinearMovingLocationProviderTest extends TextLineRendererTest {
    private final static String LINE_TEXT = "Wheee!";

    protected static FakeTextLineRenderable TextLineRenderable;

    protected static ResettableProvider<Vertex> LoopingLinearMovingLocationProvider;

    /** @noinspection rawtypes */
    protected static List<Renderer> generateRenderablesAndRenderersWithMeshAndShader(
            WindowResolutionManager windowResolutionManager) {
        FontStyleDefinition plain = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_TRAJAN,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_TRAJAN);
        FontStyleDefinition italic = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_TRAJAN,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_TRAJAN);
        FontStyleDefinition bold = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_TRAJAN,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_TRAJAN);
        FontStyleDefinition boldItalic = new FontStyleDefinition(
                ADDITIONAL_GLYPH_HORIZONTAL_TEXTURE_SPACING_TRAJAN,
                GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING,
                GLYPHWISE_ADDITIONAL_LEFT_BOUNDARY_SHIFT,
                ADDITIONAL_GLYPH_VERTICAL_TEXTURE_SPACING_TRAJAN);
        FontDefinition = new FontDefinition("id", RELATIVE_LOCATION_TRAJAN,
                MAX_LOSSLESS_FONT_SIZE_TRAJAN, LEADING_ADJUSTMENT,
                plain, italic, bold, boldItalic);

        long startTimestamp = GLOBAL_CLOCK.globalTimestamp();
        int periodDuration = 4000;
        int periodModuloOffset = periodDuration - (int) (startTimestamp % (periodDuration));
        HashMap<Integer, Vertex> valuesAtTimes =
                new HashMap<Integer, Vertex>() {{
                    put(0, Vertex.of(0.125f, 0.125f));
                    put(1000, Vertex.of(0.75f, 0.125f));
                    put(2000, Vertex.of(0.75f, 0.75f));
                    put(3000, Vertex.of(0.125f, 0.75f));
                }};

        LoopingLinearMovingLocationProvider = new LoopingLinearMovingVertexProvider(
                UUID,
                valuesAtTimes,
                periodDuration,
                periodModuloOffset,
                null,
                null
        );

        TextLineRenderable = new FakeTextLineRenderable(null,
                new FakeStaticProvider<>(0.05f), 0f, LINE_TEXT,
                new FakeStaticProvider<>(null), new FakeStaticProvider<>(null), null,
                null, null,
                LoopingLinearMovingLocationProvider,
                java.util.UUID.randomUUID());

        TextLineRenderer =
                new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY, Color.WHITE,
                        windowResolutionManager, null);

        return new ArrayList<Renderer>() {{
            add(TextLineRenderer);
        }};
    }
}