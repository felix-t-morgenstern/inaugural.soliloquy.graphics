package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.Sprite;
import soliloquy.specs.graphics.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.util.List;

public class SpriteRenderer extends CanRenderSnippets<SpriteRenderable> {
    public SpriteRenderer(RenderingBoundaries renderingBoundaries,
                          FloatBoxFactory floatBoxFactory) {
        super(renderingBoundaries, floatBoxFactory, ARCHETYPE);
    }

    @Override
    public void render(SpriteRenderable spriteRenderable, long timestamp)
            throws IllegalArgumentException {
        validateRenderableWithArea(spriteRenderable, "spriteRenderable");

        Check.ifNull(spriteRenderable.sprite(), "spriteRenderable.sprite()");

        validateTimestamp(timestamp, "SpriteRenderer");

        super.render(spriteRenderable.renderingArea(),
                spriteRenderable.sprite(),
                1.0f, 1.0f, 1.0f, 1.0f);
    }

    private final static SpriteRenderable ARCHETYPE = new SpriteRenderable() {
        @Override
        public String getInterfaceName() {
            return SpriteRenderable.class.getCanonicalName();
        }

        @Override
        public EntityUuid id() {
            return null;
        }

        @Override
        public FloatBox renderingArea() {
            return null;
        }

        @Override
        public int z() {
            return 0;
        }

        @Override
        public void delete() {

        }

        @Override
        public boolean capturesMouseEvents() {
            return false;
        }

        @Override
        public void click() throws UnsupportedOperationException {

        }

        @Override
        public void mouseOver() throws UnsupportedOperationException {

        }

        @Override
        public void mouseLeave() throws UnsupportedOperationException {

        }

        @Override
        public List<ColorShift> colorShifts() {
            return null;
        }

        @Override
        public Sprite sprite() {
            return null;
        }
    };
}