package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.GlobalLoopingAnimationRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.providers.GlobalLoopingAnimation;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class FakeGlobalLoopingAnimationRenderable implements GlobalLoopingAnimationRenderable {
    public GlobalLoopingAnimation GlobalLoopingAnimation;
    public List<ColorShift> ColorShifts;
    public ProviderAtTime<FloatBox> RenderingDimensionsProvider;
    public EntityUuid Id;

    public FakeGlobalLoopingAnimationRenderable(GlobalLoopingAnimation globalLoopingAnimation,
                                                List<ColorShift> colorShifts,
                                                ProviderAtTime<FloatBox>
                                                        renderingDimensionsProvider,
                                                EntityUuid id) {
        GlobalLoopingAnimation = globalLoopingAnimation;
        ColorShifts = colorShifts;
        RenderingDimensionsProvider = renderingDimensionsProvider;
        Id = id;
    }

    @Override
    public GlobalLoopingAnimation getGlobalLoopingAnimation() {
        return GlobalLoopingAnimation;
    }

    @Override
    public void setGlobalLoopingAnimation(GlobalLoopingAnimation globalLoopingAnimation)
            throws IllegalArgumentException {
        GlobalLoopingAnimation = globalLoopingAnimation;
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
    public Map<Integer, String> pressActionIds() {
        return null;
    }

    @Override
    public void release(int i, long l) throws UnsupportedOperationException, IllegalArgumentException {

    }

    @Override
    public void setOnRelease(int i, Action<Long> action) throws IllegalArgumentException {

    }

    @Override
    public Map<Integer, String> releaseActionIds() {
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
        return ColorShifts;
    }

    @Override
    public ProviderAtTime<Float> getBorderThicknessProvider() {
        return null;
    }

    @Override
    public void setBorderThicknessProvider(ProviderAtTime<Float> providerAtTime) throws IllegalArgumentException {

    }

    @Override
    public ProviderAtTime<Color> getBorderColorProvider() {
        return null;
    }

    @Override
    public void setBorderColorProvider(ProviderAtTime<Color> providerAtTime) throws IllegalArgumentException {

    }

    @Override
    public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
        return RenderingDimensionsProvider;
    }

    @Override
    public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox>
                                                           renderingDimensionsProvider)
            throws IllegalArgumentException {

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
        return Id;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
