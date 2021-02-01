package inaugural.soliloquy.graphics.test.unit;

import inaugural.soliloquy.common.test.fakes.FakeCollectionFactory;
import inaugural.soliloquy.common.test.fakes.FakeMapFactory;
import inaugural.soliloquy.graphics.rendering.RenderableStackImpl;
import inaugural.soliloquy.graphics.test.fakes.FakeRenderable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.factories.CollectionFactory;
import soliloquy.specs.common.factories.MapFactory;
import soliloquy.specs.common.infrastructure.Collection;
import soliloquy.specs.common.infrastructure.ReadableCollection;
import soliloquy.specs.common.infrastructure.ReadableMap;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.rendering.RenderableStack;

import static org.junit.jupiter.api.Assertions.*;

class RenderableStackImplTests {
    private RenderableStack _renderableStack;

    @BeforeEach
    void setUp() {
        MapFactory mapFactory = new FakeMapFactory();
        CollectionFactory collectionFactory = new FakeCollectionFactory();

        _renderableStack = new RenderableStackImpl(mapFactory, collectionFactory);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> new RenderableStackImpl(null, new FakeCollectionFactory()));
        assertThrows(IllegalArgumentException.class,
                () -> new RenderableStackImpl(new FakeMapFactory(), null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RenderableStack.class.getCanonicalName(),
                _renderableStack.getInterfaceName());
    }

    @Test
    void testAddAndSnapshot() {
        Renderable renderable1 = new FakeRenderable(1);
        Renderable renderable2 = new FakeRenderable(2);
        Renderable renderable3 = new FakeRenderable(1);

        _renderableStack.add(renderable1);
        _renderableStack.add(renderable2);
        _renderableStack.add(renderable3);

        ReadableMap<Integer, ReadableCollection<Renderable>> snapshot =
                _renderableStack.snapshot();

        assertNotNull(snapshot);

        assertNotNull(snapshot.getFirstArchetype());
        assertNotNull(snapshot.getSecondArchetype());
        assertEquals(Collection.class.getCanonicalName() + "<" +
                Renderable.class.getCanonicalName() + ">",
                snapshot.getSecondArchetype().getInterfaceName());

        ReadableCollection<Renderable> zIndex1 = snapshot.get(1);
        assertNotNull(zIndex1.getArchetype());
        assertEquals(Renderable.class.getCanonicalName(),
                zIndex1.getArchetype().getInterfaceName());
        assertEquals(2, zIndex1.size());
        assertTrue(zIndex1.contains(renderable1));
        assertTrue(zIndex1.contains(renderable3));

        ReadableCollection<Renderable> zIndex2 = snapshot.get(2);
        assertNotNull(zIndex2.getArchetype());
        assertEquals(Renderable.class.getCanonicalName(),
                zIndex2.getArchetype().getInterfaceName());
        assertEquals(1, zIndex2.size());
        assertTrue(zIndex2.contains(renderable2));
    }

    @Test
    void testClear() {
        Renderable renderable = new FakeRenderable(0);

        _renderableStack.add(renderable);

        _renderableStack.clear();

        ReadableMap<Integer, ReadableCollection<Renderable>> snapshot =
                _renderableStack.snapshot();

        assertEquals(0, snapshot.size());
    }
}