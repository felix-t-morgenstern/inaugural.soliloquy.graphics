package inaugural.soliloquy.graphics.renderables.factories;

import inaugural.soliloquy.graphics.renderables.RectangleRenderableImpl;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.graphics.renderables.RectangleRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.factories.RectangleRenderableFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class RectangleRenderableFactoryImpl implements RectangleRenderableFactory {
    @Override
    public RectangleRenderable make(ProviderAtTime<Color> topLeftColorProvider,
                                    ProviderAtTime<Color> topRightColorProvider,
                                    ProviderAtTime<Color> bottomRightColorProvider,
                                    ProviderAtTime<Color> bottomLeftColorProvider,
                                    ProviderAtTime<Integer> backgroundTextureIdProvider,
                                    float backgroundTextureTileWidth,
                                    float backgroundTextureTileHeight,
                                    Map<Integer, Action<Long>> onPress,
                                    Map<Integer, Action<Long>> onRelease,
                                    Action<Long> onMouseOver,
                                    Action<Long> onMouseLeave,
                                    ProviderAtTime<FloatBox> renderingAreaProvider,
                                    int z,
                                    UUID uuid,
                                    Consumer<Renderable> updateZIndexInContainer,
                                    Consumer<Renderable> removeFromContainer)
            throws IllegalArgumentException {
        return new RectangleRenderableImpl(topLeftColorProvider, topRightColorProvider,
                bottomRightColorProvider, bottomLeftColorProvider, backgroundTextureIdProvider,
                backgroundTextureTileWidth, backgroundTextureTileHeight, onPress, onRelease,
                onMouseOver, onMouseLeave, renderingAreaProvider, z, uuid,
                updateZIndexInContainer, removeFromContainer);
    }

    @Override
    public String getInterfaceName() {
        return RectangleRenderableFactory.class.getCanonicalName();
    }
}
