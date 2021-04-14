package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import inaugural.soliloquy.common.test.fakes.FakeList;
import inaugural.soliloquy.common.test.fakes.FakeMap;
import soliloquy.specs.common.infrastructure.List;
import soliloquy.specs.common.infrastructure.Map;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.rendering.RenderableStack;

public class FakeRenderableStack implements RenderableStack {
    public Map<Integer, List<Renderable>> RENDERABLES = new FakeMap<>();

    @Override
    public void clearContainedRenderables() {
        RENDERABLES.clear();
    }

    @Override
    public void add(Renderable renderable) throws IllegalArgumentException {
        if (!RENDERABLES.containsKey(renderable.z())) {
            List<Renderable> renderablesAtZ = new FakeList<>();
            renderablesAtZ.add(renderable);
            RENDERABLES.put(renderable.z(), renderablesAtZ);
            return;
        }

        RENDERABLES.get(renderable.z()).add(renderable);
    }

    @Override
    public Map<Integer, List<Renderable>> snapshot() {
        Map<Integer, List<Renderable>> snapshot = new FakeMap<>();

        RENDERABLES.forEach(snapshot::put);

        return snapshot;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}