package inaugural.soliloquy.graphics.test.display.io;

import inaugural.soliloquy.graphics.io.MouseCursorImpl;
import inaugural.soliloquy.graphics.test.display.DisplayTest;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.WindowResolutionManager;
import soliloquy.specs.graphics.rendering.renderers.Renderer;

import java.util.ArrayList;
import java.util.HashMap;

public class MouseCursorImplTest extends DisplayTest {
    protected static HashMap<String, ProviderAtTime<Long>> _mouseCursorProviders = new HashMap<>();

    /** @noinspection rawtypes*/
    public static java.util.List<Renderer> generateRenderablesAndRenderersWithMeshAndShader(
            WindowResolutionManager windowResolutionManager) {
        MouseCursor = new MouseCursorImpl(_mouseCursorProviders, new FakeGlobalClock());
        FrameTimer.ShouldExecuteNextFrame = true;

        return new ArrayList<>();
    }
}
