package inaugural.soliloquy.graphics.test.unit.rendering.renderers;

import inaugural.soliloquy.graphics.rendering.renderers.TriangleRenderer;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeStaticProvider;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeTriangleRenderable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.graphics.renderables.TriangleRenderable;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.Mesh;
import soliloquy.specs.graphics.rendering.Shader;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;

import static inaugural.soliloquy.tools.random.Random.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.mockito.Mockito.*;

class TriangleRendererTests {
    private final ProviderAtTime<Pair<Float, Float>> VERTEX_1_LOCATION_PROVIDER =
            new FakeStaticProvider<>(new Pair<>(randomFloat(), randomFloat()));
    private final ProviderAtTime<Color> VERTEX_1_COLOR_PROVIDER =
            new FakeStaticProvider<>(randomColor());
    private final ProviderAtTime<Pair<Float, Float>> VERTEX_2_LOCATION_PROVIDER =
            new FakeStaticProvider<>(new Pair<>(randomFloat(), randomFloat()));
    private final ProviderAtTime<Color> VERTEX_2_COLOR_PROVIDER =
            new FakeStaticProvider<>(randomColor());
    private final ProviderAtTime<Pair<Float, Float>> VERTEX_3_LOCATION_PROVIDER =
            new FakeStaticProvider<>(new Pair<>(randomFloat(), randomFloat()));
    private final ProviderAtTime<Color> VERTEX_3_COLOR_PROVIDER =
            new FakeStaticProvider<>(randomColor());
    private final ProviderAtTime<Integer> BACKGROUND_TEXTURE_ID_PROVIDER =
            new FakeStaticProvider<>(randomInt());
    private final float BACKGROUND_TEXTURE_TILE_WIDTH = randomFloatWithInclusiveFloor(0.001f);
    private final float BACKGROUND_TEXTURE_TILE_HEIGHT = randomFloatWithInclusiveFloor(0.001f);
    private final TriangleRenderable TRIANGLE_RENDERABLE =
            new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER, VERTEX_1_COLOR_PROVIDER,
                    VERTEX_2_LOCATION_PROVIDER, VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                    VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                    BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT);

    private final Long MOST_RECENT_TIMESTAMP = randomLong();

    @Mock private Mesh _mockMesh;
    @Mock private Shader _mockShader;

    private Renderer<TriangleRenderable> _triangleRenderer;

    @BeforeAll
    static void setUpFixture() {
        if (!glfwInit()) {
            throw new RuntimeException("GLFW failed to initialize");
        }

        long window = glfwCreateWindow(1, 1, "", 0, 0);
        glfwMakeContextCurrent(window);
        createCapabilities();
    }

    @AfterAll
    static void tearDownFixture() {
        glfwTerminate();
    }

    @BeforeEach
    void setUp() {
        _mockMesh = mock(Mesh.class);
        _mockShader = mock(Shader.class);

        _triangleRenderer = new TriangleRenderer(MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testMostRecentTimestamp() {
        _triangleRenderer.setMesh(_mockMesh);
        _triangleRenderer.setShader(_mockShader);

        assertEquals(MOST_RECENT_TIMESTAMP, _triangleRenderer.mostRecentTimestamp());

        _triangleRenderer.render(TRIANGLE_RENDERABLE, MOST_RECENT_TIMESTAMP + 1);

        assertEquals(MOST_RECENT_TIMESTAMP + 1, (long) _triangleRenderer.mostRecentTimestamp());
    }

    @Test
    void testRenderWithInvalidParams() {
        _triangleRenderer.setMesh(_mockMesh);
        _triangleRenderer.setShader(_mockShader);

        assertThrows(IllegalArgumentException.class,
                () -> _triangleRenderer.render(null, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(null,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(new FakeStaticProvider<>(null),
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(
                                new FakeStaticProvider<>(new Pair<>(null, randomFloat(), 0f, 0f)),
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(
                                new FakeStaticProvider<>(new Pair<>(randomFloat(), null, 0f, 0f)),
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                null, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, null,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, new FakeStaticProvider<>(null),
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER,
                                new FakeStaticProvider<>(new Pair<>(null, randomFloat(), 0f, 0f)),
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER,
                                new FakeStaticProvider<>(new Pair<>(randomFloat(), null, 0f, 0f)),
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                null, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, null,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, new FakeStaticProvider<>(null),
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER,
                                new FakeStaticProvider<>(new Pair<>(null, randomFloat(), 0f, 0f)),
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER,
                                new FakeStaticProvider<>(new Pair<>(randomFloat(), null, 0f, 0f)),
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                null, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, null,
                                BACKGROUND_TEXTURE_TILE_WIDTH, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                randomFloatWithInclusiveCeiling(-0.001f),
                                BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, BACKGROUND_TEXTURE_ID_PROVIDER,
                                BACKGROUND_TEXTURE_TILE_WIDTH,
                                randomFloatWithInclusiveCeiling(-0.001f)),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, new FakeStaticProvider<>(randomInt()),
                                0f, BACKGROUND_TEXTURE_TILE_HEIGHT),
                        MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () -> _triangleRenderer
                .render(new FakeTriangleRenderable(VERTEX_1_LOCATION_PROVIDER,
                                VERTEX_1_COLOR_PROVIDER, VERTEX_2_LOCATION_PROVIDER,
                                VERTEX_2_COLOR_PROVIDER, VERTEX_3_LOCATION_PROVIDER,
                                VERTEX_3_COLOR_PROVIDER, new FakeStaticProvider<>(randomInt()),
                                BACKGROUND_TEXTURE_TILE_WIDTH, 0f),
                        MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testRenderWithMeshAndShaderUnset() {
        TriangleRenderer triangleRendererWithoutMesh = new TriangleRenderer(null);
        TriangleRenderer triangleRendererWithoutShader = new TriangleRenderer(null);

        triangleRendererWithoutMesh.setShader(_mockShader);
        triangleRendererWithoutShader.setMesh(_mockMesh);

        assertThrows(IllegalStateException.class, () -> triangleRendererWithoutMesh
                .render(TRIANGLE_RENDERABLE, MOST_RECENT_TIMESTAMP));
        assertThrows(IllegalStateException.class, () -> triangleRendererWithoutShader
                .render(TRIANGLE_RENDERABLE, MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testRenderUnbindsMeshAndShader() {
        _triangleRenderer.setMesh(_mockMesh);
        _triangleRenderer.setShader(_mockShader);

        _triangleRenderer.render(TRIANGLE_RENDERABLE, MOST_RECENT_TIMESTAMP);

        verify(_mockMesh, times(1)).unbind();
        verify(_mockShader, times(1)).unbind();
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Renderer.class.getCanonicalName() + "<" +
                        TriangleRenderable.class.getCanonicalName() + ">",
                _triangleRenderer.getInterfaceName());
    }
}
