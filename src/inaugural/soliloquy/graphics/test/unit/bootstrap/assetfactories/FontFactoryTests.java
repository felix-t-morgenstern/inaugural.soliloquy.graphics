package inaugural.soliloquy.graphics.test.unit.bootstrap.assetfactories;

import inaugural.soliloquy.common.test.fakes.FakeCoordinateFactory;
import inaugural.soliloquy.graphics.assets.FontImpl;
import inaugural.soliloquy.graphics.bootstrap.assetfactories.FontFactory;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.graphics.assets.Font;
import soliloquy.specs.graphics.bootstrap.assetfactories.AssetFactory;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontDefinition;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;

class FontFactoryTests {
    private final String ID = "FontId";
    private final String RELATIVE_LOCATION = "./res/fonts/Trajan Pro Regular.ttf";
    private final float MAX_LOSSLESS_FONT_SIZE = 12.3f;
    private final float ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN = 0.123f;
    private final float ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC = 0.234f;
    private final float ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD = 0.345f;
    private final float ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC = 0.456f;
    private final float ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN = 0.567f;
    private final float ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC = 0.678f;
    private final float ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD = 0.789f;
    private final float ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC = 0.890f;
    private final float LEADING_ADJUSTMENT = 0.090f;
    private final FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();
    private final FakeCoordinateFactory COORDINATE_FACTORY = new FakeCoordinateFactory();

    private AssetFactory<FontDefinition, Font> _fontFactory;

    @BeforeAll
    static void setUpFixture() {
        if (!glfwInit()) {
            throw new RuntimeException("GLFW failed to initialize");
        }

        long window = glfwCreateWindow(1, 1, "", 0, 0);
        glfwMakeContextCurrent(window);
        createCapabilities();
    }

    @AfterAll
    static void tearDownFixture() {
        glfwTerminate();
    }

    @BeforeEach
    void setUp() {
        _fontFactory = new FontFactory(FLOAT_BOX_FACTORY, COORDINATE_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new FontFactory(null, COORDINATE_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new FontFactory(FLOAT_BOX_FACTORY, null));
    }

    @Test
    void testMake() {
        FakeFontDefinition fontDefinition = new FakeFontDefinition(
                ID,
                RELATIVE_LOCATION,
                MAX_LOSSLESS_FONT_SIZE,
                new FakeFontStyleDefinition(
                        ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                        null,
                        ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                ),
                new FakeFontStyleDefinition(
                        ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                        null,
                        ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                ),
                new FakeFontStyleDefinition(
                        ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                        null,
                        ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                ),
                new FakeFontStyleDefinition(
                        ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                        null,
                        ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                ),
                LEADING_ADJUSTMENT);

        Font createdFont = _fontFactory.make(fontDefinition);

        assertNotNull(createdFont);
        assertEquals(ID, createdFont.id());
        assertTrue(createdFont instanceof FontImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        null,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        "",
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        null,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        "",
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        0f,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        null,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                -0.0001f,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                -0.0001f
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                1f - LEADING_ADJUSTMENT
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        null,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                -0.0001f,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                -0.0001f
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                1f - LEADING_ADJUSTMENT
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        null,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                -0.0001f,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                -0.0001f
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                1f - LEADING_ADJUSTMENT
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        null,
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                -0.0001f,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                -0.0001f
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                1f - LEADING_ADJUSTMENT
                        ),
                        LEADING_ADJUSTMENT)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(
                        ID,
                        RELATIVE_LOCATION,
                        MAX_LOSSLESS_FONT_SIZE,
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_PLAIN,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_PLAIN
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_ITALIC
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD
                        ),
                        new FakeFontStyleDefinition(
                                ADDITIONAL_GLYPH_HORIZONTAL_PADDING_BOLD_ITALIC,
                                null,
                                ADDITIONAL_GLYPH_VERTICAL_PADDING_BOLD_ITALIC
                        ),
                        1f)));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(AssetFactory.class.getCanonicalName() + "<" +
                FontDefinition.class.getCanonicalName() + "," + Font.class.getCanonicalName() +
                ">",
                _fontFactory.getInterfaceName());
    }
}
