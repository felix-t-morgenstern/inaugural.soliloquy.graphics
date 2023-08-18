package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.FiniteAnimationRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.graphics.renderables.FiniteAnimationRenderable;
import soliloquy.specs.graphics.renderables.RenderableWithMouseEvents.MouseEventInputs;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderableStack;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static inaugural.soliloquy.graphics.api.Constants.WHOLE_SCREEN;
import static inaugural.soliloquy.tools.random.Random.randomInt;
import static inaugural.soliloquy.tools.random.Random.randomLongWithInclusiveFloor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FiniteAnimationRenderableImplTests {
    int ANIMATION_DURATION = 555;
    private final String ANIMATION_SUPPORTING_ID = "animationSupportingId";
    private final FakeAnimation ANIMATION_SUPPORTING_MOUSE_EVENTS =
            new FakeAnimation(ANIMATION_SUPPORTING_ID, ANIMATION_DURATION, true);
    private final String ANIMATION_NOT_SUPPORTING_ID = "animationNotSupportingId";
    private final FakeAnimation ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS =
            new FakeAnimation(ANIMATION_NOT_SUPPORTING_ID, ANIMATION_DURATION, false);
    private final FakeProviderAtTime<Float> BORDER_THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Color> BORDER_COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final HashMap<Integer, Action<MouseEventInputs>> ON_PRESS_ACTIONS =
            new HashMap<>();
    private final ArrayList<ProviderAtTime<ColorShift>> COLOR_SHIFT_PROVIDERS = new ArrayList<>();
    private final FakeStaticProvider<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeStaticProvider<>(null);
    private final int Z = randomInt();

    private final long START_TIMESTAMP = 111L;
    private final Long PAUSED_TIMESTAMP_1 = -456L;
    private final Long PAUSED_TIMESTAMP_2 = 456L;
    private final Long MOST_RECENT_TIMESTAMP = -123L;

    long TIMESTAMP = randomLongWithInclusiveFloor(MOST_RECENT_TIMESTAMP);

    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private RenderableStack mockContainingStack;
    @Mock private RenderingBoundaries mockRenderingBoundaries;
    @Mock private Action<MouseEventInputs> mockOnPressAction;
    @Mock private Action<MouseEventInputs> mockOnMouseOverAction;
    @Mock private Action<MouseEventInputs> mockOnMouseLeaveAction;

    private FiniteAnimationRenderable finiteAnimationRenderableWithMouseEvents;
    private FiniteAnimationRenderable finiteAnimationRenderableWithoutMouseEvents;


    @BeforeEach
    void setUp() {
        mockContainingStack = mock(RenderableStack.class);
        mockRenderingBoundaries = mock(RenderingBoundaries.class);
        when(mockRenderingBoundaries.currentBoundaries()).thenReturn(WHOLE_SCREEN);

        //noinspection unchecked
        mockOnPressAction = mock(Action.class);
        //noinspection unchecked
        mockOnMouseOverAction = mock(Action.class);
        //noinspection unchecked
        mockOnMouseLeaveAction = mock(Action.class);

        finiteAnimationRenderableWithMouseEvents = new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, null,
                MOST_RECENT_TIMESTAMP);
        finiteAnimationRenderableWithoutMouseEvents = new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, null,
                MOST_RECENT_TIMESTAMP);
    }

    @Test
    void testConstructorWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                null, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, null, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, null, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, null,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                null, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, null, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                null
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP,
                MOST_RECENT_TIMESTAMP + 1,
                MOST_RECENT_TIMESTAMP
        ));


        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                null, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, null, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, null, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, null,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                null, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, null, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                MOST_RECENT_TIMESTAMP
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP, PAUSED_TIMESTAMP_1,
                null
        ));
        assertThrows(IllegalArgumentException.class, () -> new FiniteAnimationRenderableImpl(
                ANIMATION_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, mockOnMouseOverAction,
                mockOnMouseLeaveAction, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                mockContainingStack, mockRenderingBoundaries, START_TIMESTAMP,
                MOST_RECENT_TIMESTAMP + 1,
                MOST_RECENT_TIMESTAMP
        ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(FiniteAnimationRenderable.class.getCanonicalName(),
                finiteAnimationRenderableWithMouseEvents.getInterfaceName());
    }

    @Test
    void testAnimationId() {
        assertEquals(ANIMATION_SUPPORTING_ID,
                finiteAnimationRenderableWithMouseEvents.animationId());
        assertEquals(ANIMATION_NOT_SUPPORTING_ID,
                finiteAnimationRenderableWithoutMouseEvents.animationId());
    }

    @Test
    void testGetAndSetBorderThicknessProvider() {
        assertSame(BORDER_THICKNESS_PROVIDER,
                finiteAnimationRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(BORDER_THICKNESS_PROVIDER,
                finiteAnimationRenderableWithoutMouseEvents.getBorderThicknessProvider());

        FakeProviderAtTime<Float> newBorderThicknessProvider = new FakeProviderAtTime<>();

        finiteAnimationRenderableWithMouseEvents
                .setBorderThicknessProvider(newBorderThicknessProvider);
        finiteAnimationRenderableWithoutMouseEvents
                .setBorderThicknessProvider(newBorderThicknessProvider);

        assertSame(newBorderThicknessProvider,
                finiteAnimationRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(newBorderThicknessProvider,
                finiteAnimationRenderableWithoutMouseEvents.getBorderThicknessProvider());
    }

    @Test
    void testSetBorderThicknessProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setBorderThicknessProvider(null));
    }

    @Test
    void testGetAndSetBorderColorProvider() {
        assertSame(BORDER_COLOR_PROVIDER,
                finiteAnimationRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(BORDER_COLOR_PROVIDER,
                finiteAnimationRenderableWithoutMouseEvents.getBorderColorProvider());

        FakeProviderAtTime<Color> newBorderColorProvider = new FakeProviderAtTime<>();

        finiteAnimationRenderableWithMouseEvents.setBorderColorProvider(newBorderColorProvider);
        finiteAnimationRenderableWithoutMouseEvents.setBorderColorProvider(newBorderColorProvider);

        assertSame(newBorderColorProvider,
                finiteAnimationRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(newBorderColorProvider,
                finiteAnimationRenderableWithoutMouseEvents.getBorderColorProvider());
    }

    @Test
    void testSetBorderColorProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setBorderColorProvider(null));
    }

    @Test
    void testMostRecentTimestamp() {
        assertEquals(MOST_RECENT_TIMESTAMP,
                finiteAnimationRenderableWithMouseEvents.mostRecentTimestamp());
        assertEquals(MOST_RECENT_TIMESTAMP,
                finiteAnimationRenderableWithoutMouseEvents.mostRecentTimestamp());
    }

    @Test
    void testStartAndEndTimestamps() {
        assertEquals(START_TIMESTAMP,
                finiteAnimationRenderableWithMouseEvents.startTimestamp());
        assertEquals(START_TIMESTAMP,
                finiteAnimationRenderableWithoutMouseEvents.startTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION,
                finiteAnimationRenderableWithMouseEvents.endTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION,
                finiteAnimationRenderableWithoutMouseEvents.endTimestamp());
    }

    @Test
    void testReportPause() {
        finiteAnimationRenderableWithMouseEvents.reportPause(PAUSED_TIMESTAMP_2);
        finiteAnimationRenderableWithoutMouseEvents.reportPause(PAUSED_TIMESTAMP_2);

        assertEquals(PAUSED_TIMESTAMP_2,
                finiteAnimationRenderableWithMouseEvents.pausedTimestamp());
        assertEquals(PAUSED_TIMESTAMP_2,
                finiteAnimationRenderableWithoutMouseEvents.pausedTimestamp());
    }

    @Test
    void testReportUnpauseUpdatesStartAndEndTimestamps() {
        long pauseDuration = 789789L;

        finiteAnimationRenderableWithMouseEvents.reportPause(PAUSED_TIMESTAMP_2);
        finiteAnimationRenderableWithoutMouseEvents.reportPause(PAUSED_TIMESTAMP_2);

        assertEquals(PAUSED_TIMESTAMP_2,
                finiteAnimationRenderableWithMouseEvents.pausedTimestamp());
        assertEquals(PAUSED_TIMESTAMP_2,
                finiteAnimationRenderableWithoutMouseEvents.pausedTimestamp());

        finiteAnimationRenderableWithMouseEvents.reportUnpause(PAUSED_TIMESTAMP_2 + pauseDuration);
        finiteAnimationRenderableWithoutMouseEvents
                .reportUnpause(PAUSED_TIMESTAMP_2 + pauseDuration);

        assertEquals(START_TIMESTAMP + pauseDuration,
                finiteAnimationRenderableWithMouseEvents.startTimestamp());
        assertEquals(START_TIMESTAMP + pauseDuration,
                finiteAnimationRenderableWithoutMouseEvents.startTimestamp());
        assertNull(finiteAnimationRenderableWithMouseEvents.pausedTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION + pauseDuration,
                finiteAnimationRenderableWithMouseEvents.endTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION + pauseDuration,
                finiteAnimationRenderableWithoutMouseEvents.endTimestamp());
        assertNull(finiteAnimationRenderableWithoutMouseEvents.pausedTimestamp());
    }

    @Test
    void testPauseWhilePaused() {
        finiteAnimationRenderableWithMouseEvents.reportPause(PAUSED_TIMESTAMP_2);
        finiteAnimationRenderableWithoutMouseEvents.reportPause(PAUSED_TIMESTAMP_2);

        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(PAUSED_TIMESTAMP_2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.reportPause(PAUSED_TIMESTAMP_2));
    }

    @Test
    void testUnpauseWhileUnpaused() {
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(999999L));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.reportUnpause(999999L));
    }

    @Test
    void testGetAndSetCapturesMouseEvents() {
        assertTrue(finiteAnimationRenderableWithMouseEvents.getCapturesMouseEvents());
        assertFalse(finiteAnimationRenderableWithoutMouseEvents.getCapturesMouseEvents());

        finiteAnimationRenderableWithMouseEvents.setCapturesMouseEvents(false);
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.setCapturesMouseEvents(false));

        assertFalse(finiteAnimationRenderableWithMouseEvents.getCapturesMouseEvents());
    }

    @Test
    void testPressAndSetOnPress() {
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.press(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.setOnPress(2, new FakeAction<>()));

        finiteAnimationRenderableWithMouseEvents.setOnPress(2, mockOnPressAction);

        finiteAnimationRenderableWithMouseEvents.press(2, TIMESTAMP);
        verify(mockOnPressAction, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP, finiteAnimationRenderableWithMouseEvents)));

        //noinspection unchecked
        Action<MouseEventInputs> newOnPress = mock(Action.class);
        finiteAnimationRenderableWithMouseEvents.setOnPress(2, newOnPress);

        finiteAnimationRenderableWithMouseEvents.press(2, TIMESTAMP + 1);

        verify(newOnPress, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP + 1, finiteAnimationRenderableWithMouseEvents)));

        finiteAnimationRenderableWithMouseEvents.press(0, TIMESTAMP + 2);

        verify(newOnPress, times(1)).run(any());
    }

    @Test
    void testPressActionIds() {
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        finiteAnimationRenderableWithMouseEvents.setOnPress(0, new FakeAction<>(id1));
        finiteAnimationRenderableWithMouseEvents.setOnPress(2, new FakeAction<>(id2));
        finiteAnimationRenderableWithMouseEvents.setOnPress(7, new FakeAction<>(id3));
        finiteAnimationRenderableWithMouseEvents.setOnPress(2, null);

        Map<Integer, String> pressActionIds =
                finiteAnimationRenderableWithMouseEvents.pressActionIds();

        assertNotNull(pressActionIds);
        assertEquals(2, pressActionIds.size());
        assertEquals(id1, pressActionIds.get(0));
        assertEquals(id3, pressActionIds.get(7));
    }

    @Test
    void testReleaseAndSetOnRelease() {
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.release(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.setOnRelease(2, new FakeAction<>()));

        finiteAnimationRenderableWithMouseEvents.release(2, TIMESTAMP);

        //noinspection unchecked
        Action<MouseEventInputs> newOnRelease = mock(Action.class);
        finiteAnimationRenderableWithMouseEvents.setOnRelease(2, newOnRelease);
        finiteAnimationRenderableWithMouseEvents.release(2, TIMESTAMP + 1);

        verify(newOnRelease, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP + 1, finiteAnimationRenderableWithMouseEvents)));
    }

    @Test
    void testReleaseActionIds() {
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        finiteAnimationRenderableWithMouseEvents.setOnRelease(0, new FakeAction<>(id1));
        finiteAnimationRenderableWithMouseEvents.setOnRelease(2, new FakeAction<>(id2));
        finiteAnimationRenderableWithMouseEvents.setOnRelease(7, new FakeAction<>(id3));
        finiteAnimationRenderableWithMouseEvents.setOnRelease(2, null);

        Map<Integer, String> releaseActionIds =
                finiteAnimationRenderableWithMouseEvents.releaseActionIds();

        assertNotNull(releaseActionIds);
        assertEquals(2, releaseActionIds.size());
        assertEquals(id1, releaseActionIds.get(0));
        assertEquals(id3, releaseActionIds.get(7));
    }

    @Test
    void testPressOrReleaseMethodsWithInvalidButtons() {
        long timestamp = 456456L;

        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setOnPress(-1, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setOnRelease(-1, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(-1, timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(-1, timestamp + 1));

        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setOnPress(8, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.setOnRelease(8, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(8, timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(8, timestamp + 3));
    }

    @Test
    void testMouseOverAndSetOnMouseOver() {
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.mouseOver(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.setOnMouseOver(mockOnMouseOverAction));

        finiteAnimationRenderableWithMouseEvents.mouseOver(TIMESTAMP);

        verify(mockOnMouseOverAction, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP, finiteAnimationRenderableWithMouseEvents)));

        //noinspection unchecked
        Action<MouseEventInputs> newOnMouseOver = mock(Action.class);
        finiteAnimationRenderableWithMouseEvents.setOnMouseOver(newOnMouseOver);
        finiteAnimationRenderableWithMouseEvents.mouseOver(TIMESTAMP + 1);

        verify(newOnMouseOver, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP + 1, finiteAnimationRenderableWithMouseEvents)));
    }

    @Test
    void testMouseOverActionId() {
        String mouseOverActionId = "mouseOverActionId";

        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.mouseOverActionId());

        finiteAnimationRenderableWithMouseEvents.setOnMouseOver(null);

        assertNull(finiteAnimationRenderableWithMouseEvents.mouseOverActionId());

        finiteAnimationRenderableWithMouseEvents
                .setOnMouseOver(new FakeAction<>(mouseOverActionId));

        assertEquals(mouseOverActionId,
                finiteAnimationRenderableWithMouseEvents.mouseOverActionId());
    }

    @Test
    void testMouseLeaveAndSetOnMouseLeave() {
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.mouseLeave(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.setOnMouseLeave(mockOnMouseLeaveAction));

        finiteAnimationRenderableWithMouseEvents.mouseLeave(TIMESTAMP);

        verify(mockOnMouseLeaveAction, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP, finiteAnimationRenderableWithMouseEvents)));

        //noinspection unchecked
        Action<MouseEventInputs> newOnMouseLeave = mock(Action.class);
        finiteAnimationRenderableWithMouseEvents.setOnMouseLeave(newOnMouseLeave);
        finiteAnimationRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 1);

        verify(newOnMouseLeave, times(1)).run(eq(MouseEventInputs.of(TIMESTAMP + 1, finiteAnimationRenderableWithMouseEvents)));
    }

    @Test
    void testMouseLeaveActionId() {
        String mouseLeaveActionId = "mouseLeaveActionId";

        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents.mouseLeaveActionId());

        finiteAnimationRenderableWithMouseEvents.setOnMouseLeave(null);

        assertNull(finiteAnimationRenderableWithMouseEvents.mouseLeaveActionId());

        finiteAnimationRenderableWithMouseEvents
                .setOnMouseLeave(new FakeAction<>(mouseLeaveActionId));

        assertEquals(mouseLeaveActionId,
                finiteAnimationRenderableWithMouseEvents.mouseLeaveActionId());
    }

    @Test
    void testProvide() {
        int msAfterStartTimestampForMidpointFrame = 333;

        finiteAnimationRenderableWithMouseEvents.provide(START_TIMESTAMP - 1);
        finiteAnimationRenderableWithMouseEvents.provide(START_TIMESTAMP);
        finiteAnimationRenderableWithMouseEvents
                .provide(START_TIMESTAMP + msAfterStartTimestampForMidpointFrame);
        finiteAnimationRenderableWithMouseEvents.provide(START_TIMESTAMP + ANIMATION_DURATION);
        finiteAnimationRenderableWithMouseEvents
                .provide(START_TIMESTAMP + ANIMATION_DURATION + 1);

        finiteAnimationRenderableWithoutMouseEvents.provide(START_TIMESTAMP - 1);
        finiteAnimationRenderableWithoutMouseEvents.provide(START_TIMESTAMP);
        finiteAnimationRenderableWithoutMouseEvents
                .provide(START_TIMESTAMP + msAfterStartTimestampForMidpointFrame);
        finiteAnimationRenderableWithoutMouseEvents.provide(START_TIMESTAMP + ANIMATION_DURATION);
        finiteAnimationRenderableWithoutMouseEvents
                .provide(START_TIMESTAMP + ANIMATION_DURATION + 1);

        assertEquals(5, ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.size());
        assertEquals(0,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(0).item1());
        assertEquals(0,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(1).item1());
        assertEquals(msAfterStartTimestampForMidpointFrame,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(2).item1());
        assertEquals(ANIMATION_DURATION,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(3).item1());
        assertEquals(ANIMATION_DURATION,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(4).item1());

        assertEquals(5, ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.size());
        assertEquals(0,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(0).item1());
        assertEquals(0,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(1).item1());
        assertEquals(msAfterStartTimestampForMidpointFrame,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(2).item1());
        assertEquals(ANIMATION_DURATION,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(3).item1());
        assertEquals(ANIMATION_DURATION,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(4).item1());
    }

    @Test
    void testProvideWhenPaused() {
        finiteAnimationRenderableWithMouseEvents.reportPause(PAUSED_TIMESTAMP_2);
        finiteAnimationRenderableWithoutMouseEvents.reportPause(PAUSED_TIMESTAMP_2);

        finiteAnimationRenderableWithMouseEvents.provide(789789L);
        finiteAnimationRenderableWithoutMouseEvents.provide(789789L);

        assertEquals(1, ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.size());
        assertEquals(PAUSED_TIMESTAMP_2 - START_TIMESTAMP,
                (int) ANIMATION_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(0).item1());
        assertEquals(1, ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.size());
        assertEquals(PAUSED_TIMESTAMP_2 - START_TIMESTAMP,
                (int) ANIMATION_NOT_SUPPORTING_MOUSE_EVENTS.SnippetsProvided.get(0).item1());
    }

    @Test
    void testUnpauseUpdatesStartAndEndTimestamps() {
        long pauseTimestamp = 777777L;
        long unpauseTimestamp = 888888L;

        finiteAnimationRenderableWithMouseEvents.reportPause(pauseTimestamp);
        finiteAnimationRenderableWithoutMouseEvents.reportPause(pauseTimestamp);
        finiteAnimationRenderableWithMouseEvents.reportUnpause(unpauseTimestamp);
        finiteAnimationRenderableWithoutMouseEvents.reportUnpause(unpauseTimestamp);

        assertEquals(START_TIMESTAMP + (unpauseTimestamp - pauseTimestamp),
                finiteAnimationRenderableWithMouseEvents.startTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION +
                        (unpauseTimestamp - pauseTimestamp),
                finiteAnimationRenderableWithMouseEvents.endTimestamp());
        assertEquals(START_TIMESTAMP + (unpauseTimestamp - pauseTimestamp),
                finiteAnimationRenderableWithoutMouseEvents.startTimestamp());
        assertEquals(START_TIMESTAMP + ANIMATION_DURATION +
                        (unpauseTimestamp - pauseTimestamp),
                finiteAnimationRenderableWithoutMouseEvents.endTimestamp());
    }

    @Test
    void testMouseEventPauseUnpauseAndProvideCallsToOutdatedTimestamps() {
        long timestamp = 456456L;
        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(0f, 0f, 1f, 1f);
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet = new FakeAnimationFrameSnippet();

        finiteAnimationRenderableWithMouseEvents.press(0, timestamp);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp - 1));

        finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 1);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp));

        finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 2);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 1));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 1));

        finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 3);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 2));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 2));

        finiteAnimationRenderableWithMouseEvents.capturesMouseEventAtPoint(Vertex.of(0f, 0f),
                timestamp + 4);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 3));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 3));

        finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 5);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 4));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 4));

        finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 6);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 5));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 5));

        finiteAnimationRenderableWithMouseEvents.provide(timestamp + 7);
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.press(0, timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.release(0, timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseOver(timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.mouseLeave(timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportPause(timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.reportUnpause(timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents.provide(timestamp + 6));
        assertThrows(IllegalArgumentException.class, () ->
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0f, 0f), timestamp + 6));
    }

    @Test
    void testColorShiftProviders() {
        assertSame(COLOR_SHIFT_PROVIDERS,
                finiteAnimationRenderableWithMouseEvents.colorShiftProviders());
        assertSame(COLOR_SHIFT_PROVIDERS,
                finiteAnimationRenderableWithoutMouseEvents.colorShiftProviders());
    }

    @Test
    void testGetAndSetRenderingAreaProvider() {
        assertSame(RENDERING_AREA_PROVIDER,
                finiteAnimationRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(RENDERING_AREA_PROVIDER,
                finiteAnimationRenderableWithoutMouseEvents.getRenderingDimensionsProvider());

        FakeProviderAtTime<FloatBox> newRenderingDimensionsProvider = new FakeProviderAtTime<>();

        finiteAnimationRenderableWithMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);
        finiteAnimationRenderableWithoutMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);

        assertSame(newRenderingDimensionsProvider,
                finiteAnimationRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(newRenderingDimensionsProvider,
                finiteAnimationRenderableWithoutMouseEvents.getRenderingDimensionsProvider());
    }

    @Test
    void testGetAndSetZ() {
        assertEquals(Z, finiteAnimationRenderableWithMouseEvents.getZ());
        assertEquals(Z, finiteAnimationRenderableWithoutMouseEvents.getZ());

        int newZ = 456;

        finiteAnimationRenderableWithMouseEvents.setZ(newZ);

        finiteAnimationRenderableWithoutMouseEvents.setZ(newZ);

        assertEquals(newZ, finiteAnimationRenderableWithMouseEvents.getZ());
        assertEquals(newZ, finiteAnimationRenderableWithoutMouseEvents.getZ());

        verify(mockContainingStack, times(1)).add(finiteAnimationRenderableWithMouseEvents);
        verify(mockContainingStack, times(1)).add(finiteAnimationRenderableWithoutMouseEvents);
    }

    @Test
    void testCapturesMouseEventAtPoint() {
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet = new FakeAnimationFrameSnippet();
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.LeftX = 250;
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.RightX = 750;
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.TopY = 1000;
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.BottomY = 2500;
        ((FakeImage) ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.Image).Width = 1000;
        ((FakeImage) ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.Image).Height = 3000;
        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(-0.5f, -2f, 0.75f, 0.5f);

        boolean capturesMouseEventAtPoint =
                finiteAnimationRenderableWithMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(0.123f, 0.456f), 789L);

        assertTrue(capturesMouseEventAtPoint);
        ArrayList<Pair<Integer, Integer>> capturesMouseEventsAtPixelInputs =
                ((FakeImage) ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.Image)
                        .CapturesMouseEventsAtPixelInputs;
        assertEquals(1, capturesMouseEventsAtPixelInputs.size());
        assertEquals(
                (int) ((((0.123f - (-0.5f)) / (0.75f - (-0.5f))) * (750 - 250)) + 250),
                (int) capturesMouseEventsAtPixelInputs.get(0).item1());
        assertEquals(
                (int) ((((0.456f - (-2.0f)) / (0.5f - (-2.0f))) * (2500 - 1000)) + 1000),
                (int) capturesMouseEventsAtPixelInputs.get(0).item2());
        assertEquals(1, RENDERING_AREA_PROVIDER.TimestampInputs.size());
        assertEquals(789L, (long) RENDERING_AREA_PROVIDER.TimestampInputs.get(0));
    }

    @Test
    void testCapturesMouseEventAtPointDoesNotExceedRenderingBoundaries() {
        ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet = new FakeAnimationFrameSnippet();
        ((FakeImage) ANIMATION_SUPPORTING_MOUSE_EVENTS.AnimationFrameSnippet.Image)
                .SupportsMouseEventCapturing = true;
        RENDERING_AREA_PROVIDER.ProvidedValue = WHOLE_SCREEN;
        when(mockRenderingBoundaries.currentBoundaries()).thenReturn(new FloatBox() {
            @Override
            public float leftX() {
                return 0f;
            }

            @Override
            public float topY() {
                return 0f;
            }

            @Override
            public float rightX() {
                return 0.5f;
            }

            @Override
            public float bottomY() {
                return 1f;
            }

            @Override
            public float width() {
                return 0;
            }

            @Override
            public float height() {
                return 0;
            }

            @Override
            public FloatBox intersection(FloatBox floatBox) throws IllegalArgumentException {
                return null;
            }

            @Override
            public FloatBox translate(float v, float v1) {
                return null;
            }

            @Override
            public String getInterfaceName() {
                return null;
            }
        });

        assertTrue(finiteAnimationRenderableWithMouseEvents.capturesMouseEventAtPoint(
                Vertex.of(0.499f, 0f), MOST_RECENT_TIMESTAMP));
        assertFalse(finiteAnimationRenderableWithMouseEvents.capturesMouseEventAtPoint(
                Vertex.of(0.501f, 0f), MOST_RECENT_TIMESTAMP));
    }

    @Test
    void testCapturesMouseEventAtPointWithInvalidParams() {
        float verySmallNumber = 0.0001f;

        assertThrows(UnsupportedOperationException.class, () ->
                finiteAnimationRenderableWithoutMouseEvents
                        .capturesMouseEventAtPoint(Vertex.of(.5f, .5f), 0L));

        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(.5f, .5f, 1.5f, 1.5f);

        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(.5f - verySmallNumber, .75f), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(1f + verySmallNumber, .75f), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(.75f, .5f - verySmallNumber), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(.75f, 1.5f + verySmallNumber), 0L));

        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(-0.5f, -0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(0f - verySmallNumber, .25f), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(0.5f + verySmallNumber, .25f), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(.25f, 0f - verySmallNumber), 0L));
        assertThrows(IllegalArgumentException.class, () -> finiteAnimationRenderableWithMouseEvents
                .capturesMouseEventAtPoint(Vertex.of(.25f, 0.5f + verySmallNumber), 0L));
    }

    @Test
    void testContainingStack() {
        assertSame(mockContainingStack,
                finiteAnimationRenderableWithMouseEvents.containingStack());
        assertSame(mockContainingStack,
                finiteAnimationRenderableWithoutMouseEvents.containingStack());
    }

    @Test
    void testDelete() {
        finiteAnimationRenderableWithMouseEvents.delete();
        assertNull(finiteAnimationRenderableWithMouseEvents.containingStack());
        verify(mockContainingStack, times(1)).remove(finiteAnimationRenderableWithMouseEvents);

        finiteAnimationRenderableWithoutMouseEvents.delete();
        assertNull(finiteAnimationRenderableWithoutMouseEvents.containingStack());
        verify(mockContainingStack, times(1)).remove(finiteAnimationRenderableWithoutMouseEvents);
    }

    @Test
    void testUuid() {
        assertSame(UUID, finiteAnimationRenderableWithMouseEvents.uuid());
        assertSame(UUID, finiteAnimationRenderableWithoutMouseEvents.uuid());
    }

    @Test
    void testArchetype() {
        assertThrows(UnsupportedOperationException.class,
                finiteAnimationRenderableWithMouseEvents::archetype);
        assertThrows(UnsupportedOperationException.class,
                finiteAnimationRenderableWithoutMouseEvents::archetype);
    }
}
