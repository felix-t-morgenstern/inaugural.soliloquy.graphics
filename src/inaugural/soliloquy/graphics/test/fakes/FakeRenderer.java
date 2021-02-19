package inaugural.soliloquy.graphics.test.fakes;

import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.rendering.RendererType;

import java.util.ArrayList;
import java.util.List;

public class FakeRenderer implements RendererType<Renderable> {
    public List<Renderable> RENDERED = new ArrayList<>();

    @Override
    public void render(Renderable renderable) throws IllegalArgumentException {
        RENDERED.add(renderable);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}