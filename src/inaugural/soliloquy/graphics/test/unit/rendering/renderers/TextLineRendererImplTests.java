package inaugural.soliloquy.graphics.test.unit.rendering.renderers;

import inaugural.soliloquy.common.test.fakes.FakePair;
import inaugural.soliloquy.graphics.rendering.renderers.TextLineRendererImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.Tools;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.graphics.renderables.TextJustification;
import soliloquy.specs.graphics.renderables.TextLineRenderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.renderers.Renderer;
import soliloquy.specs.graphics.rendering.renderers.TextLineRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TextLineRendererImplTests {
    private final FakeRenderingBoundaries RENDERING_BOUNDARIES = new FakeRenderingBoundaries();
    private final FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final Color DEFAULT_COLOR = Color.BLACK;
    private final FakeWindowResolutionManager WINDOW_RESOLUTION_MANAGER =
            new FakeWindowResolutionManager();

    private TextLineRenderer _textLineRenderer;

    @BeforeEach
    void setUp() {
        _textLineRenderer = new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY,
                DEFAULT_COLOR, WINDOW_RESOLUTION_MANAGER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new TextLineRendererImpl(null, FLOAT_BOX_FACTORY, DEFAULT_COLOR,
                        WINDOW_RESOLUTION_MANAGER));
        assertThrows(IllegalArgumentException.class,
                () -> new TextLineRendererImpl(RENDERING_BOUNDARIES, null, DEFAULT_COLOR,
                        WINDOW_RESOLUTION_MANAGER));
        assertThrows(IllegalArgumentException.class,
                () -> new TextLineRendererImpl(RENDERING_BOUNDARIES, FLOAT_BOX_FACTORY, null,
                        WINDOW_RESOLUTION_MANAGER));
    }

    @Test
    void testSetMeshWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _textLineRenderer.setMesh(null));
    }

    @Test
    void testSetShaderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _textLineRenderer.setShader(null));
    }

    @Test
    void testRenderWithInvalidParams() {
        FakeFont font = new FakeFont();
        float lineHeight = 0.25f;
        String textLine = "Text line";
        HashMap<Integer, ProviderAtTime<Color>> colorProviderIndices = new HashMap<>();
        colorProviderIndices.put(4, new FakeStaticProviderAtTime<>(Color.RED));
        ArrayList<Integer> italicIndices = new ArrayList<>();
        italicIndices.add(2);
        italicIndices.add(6);
        ArrayList<Integer> boldIndices = new ArrayList<>();
        boldIndices.add(3);
        boldIndices.add(5);
        ProviderAtTime<Pair<Float,Float>> renderingAreaProvider =
                new FakeStaticProviderAtTime<>(new FakePair<>(0f, 0f));
        FakeEntityUuid id = new FakeEntityUuid();
        FakeTextLineRenderable textLineRenderable = new FakeTextLineRenderable(font, lineHeight,
                0f, textLine, new FakeStaticProviderAtTime<>(1f),
                new FakeStaticProviderAtTime<>(null), colorProviderIndices, italicIndices,
                boldIndices, renderingAreaProvider, id);
        long timestamp = 0L;



        assertThrows(IllegalArgumentException.class, () -> _textLineRenderer.render(null, 0L));

        textLineRenderable.RenderingLocationProvider = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.RenderingLocationProvider = renderingAreaProvider;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Font = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.Font = font;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.LineHeight = 0f;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.LineHeight = 0.25f;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(null, new FakeStaticProviderAtTime<>(Color.BLUE));
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        colorProviderIndices.remove(null);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(6, null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        colorProviderIndices.remove(6);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(-1, new FakeStaticProviderAtTime<>(Color.BLUE));
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        colorProviderIndices.remove(-1);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(textLine.length(), null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        colorProviderIndices.remove(textLine.length());
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        //noinspection RedundantCast,SuspiciousMethodCalls
        italicIndices.remove((Object)(null));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(-1);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        italicIndices.remove((Object)(-1));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(2);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        italicIndices.remove(2);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(textLine.length());
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        italicIndices.remove((Object)(textLine.length()));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.ItalicIndices = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.ItalicIndices = italicIndices;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        //noinspection SuspiciousMethodCalls,RedundantCast
        boldIndices.remove((Object)(null));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(-1);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        boldIndices.remove((Object)(-1));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(textLine.length());
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        boldIndices.remove((Object)(textLine.length()));
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(3);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        boldIndices.remove(2);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.BoldIndices = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.BoldIndices = boldIndices;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.BorderColorProvider = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.BorderColorProvider = new FakeStaticProviderAtTime<>(null);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.BorderThicknessProvider = new FakeStaticProviderAtTime<>(-0.0001f);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.BorderThicknessProvider = new FakeStaticProviderAtTime<>(1f);
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Uuid = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.Uuid = id;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Justification = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.Justification = TextJustification.LEFT;
        try {
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Justification = TextJustification.UNKNOWN;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, 0L));
        textLineRenderable.Justification = TextJustification.LEFT;
        try {
            //noinspection UnusedAssignment
            _textLineRenderer.render(textLineRenderable, timestamp++);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}
    }

    @Test
    void testTextLineLengthWithInvalidParams() {
        FakeFont font = new FakeFont();
        float lineHeight = 0.25f;
        String textLine = "Text line";
        HashMap<Integer, ProviderAtTime<Color>> colorProviderIndices = new HashMap<>();
        colorProviderIndices.put(4, new FakeStaticProviderAtTime<>(Color.RED));
        ArrayList<Integer> italicIndices = new ArrayList<>();
        italicIndices.add(2);
        italicIndices.add(6);
        ArrayList<Integer> boldIndices = new ArrayList<>();
        boldIndices.add(3);
        boldIndices.add(5);
        ProviderAtTime<Pair<Float,Float>> renderingAreaProvider =
                new FakeStaticProviderAtTime<>(new FakePair<>(0f, 0f));
        FakeEntityUuid id = new FakeEntityUuid();
        FakeTextLineRenderable textLineRenderable = new FakeTextLineRenderable(font, lineHeight,
                0f, textLine, new FakeStaticProviderAtTime<>(1f),
                new FakeStaticProviderAtTime<>(null), colorProviderIndices, italicIndices,
                boldIndices, renderingAreaProvider, id);



        assertThrows(IllegalArgumentException.class, () -> _textLineRenderer.render(null, 0L));

        textLineRenderable.RenderingLocationProvider = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.RenderingLocationProvider = renderingAreaProvider;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Font = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.Font = font;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.LineHeight = 0f;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.LineHeight = 0.25f;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(null, new FakeStaticProviderAtTime<>(Color.BLUE));
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        colorProviderIndices.remove(null);
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(6, null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        colorProviderIndices.remove(6);
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(-1, new FakeStaticProviderAtTime<>(Color.BLUE));
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        colorProviderIndices.remove(-1);
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        colorProviderIndices.put(textLine.length(), null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        colorProviderIndices.remove(textLine.length());
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        //noinspection RedundantCast,SuspiciousMethodCalls
        italicIndices.remove((Object)(null));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(-1);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        italicIndices.remove((Object)(-1));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(2);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        italicIndices.remove(2);
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        italicIndices.add(textLine.length());
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        italicIndices.remove((Object)(textLine.length()));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.ItalicIndices = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.ItalicIndices = italicIndices;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(null);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        //noinspection SuspiciousMethodCalls,RedundantCast
        boldIndices.remove((Object)(null));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(-1);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        boldIndices.remove((Object)(-1));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(textLine.length());
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        boldIndices.remove((Object)(textLine.length()));
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        boldIndices.add(3);
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        boldIndices.remove(2);
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.BoldIndices = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.BoldIndices = boldIndices;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Uuid = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.Uuid = id;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Justification = null;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.Justification = TextJustification.LEFT;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}

        textLineRenderable.Justification = TextJustification.UNKNOWN;
        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.textLineLength(textLineRenderable));
        textLineRenderable.Justification = TextJustification.LEFT;
        try {
            _textLineRenderer.textLineLength(textLineRenderable);
        }
        catch (IllegalArgumentException e) {
            fail("Should not throw IllegalArgumentException when all params are ostensibly valid");
        }
        catch (Exception ignored) {}
    }

    @Test
    void testTextLineLength() {
        FakeFontStyleInfo plain = new FakeFontStyleInfo();
        FakeFontStyleInfo italic = new FakeFontStyleInfo();
        FakeFontStyleInfo bold = new FakeFontStyleInfo();
        FakeFontStyleInfo boldItalic = new FakeFontStyleInfo();
        FakeFont font = new FakeFont(plain, italic, bold, boldItalic);

        float glyphHeight = 0.1f;
        FakeFloatBox glyphA = new FakeFloatBox(0.0f, 0.0f, 0.356659007059121f, glyphHeight);
        FakeFloatBox glyphAItalic = new FakeFloatBox(0.0f, 0.0f, 0.48381785202459f, glyphHeight);
        FakeFloatBox glyphABold = new FakeFloatBox(0.0f, 0.0f, 0.677026478f, glyphHeight);
        FakeFloatBox glyphABoldItalic = new FakeFloatBox(0.0f, 0.0f, 0.24048836420184f,
                glyphHeight);
        FakeFloatBox glyphB = new FakeFloatBox(0.0f, 0.0f, 0.213723488507345f, glyphHeight);
        FakeFloatBox glyphBItalic = new FakeFloatBox(0.0f, 0.0f, 0.331731488913315f, glyphHeight);
        FakeFloatBox glyphBBold = new FakeFloatBox(0.0f, 0.0f, 0.709300081504505f, glyphHeight);
        FakeFloatBox glyphBBoldItalic = new FakeFloatBox(0.0f, 0.0f, 0.0767894524389122f,
                glyphHeight);

        plain.Glyphs.put('A', glyphA);
        italic.Glyphs.put('A', glyphAItalic);
        bold.Glyphs.put('A', glyphABold);
        boldItalic.Glyphs.put('A', glyphABoldItalic);
        plain.Glyphs.put('B', glyphB);
        italic.Glyphs.put('B', glyphBItalic);
        bold.Glyphs.put('B', glyphBBold);
        boldItalic.Glyphs.put('B', glyphBBoldItalic);

        float textureWidthToHeightRatio = 0.12f;
        float textureWidthToHeightRatioItalic = 0.34f;
        float textureWidthToHeightRatioBold = 0.56f;
        float textureWidthToHeightRatioBoldItalic = 0.78f;
        plain.TextureWidthToHeightRatio = textureWidthToHeightRatio;
        italic.TextureWidthToHeightRatio = textureWidthToHeightRatioItalic;
        bold.TextureWidthToHeightRatio = textureWidthToHeightRatioBold;
        boldItalic.TextureWidthToHeightRatio = textureWidthToHeightRatioBoldItalic;

        float lineHeight = 0.5f;
        @SuppressWarnings("SpellCheckingInspection") String lineText = "AAAAAAAABBBBBBBB";
        ArrayList<Integer> italicIndices = new ArrayList<Integer>(){{
            add(1);
            add(9);
        }};
        ArrayList<Integer> boldIndices = new ArrayList<Integer>(){{
            add(6);
            add(14);
        }};

        FakeTextLineRenderable textLineRenderable = new FakeTextLineRenderable(font, lineHeight,
                0f, lineText, new FakeStaticProviderAtTime<>(null),
                new FakeStaticProviderAtTime<>(null), null, italicIndices, boldIndices,
                new FakeStaticProviderAtTime<>(new FakePair<>(0f, 0f)),
                new FakeEntityUuid());

        float textLineLength = _textLineRenderer.textLineLength(textLineRenderable);

        float expectedTextLineLength = ((glyphA.width() * 1 * textureWidthToHeightRatio) +
                (glyphAItalic.width() * 5 * textureWidthToHeightRatioItalic) +
                (glyphABoldItalic.width() * 2 * textureWidthToHeightRatioBoldItalic) +
                (glyphBBoldItalic.width() * 1 * textureWidthToHeightRatioBoldItalic) +
                (glyphBBold.width() * 5 * textureWidthToHeightRatioBold) +
                (glyphB.width() * 2 * textureWidthToHeightRatio)) *
                (lineHeight / glyphHeight);

        // NB: Test is accurate to four significant digits; inaccuracy beyond that point is likely
        //     due to floating point rounding discrepancies
        assertEquals(Tools.round(expectedTextLineLength, 4), Tools.round(textLineLength, 4));
    }

    @Test
    void testTextLineLengthWithPaddingBetweenGlyphs() {
        FakeFontStyleInfo plain = new FakeFontStyleInfo();
        FakeFontStyleInfo italic = new FakeFontStyleInfo();
        FakeFontStyleInfo bold = new FakeFontStyleInfo();
        FakeFontStyleInfo boldItalic = new FakeFontStyleInfo();
        FakeFont font = new FakeFont(plain, italic, bold, boldItalic);

        float glyphHeight = 0.1f;
        FakeFloatBox glyphA = new FakeFloatBox(0.0f, 0.0f, 0.356659007059121f, glyphHeight);
        FakeFloatBox glyphAItalic = new FakeFloatBox(0.0f, 0.0f, 0.48381785202459f, glyphHeight);
        FakeFloatBox glyphABold = new FakeFloatBox(0.0f, 0.0f, 0.677026478f, glyphHeight);
        FakeFloatBox glyphABoldItalic = new FakeFloatBox(0.0f, 0.0f, 0.24048836420184f,
                glyphHeight);
        FakeFloatBox glyphB = new FakeFloatBox(0.0f, 0.0f, 0.213723488507345f, glyphHeight);
        FakeFloatBox glyphBItalic = new FakeFloatBox(0.0f, 0.0f, 0.331731488913315f, glyphHeight);
        FakeFloatBox glyphBBold = new FakeFloatBox(0.0f, 0.0f, 0.709300081504505f, glyphHeight);
        FakeFloatBox glyphBBoldItalic = new FakeFloatBox(0.0f, 0.0f, 0.0767894524389122f,
                glyphHeight);

        plain.Glyphs.put('A', glyphA);
        italic.Glyphs.put('A', glyphAItalic);
        bold.Glyphs.put('A', glyphABold);
        boldItalic.Glyphs.put('A', glyphABoldItalic);
        plain.Glyphs.put('B', glyphB);
        italic.Glyphs.put('B', glyphBItalic);
        bold.Glyphs.put('B', glyphBBold);
        boldItalic.Glyphs.put('B', glyphBBoldItalic);

        float textureWidthToHeightRatio = 0.12f;
        float textureWidthToHeightRatioItalic = 0.34f;
        float textureWidthToHeightRatioBold = 0.56f;
        float textureWidthToHeightRatioBoldItalic = 0.78f;
        plain.TextureWidthToHeightRatio = textureWidthToHeightRatio;
        italic.TextureWidthToHeightRatio = textureWidthToHeightRatioItalic;
        bold.TextureWidthToHeightRatio = textureWidthToHeightRatioBold;
        boldItalic.TextureWidthToHeightRatio = textureWidthToHeightRatioBoldItalic;

        float lineHeight = 0.5f;
        @SuppressWarnings("SpellCheckingInspection") String lineText = "AAAAAAAABBBBBBBB";
        ArrayList<Integer> italicIndices = new ArrayList<Integer>(){{
            add(1);
            add(9);
        }};
        ArrayList<Integer> boldIndices = new ArrayList<Integer>(){{
            add(6);
            add(14);
        }};

        float paddingBetweenGlyphs = 0.123f;

        FakeTextLineRenderable textLineRenderable = new FakeTextLineRenderable(font, lineHeight,
                paddingBetweenGlyphs, lineText, new FakeStaticProviderAtTime<>(null),
                new FakeStaticProviderAtTime<>(null), null, italicIndices, boldIndices,
                new FakeStaticProviderAtTime<>(new FakePair<>(0f, 0f)),
                new FakeEntityUuid());

        float textLineLength = _textLineRenderer.textLineLength(textLineRenderable);

        float expectedTextLineLength = ((glyphA.width() * 1 * textureWidthToHeightRatio) +
                (glyphAItalic.width() * 5 * textureWidthToHeightRatioItalic) +
                (glyphABoldItalic.width() * 2 * textureWidthToHeightRatioBoldItalic) +
                (glyphBBoldItalic.width() * 1 * textureWidthToHeightRatioBoldItalic) +
                (glyphBBold.width() * 5 * textureWidthToHeightRatioBold) +
                (glyphB.width() * 2 * textureWidthToHeightRatio)) *
                (lineHeight / glyphHeight) +
                (lineHeight * paddingBetweenGlyphs * (lineText.length() - 1));

        // NB: Test is accurate to four significant digits; inaccuracy beyond that point is likely
        //     due to floating point rounding discrepancies
        assertEquals(Tools.round(expectedTextLineLength, 4), Tools.round(textLineLength, 4));
    }

    @Test
    void testRenderOutdatedTimestamp() {
        FakeFont font = new FakeFont();
        float lineHeight = 0.5f;
        FakeTextLineRenderable textLineRenderable = new FakeTextLineRenderable(font, lineHeight,
                0f, "", new FakeStaticProviderAtTime<>(null), new FakeStaticProviderAtTime<>(null),
                null, new ArrayList<>(), new ArrayList<>(),
                new FakeStaticProviderAtTime<>(new FakePair<>(0f, 0f)),
                new FakeEntityUuid());
        long timestamp = 100L;
        _textLineRenderer.render(textLineRenderable, timestamp);

        assertThrows(IllegalArgumentException.class,
                () -> _textLineRenderer.render(textLineRenderable, timestamp - 1L));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Renderer.class.getCanonicalName() + "<" +
                        TextLineRenderable.class.getCanonicalName() + ">",
                _textLineRenderer.getInterfaceName());
    }
}
