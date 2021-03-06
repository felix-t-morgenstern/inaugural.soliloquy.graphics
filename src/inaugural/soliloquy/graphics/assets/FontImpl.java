package inaugural.soliloquy.graphics.assets;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.CoordinateFactory;
import soliloquy.specs.common.valueobjects.Coordinate;
import soliloquy.specs.graphics.assets.Font;
import soliloquy.specs.graphics.assets.FontStyleInfo;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontDefinition;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.FontStyleDefinition;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.lwjgl.opengl.GL11.*;

public class FontImpl extends CanValidateFontDefinitions implements Font {
    private final static int ASCII_CHAR_SPACE = 32;
    private final static int ASCII_CHAR_DELETE = 127;
    private final static int NUMBER_EXTENDED_ASCII_CHARS = 256;
    private final static int RGBA_BYTES = 4;
    private final static int RED_OFFSET = 16;
    private final static int GREEN_OFFSET = 8;
    private final static int BLUE_OFFSET = 0;
    private final static int ALPHA_OFFSET = 24;

    // NB: This field is static, since the GPU's maximum texture dimension size is unlikely to
    //     change from one font to the next
    @SuppressWarnings("FieldCanBeLocal")
    private static int MAXIMUM_TEXTURE_DIMENSION_SIZE = -1;

    private final String ID;
    private final FontStyleInfoImpl PLAIN;
    private final FontStyleInfoImpl ITALIC;
    private final FontStyleInfoImpl BOLD;
    private final FontStyleInfoImpl BOLD_ITALIC;

    public FontImpl(FontDefinition fontDefinition,
                    FloatBoxFactory floatBoxFactory,
                    CoordinateFactory coordinateFactory) {
        validateFontDefinition(fontDefinition);
        Check.ifNull(floatBoxFactory, "floatBoxFactory");
        Check.ifNull(coordinateFactory, "coordinateFactory");

        if (MAXIMUM_TEXTURE_DIMENSION_SIZE < 0) {
            MAXIMUM_TEXTURE_DIMENSION_SIZE = glGetInteger(GL_MAX_TEXTURE_SIZE);
        }

        ID = fontDefinition.id();



        java.awt.Font fontFromFile = loadFontFromFile(fontDefinition.relativeLocation(),
                fontDefinition.maxLosslessFontSize());
        java.awt.Font fontFromFileItalic = fontFromFile.deriveFont(java.awt.Font.ITALIC);
        java.awt.Font fontFromFileBold = fontFromFile.deriveFont(java.awt.Font.BOLD);
        java.awt.Font fontFromFileBoldItalic = fontFromFile.deriveFont(
                java.awt.Font.ITALIC | java.awt.Font.BOLD);



        PLAIN = loadFontStyle(fontFromFile, fontDefinition.plain(),
                fontDefinition.leadingAdjustment(), floatBoxFactory, coordinateFactory);

        ITALIC = loadFontStyle(fontFromFileItalic, fontDefinition.italic(),
                fontDefinition.leadingAdjustment(), floatBoxFactory, coordinateFactory);

        BOLD = loadFontStyle(fontFromFileBold, fontDefinition.bold(),
                fontDefinition.leadingAdjustment(), floatBoxFactory, coordinateFactory);

        BOLD_ITALIC = loadFontStyle(fontFromFileBoldItalic, fontDefinition.boldItalic(),
                fontDefinition.leadingAdjustment(), floatBoxFactory, coordinateFactory);
    }

    private FontStyleInfoImpl loadFontStyle(java.awt.Font fontFromFile,
                                            FontStyleDefinition fontStyleDefinition,
                                            float leadingAdjustment,
                                            FloatBoxFactory floatBoxFactory,
                                            CoordinateFactory coordinateFactory) {
        Map<Character, FloatBox> glyphs = new HashMap<>();

        FontImageInfo textureInfo = generateFontAsset(
                fontFromFile,
                fontStyleDefinition.additionalGlyphHorizontalTextureSpacing(),
                fontStyleDefinition.glyphwiseAdditionalHorizontalTextureSpacing(),
                fontStyleDefinition.glyphwiseAdditionalLeftBoundaryShift(),
                fontStyleDefinition.additionalGlyphVerticalTextureSpacing(),
                leadingAdjustment,
                glyphs,
                floatBoxFactory,
                coordinateFactory);

        return new FontStyleInfoImpl(
                glyphs,
                textureInfo.ImageDimensions,
                textureInfo.ImageDimensions.getX() / (float)textureInfo.ImageDimensions.getY(),
                fontStyleDefinition.additionalGlyphHorizontalTextureSpacing(),
                fontStyleDefinition.glyphwiseAdditionalHorizontalTextureSpacing(),
                textureInfo.TextureId
        );
    }

    private static java.awt.Font loadFontFromFile(String relativeLocation,
                                                  float maxLosslessFontSize) {
        try {
            return java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT,
                    new File(relativeLocation)).deriveFont(maxLosslessFontSize);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static FontImageInfo generateFontAsset(java.awt.Font font,
                                                   float additionalGlyphHorizontalTextureSpacing,
                                                   Map<Character, Float>
                                                           glyphwiseAdditionalHorizontalTextureSpacing,
                                                   Map<Character, Float>
                                                           glyphwiseAdditionalLeftBoundaryShift,
                                                   float additionalGlyphVerticalTextureSpacing,
                                                   float leadingAdjustment,
                                                   Map<Character, FloatBox> glyphs,
                                                   FloatBoxFactory floatBoxFactory,
                                                   CoordinateFactory coordinateFactory) {
        GraphicsConfiguration graphicsConfiguration =
                GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
                        .getDefaultConfiguration();

        Graphics2D graphics2d = graphicsConfiguration.createCompatibleImage(1, 1,
                Transparency.TRANSLUCENT).createGraphics();

        graphics2d.setFont(font);

        FontMetrics fontMetrics = graphics2d.getFontMetrics();

        FontImageInfo fontImageInfo = loopOverCharacters(fontMetrics,
                additionalGlyphHorizontalTextureSpacing,
                glyphwiseAdditionalHorizontalTextureSpacing, glyphwiseAdditionalLeftBoundaryShift,
                additionalGlyphVerticalTextureSpacing, leadingAdjustment, null, coordinateFactory);

        BufferedImage bufferedImage = graphics2d.getDeviceConfiguration()
                .createCompatibleImage(fontImageInfo.ImageDimensions.getX(),
                        fontImageInfo.ImageDimensions.getY(),
                        Transparency.TRANSLUCENT);

        int textureId = glGenTextures();

        ByteBuffer generatedImage = generateImage(bufferedImage, font, fontMetrics,
                fontImageInfo.ImageDimensions.getX(), fontImageInfo.ImageDimensions.getY(),
                additionalGlyphHorizontalTextureSpacing,
                glyphwiseAdditionalHorizontalTextureSpacing, glyphwiseAdditionalLeftBoundaryShift,
                additionalGlyphVerticalTextureSpacing, leadingAdjustment,
                fontImageInfo.GlyphHeight, fontImageInfo.GlyphDescent, glyphs, floatBoxFactory,
                coordinateFactory);

        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureId);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, fontImageInfo.ImageDimensions.getX(),
                fontImageInfo.ImageDimensions.getY(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
                generatedImage);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        fontImageInfo.TextureId = textureId;

        return fontImageInfo;
    }

    private static FontImageInfo loopOverCharacters(
            FontMetrics fontMetrics, float additionalGlyphHorizontalTextureSpacing,
            Map<Character, Float> glyphwiseAdditionalHorizontalTextureSpacing,
            Map<Character, Float> glyphwiseAdditionalLeftBoundaryShift,
            float additionalGlyphVerticalTextureSpacing, float leadingAdjustment,
            Function<Character, Function<Integer, Function<Integer, Function<Float, Function<Float,
                    Consumer<Float>>>>>> glyphFunction,
            CoordinateFactory coordinateFactory) {
        int widthThusFar = 0;
        int rowNumber = 0;
        float leading = fontMetrics.getLeading() + (leadingAdjustment * fontMetrics.getHeight());
        float glyphHeight = fontMetrics.getHeight() - leading;
        float glyphDescent = fontMetrics.getMaxDescent();

        float charLeftShift;
        float nextCharLeftShift = 0f;

        for (int i = ASCII_CHAR_SPACE; i < NUMBER_EXTENDED_ASCII_CHARS; i++) {
            if (i == ASCII_CHAR_DELETE) {
                continue;
            }

            char character = (char)i;
            char nextCharacter = (char)(i + 1);

            charLeftShift = nextCharLeftShift;
            if (glyphwiseAdditionalLeftBoundaryShift.containsKey(nextCharacter) &&
                    glyphwiseAdditionalLeftBoundaryShift.get(nextCharacter) != null) {
                nextCharLeftShift = glyphwiseAdditionalLeftBoundaryShift.get(nextCharacter);
            }
            else {
                nextCharLeftShift = 0f;
            }

            float glyphWidth = fontMetrics.charWidth(character);
            if (glyphwiseAdditionalHorizontalTextureSpacing != null &&
                    glyphwiseAdditionalHorizontalTextureSpacing.containsKey(character)) {
                glyphWidth +=
                        (glyphHeight * glyphwiseAdditionalHorizontalTextureSpacing.get(character));
            }

            float glyphWidthWithTextureSpacing = glyphWidth +
                    (glyphHeight * additionalGlyphHorizontalTextureSpacing);

            if (widthThusFar + glyphWidthWithTextureSpacing > MAXIMUM_TEXTURE_DIMENSION_SIZE) {
                widthThusFar = 0;
                rowNumber++;
            }

            if (glyphFunction != null) {
                glyphFunction.apply(character).apply(widthThusFar).apply(rowNumber)
                        .apply(glyphWidthWithTextureSpacing).apply(charLeftShift)
                        .accept(nextCharLeftShift);
            }

            // NB: The 0.5f factor is to ensure that the casting rounds up, so no glyph pixels
            //     overlap
            widthThusFar += glyphWidthWithTextureSpacing + 0.5f;
        }
        // NB: The 0.5f factor is to ensure that the casting rounds up, so no glyph pixels are lost
        int imageHeight = (int)
                ((glyphHeight * (1f + additionalGlyphVerticalTextureSpacing) * (rowNumber + 1))
                        - (glyphHeight * additionalGlyphVerticalTextureSpacing)
                        + 0.5f);



        return new FontImageInfo(coordinateFactory.make(
                rowNumber > 0 ? MAXIMUM_TEXTURE_DIMENSION_SIZE : widthThusFar,
                imageHeight),
                glyphHeight,
                glyphDescent);
    }

    private static ByteBuffer generateImage(BufferedImage bufferedImage, java.awt.Font font,
                                            FontMetrics fontMetrics,
                                            int imageWidth, int imageHeight,
                                            float additionalGlyphHorizontalTextureSpacing,
                                            Map<Character, Float>
                                                    glyphwiseAdditionalHorizontalTextureSpacing,
                                            Map<Character, Float>
                                                    glyphwiseAdditionalLeftBoundaryShift,
                                            float additionalGlyphVerticalTextureSpacing,
                                            float leadingAdjustment,
                                            float glyphHeight,
                                            float glyphDescent,
                                            Map<Character, FloatBox> glyphs,
                                            FloatBoxFactory floatBoxFactory,
                                            CoordinateFactory coordinateFactory) {
        Graphics2D graphics2d = (Graphics2D)bufferedImage.getGraphics();
        graphics2d.setFont(font);
        graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawCharacters(graphics2d, fontMetrics, imageWidth, imageHeight,
                additionalGlyphHorizontalTextureSpacing,
                glyphwiseAdditionalHorizontalTextureSpacing, glyphwiseAdditionalLeftBoundaryShift,
                additionalGlyphVerticalTextureSpacing, leadingAdjustment, glyphHeight, glyphDescent,
                glyphs, floatBoxFactory, coordinateFactory);

        return createBuffer(bufferedImage, imageWidth, imageHeight);
    }

    private static void drawCharacters(Graphics2D graphics2d, FontMetrics fontMetrics,
                                       int imageWidth, int imageHeight,
                                       float additionalGlyphHorizontalTextureSpacing,
                                       Map<Character, Float>
                                               glyphwiseAdditionalHorizontalTextureSpacing,
                                       Map<Character, Float> glyphwiseAdditionalLeftBoundaryShift,
                                       float additionalGlyphVerticalTextureSpacing,
                                       float leadingAdjustment,
                                       float glyphHeight,
                                       float glyphDescent,
                                       Map<Character, FloatBox> glyphs,
                                       FloatBoxFactory floatBoxFactory,
                                       CoordinateFactory coordinateFactory) {
        float imageWidthFloat = (float)imageWidth;
        float imageHeightFloat = (float)imageHeight;
        float rowHeightInclTextureSpacing =
                (glyphHeight * (1f + additionalGlyphVerticalTextureSpacing));
        float rowTextureSpacing = (additionalGlyphVerticalTextureSpacing * glyphHeight);
        float glyphHeightInImage = glyphHeight / imageHeightFloat;
        loopOverCharacters(fontMetrics, additionalGlyphHorizontalTextureSpacing,
                glyphwiseAdditionalHorizontalTextureSpacing, glyphwiseAdditionalLeftBoundaryShift,
                additionalGlyphVerticalTextureSpacing, leadingAdjustment,
                character -> widthThusFar -> rowNumber -> glyphWidth -> charLeftShift ->
                        nextCharLeftShift -> {
                    float leftX = (widthThusFar / imageWidthFloat)
                            - (charLeftShift * glyphHeightInImage);
                    float topY = (rowHeightInclTextureSpacing * rowNumber) / imageHeightFloat;
                    float rightX = (glyphWidth / imageWidthFloat)
                            - (nextCharLeftShift * glyphHeightInImage) + leftX;
                    float bottomY = topY + glyphHeightInImage;
                    glyphs.put(character, floatBoxFactory.make(leftX, topY, rightX, bottomY));

                    float glyphDrawTopY = (rowHeightInclTextureSpacing * (rowNumber + 1))
                            - glyphDescent - rowTextureSpacing;
                    graphics2d.drawString(String.valueOf(character), widthThusFar,
                            glyphDrawTopY);
                }, coordinateFactory);
    }

    private static class FontImageInfo {
        Coordinate ImageDimensions;
        int TextureId;
        float GlyphHeight;
        float GlyphDescent;

        private FontImageInfo(Coordinate imageDimensions, float glyphHeight, float glyphDescent) {
            ImageDimensions = imageDimensions;
            GlyphHeight = glyphHeight;
            GlyphDescent = glyphDescent;
        }
    }

    private static ByteBuffer createBuffer(BufferedImage bufferedImage, int imageWidth,
                                           int imageHeight) {
        int[] pixels = new int[imageWidth * imageHeight];

        bufferedImage.getRGB(0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
        ByteBuffer byteBuffer =
                ByteBuffer.allocateDirect(imageWidth * imageHeight * RGBA_BYTES);

        for (int pixel : pixels) {
            byteBuffer.put((byte) ((pixel >> RED_OFFSET) & 0xFF));
            byteBuffer.put((byte) ((pixel >> GREEN_OFFSET) & 0xFF));
            byteBuffer.put((byte) ((pixel >> BLUE_OFFSET) & 0xFF));
            byteBuffer.put((byte) ((pixel >> ALPHA_OFFSET) & 0xFF));
        }

        byteBuffer.flip();

        return byteBuffer;
    }

    private static class FontStyleInfoImpl implements FontStyleInfo {
        private final Map<Character, FloatBox> GLYPHS;
        private final Coordinate TEXTURE_DIMENSIONS;
        private final float TEXTURE_WIDTH_TO_HEIGHT_RATIO;
        private final float ADDITIONAL_HORIZONTAL_TEXTURE_SPACING;
        private final Map<Character, Float> GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING;
        private final int TEXTURE_ID;

        private FontStyleInfoImpl(Map<Character, FloatBox> glyphs,
                                  Coordinate textureDimensions,
                                  float textureWidthToHeightRatio,
                                  float additionalHorizontalTextureSpacing,
                                  Map<Character, Float>
                                          glyphwiseAdditionalHorizontalTextureSpacing,
                                  int textureId) {
            GLYPHS = glyphs;
            TEXTURE_DIMENSIONS = textureDimensions;
            TEXTURE_WIDTH_TO_HEIGHT_RATIO = textureWidthToHeightRatio;
            ADDITIONAL_HORIZONTAL_TEXTURE_SPACING = additionalHorizontalTextureSpacing;
            GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING =
                    glyphwiseAdditionalHorizontalTextureSpacing;
            TEXTURE_ID = textureId;
        }

        @Override
        public FloatBox getUvCoordinatesForGlyph(char glyph) throws IllegalArgumentException {
            Check.throwOnLtValue(glyph, ASCII_CHAR_SPACE, "glyph");
            Check.throwOnEqualsValue(glyph, ASCII_CHAR_DELETE, "glyph");
            Check.throwOnGteValue(glyph, NUMBER_EXTENDED_ASCII_CHARS, "glyph");
            return GLYPHS.get(glyph);
        }

        @Override
        public Coordinate textureDimensions() {
            return TEXTURE_DIMENSIONS;
        }

        @Override
        public float textureWidthToHeightRatio() {
            return TEXTURE_WIDTH_TO_HEIGHT_RATIO;
        }

        @Override
        public float additionalHorizontalTextureSpacing() {
            return ADDITIONAL_HORIZONTAL_TEXTURE_SPACING;
        }

        @Override
        public Map<Character, Float> glyphwiseAdditionalHorizontalTextureSpacing() {
            return GLYPHWISE_ADDITIONAL_HORIZONTAL_TEXTURE_SPACING;
        }

        @Override
        public int textureId() {
            return TEXTURE_ID;
        }

        // TODO: Test and implement
        @Override
        public String getInterfaceName() {
            return null;
        }
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public FontStyleInfo plain() {
        return PLAIN;
    }

    @Override
    public FontStyleInfo italic() {
        return ITALIC;
    }

    @Override
    public FontStyleInfo bold() {
        return BOLD;
    }

    @Override
    public FontStyleInfo boldItalic() {
        return BOLD_ITALIC;
    }

    @Override
    public String getInterfaceName() {
        return Font.class.getCanonicalName();
    }
}
