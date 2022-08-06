package inaugural.soliloquy.graphics.test.unit.renderables.providers.factories;

import inaugural.soliloquy.graphics.renderables.providers.StaticMouseCursorProviderImpl;
import inaugural.soliloquy.graphics.renderables.providers.factories.StaticMouseCursorPreloaderFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.StaticMouseCursorProviderDefinition;
import soliloquy.specs.graphics.renderables.providers.StaticMouseCursorProvider;
import soliloquy.specs.graphics.renderables.providers.factories.StaticMouseCursorProviderFactory;

import static inaugural.soliloquy.tools.random.Random.randomLong;
import static inaugural.soliloquy.tools.random.Random.randomString;
import static org.junit.jupiter.api.Assertions.*;

class StaticMouseCursorPreloaderFactoryImplTests {
    private final String ID = randomString();
    private final Long PROVIDED_VALUE = randomLong();

    private StaticMouseCursorProviderFactory _staticMouseCursorProviderFactory;

    @BeforeEach
    void setUp() {
        _staticMouseCursorProviderFactory = new StaticMouseCursorPreloaderFactoryImpl();
    }

    @Test
    void make() {
        StaticMouseCursorProvider staticMouseCursorProvider = _staticMouseCursorProviderFactory
                .make(new StaticMouseCursorProviderDefinition(ID, PROVIDED_VALUE));

        assertNotNull(staticMouseCursorProvider);
        assertTrue(staticMouseCursorProvider instanceof StaticMouseCursorProviderImpl);
        assertEquals(ID, staticMouseCursorProvider.id());
        assertEquals(PROVIDED_VALUE, staticMouseCursorProvider.provide(randomLong()));
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _staticMouseCursorProviderFactory
                .make(new StaticMouseCursorProviderDefinition(null, PROVIDED_VALUE)));
        assertThrows(IllegalArgumentException.class, () -> _staticMouseCursorProviderFactory
                .make(new StaticMouseCursorProviderDefinition("", PROVIDED_VALUE)));
    }
}
