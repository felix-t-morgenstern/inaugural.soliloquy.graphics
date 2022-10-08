package inaugural.soliloquy.graphics.test.display.renderables.providers.finite.linear.floatbox;

import inaugural.soliloquy.graphics.bootstrap.assetfactories.ImageFactoryImpl;
import inaugural.soliloquy.graphics.renderables.providers.FiniteLinearMovingFloatBoxProvider;
import inaugural.soliloquy.graphics.rendering.FloatBoxImpl;
import inaugural.soliloquy.graphics.test.display.renderables.providers.finite.linear.FiniteLinearMovingProviderTest;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.ImageDefinition;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.util.HashMap;

class FiniteLinearMovingFloatBoxProviderTest extends FiniteLinearMovingProviderTest {
    protected static void graphicsPreloaderLoadAction() {
        Sprite.Image = new ImageFactoryImpl(0.5f)
                .make(new ImageDefinition(RPG_WEAPONS_RELATIVE_LOCATION, false));
        long timestamp = GLOBAL_CLOCK.globalTimestamp();
        HashMap<Long, FloatBox> renderingDimensionsAtTimes = new HashMap<>();

        FloatBoxImpl startingLocation = new FloatBoxImpl(0f, 0f, 0.25f, 0.375f);
        FloatBoxImpl endingLocation = new FloatBoxImpl(0.25f, 0.125f, 0.75f, 0.875f);

        renderingDimensionsAtTimes.put(timestamp, startingLocation);
        renderingDimensionsAtTimes.put(timestamp + 3000, endingLocation);

        SpriteRenderable.RenderingDimensionsProvider = new FiniteLinearMovingFloatBoxProvider(
                FLOAT_BOX_FACTORY,
                java.util.UUID.randomUUID(),
                renderingDimensionsAtTimes,
                null,
                null
        );

        FrameTimer.ShouldExecuteNextFrame = true;
    }
}