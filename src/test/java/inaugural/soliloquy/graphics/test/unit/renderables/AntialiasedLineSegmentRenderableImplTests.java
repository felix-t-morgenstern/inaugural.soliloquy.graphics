package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.AntialiasedLineSegmentRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeProviderAtTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.graphics.renderables.AntialiasedLineSegmentRenderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.RenderableStack;

import java.awt.*;
import java.util.UUID;

import static inaugural.soliloquy.tools.random.Random.randomInt;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AntialiasedLineSegmentRenderableImplTests {
    private final FakeProviderAtTime<Vertex> VERTEX_1_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Vertex> VERTEX_2_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Float> THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Color> COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Float> THICKNESS_GRADIENT_PERCENT_PROVIDER =
            new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Float> LENGTH_GRADIENT_PERCENT_PROVIDER =
            new FakeProviderAtTime<>();
    private final int Z = randomInt();
    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private RenderableStack _mockContainingStack;

    private AntialiasedLineSegmentRenderable _antialiasedLineSegmentRenderable;

    @BeforeEach
    void setUp() {
        _mockContainingStack = mock(RenderableStack.class);

        _antialiasedLineSegmentRenderable = new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        );
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                null,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                null,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                null,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                null,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                null,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                null,
                Z,
                UUID,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                null,
                _mockContainingStack
        ));
        assertThrows(IllegalArgumentException.class, () -> new AntialiasedLineSegmentRenderableImpl(
                VERTEX_1_PROVIDER,
                VERTEX_2_PROVIDER,
                THICKNESS_PROVIDER,
                COLOR_PROVIDER,
                THICKNESS_GRADIENT_PERCENT_PROVIDER,
                LENGTH_GRADIENT_PERCENT_PROVIDER,
                Z,
                UUID,
                null
        ));
    }

    @Test
    void testSetAndGetVertexProviders() {
        assertSame(VERTEX_1_PROVIDER, _antialiasedLineSegmentRenderable.getVertex1Provider());
        assertSame(VERTEX_2_PROVIDER, _antialiasedLineSegmentRenderable.getVertex2Provider());

        FakeProviderAtTime<Vertex> newVertex1Provider = new FakeProviderAtTime<>();
        FakeProviderAtTime<Vertex> newVertex2Provider = new FakeProviderAtTime<>();

        _antialiasedLineSegmentRenderable.setVertex1Provider(newVertex1Provider);
        _antialiasedLineSegmentRenderable.setVertex2Provider(newVertex2Provider);

        assertSame(newVertex1Provider, _antialiasedLineSegmentRenderable.getVertex1Provider());
        assertSame(newVertex2Provider, _antialiasedLineSegmentRenderable.getVertex2Provider());
    }

    @Test
    void testGetAndSetThicknessProvider() {
        assertSame(THICKNESS_PROVIDER, _antialiasedLineSegmentRenderable.getThicknessProvider());

        FakeProviderAtTime<Float> newThicknessProvider = new FakeProviderAtTime<>();

        _antialiasedLineSegmentRenderable.setThicknessProvider(newThicknessProvider);

        assertSame(newThicknessProvider, _antialiasedLineSegmentRenderable.getThicknessProvider());
    }

    @Test
    void testSetThicknessProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _antialiasedLineSegmentRenderable.setThicknessProvider(null));
    }

    @Test
    void testGetAndSetColorProvider() {
        assertSame(COLOR_PROVIDER, _antialiasedLineSegmentRenderable.getColorProvider());

        FakeProviderAtTime<Color> newColorProvider = new FakeProviderAtTime<>();
        _antialiasedLineSegmentRenderable.setColorProvider(newColorProvider);

        assertSame(newColorProvider, _antialiasedLineSegmentRenderable.getColorProvider());
    }

    @Test
    void testGetAndSetThicknessGradientPercentProvider() {
        assertSame(THICKNESS_GRADIENT_PERCENT_PROVIDER,
                _antialiasedLineSegmentRenderable.getThicknessGradientPercentProvider());

        ProviderAtTime<Float> newThicknessGradientPercentProvider = new FakeProviderAtTime<>();

        _antialiasedLineSegmentRenderable
                .setThicknessGradientPercentProvider(newThicknessGradientPercentProvider);

        assertSame(newThicknessGradientPercentProvider,
                _antialiasedLineSegmentRenderable.getThicknessGradientPercentProvider());
    }

    @Test
    void testGetAndSetLengthGradientPercentProvider() {
        assertSame(LENGTH_GRADIENT_PERCENT_PROVIDER,
                _antialiasedLineSegmentRenderable.getLengthGradientPercentProvider());

        ProviderAtTime<Float> newLengthGradientPercentProvider = new FakeProviderAtTime<>();

        _antialiasedLineSegmentRenderable
                .setLengthGradientPercentProvider(newLengthGradientPercentProvider);

        assertSame(newLengthGradientPercentProvider,
                _antialiasedLineSegmentRenderable.getLengthGradientPercentProvider());
    }

    @Test
    void testSetColorProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _antialiasedLineSegmentRenderable.setColorProvider(null));
    }

    @Test
    void testSetVertexProvidersWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _antialiasedLineSegmentRenderable.setVertex1Provider(null));
        assertThrows(IllegalArgumentException.class, () ->
                _antialiasedLineSegmentRenderable.setVertex2Provider(null));
    }

    @Test
    void testGetAndSetZ() {
        assertEquals(Z, _antialiasedLineSegmentRenderable.getZ());

        int newZ = 456;
        _antialiasedLineSegmentRenderable.setZ(newZ);

        assertEquals(newZ, _antialiasedLineSegmentRenderable.getZ());
        verify(_mockContainingStack, times(1)).add(_antialiasedLineSegmentRenderable);
    }

    @Test
    void testDelete() {
        _antialiasedLineSegmentRenderable.delete();

        verify(_mockContainingStack, times(1)).remove(_antialiasedLineSegmentRenderable);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(AntialiasedLineSegmentRenderable.class.getCanonicalName(),
                _antialiasedLineSegmentRenderable.getInterfaceName());
    }
}
