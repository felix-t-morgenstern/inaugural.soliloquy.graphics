package inaugural.soliloquy.graphics.renderables;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.RenderableWithDimensions;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.util.function.Consumer;

public abstract class AbstractRenderableWithDimensions extends AbstractRenderable
        implements RenderableWithDimensions {
    private ProviderAtTime<FloatBox> _renderingAreaProvider;

    public AbstractRenderableWithDimensions(ProviderAtTime<FloatBox> renderingDimensionsProvider,
                                            int z, EntityUuid uuid,
                                            Consumer<Renderable> updateZIndexInContainer,
                                            Consumer<Renderable> removeFromContainer) {
        super(z, uuid, updateZIndexInContainer, removeFromContainer);
        setRenderingDimensionsProvider(renderingDimensionsProvider);
    }

    @Override
    public ProviderAtTime<FloatBox> getRenderingDimensionsProvider() {
        return _renderingAreaProvider;
    }

    @Override
    public void setRenderingDimensionsProvider(ProviderAtTime<FloatBox>
                                                           renderingDimensionsProvider)
            throws IllegalArgumentException {
        _renderingAreaProvider = Check.ifNull(renderingDimensionsProvider,
                "renderingDimensionsProvider");
    }
}
