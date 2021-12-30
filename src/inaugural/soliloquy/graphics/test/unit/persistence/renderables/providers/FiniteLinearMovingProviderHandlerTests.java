package inaugural.soliloquy.graphics.test.unit.persistence.renderables.providers;

import inaugural.soliloquy.graphics.persistence.renderables.providers.FiniteLinearMovingProviderHandler;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeEntityUuid;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeFiniteLinearMovingProvider;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeFiniteLinearMovingProviderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import soliloquy.specs.common.persistence.PersistentValuesHandler;
import soliloquy.specs.common.persistence.TypeHandler;
import soliloquy.specs.common.persistence.TypeWithOneGenericParamHandler;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.providers.FiniteLinearMovingProvider;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FiniteLinearMovingProviderHandlerTests {
    private static final FakeEntityUuid FINITE_LINEAR_MOVING_PROVIDER_INPUT_UUID =
            new FakeEntityUuid();
    private static final Long TIMESTAMP_1 = 123L;
    private static final Long TIMESTAMP_2 = 456L;
    private static final Long TIMESTAMP_3 = 789L;
    private static final Float VALUE_1 = 0.123f;
    private static final Float VALUE_2 = 0.456f;
    private static final Float VALUE_3 = 0.789f;
    private static final HashMap<Long, Float> VALUES_AT_TIMESTAMPS = new HashMap<Long, Float>() {{
        put(TIMESTAMP_1, VALUE_1);
        put(TIMESTAMP_2, VALUE_2);
        put(TIMESTAMP_3, VALUE_3);
    }};
    private static final Long PAUSED_TIMESTAMP = 123L;
    private static final Long MOST_RECENT_TIMESTAMP = 456L;
    private static final FakeFiniteLinearMovingProvider<Float> FINITE_LINEAR_MOVING_PROVIDER =
            new FakeFiniteLinearMovingProvider<>(FINITE_LINEAR_MOVING_PROVIDER_INPUT_UUID,
                    VALUES_AT_TIMESTAMPS, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP, -123123f);

    private static final FakeEntityUuid UUID_READ_OUTPUT = new FakeEntityUuid();
    private static final String UUID_WRITE_OUTPUT = "uuidWriteOutput";
    private static final float FLOAT_READ_OUTPUT = 0.1312f;
    private static final String FLOAT_WRITE_OUTPUT = "floatWriteOutput";

    private static final int INTEGER_ARCHETYPE = 1312;

    private static final FakeFiniteLinearMovingProviderFactory FACTORY =
            new FakeFiniteLinearMovingProviderFactory();

    private static final String WRITTEN_VALUE = "{\"uuid\":\"uuidWriteOutput\",\"valueType\":\"java.lang.Float\",\"values\":[{\"timestamp\":456,\"value\":\"floatWriteOutput\"},{\"timestamp\":123,\"value\":\"floatWriteOutput\"},{\"timestamp\":789,\"value\":\"floatWriteOutput\"}],\"pausedTimestamp\":123,\"mostRecentTimestamp\":456}";

    @Mock
    private PersistentValuesHandler _persistentValuesHandler;
    @Mock
    private TypeHandler<EntityUuid> _entityUuidHandler;
    @Mock
    private TypeHandler<Float> _floatHandler;

    @SuppressWarnings("rawtypes")
    private TypeWithOneGenericParamHandler<FiniteLinearMovingProvider>
            _finiteLinearMovingProviderHandler;

    @BeforeEach
    void setUp() {
        _persistentValuesHandler = Mockito.mock(PersistentValuesHandler.class);

        //noinspection unchecked
        _entityUuidHandler = Mockito.mock(TypeHandler.class);
        when(_entityUuidHandler.read(Mockito.anyString())).thenReturn(UUID_READ_OUTPUT);
        when(_entityUuidHandler.write(Mockito.any())).thenReturn(UUID_WRITE_OUTPUT);

        //noinspection unchecked
        _floatHandler = Mockito.mock(TypeHandler.class);
        when(_floatHandler.read(Mockito.anyString())).thenReturn(FLOAT_READ_OUTPUT);
        when(_floatHandler.write(Mockito.anyFloat())).thenReturn(FLOAT_WRITE_OUTPUT);

        //noinspection unchecked,rawtypes
        when(_persistentValuesHandler
                .getTypeHandler(EntityUuid.class.getCanonicalName()))
                .thenReturn((TypeHandler) _entityUuidHandler);
        //noinspection unchecked,rawtypes
        when(_persistentValuesHandler
                .getTypeHandler(Float.class.getCanonicalName()))
                .thenReturn((TypeHandler) _floatHandler);

        when(_persistentValuesHandler
                .generateArchetype(Integer.class.getCanonicalName()))
                .thenReturn(INTEGER_ARCHETYPE);

        _finiteLinearMovingProviderHandler = new FiniteLinearMovingProviderHandler(
                _entityUuidHandler, _persistentValuesHandler, FACTORY);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new FiniteLinearMovingProviderHandler(
                null, _persistentValuesHandler, FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new FiniteLinearMovingProviderHandler(
                _entityUuidHandler, null, FACTORY));
        assertThrows(IllegalArgumentException.class, () -> new FiniteLinearMovingProviderHandler(
                _entityUuidHandler, _persistentValuesHandler, null));
    }

    @Test
    void testWrite() {
        String writtenValue =
                _finiteLinearMovingProviderHandler.write(FINITE_LINEAR_MOVING_PROVIDER);

        assertEquals(WRITTEN_VALUE, writtenValue);
        verify(_entityUuidHandler).write(FINITE_LINEAR_MOVING_PROVIDER_INPUT_UUID);
        verify(_floatHandler).write(VALUE_1);
        verify(_floatHandler).write(VALUE_2);
        verify(_floatHandler).write(VALUE_3);
    }

    @Test
    void testRead() {
        //noinspection unchecked
        FiniteLinearMovingProvider<Float> finiteLinearMovingProvider =
                _finiteLinearMovingProviderHandler.read(WRITTEN_VALUE);

        assertNotNull(finiteLinearMovingProvider);
        assertOnlyContains(FACTORY.InputUuids, UUID_READ_OUTPUT);
        assertEquals(1, FACTORY.InputValuesAtTimestamps.size());
        assertEquals(3, FACTORY.InputValuesAtTimestamps.get(0).size());
        assertEquals(FLOAT_READ_OUTPUT, FACTORY.InputValuesAtTimestamps.get(0).get(TIMESTAMP_1));
        assertEquals(FLOAT_READ_OUTPUT, FACTORY.InputValuesAtTimestamps.get(0).get(TIMESTAMP_2));
        assertEquals(FLOAT_READ_OUTPUT, FACTORY.InputValuesAtTimestamps.get(0).get(TIMESTAMP_3));
        assertOnlyContains(FACTORY.InputPausedTimestamps, PAUSED_TIMESTAMP);
        assertOnlyContains(FACTORY.InputMostRecentTimestamps, MOST_RECENT_TIMESTAMP);
        assertOnlyContains(FACTORY.Outputs, finiteLinearMovingProvider);
        verify(_entityUuidHandler).read(UUID_WRITE_OUTPUT);
        verify(_persistentValuesHandler).getTypeHandler(Float.class.getCanonicalName());
        verify(_floatHandler, times(3)).read(FLOAT_WRITE_OUTPUT);
    }

    @Test
    void testGenerateArchetype() {
        //noinspection unchecked
        FiniteLinearMovingProvider<Integer> generatedArchetype =
                (FiniteLinearMovingProvider<Integer>) _finiteLinearMovingProviderHandler
                        .generateArchetype(Integer.class.getCanonicalName());

        assertNotNull(generatedArchetype);
        assertNotNull(generatedArchetype.getArchetype());
    }

    @Test
    void testGenerateArchetypeWithInvalidInputs() {
        assertThrows(IllegalArgumentException.class, () ->
                _finiteLinearMovingProviderHandler.generateArchetype(null));
        assertThrows(IllegalArgumentException.class, () ->
                _finiteLinearMovingProviderHandler.generateArchetype(""));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(TypeHandler.class.getCanonicalName() + "<" +
                FiniteLinearMovingProvider.class.getCanonicalName() + ">",
                _finiteLinearMovingProviderHandler.getInterfaceName());
    }

    // TODO: Consider extracting this to Tools, and looking for good places to use it!
    private static <T> void assertOnlyContains(List<T> list, T item) {
        assertEquals(1, list.size());
        if (item instanceof Integer ||
                item instanceof Long ||
                item instanceof Float ||
                item instanceof Double) {
            assertEquals(item, list.get(0));
        }
        else {
            assertSame(item, list.get(0));
        }
    }
}
