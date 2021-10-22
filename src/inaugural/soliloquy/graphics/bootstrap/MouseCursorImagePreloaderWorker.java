package inaugural.soliloquy.graphics.bootstrap;

import inaugural.soliloquy.tools.Check;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.lwjgl.stb.STBImage.stbi_image_free;
import static org.lwjgl.stb.STBImage.stbi_load;

public class MouseCursorImagePreloaderWorker implements Runnable {
    private final String RELATIVE_LOCATION;
    private final int HOTSPOT_X;
    private final int HOTSPOT_Y;
    private final Function<String, Consumer<Long>> CONSUME_RESULT;

    private final static int DESIRED_CHANNELS = 4;

    public MouseCursorImagePreloaderWorker(String relativeLocation, int hotspotX, int hotspotY,
                                           Function<String, Consumer<Long>> consumeResult) {
        RELATIVE_LOCATION = Check.ifNullOrEmpty(relativeLocation, "relativeLocation");
        HOTSPOT_X = Check.ifNonNegative(hotspotX, "hotspotX");
        HOTSPOT_Y = Check.ifNonNegative(hotspotY, "hotspotY");
        CONSUME_RESULT = Check.ifNull(consumeResult, "consumeResult");
    }

    @Override
    public void run() {
        // NB: Look for commonality to refactor with ImageFactoryImpl
        IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
        IntBuffer channelsBuffer = BufferUtils.createIntBuffer(1);

        ByteBuffer imageBytes = stbi_load(RELATIVE_LOCATION, widthBuffer, heightBuffer,
                channelsBuffer, DESIRED_CHANNELS);
        assert imageBytes != null;

        int width = widthBuffer.get();
        int height = heightBuffer.get();

        if (HOTSPOT_X >= width) {
            throw new IllegalStateException("MouseCursorImagePreloaderWorker.run: HOTSPOT_X (" +
                    HOTSPOT_X + ") is out of bounds, width = " + width);
        }
        if (HOTSPOT_Y >= height) {
            throw new IllegalStateException("MouseCursorImagePreloaderWorker.run: HOTSPOT_Y (" +
                    HOTSPOT_Y + ") is out of bounds, height = " + height);
        }

        GLFWImage mouseCursorImage = GLFWImage.create();
        mouseCursorImage.width(width);
        mouseCursorImage.height(height);
        mouseCursorImage.pixels(imageBytes);

        long mouseCursorId = GLFW.glfwCreateCursor(mouseCursorImage, HOTSPOT_X, HOTSPOT_Y);

        CONSUME_RESULT.apply(RELATIVE_LOCATION).accept(mouseCursorId);

        // NB: Refactor this out too
        stbi_image_free(imageBytes);
    }
}
