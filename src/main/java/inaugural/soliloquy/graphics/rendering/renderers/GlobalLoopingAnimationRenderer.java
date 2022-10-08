package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.graphics.assets.GlobalLoopingAnimation;
import soliloquy.specs.graphics.renderables.GlobalLoopingAnimationRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShiftStackAggregator;
import soliloquy.specs.graphics.renderables.colorshifting.NetColorShifts;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderableStack;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;

public class GlobalLoopingAnimationRenderer
        extends CanRenderSnippets<GlobalLoopingAnimationRenderable> {
    private final ColorShiftStackAggregator COLOR_SHIFT_STACK_AGGREGATOR;

    public GlobalLoopingAnimationRenderer(RenderingBoundaries renderingBoundaries,
                                          FloatBoxFactory floatBoxFactory,
                                          ColorShiftStackAggregator colorShiftStackAggregator,
                                          Long mostRecentTimestamp) {
        super(renderingBoundaries, floatBoxFactory, ARCHETYPE, mostRecentTimestamp);
        COLOR_SHIFT_STACK_AGGREGATOR = Check.ifNull(colorShiftStackAggregator,
                "colorShiftStackAggregator");
    }

    @Override
    public void render(GlobalLoopingAnimationRenderable globalLoopingAnimationRenderable,
                       long timestamp)
            throws IllegalArgumentException {
        Check.ifNull(globalLoopingAnimationRenderable, "globalLoopingAnimationRenderable");

        FloatBox renderingArea =
                Check.ifNull(globalLoopingAnimationRenderable.getRenderingDimensionsProvider(),
                        "globalLoopingAnimationRenderable.getRenderingDimensionsProvider()")
                        .provide(timestamp);

        validateRenderableWithDimensionsMembers(renderingArea,
                globalLoopingAnimationRenderable.colorShiftProviders(),
                globalLoopingAnimationRenderable.uuid(), "globalLoopingAnimationRenderable");

        Check.ifNull(globalLoopingAnimationRenderable.getGlobalLoopingAnimation(),
                "globalLoopingAnimationRenderable.getGlobalLoopingAnimation()");

        TIMESTAMP_VALIDATOR.validateTimestamp(this.getClass().getCanonicalName(), timestamp);

        NetColorShifts netColorShifts = netColorShifts(
                globalLoopingAnimationRenderable.colorShiftProviders(),
                COLOR_SHIFT_STACK_AGGREGATOR,
                timestamp);

        super.render(
                renderingArea,
                globalLoopingAnimationRenderable.getGlobalLoopingAnimation().provide(timestamp),
                INTACT_COLOR,
                netColorShifts
        );
    }

    private final static GlobalLoopingAnimationRenderable ARCHETYPE =
            new GlobalLoopingAnimationRenderable() {
                @Override
                public UUID uuid() {
                    return null;
                }

                @Override
                public int getZ() {
                    return 0;
                }

                @Override
                public void setZ(int i) {

                }

                @Override
                public RenderableStack containingStack() {
                    return null;
                }

                @Override
                public void delete() {

                }

                @Override
                public boolean getCapturesMouseEvents() {
                    return false;
                }

                @Override
                public void setCapturesMouseEvents(boolean b) throws IllegalArgumentException {

                }

                @Override
                public boolean capturesMouseEventAtPoint(float v, float v1, long l)
                        throws UnsupportedOperationException, IllegalArgumentException {
                    return false;
                }

                @Override
                public void press(int i, long l)
                        throws UnsupportedOperationException, IllegalArgumentException {

                }

                @Override
                public void setOnPress(int i, Action<Long> action) throws IllegalArgumentException {

                }

                @Override
                public Map<Integer, String> pressActionIds() {
                    return null;
                }

                @Override
                public void release(int i, long l)
                        throws UnsupportedOperationException, IllegalArgumentException {

                }

                @Override
                public void setOnRelease(int i, Action<Long> action)
                        throws IllegalArgumentException {

                }

                @Override
                public Map<Integer, String> releaseActionIds() {
                    return null;
                }

                @Override
                public void mouseOver(long l)
                        throws UnsupportedOperationException, IllegalArgumentException {

                }

                @Override
                public void setOnMouseOver(Action<Long> action) {

                }

                @Override
                public String mouseOverActionId() {
                    return null;
                }

                @Override
                public void mouseLeave(long l)
                        throws UnsupportedOperationException, IllegalArgumentException {

                }

                @Override
                public void setOnMouseLeave(Action<Long> action) {

                }

                @Override
                public String mouseLeaveActionId() {
                    return null;
                }

                @Override
                public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
                    return null;
                }

                @Override
                public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox> providerAtTime)
                        throws IllegalArgumentException {

                }

                @Override
                public ProviderAtTime<Float> getBorderThicknessProvider() {
                    return null;
                }

                @Override
                public void setBorderThicknessProvider(ProviderAtTime<Float> providerAtTime)
                        throws IllegalArgumentException {

                }

                @Override
                public ProviderAtTime<Color> getBorderColorProvider() {
                    return null;
                }

                @Override
                public void setBorderColorProvider(ProviderAtTime<Color> providerAtTime)
                        throws IllegalArgumentException {

                }

                @Override
                public List<ProviderAtTime<ColorShift>> colorShiftProviders() {
                    return null;
                }

                @Override
                public GlobalLoopingAnimation getGlobalLoopingAnimation() {
                    return null;
                }

                @Override
                public void setGlobalLoopingAnimation(GlobalLoopingAnimation globalLoopingAnimation)
                        throws IllegalArgumentException {

                }

                @Override
                public String getInterfaceName() {
                    return GlobalLoopingAnimationRenderable.class.getCanonicalName();
                }
            };
}