package inaugural.soliloquy.graphics.test.unit.bootstrap.assetfactories;

import inaugural.soliloquy.graphics.assets.FontImpl;
import inaugural.soliloquy.graphics.bootstrap.assetfactories.FontFactory;
import inaugural.soliloquy.graphics.test.fakes.FakeFloatBoxFactory;
import inaugural.soliloquy.graphics.test.fakes.FakeFontDefinition;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.graphics.assets.Font;
import soliloquy.specs.graphics.bootstrap.assetfactories.AssetFactory;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontDefinition;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL.createCapabilities;

class FontFactoryTests {
    private final String ID = "FontId";
    private final String RELATIVE_LOCATION = "./res/fonts/Trajan Pro Regular.ttf";
    private final float MAX_LOSSLESS_FONT_SIZE = 12.3f;
    private final float ADDITIONAL_GLYPH_PADDING = 0.123f;
    private final int IMAGE_WIDTH = 123;
    private final int IMAGE_HEIGHT = 2340;
    private final FakeFloatBoxFactory FLOAT_BOX_FACTORY = new FakeFloatBoxFactory();

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
        _fontFactory = new FontFactory(IMAGE_WIDTH, IMAGE_HEIGHT, FLOAT_BOX_FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new FontFactory(0, IMAGE_HEIGHT, FLOAT_BOX_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new FontFactory(IMAGE_WIDTH, 0, FLOAT_BOX_FACTORY));
        assertThrows(IllegalArgumentException.class,
                () -> new FontFactory(IMAGE_WIDTH, IMAGE_HEIGHT, null));
    }

    @Test
    void testMake() {
        FakeFontDefinition fontDefinition = new FakeFontDefinition(ID, RELATIVE_LOCATION,
                MAX_LOSSLESS_FONT_SIZE, ADDITIONAL_GLYPH_PADDING);

        Font createdFont = _fontFactory.make(fontDefinition);

        assertNotNull(createdFont);
        assertEquals(ID, createdFont.id());
        assertTrue(createdFont instanceof FontImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(null, RELATIVE_LOCATION, MAX_LOSSLESS_FONT_SIZE,
                        ADDITIONAL_GLYPH_PADDING)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition("", RELATIVE_LOCATION, MAX_LOSSLESS_FONT_SIZE,
                        ADDITIONAL_GLYPH_PADDING)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(ID, null, MAX_LOSSLESS_FONT_SIZE,
                        ADDITIONAL_GLYPH_PADDING)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(ID, "", MAX_LOSSLESS_FONT_SIZE,
                        ADDITIONAL_GLYPH_PADDING)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(ID, RELATIVE_LOCATION, 0,
                        ADDITIONAL_GLYPH_PADDING)));
        assertThrows(IllegalArgumentException.class, () -> _fontFactory.make(
                new FakeFontDefinition(ID, RELATIVE_LOCATION, MAX_LOSSLESS_FONT_SIZE,
                        -0.0001f)));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(AssetFactory.class.getCanonicalName() + "<" +
                FontDefinition.class.getCanonicalName() + "," + Font.class.getCanonicalName() +
                ">",
                _fontFactory.getInterfaceName());
    }
}
