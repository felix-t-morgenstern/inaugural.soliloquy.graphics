package inaugural.soliloquy.graphics.test.fakes;

import soliloquy.specs.graphics.renderables.Renderable;

public class FakeRenderable implements Renderable {
    public int _z;

    public FakeRenderable(int z) {
        _z = z;
    }

    @Override
    public float xLoc() {
        return 0;
    }

    @Override
    public float yLoc() {
        return 0;
    }

    @Override
    public float width() {
        return 0;
    }

    @Override
    public float height() {
        return 0;
    }

    @Override
    public int z() {
        return _z;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
