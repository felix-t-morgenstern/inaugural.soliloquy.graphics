package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Font;
import soliloquy.specs.graphics.renderables.TextLineRenderable;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class FakeTextLineRenderable implements TextLineRenderable {
    public Font Font;
    public float LineHeight;
    public String LineText;
    public Map<Integer, Color> ColorIndices;
    public List<Integer> ItalicIndices;
    public List<Integer> BoldIndices;
    public FloatBox RenderingArea;

    public FakeTextLineRenderable(Font font, float lineHeight, String lineText,
                                  Map<Integer, Color> colorIndices, List<Integer> italicIndices,
                                  List<Integer> boldIndices) {
        Font = font;
        LineHeight = lineHeight;
        LineText = lineText;
        ColorIndices = colorIndices;
        ItalicIndices = italicIndices;
        BoldIndices = boldIndices;
    }

    public FakeTextLineRenderable(Font font, float lineHeight, String lineText,
                                  Map<Integer, Color> colorIndices, List<Integer> italicIndices,
                                  List<Integer> boldIndices, FloatBox renderingArea) {
        this(font, lineHeight, lineText, colorIndices, italicIndices, boldIndices);
        RenderingArea = renderingArea;
    }

    @Override
    public Font font() {
        return Font;
    }

    @Override
    public float lineHeight() {
        return LineHeight;
    }

    @Override
    public String lineText() {
        return LineText;
    }

    @Override
    public Map<Integer, Color> colorIndices() {
        return ColorIndices;
    }

    @Override
    public List<Integer> italicIndices() {
        return ItalicIndices;
    }

    @Override
    public List<Integer> boldIndices() {
        return BoldIndices;
    }

    @Override
    public FloatBox renderingArea() {
        return RenderingArea;
    }

    @Override
    public int z() {
        return 0;
    }

    @Override
    public void delete() {

    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public EntityUuid id() {
        return null;
    }
}