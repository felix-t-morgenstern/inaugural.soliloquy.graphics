package inaugural.soliloquy.graphics.test.display.renderables.providers.finite.linear.floatf;

import inaugural.soliloquy.graphics.bootstrap.assetfactories.ImageFactoryImpl;
import inaugural.soliloquy.graphics.renderables.providers.FiniteLinearMovingFloatProvider;
import inaugural.soliloquy.graphics.test.display.renderables.providers.finite.linear.FiniteLinearMovingProviderTest;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.ImageDefinition;
import soliloquy.specs.graphics.renderables.SpriteRenderable;

import java.awt.*;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

class FiniteLinearMovingFloatProviderTest extends FiniteLinearMovingProviderTest {
    protected static final Color BORDER_COLOR = Color.getHSBColor(0.75f, 1f, 1f);
    protected static final Float BORDER_THICKNESS = 0.025f;

    protected static void graphicsPreloaderLoadAction() {
        Sprite.Image = new ImageFactoryImpl(0.5f)
                .make(new ImageDefinition(RPG_WEAPONS_RELATIVE_LOCATION, false));
        var timestamp = GLOBAL_CLOCK.globalTimestamp();
        var borderThicknessProvider = mapOf(
                Pair.of(timestamp, 0f),
                Pair.of(timestamp + 3000, BORDER_THICKNESS)
        );
        SpriteRenderable.setBorderThicknessProvider(
                new FiniteLinearMovingFloatProvider(
                        java.util.UUID.randomUUID(),
                        borderThicknessProvider,
                        null, null
                ));

        FirstChildStack.add(SpriteRenderable);
        Renderers.registerRenderer(SpriteRenderable.class.getCanonicalName(), SpriteRenderer);

        FrameTimer.ShouldExecuteNextFrame = true;
    }
}
