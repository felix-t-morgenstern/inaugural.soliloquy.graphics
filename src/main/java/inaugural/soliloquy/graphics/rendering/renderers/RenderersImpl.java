package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.rendering.renderers.Renderer;
import soliloquy.specs.graphics.rendering.renderers.Renderers;

import java.util.Map;

import static inaugural.soliloquy.tools.collections.Collections.mapOf;

public class RenderersImpl implements Renderers {
    private final Map<String, Renderer<? extends Renderable>> RENDERERS;
    private final TimestampValidator TIMESTAMP_VALIDATOR;

    public RenderersImpl(TimestampValidator timestampValidator) {
        RENDERERS = mapOf();
        TIMESTAMP_VALIDATOR = Check.ifNull(timestampValidator, "timestampValidator");
    }

    @Override
    public <T extends Renderable> void registerRenderer(String renderableInterfaceName,
                                                        Renderer<T> renderer)
            throws IllegalArgumentException {
        RENDERERS.put(
                Check.ifNullOrEmpty(renderableInterfaceName, "renderableInterfaceName"),
                Check.ifNull(renderer, "renderer"));
    }

    @Override
    public <T extends Renderable> void render(T renderable, long timestamp)
            throws IllegalArgumentException {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        var renderableInterfaceName = renderable.getInterfaceName();
        //noinspection unchecked
        var renderer = (Renderer<T>) RENDERERS.get(renderableInterfaceName);
        if (renderer == null) {
            throw new IllegalArgumentException("RenderersImpl.render: renderable class (" +
                    renderableInterfaceName + ") is unregistered");
        }
        renderer.render(renderable, timestamp);
    }

    @Override
    public Long mostRecentTimestamp() {
        return TIMESTAMP_VALIDATOR.mostRecentTimestamp();
    }

    @Override
    public String getInterfaceName() {
        return Renderer.class.getCanonicalName();
    }
}
