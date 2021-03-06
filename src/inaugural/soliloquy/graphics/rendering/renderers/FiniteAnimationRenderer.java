package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.AnimationFrameSnippet;
import soliloquy.specs.graphics.assets.Image;
import soliloquy.specs.graphics.renderables.FiniteAnimationRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShiftStackAggregator;
import soliloquy.specs.graphics.renderables.colorshifting.NetColorShifts;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;

public class FiniteAnimationRenderer
        extends CanRenderSnippets<FiniteAnimationRenderable>
        implements Renderer<FiniteAnimationRenderable> {
    private final ColorShiftStackAggregator COLOR_SHIFT_STACK_AGGREGATOR;

    public FiniteAnimationRenderer(RenderingBoundaries renderingBoundaries,
                                   FloatBoxFactory floatBoxFactory,
                                   ColorShiftStackAggregator colorShiftStackAggregator) {
        super(renderingBoundaries, floatBoxFactory, ARCHETYPE);
        COLOR_SHIFT_STACK_AGGREGATOR = Check.ifNull(colorShiftStackAggregator,
                "colorShiftStackAggregator");
    }

    @Override
    public void render(FiniteAnimationRenderable finiteAnimationRenderable, long timestamp)
            throws IllegalArgumentException {
        Check.ifNull(finiteAnimationRenderable, "finiteAnimationRenderable");

        FloatBox renderingArea = Check.ifNull(
                finiteAnimationRenderable.getRenderingDimensionsProvider(),
                "finiteAnimationRenderable.getRenderingDimensionsProvider()")
                .provide(timestamp);

        validateRenderableWithAreaMembers(renderingArea, finiteAnimationRenderable.colorShifts(),
                finiteAnimationRenderable.uuid(), "finiteAnimationRenderable");

        TIMESTAMP_VALIDATOR.validateTimestamp(this.getClass().getCanonicalName(), timestamp);

        if (timestamp < finiteAnimationRenderable.startTimestamp()) {
            return;
        }

        if (timestamp >= finiteAnimationRenderable.endTimestamp()) {
            finiteAnimationRenderable.delete();
            return;
        }

        NetColorShifts netColorShifts = COLOR_SHIFT_STACK_AGGREGATOR.aggregate(
                finiteAnimationRenderable.colorShifts(), timestamp);

        AnimationFrameSnippet snippet =  finiteAnimationRenderable.provide(timestamp);

        super.render(
                renderingArea,
                snippet,
                INTACT_COLOR,
                netColorShifts.netColorRotationShift());
    }

    private static final FiniteAnimationRenderable ARCHETYPE = new FiniteAnimationRenderable() {
        @Override
        public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
            return null;
        }

        @Override
        public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox> providerAtTime) throws IllegalArgumentException {

        }

        @Override
        public AnimationFrameSnippet getArchetype() {
            return new AnimationFrameSnippet() {
                @Override
                public float offsetX() {
                    return 0;
                }

                @Override
                public float offsetY() {
                    return 0;
                }

                @Override
                public Image image() {
                    return null;
                }

                @Override
                public int leftX() {
                    return 0;
                }

                @Override
                public int topY() {
                    return 0;
                }

                @Override
                public int rightX() {
                    return 0;
                }

                @Override
                public int bottomY() {
                    return 0;
                }

                @Override
                public String getInterfaceName() {
                    return AnimationFrameSnippet.class.getCanonicalName();
                }
            };
        }

        @Override
        public AnimationFrameSnippet provide(long l) throws IllegalArgumentException {
            return null;
        }

        @Override
        public void reportPause(long l) throws IllegalArgumentException {

        }

        @Override
        public void reportUnpause(long l) throws IllegalArgumentException {

        }

        @Override
        public Long pausedTimestamp() {
            return null;
        }

        @Override
        public EntityUuid uuid() {
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
            return null;
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
        public long startTimestamp() {
            return 0;
        }

        @Override
        public long endTimestamp() {
            return 0;
        }

        @Override
        public String getInterfaceName() {
            return FiniteAnimationRenderable.class.getCanonicalName();
        }
    };
}
