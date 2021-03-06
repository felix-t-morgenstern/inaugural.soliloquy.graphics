package inaugural.soliloquy.graphics.rendering.factories;

import inaugural.soliloquy.graphics.rendering.RenderableStackImpl;
import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.factories.ListFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.graphics.rendering.RenderableStack;
import soliloquy.specs.graphics.rendering.factories.RenderableStackFactory;

public class RenderableStackFactoryImpl implements RenderableStackFactory {
    @Override
    public RenderableStack make(MapFactory mapFactory, ListFactory listFactory)
            throws IllegalArgumentException {
        return new RenderableStackImpl(Check.ifNull(mapFactory, "mapFactory"),
                Check.ifNull(listFactory, "listFactory"));
    }

    @Override
    public String getInterfaceName() {
        return RenderableStackFactory.class.getCanonicalName();
    }
}
