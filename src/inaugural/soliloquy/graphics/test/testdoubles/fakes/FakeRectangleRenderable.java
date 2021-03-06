package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.RectangleRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;

public class FakeRectangleRenderable implements RectangleRenderable {
    public ProviderAtTime<Color> TopLeftColorProvider;
    public ProviderAtTime<Color> TopRightColorProvider;
    public ProviderAtTime<Color> BottomRightColorProvider;
    public ProviderAtTime<Color> BottomLeftColorProvider;
    public ProviderAtTime<Integer> BackgroundTextureIdProvider;
    public float BackgroundTextureTileWidth;
    public float BackgroundTextureTileHeight;
    public ProviderAtTime<Float> BorderThicknessProvider;
    public ProviderAtTime<Color> BorderColorProvider;
    public ProviderAtTime<FloatBox> RenderingDimensionsProvider;
    public EntityUuid Uuid;

    public FakeRectangleRenderable(ProviderAtTime<Color> topLeftColorProvider,
                                   ProviderAtTime<Color> topRightColorProvider,
                                   ProviderAtTime<Color> bottomRightColorProvider,
                                   ProviderAtTime<Color> bottomLeftColorProvider,
                                   ProviderAtTime<Integer> backgroundTextureIdProvider,
                                   float backgroundTextureTileWidth,
                                   float backgroundTextureTileHeight,
                                   ProviderAtTime<Float> borderThicknessProvider,
                                   ProviderAtTime<Color> borderColorProvider,
                                   ProviderAtTime<FloatBox> renderingDimensionsProvider,
                                   EntityUuid uuid) {
        TopLeftColorProvider = topLeftColorProvider;
        TopRightColorProvider = topRightColorProvider;
        BottomRightColorProvider = bottomRightColorProvider;
        BottomLeftColorProvider = bottomLeftColorProvider;
        BackgroundTextureIdProvider = backgroundTextureIdProvider;
        BackgroundTextureTileWidth = backgroundTextureTileWidth;
        BackgroundTextureTileHeight = backgroundTextureTileHeight;
        BorderThicknessProvider = borderThicknessProvider;
        BorderColorProvider = borderColorProvider;
        RenderingDimensionsProvider = renderingDimensionsProvider;
        Uuid = uuid;
    }

    @Override
    public ProviderAtTime<Color> getTopLeftColorProvider() {
        return TopLeftColorProvider;
    }

    @Override
    public void setTopLeftColorProvider(ProviderAtTime<Color> topLeftColorProvider)
            throws IllegalArgumentException {
        TopLeftColorProvider = topLeftColorProvider;
    }

    @Override
    public ProviderAtTime<Color> getTopRightColorProvider() {
        return TopRightColorProvider;
    }

    @Override
    public void setTopRightColorProvider(ProviderAtTime<Color> topRightColorProvider)
            throws IllegalArgumentException {
        TopRightColorProvider = topRightColorProvider;
    }

    @Override
    public ProviderAtTime<Color> getBottomRightColorProvider() {
        return BottomRightColorProvider;
    }

    @Override
    public void setBottomRightColorProvider(ProviderAtTime<Color> bottomRightColorProvider)
            throws IllegalArgumentException {
        BottomRightColorProvider = bottomRightColorProvider;
    }

    @Override
    public ProviderAtTime<Color> getBottomLeftColorProvider() {
        return BottomLeftColorProvider;
    }

    @Override
    public void setBottomLeftColorProvider(ProviderAtTime<Color> bottomLeftColorProvider)
            throws IllegalArgumentException {
        BottomLeftColorProvider = bottomLeftColorProvider;
    }

    @Override
    public ProviderAtTime<Integer> getBackgroundTextureIdProvider() {
        return BackgroundTextureIdProvider;
    }

    @Override
    public void setBackgroundTextureIdProvider(ProviderAtTime<Integer> backgroundTextureIdProvider)
            throws IllegalArgumentException {
        BackgroundTextureIdProvider = backgroundTextureIdProvider;
    }

    @Override
    public float getBackgroundTextureTileWidth() {
        return BackgroundTextureTileWidth;
    }

    @Override
    public void setBackgroundTextureTileWidth(float backgroundTextureTileWidth)
            throws IllegalArgumentException {
        BackgroundTextureTileWidth = backgroundTextureTileWidth;
    }

    @Override
    public float getBackgroundTextureTileHeight() {
        return BackgroundTextureTileHeight;
    }

    @Override
    public void setBackgroundTextureTileHeight(float backgroundTextureTileHeight)
            throws IllegalArgumentException {
        BackgroundTextureTileHeight = backgroundTextureTileHeight;
    }

    @Override
    public boolean getCapturesMouseEvents() {
        return false;
    }

    @Override
    public void setCapturesMouseEvents(boolean b) throws IllegalArgumentException {

    }

    @Override
    public void press(int i, long l) throws UnsupportedOperationException, IllegalArgumentException {

    }

    @Override
    public void setOnPress(int i, Action<Long> action) throws IllegalArgumentException {

    }

    @Override
    public java.util.Map<Integer, String> pressActionIds() {
        return null;
    }

    @Override
    public void release(int i, long l) throws UnsupportedOperationException, IllegalArgumentException {

    }

    @Override
    public void setOnRelease(int i, Action<Long> action) throws IllegalArgumentException {

    }

    @Override
    public java.util.Map<Integer, String> releaseActionIds() {
        return null;
    }

    @Override
    public void mouseOver(long l) throws UnsupportedOperationException, IllegalArgumentException {

    }

    @Override
    public String mouseOverActionId() {
        return null;
    }

    @Override
    public void mouseLeave(long l) throws UnsupportedOperationException, IllegalArgumentException {

    }

    @Override
    public String mouseLeaveActionId() {
        return null;
    }

    @Override
    public void setOnMouseOver(Action action) {

    }

    @Override
    public void setOnMouseLeave(Action action) {

    }

    @Override
    public List<ColorShift> colorShifts() {
        return null;
    }

    @Override
    public ProviderAtTime<Float> getBorderThicknessProvider() {
        return BorderThicknessProvider;
    }

    @Override
    public void setBorderThicknessProvider(ProviderAtTime<Float> borderThicknessProvider)
            throws IllegalArgumentException {
        BorderThicknessProvider = borderThicknessProvider;
    }

    @Override
    public ProviderAtTime<Color> getBorderColorProvider() {
        return BorderColorProvider;
    }

    @Override
    public void setBorderColorProvider(ProviderAtTime<Color> borderColorProvider)
            throws IllegalArgumentException {
        BorderColorProvider = borderColorProvider;
    }

    @Override
    public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
        return RenderingDimensionsProvider;
    }

    @Override
    public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox>
                                                           renderingDimensionsProvider)
            throws IllegalArgumentException {
        RenderingDimensionsProvider = renderingDimensionsProvider;
    }

    @Override
    public int getZ() {
        return 0;
    }

    @Override
    public void setZ(int i) {

    }

    @Override
    public void delete() {

    }

    @Override
    public EntityUuid uuid() {
        return Uuid;
    }

    @Override
    public void clearContainedRenderables() {

    }

    @Override
    public void add(Renderable renderable) throws IllegalArgumentException {

    }

    @Override
    public Map<Integer, soliloquy.specs.common.infrastructure.List<Renderable>> snapshot() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
