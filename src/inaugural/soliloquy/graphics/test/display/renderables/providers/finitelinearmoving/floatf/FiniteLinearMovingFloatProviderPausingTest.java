package inaugural.soliloquy.graphics.test.display.renderables.providers.finitelinearmoving.floatf;

import inaugural.soliloquy.graphics.test.display.DisplayTest;
import inaugural.soliloquy.graphics.test.display.renderables.providers.finitelinearmoving.FiniteLinearMovingProviderTest;
import inaugural.soliloquy.graphics.test.display.rendering.renderers.spriterenderer.SpriteRendererTest;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import inaugural.soliloquy.tools.CheckedExceptionWrapper;
import soliloquy.specs.graphics.bootstrap.GraphicsCoreLoop;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a window of 1920x1080 pixels in the middle of the screen for 6000ms
 *    with a titlebar reading "My title bar". The window will contain a picture of a shield,
 *    centered in the window, taking up half of the width and three-fourths of the height of the
 *    window.
 * 2. At first, there should be no (or next-to-no border); for the first 2000ms, it will expand.
 * 3. Its expansion will pause for 2000ms.
 * 4. Its expansion will continue for another 2000ms.
 * 5. The window will then close.
 *
 */
class FiniteLinearMovingFloatProviderPausingTest extends FiniteLinearMovingFloatProviderTest {
    public static void main(String[] args) {
        DisplayTest.runTest(windowResolutionManager ->
                        FiniteLinearMovingProviderTest.generateRenderablesAndRenderersWithMeshAndShader(BORDER_THICKNESS,
                                BORDER_COLOR, null, windowResolutionManager),
                FiniteLinearMovingProviderTest::stackRendererAction,
                FiniteLinearMovingFloatProviderTest::graphicsPreloaderLoadAction,
                FiniteLinearMovingFloatProviderPausingTest::closeAfterSomeTime);
    }

    public static void closeAfterSomeTime(GraphicsCoreLoop graphicsCoreLoop) {
        CheckedExceptionWrapper.sleep(2000);

        long timestamp = new FakeGlobalClock().globalTimestamp();
        SpriteRendererTest.SpriteRenderable.BorderThicknessProvider.reportPause(timestamp);

        CheckedExceptionWrapper.sleep(2000);

        timestamp = new FakeGlobalClock().globalTimestamp();
        SpriteRendererTest.SpriteRenderable.BorderThicknessProvider.reportUnpause(timestamp);

        CheckedExceptionWrapper.sleep(2000);

        glfwSetWindowShouldClose(graphicsCoreLoop.windowId(), true);
    }
}