package inaugural.soliloquy.graphics.test.unit.renderables.factories;

import inaugural.soliloquy.graphics.renderables.RasterizedLineSegmentRenderableImpl;
import inaugural.soliloquy.graphics.renderables.factories.RasterizedLineSegmentRenderableFactoryImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeEntityUuid;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeProviderAtTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.RasterizedLineSegmentRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.factories.RasterizedLineSegmentRenderableFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class RasterizedLineSegmentRenderableFactoryImplTests {
    private final ProviderAtTime<Float> THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final short STIPPLE_PATTERN = 456;
    private final short STIPPLE_FACTOR = 123;
    private final ProviderAtTime<Color> COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final ProviderAtTime<FloatBox> RENDERING_AREA_PROVIDER = new FakeProviderAtTime<>();
    private final int Z = 789;
    private final EntityUuid UUID = new FakeEntityUuid();
    private final Consumer<Renderable> REMOVE_FROM_CONTAINER = renderable -> {};
    private final Consumer<Renderable> UPDATE_Z_INDEX_IN_CONTAINER = renderable -> {};
    
    private RasterizedLineSegmentRenderableFactory _rasterizedLineSegmentRenderableFactory;

    @BeforeEach
    void setUp() {
        _rasterizedLineSegmentRenderableFactory = new RasterizedLineSegmentRenderableFactoryImpl();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(RasterizedLineSegmentRenderableFactory.class.getCanonicalName(),
                _rasterizedLineSegmentRenderableFactory.getInterfaceName());
    }
    
    @Test
    void testMake() {
        RasterizedLineSegmentRenderable rasterizedLineSegmentRenderable = 
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN, 
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID, 
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER);

        assertNotNull(rasterizedLineSegmentRenderable);
        assertTrue(rasterizedLineSegmentRenderable instanceof RasterizedLineSegmentRenderableImpl);
    }
    
    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(null, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, (short) 0,
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        (short) 0, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        (short) 257, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, null, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, COLOR_PROVIDER, null, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, null,
                        UPDATE_Z_INDEX_IN_CONTAINER, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        null, REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () ->
                _rasterizedLineSegmentRenderableFactory.make(THICKNESS_PROVIDER, STIPPLE_PATTERN,
                        STIPPLE_FACTOR, COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                        UPDATE_Z_INDEX_IN_CONTAINER, null
        ));
    }
}
