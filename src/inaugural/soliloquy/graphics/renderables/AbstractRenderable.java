package inaugural.soliloquy.graphics.renderables;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.util.function.Consumer;

abstract class AbstractRenderable implements Renderable {
    private final Consumer<Renderable> UPDATE_Z_INDEX_IN_CONTAINER;
    private final Consumer<Renderable> REMOVE_FROM_CONTAINER;
    private final EntityUuid UUID;

    private int _z;

    public AbstractRenderable(int z, EntityUuid uuid, Consumer<Renderable> updateZIndexInContainer,
                              Consumer<Renderable> removeFromContainer) {
        _z = z;
        UUID = Check.ifNull(uuid, "uuid");
        UPDATE_Z_INDEX_IN_CONTAINER = Check.ifNull(updateZIndexInContainer,
                "updateZIndexInContainer");
        REMOVE_FROM_CONTAINER = Check.ifNull(removeFromContainer, "removeFromContainer");
    }

    @Override
    public int getZ() {
        return _z;
    }

    @Override
    public void setZ(int z) {
        _z = z;
        UPDATE_Z_INDEX_IN_CONTAINER.accept(this);
    }

    // NB: deleted SpriteRenderables should _NOT_ make other calls unsupported, unlike
    //     TileEntities, since it might be deleted in the middle of rendering a frame which
    //     contains it, causing a breaking race condition.
    @Override
    public void delete() {
        REMOVE_FROM_CONTAINER.accept(this);
    }

    @Override
    public EntityUuid uuid() {
        return UUID;
    }
}
