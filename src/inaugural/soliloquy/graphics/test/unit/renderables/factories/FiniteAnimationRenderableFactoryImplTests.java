package inaugural.soliloquy.graphics.test.unit.renderables.factories;

import inaugural.soliloquy.graphics.renderables.FiniteAnimationRenderableImpl;
import inaugural.soliloquy.graphics.renderables.factories.FiniteAnimationRenderableFactoryImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.graphics.renderables.FiniteAnimationRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.factories.FiniteAnimationRenderableFactory;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class FiniteAnimationRenderableFactoryImplTests {
    private final String ANIMATION_SUPPORTING_ID = "animationSupportingId";
    private final FakeAnimation ANIMATION_SUPPORTING_MOUSE_EVENTS =
            new FakeAnimation(ANIMATION_SUPPORTING_ID, true);
    private final String ANIMATION_NOT_SUPPORTING_ID = "animationNotSupportingId";
    private final FakeAnimation ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS =
            new FakeAnimation(ANIMATION_NOT_SUPPORTING_ID, false);
    private final FakeProviderAtTime<Float> BORDER_THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Color> BORDER_COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final HashMap<Integer, Action<Long>> ON_PRESS_ACTIONS = new HashMap<>();
    private final FakeAction<Long> ON_MOUSE_OVER = new FakeAction<>();
    private final FakeAction<Long> ON_MOUSE_LEAVE = new FakeAction<>();
    private final ArrayList<ProviderAtTime<ColorShift>> COLOR_SHIFT_PROVIDERS = new ArrayList<>();
    private final FakeStaticProvider<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeStaticProvider<>(null);
    private final int Z = 123;
    private final FakeEntityUuid UUID = new FakeEntityUuid();
    private final Consumer<Renderable>
            FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER =
            renderable -> {};
    private final Consumer<Renderable>
            FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER =
            renderable -> {};
    private final Consumer<Renderable>
            FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER =
            renderable -> {};
    private final Consumer<Renderable>
            FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER =
            renderable -> {};

    private final long START_TIMESTAMP = 111L;
    private final Long PAUSED_TIMESTAMP = -456L;
    private final Long MOST_RECENT_TIMESTAMP = -123L;

    private FiniteAnimationRenderableFactory _finiteAnimationRenderableFactory;

    @BeforeEach
    void setUp() {
        _finiteAnimationRenderableFactory = new FiniteAnimationRenderableFactoryImpl();
    }

    @Test
    void testMake() {
        FiniteAnimationRenderable finiteAnimationRenderableWithMouseEvents =
                _finiteAnimationRenderableFactory.make(
                        ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                        BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                        ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                        FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                        FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                        START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
                );
        FiniteAnimationRenderable finiteAnimationRenderableWithoutMouseEvents =
                _finiteAnimationRenderableFactory.make(
                        ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                        BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z,
                        UUID,
                        FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                        FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                        START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
                );

        assertNotNull(finiteAnimationRenderableWithMouseEvents);
        assertNotNull(finiteAnimationRenderableWithoutMouseEvents);
        assertTrue(finiteAnimationRenderableWithMouseEvents
                instanceof FiniteAnimationRenderableImpl);
        assertTrue(finiteAnimationRenderableWithoutMouseEvents
                instanceof FiniteAnimationRenderableImpl);
    }

    @Test
    void testMakeWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                null, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, null, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, null, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, null,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                null,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                null,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, null
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP
        ));


        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                null, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, null, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, null, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, null,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                null,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                null,
                START_TIMESTAMP, PAUSED_TIMESTAMP, MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, PAUSED_TIMESTAMP, null
        ));
        assertThrows(IllegalArgumentException.class, () -> _finiteAnimationRenderableFactory.make(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                FINITE_ANIMATION_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER,
                START_TIMESTAMP, MOST_RECENT_TIMESTAMP + 1, MOST_RECENT_TIMESTAMP
        ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(FiniteAnimationRenderableFactory.class.getCanonicalName(),
                _finiteAnimationRenderableFactory.getInterfaceName());
    }
}