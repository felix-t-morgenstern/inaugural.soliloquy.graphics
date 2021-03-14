package inaugural.soliloquy.graphics.test.fakes;

import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.Renderer;
import soliloquy.specs.graphics.rendering.Shader;

import java.util.ArrayList;
import java.util.List;

public class FakeRenderer implements Renderer<Renderable> {
    public Mesh Mesh;
    public Shader Shader;
    public List<Renderable> Rendered = new ArrayList<>();

    @Override
    public void setMesh(Mesh mesh) throws IllegalArgumentException {
        Mesh = mesh;
    }

    @Override
    public void setShader(Shader shader) throws IllegalArgumentException {
        Shader = shader;
    }

    @Override
    public void render(Renderable renderable) throws IllegalArgumentException {
        Rendered.add(renderable);
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
