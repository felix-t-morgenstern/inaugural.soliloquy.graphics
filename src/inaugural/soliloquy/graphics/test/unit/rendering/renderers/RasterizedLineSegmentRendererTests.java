package inaugural.soliloquy.graphics.test.unit.rendering.renderers;

import inaugural.soliloquy.graphics.rendering.renderers.RasterizedLineSegmentRenderer;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.graphics.renderables.RasterizedLineSegmentRenderable;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL.createCapabilities;

class RasterizedLineSegmentRendererTests {
    private final FakeMesh MESH = new FakeMesh();
    private final FakeShader SHADER = new FakeShader();
    private final long MOST_RECENT_TIMESTAMP = 123123L;

    private RasterizedLineSegmentRenderer _lineSegmentRenderer;

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
        _lineSegmentRenderer = new RasterizedLineSegmentRenderer(MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(Renderer.class.getCanonicalName() + "<" +
                        RasterizedLineSegmentRenderable.class.getCanonicalName() + ">",
                _lineSegmentRenderer.getInterfaceName());
    }

    @Test
    void testSetMeshOrShaderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.setMesh(null));
        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.setShader(null));
    }

    @Test
    void testRenderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(null, 0L));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        null, (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(null), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        null,
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(null),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        null,
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(null),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0x0000, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 0,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));
        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 257,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        //noinspection RedundantCast
        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>((Color)null),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        null,
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(null),
                        1, new FakeEntityUuid()),
                0L
        ));

        assertThrows(IllegalArgumentException.class, () -> _lineSegmentRenderer.render(
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, null),
                0L
        ));
    }

    @Test
    void testRenderOutdatedTimestamp() {
        FakeRasterizedLineSegmentRenderable lineSegmentRenderable =
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid());
        _lineSegmentRenderer.setMesh(MESH);
        _lineSegmentRenderer.setShader(SHADER);

        assertThrows(IllegalArgumentException.class, () ->
                _lineSegmentRenderer.render(lineSegmentRenderable, MOST_RECENT_TIMESTAMP - 1L));
    }

    @Test
    void testRenderWithoutMeshOrShader() {
        FakeRasterizedLineSegmentRenderable lineSegmentRenderable =
                new FakeRasterizedLineSegmentRenderable(
                        new FakeStaticProviderAtTime<>(1.0f), (short)0xAAAA, (short) 1,
                        new FakeStaticProviderAtTime<>(Color.WHITE),
                        new FakeStaticProviderAtTime<>(new FakeFloatBox(-0.5f, 0.5f, 0.5f, -0.5f)),
                        1, new FakeEntityUuid());

        Renderer<RasterizedLineSegmentRenderable> lineSegmentRendererWithoutMesh =
                new RasterizedLineSegmentRenderer(MOST_RECENT_TIMESTAMP);

        lineSegmentRendererWithoutMesh.setShader(SHADER);

        assertThrows(IllegalStateException.class, () -> lineSegmentRendererWithoutMesh
                .render(lineSegmentRenderable, MOST_RECENT_TIMESTAMP));

        Renderer<RasterizedLineSegmentRenderable> lineSegmentRendererWithoutShader =
                new RasterizedLineSegmentRenderer(MOST_RECENT_TIMESTAMP);

        lineSegmentRendererWithoutShader.setMesh(MESH);

        assertThrows(IllegalStateException.class, () -> lineSegmentRendererWithoutShader
                .render(lineSegmentRenderable, MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testMostRecentTimestamp() {
        assertEquals(MOST_RECENT_TIMESTAMP, (long)_lineSegmentRenderer.mostRecentTimestamp());
    }
}
