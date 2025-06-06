package inaugural.soliloquy.graphics.test.display.rendering.renderers.rectanglerenderer;

import inaugural.soliloquy.graphics.bootstrap.assetfactories.ImageFactoryImpl;
import inaugural.soliloquy.graphics.renderables.RectangleRenderableImpl;
import inaugural.soliloquy.graphics.rendering.renderers.RectangleRenderer;
import inaugural.soliloquy.graphics.test.display.DisplayTest;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeStaticProvider;
import soliloquy.specs.common.valueobjects.FloatBox;
import soliloquy.specs.graphics.bootstrap.assetfactories.definitions.ImageDefinition;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.awt.*;
import java.util.List;

import static inaugural.soliloquy.tools.collections.Collections.*;
import static soliloquy.specs.common.valueobjects.FloatBox.floatBoxOf;

/**
 * Test acceptance criteria:
 * 1. This test will display a window of 800x600 pixels for 2000ms with a titlebar reading "New
 * Window"
 * 2. During the 3000ms, a window taking up half of the screen, centered in the middle, will have
 * background tile of some flowers; this tile will repeat twice horizontally, and three times
 * vertically
 * 3. The window will then close
 */
class RectangleRendererTileTest extends RectangleRendererTest {
    private final static FakeStaticProvider<Color> TOP_LEFT_COLOR_PROVIDER =
            new FakeStaticProvider<>(null);
    private final static FakeStaticProvider<Color> TOP_RIGHT_COLOR_PROVIDER =
            new FakeStaticProvider<>(null);
    private final static FakeStaticProvider<Color> BOTTOM_RIGHT_COLOR_PROVIDER =
            new FakeStaticProvider<>(null);
    private final static FakeStaticProvider<Color> BOTTOM_LEFT_COLOR_PROVIDER =
            new FakeStaticProvider<>(null);
    private final static FakeStaticProvider<Integer> BACKGROUND_TEXTURE_ID_PROVIDER =
            new FakeStaticProvider<>(null);
    private final static float BACKGROUND_TEXTURE_TILE_WIDTH = 0.25f;
    private final static float BACKGROUND_TEXTURE_TILE_HEIGHT = 0.16667f;
    private final static FakeStaticProvider<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeStaticProvider<>(floatBoxOf(0.25f, 0.25f, 0.75f, 0.75f));
    private final static String TILE_LOCATION =
            "./src/test/resources/images/tiles/sergey-shmidt-koy6FlCCy5s-unsplash.jpg";

    public static void main(String[] args) {
        runTest(
                RectangleRendererTileTest::generateRenderablesAndRenderersWithMeshAndShader,
                () -> {
                    BACKGROUND_TEXTURE_ID_PROVIDER.ProvidedValue =
                            new ImageFactoryImpl(0.5f)
                                    .make(new ImageDefinition(TILE_LOCATION, false)).textureId();
                    FrameTimer.ShouldExecuteNextFrame = true;
                },
                DisplayTest::closeAfterSomeTime
        );
    }

    /** @noinspection rawtypes */
    public static List<Renderer> generateRenderablesAndRenderersWithMeshAndShader(
            WindowResolutionManager windowResolutionManager) {
        RectangleRenderer = new RectangleRenderer(null);
        RectangleRenderable = new RectangleRenderableImpl(TOP_LEFT_COLOR_PROVIDER,
                TOP_RIGHT_COLOR_PROVIDER, BOTTOM_RIGHT_COLOR_PROVIDER, BOTTOM_LEFT_COLOR_PROVIDER,
                BACKGROUND_TEXTURE_ID_PROVIDER, BACKGROUND_TEXTURE_TILE_WIDTH,
                BACKGROUND_TEXTURE_TILE_HEIGHT, null, null, null, null,
                RENDERING_AREA_PROVIDER, 123, java.util.UUID.randomUUID(), FirstChildStack,
                RENDERING_BOUNDARIES);

        Renderers.registerRenderer(RectangleRenderableImpl.class, RectangleRenderer);
        FirstChildStack.add(RectangleRenderable);

        return listOf(RectangleRenderer);
    }
}
