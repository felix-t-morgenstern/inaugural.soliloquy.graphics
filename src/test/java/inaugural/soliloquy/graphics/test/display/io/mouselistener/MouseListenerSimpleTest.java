package inaugural.soliloquy.graphics.test.display.io.mouselistener;

import inaugural.soliloquy.graphics.test.display.DisplayTest;
import inaugural.soliloquy.graphics.test.display.rendering.renderers.spriterenderer.SpriteRendererTest;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.game.Game;
import soliloquy.specs.logger.Logger;

import static inaugural.soliloquy.graphics.api.Constants.INTACT_COLOR;
import static inaugural.soliloquy.graphics.api.Constants.LEFT_MOUSE_BUTTON;

/**
 * Test acceptance criteria:
 *
 * 1. This test will display a window of 1920x1080 pixels in the middle of the screen for 24000ms
 * with a titlebar reading "My title bar". The window will contain a picture of a shield,
 * centered in the window, taking up half of the width and three-fourths of the height of the
 * window.
 * 2. During this time, any time the mouse moves over the shield, the console will display the
 * message, "MOUSE OVER". When the mouse moves off of the shield, it will say "MOUSE LEAVE". When
 * the left mouse button is pressed over the shield, it will say "LEFT MOUSE BUTTON PRESS". When the
 * mouse button is released over the shield, it will say "LEFT MOUSE BUTTON RELEASE".
 * 3. The window will then close.
 */
class MouseListenerSimpleTest extends SpriteRendererTest {
    public static void main(String[] args) {
        runTest(
                windowResolutionManager -> generateRenderablesAndRenderersWithMeshAndShader(
                        0f,
                        INTACT_COLOR,
                        null,
                        windowResolutionManager),
                SpriteRendererTest::stackRendererAction,
                MouseListenerSimpleTest::graphicsPreloaderLoadAction,
                graphicsCoreLoop -> DisplayTest.closeAfterSomeTime(graphicsCoreLoop, 24000));
    }

    protected static void graphicsPreloaderLoadAction() {
        SpriteRendererTest.graphicsPreloaderLoadAction();

        SpriteRenderable.setCapturesMouseEvents(true);
        SpriteRenderable.setOnMouseOver(new Action<Long>() {
            @Override
            public void run(Long aLong) throws IllegalArgumentException {
                System.out.println("MOUSE OVER");
            }

            @Override
            public Game game() {
                return null;
            }

            @Override
            public Logger logger() {
                return null;
            }

            @Override
            public String id() throws IllegalStateException {
                return null;
            }

            @Override
            public Long getArchetype() {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return null;
            }
        });
        SpriteRenderable.setOnMouseLeave(new Action<Long>() {
            @Override
            public void run(Long aLong) throws IllegalArgumentException {
                System.out.println("MOUSE LEAVE");
            }

            @Override
            public Game game() {
                return null;
            }

            @Override
            public Logger logger() {
                return null;
            }

            @Override
            public String id() throws IllegalStateException {
                return null;
            }

            @Override
            public Long getArchetype() {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return null;
            }
        });
        SpriteRenderable.setOnPress(LEFT_MOUSE_BUTTON, new Action<Long>() {
            @Override
            public void run(Long aLong) throws IllegalArgumentException {
                System.out.println("LEFT MOUSE BUTTON PRESS");
            }

            @Override
            public Game game() {
                return null;
            }

            @Override
            public Logger logger() {
                return null;
            }

            @Override
            public String id() throws IllegalStateException {
                return null;
            }

            @Override
            public Long getArchetype() {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return null;
            }
        });
        SpriteRenderable.setOnRelease(LEFT_MOUSE_BUTTON, new Action<Long>() {
            @Override
            public void run(Long aLong) throws IllegalArgumentException {
                System.out.println("LEFT MOUSE BUTTON RELEASE");
            }

            @Override
            public Game game() {
                return null;
            }

            @Override
            public Logger logger() {
                return null;
            }

            @Override
            public String id() throws IllegalStateException {
                return null;
            }

            @Override
            public Long getArchetype() {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return null;
            }
        });

        MOUSE_EVENT_CAPTURING_SPATIAL_INDEX.putRenderable(SpriteRenderable,
                SpriteRenderingDimensions);
    }
}
