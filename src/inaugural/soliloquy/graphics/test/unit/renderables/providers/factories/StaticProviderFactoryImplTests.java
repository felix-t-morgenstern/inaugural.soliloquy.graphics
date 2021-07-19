package inaugural.soliloquy.graphics.test.unit.renderables.providers.factories;

import inaugural.soliloquy.graphics.renderables.providers.StaticProviderImpl;
import inaugural.soliloquy.graphics.renderables.providers.factories.StaticProviderFactoryImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeEntityUuid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.renderables.providers.factories.StaticProviderFactory;

import static org.junit.jupiter.api.Assertions.*;

class StaticProviderFactoryImplTests {
    private final FakeEntityUuid ID = new FakeEntityUuid();
    private final Object VALUE = new Object();
    private final Object ARCHETYPE = new Object();

    private StaticProviderFactory _staticProviderFactory;

    @BeforeEach
    void setUp() {
        _staticProviderFactory = new StaticProviderFactoryImpl();
    }

    @Test
    void testMake() {
        ProviderAtTime<Object> staticProvider = _staticProviderFactory.make(ID, VALUE, ARCHETYPE);

        assertNotNull(staticProvider);
        assertSame(ID, staticProvider.uuid());
        assertSame(VALUE, staticProvider.provide(0L));
        assertSame(ARCHETYPE, staticProvider.getArchetype());
        assertTrue(staticProvider instanceof StaticProviderImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class,
                () -> _staticProviderFactory.make(null, VALUE, ARCHETYPE));
        assertThrows(IllegalArgumentException.class,
                () -> _staticProviderFactory.make(ID, VALUE, null));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(StaticProviderFactory.class.getCanonicalName(),
                _staticProviderFactory.getInterfaceName());
    }
}
