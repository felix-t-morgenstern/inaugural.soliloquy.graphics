package inaugural.soliloquy.graphics.renderables.factories;

import inaugural.soliloquy.graphics.renderables.FiniteAnimationRenderableImpl;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Animation;
import soliloquy.specs.graphics.renderables.FiniteAnimationRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.factories.FiniteAnimationRenderableFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FiniteAnimationRenderableFactoryImpl implements FiniteAnimationRenderableFactory {
    @Override
    public FiniteAnimationRenderable make(Animation animation,
                                          ProviderAtTime<Float> borderThicknessProvider,
                                          ProviderAtTime<Color> borderColorProvider,
                                          List<ProviderAtTime<ColorShift>> colorShiftProviders,
                                          ProviderAtTime<FloatBox> renderingAreaProvider, int z,
                                          EntityUuid uuid,
                                          Consumer<Renderable> updateZIndexInContainer,
                                          Consumer<Renderable> removeFromContainer,
                                          long startTimestamp, Long pausedTimestamp,
                                          Long mostRecentTimestamp)
            throws IllegalArgumentException {
        return new FiniteAnimationRenderableImpl(animation, borderThicknessProvider,
                borderColorProvider, colorShiftProviders, renderingAreaProvider, z, uuid,
                updateZIndexInContainer, removeFromContainer, startTimestamp, pausedTimestamp,
                mostRecentTimestamp);
    }

    @Override
    public FiniteAnimationRenderable make(Animation animation,
                                          ProviderAtTime<Float> borderThicknessProvider,
                                          ProviderAtTime<Color> borderColorProvider,
                                          Map<Integer, Action<Long>> onPress,
                                          Map<Integer, Action<Long>> onRelease,
                                          Action<Long> onMouseOver, Action<Long> onMouseLeave,
                                          List<ProviderAtTime<ColorShift>> colorShiftProviders,
                                          ProviderAtTime<FloatBox> renderingAreaProvider,
                                          int z, EntityUuid uuid,
                                          Consumer<Renderable> updateZIndexInContainer,
                                          Consumer<Renderable> removeFromContainer,
                                          long startTimestamp, Long pausedTimestamp,
                                          Long mostRecentTimestamp)
            throws IllegalArgumentException {
        return new FiniteAnimationRenderableImpl(animation, borderThicknessProvider,
                borderColorProvider, onPress, onRelease, onMouseOver, onMouseLeave,
                colorShiftProviders, renderingAreaProvider, z, uuid, updateZIndexInContainer,
                removeFromContainer, startTimestamp, pausedTimestamp, mostRecentTimestamp);
    }

    @Override
    public String getInterfaceName() {
        return FiniteAnimationRenderableFactory.class.getCanonicalName();
    }
}