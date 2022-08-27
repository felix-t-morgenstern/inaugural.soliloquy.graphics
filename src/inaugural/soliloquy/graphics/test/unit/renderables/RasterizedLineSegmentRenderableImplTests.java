package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.RasterizedLineSegmentRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeProviderAtTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.graphics.renderables.RasterizedLineSegmentRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;

import java.awt.*;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class RasterizedLineSegmentRenderableImplTests {
    private final ProviderAtTime<Float> THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final short STIPPLE_PATTERN = 456;
    private final short STIPPLE_FACTOR = 123;
    private final ProviderAtTime<Color> COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final ProviderAtTime<Pair<Float, Float>> VERTEX_1_LOCATION_PROVIDER =
            new FakeProviderAtTime<>();
    private final ProviderAtTime<Pair<Float, Float>> VERTEX_2_LOCATION_PROVIDER =
            new FakeProviderAtTime<>();
    private final int Z = 789;
    private final Consumer<Renderable> REMOVE_FROM_CONTAINER = renderable ->
            _renderableRemovedFromContainer = renderable;
    private final Consumer<Renderable> UPDATE_Z_INDEX_IN_CONTAINER = renderable ->
            _renderableUpdatedInContainer = renderable;

    private static Renderable _renderableUpdatedInContainer;
    private static Renderable _renderableRemovedFromContainer;

    private static final UUID UUID = java.util.UUID.randomUUID();

    private RasterizedLineSegmentRenderable _rasterizedLineSegmentRenderable;

    @BeforeEach
    void setUp() {
        _rasterizedLineSegmentRenderable = new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, STIPPLE_FACTOR, COLOR_PROVIDER,
                Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                null, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER, STIPPLE_PATTERN,
                STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, null, THICKNESS_PROVIDER, STIPPLE_PATTERN,
                STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, null, STIPPLE_PATTERN,
                STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                (short) 0, STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, (short) 0, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, (short) 257, COLOR_PROVIDER, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, STIPPLE_FACTOR, null, Z, UUID, UPDATE_Z_INDEX_IN_CONTAINER,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, STIPPLE_FACTOR, COLOR_PROVIDER, Z, null,
                UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID, null,
                REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new RasterizedLineSegmentRenderableImpl(
                VERTEX_1_LOCATION_PROVIDER, VERTEX_2_LOCATION_PROVIDER, THICKNESS_PROVIDER,
                STIPPLE_PATTERN, STIPPLE_FACTOR, COLOR_PROVIDER, Z, UUID,
                UPDATE_Z_INDEX_IN_CONTAINER, null
        ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RasterizedLineSegmentRenderable.class.getCanonicalName(),
                _rasterizedLineSegmentRenderable.getInterfaceName());
    }

    @Test
    void testGetAndSetThicknessProvider() {
        assertSame(THICKNESS_PROVIDER, _rasterizedLineSegmentRenderable.getThicknessProvider());

        FakeProviderAtTime<Float> newThicknessProvider = new FakeProviderAtTime<>();

        _rasterizedLineSegmentRenderable.setThicknessProvider(newThicknessProvider);

        assertSame(newThicknessProvider, _rasterizedLineSegmentRenderable.getThicknessProvider());
    }

    @Test
    void testSetThicknessProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setThicknessProvider(null));
    }

    @Test
    void testGetAndSetStipplePattern() {
        assertEquals(STIPPLE_PATTERN, _rasterizedLineSegmentRenderable.getStipplePattern());

        short newStipplePattern = 789;
        _rasterizedLineSegmentRenderable.setStipplePattern(newStipplePattern);

        assertEquals(newStipplePattern, _rasterizedLineSegmentRenderable.getStipplePattern());
    }

    @Test
    void testSetStipplePatternWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setStipplePattern((short) 0));
    }

    @Test
    void testGetAndSetStippleFactor() {
        assertEquals(STIPPLE_FACTOR, _rasterizedLineSegmentRenderable.getStippleFactor());

        short newStippleFactor = 234;
        _rasterizedLineSegmentRenderable.setStippleFactor(newStippleFactor);

        assertEquals(newStippleFactor, _rasterizedLineSegmentRenderable.getStippleFactor());
    }

    @Test
    void testSetStippleFactorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setStippleFactor((short) 0));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setStippleFactor((short) 257));
    }

    @Test
    void testGetAndSetColorProvider() {
        assertSame(COLOR_PROVIDER, _rasterizedLineSegmentRenderable.getColorProvider());

        FakeProviderAtTime<Color> newColorProvider = new FakeProviderAtTime<>();
        _rasterizedLineSegmentRenderable.setColorProvider(newColorProvider);

        assertSame(newColorProvider, _rasterizedLineSegmentRenderable.getColorProvider());
    }

    @Test
    void testSetColorProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setColorProvider(null));
    }

    @Test
    void testSetAndGetVertexLocationProviders() {
        assertSame(VERTEX_1_LOCATION_PROVIDER,
                _rasterizedLineSegmentRenderable.getVertex1LocationProvider());
        assertSame(VERTEX_2_LOCATION_PROVIDER,
                _rasterizedLineSegmentRenderable.getVertex2LocationProvider());

        FakeProviderAtTime<Pair<Float, Float>> newVertex1LocationProvider =
                new FakeProviderAtTime<>();
        FakeProviderAtTime<Pair<Float, Float>> newVertex2LocationProvider =
                new FakeProviderAtTime<>();
        _rasterizedLineSegmentRenderable.setVertex1LocationProvider(newVertex1LocationProvider);
        _rasterizedLineSegmentRenderable.setVertex2LocationProvider(newVertex2LocationProvider);

        assertSame(newVertex1LocationProvider,
                _rasterizedLineSegmentRenderable.getVertex1LocationProvider());
        assertSame(newVertex2LocationProvider,
                _rasterizedLineSegmentRenderable.getVertex2LocationProvider());
    }

    @Test
    void testSetVertexLocationProvidersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setVertex1LocationProvider(null));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderable.setVertex2LocationProvider(null));
    }

    @Test
    void testGetAndSetZ() {
        assertEquals(Z, _rasterizedLineSegmentRenderable.getZ());

        int newZ = 456;
        _rasterizedLineSegmentRenderable.setZ(newZ);

        assertEquals(newZ, _rasterizedLineSegmentRenderable.getZ());
        assertSame(_rasterizedLineSegmentRenderable, _renderableUpdatedInContainer);
    }

    @Test
    void testDelete() {
        _rasterizedLineSegmentRenderable.delete();

        assertSame(_rasterizedLineSegmentRenderable, _renderableRemovedFromContainer);
    }

    @Test
    void testUuid() {
        assertSame(UUID, _rasterizedLineSegmentRenderable.uuid());
    }
}
