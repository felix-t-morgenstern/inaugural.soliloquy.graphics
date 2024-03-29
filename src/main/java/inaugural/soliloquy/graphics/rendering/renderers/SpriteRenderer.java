package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShiftStackAggregator;
import soliloquy.specs.graphics.renderables.colorshifting.NetColorShifts;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.awt.*;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;
import static inaugural.soliloquy.tools.generic.Archetypes.generateSimpleArchetype;

public class SpriteRenderer extends CanRenderSnippets<SpriteRenderable> {
    private final ColorShiftStackAggregator COLOR_SHIFT_STACK_AGGREGATOR;

    public SpriteRenderer(RenderingBoundaries renderingBoundaries,
                          FloatBoxFactory floatBoxFactory,
                          WindowResolutionManager windowResolutionManager,
                          ColorShiftStackAggregator colorShiftStackAggregator,
                          Long mostRecentTimestamp) {
        super(renderingBoundaries, floatBoxFactory, generateSimpleArchetype(SpriteRenderable.class),
                windowResolutionManager, mostRecentTimestamp);
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

        validateRenderableWithDimensionsMembers(renderingArea,
                spriteRenderable.colorShiftProviders(),
                spriteRenderable.uuid(), "spriteRenderable");

        if (borderThickness != null) {
            if (borderColor == null) {
                throw new IllegalArgumentException("SpriteRenderable.render: spriteRenderable " +
                        "cannot have non-null thickness, and null color");
            }

            Check.throwOnLtValue(borderThickness, 0f, "spriteRenderable borderThickness");

            Check.throwOnGtValue(borderThickness, 1f, "spriteRenderable borderThickness");

            float yThickness = borderThickness;
            float xThickness = yThickness / _getScreenWidthToHeightRatio.get();

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

        NetColorShifts netColorShifts = netColorShifts(spriteRenderable.colorShiftProviders(),
                COLOR_SHIFT_STACK_AGGREGATOR, timestamp);

        super.render(
                renderingArea,
                spriteRenderable.getSprite(),
                INTACT_COLOR,
                netColorShifts
        );
    }
}
