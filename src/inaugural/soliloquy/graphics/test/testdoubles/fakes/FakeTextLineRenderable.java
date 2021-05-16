package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Font;
import soliloquy.specs.graphics.renderables.TextLineRenderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class FakeTextLineRenderable implements TextLineRenderable {
    public Font Font;
    public float LineHeight;
    public String LineText;
    public float PaddingBetweenGlyphs;
    public Map<Integer, ProviderAtTime<Color>> ColorProviderIndices;
    public List<Integer> ItalicIndices;
    public List<Integer> BoldIndices;
    public ProviderAtTime<FloatBox> RenderingAreaProvider;
    public EntityUuid Uuid;

    public FakeTextLineRenderable(Font font, float lineHeight, float paddingBetweenGlyphs,
                                  String lineText,
                                  Map<Integer, ProviderAtTime<Color>> colorProviderIndices,
                                  List<Integer> italicIndices, List<Integer> boldIndices,
                                  EntityUuid id) {
        Font = font;
        LineHeight = lineHeight;
        PaddingBetweenGlyphs = paddingBetweenGlyphs;
        LineText = lineText;
        ColorProviderIndices = colorProviderIndices;
        ItalicIndices = italicIndices;
        BoldIndices = boldIndices;
        Uuid = id;
    }

    public FakeTextLineRenderable(Font font, float lineHeight, float paddingBetweenGlyphs,
                                  String lineText,
                                  Map<Integer, ProviderAtTime<Color>> colorProviderIndices,
                                  List<Integer> italicIndices, List<Integer> boldIndices,
                                  ProviderAtTime<FloatBox> renderingAreaProvider,
                                  EntityUuid uuid) {
        this(font, lineHeight, paddingBetweenGlyphs, lineText, colorProviderIndices, italicIndices,
                boldIndices, uuid);
        RenderingAreaProvider = renderingAreaProvider;
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
    public float paddingBetweenGlyphs() {
        return PaddingBetweenGlyphs;
    }

    @Override
    public String lineText() {
        return LineText;
    }

    @Override
    public Map<Integer, ProviderAtTime<Color>> colorProviderIndices() {
        return ColorProviderIndices;
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
    public ProviderAtTime<FloatBox> renderingAreaProvider() {
        return RenderingAreaProvider;
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
    public EntityUuid uuid() {
        return Uuid;
    }
}
