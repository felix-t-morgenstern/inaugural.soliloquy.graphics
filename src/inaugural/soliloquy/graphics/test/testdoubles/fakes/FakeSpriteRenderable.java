package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.graphics.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;

public class FakeSpriteRenderable implements SpriteRenderable {
    public Sprite Sprite;
    public List<ColorShift> ColorShifts;
    public FloatBox RenderingArea;
    public Float BorderThickness;
    public Color BorderColor;
    public int Z;

    public FakeSpriteRenderable(Sprite sprite, List<ColorShift> colorShifts,
                                FloatBox renderingArea, Float borderThickness, Color borderColor) {
        Sprite = sprite;
        ColorShifts = colorShifts;
        RenderingArea = renderingArea;
        BorderThickness = borderThickness;
        BorderColor = borderColor;
    }

    public FakeSpriteRenderable(Sprite sprite, List<ColorShift> colorShifts,
                                FloatBox renderingArea, int z) {
        Sprite = sprite;
        ColorShifts = colorShifts;
        RenderingArea = renderingArea;
        Z = z;
    }

    @Override
    public Sprite sprite() {
        return Sprite;
    }

    @Override
    public Float borderThickness() {
        return BorderThickness;
    }

    @Override
    public Color borderColor() {
        return BorderColor;
    }

    @Override
    public boolean capturesMouseEvents() {
        return false;
    }

    @Override
    public void click() throws UnsupportedOperationException {

    }

    @Override
    public void mouseOver() throws UnsupportedOperationException {

    }

    @Override
    public void mouseLeave() throws UnsupportedOperationException {

    }

    @Override
    public List<ColorShift> colorShifts() {
        return ColorShifts;
    }

    @Override
    public FloatBox renderingArea() {
        return RenderingArea;
    }

    @Override
    public int z() {
        return Z;
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
