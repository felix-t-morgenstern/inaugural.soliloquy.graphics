package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShiftStackAggregator;
import soliloquy.specs.graphics.renderables.colorshifting.NetColorShifts;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;

public class SpriteRenderer extends CanRenderSnippets<SpriteRenderable> {
    private final ColorShiftStackAggregator COLOR_SHIFT_STACK_AGGREGATOR;

    public SpriteRenderer(RenderingBoundaries renderingBoundaries,
                          FloatBoxFactory floatBoxFactory,
                          WindowResolutionManager windowResolutionManager,
                          ColorShiftStackAggregator colorShiftStackAggregator) {
        super(renderingBoundaries, floatBoxFactory, ARCHETYPE, windowResolutionManager);
        COLOR_SHIFT_STACK_AGGREGATOR = Check.ifNull(colorShiftStackAggregator,
                "colorShiftStackAggregator");
    }

    @Override
    public void render(SpriteRenderable spriteRenderable, long timestamp)
            throws IllegalArgumentException {
        Check.ifNull(spriteRenderable, "spriteRenderable");
        Check.ifNull(spriteRenderable.getSprite(), "spriteRenderable.getSprite()");

        TIMESTAMP_VALIDATOR.validateTimestamp(this.getClass().getCanonicalName(), timestamp);
        
        // TODO: Throw if rendering area or border thickness or color providers are null
        
        Float borderThickness = Check.ifNull(spriteRenderable.getBorderThicknessProvider(),
                "spriteRenderable.getBorderThicknessProvider()")
                .provide(timestamp);
        Color borderColor = Check.ifNull(spriteRenderable.getBorderColorProvider(),
                "spriteRenderable.getBorderColorProvider()")
                .provide(timestamp);
        FloatBox renderingArea = Check.ifNull(spriteRenderable.getRenderingDimensionsProvider(),
                "spriteRenderable.getRenderingDimensionsProvider()")
                .provide(timestamp);

        validateRenderableWithAreaMembers(renderingArea, spriteRenderable.colorShifts(),
                spriteRenderable.uuid(), "spriteRenderable");

        if (borderThickness != null) {
            if (borderColor == null) {
                throw new IllegalArgumentException("SpriteRenderable.render: spriteRenderable " +
                        "cannot have non-null thickness, and null color");
            }

            Check.throwOnLtValue(borderThickness, 0f, "spriteRenderable borderThickness");

            Check.throwOnGtValue(borderThickness, 1f, "spriteRenderable borderThickness");

            float yThickness = borderThickness;
            float xThickness = yThickness / _screenWidthToHeightRatio;

            // upper-left
            super.render(renderingArea.translate(-xThickness, -yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // upper-center
            super.render(renderingArea.translate(0f, -yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // upper-right
            super.render(renderingArea.translate(xThickness, -yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // center-right
            super.render(renderingArea.translate(xThickness, 0),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // bottom-right
            super.render(renderingArea.translate(xThickness, yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // bottom-center
            super.render(renderingArea.translate(0f, yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // bottom-left
            super.render(renderingArea.translate(-xThickness, yThickness),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
            // center-left
            super.render(renderingArea.translate(-xThickness, 0f),
                    spriteRenderable.getSprite(),
                    INTACT_COLOR,
                    borderColor);
        }

        NetColorShifts netColorShifts =
                COLOR_SHIFT_STACK_AGGREGATOR.aggregate(spriteRenderable.colorShifts(), timestamp);

        super.render(renderingArea,
                spriteRenderable.getSprite(),
                INTACT_COLOR,
                netColorShifts.netColorRotationShift());
    }

    private final static SpriteRenderable ARCHETYPE = new SpriteRenderable() {
        @Override
        public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
            return null;
        }

        @Override
        public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox> providerAtTime) throws IllegalArgumentException {

        }

        @Override
        public Sprite getSprite() {
            return null;
        }

        @Override
        public void setSprite(Sprite sprite) throws IllegalArgumentException {

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
        public String getInterfaceName() {
            return SpriteRenderable.class.getCanonicalName();
        }
    };
}
