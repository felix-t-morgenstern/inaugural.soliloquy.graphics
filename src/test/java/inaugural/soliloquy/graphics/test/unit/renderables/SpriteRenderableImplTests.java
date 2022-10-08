package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.SpriteRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.common.valueobjects.Pair;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
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
import static inaugural.soliloquy.tools.random.Random.randomLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SpriteRenderableImplTests {
    private final FakeSprite SPRITE_SUPPORTING_MOUSE_EVENTS = new FakeSprite(new FakeImage(true));
    private final FakeSprite SPRITE_NOT_SUPPORTING_MOUSE_EVENTS =
            new FakeSprite(new FakeImage(false));
    private final FakeProviderAtTime<Float> BORDER_THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Color> BORDER_COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final HashMap<Integer, Action<Long>> ON_PRESS_ACTIONS = new HashMap<>();
    private final FakeAction<Long> ON_PRESS_ACTION = new FakeAction<>();
    private final FakeAction<Long> ON_MOUSE_OVER = new FakeAction<>();
    private final FakeAction<Long> ON_MOUSE_LEAVE = new FakeAction<>();
    private final ArrayList<ProviderAtTime<ColorShift>> COLOR_SHIFT_PROVIDERS = new ArrayList<>();
    private final FakeStaticProvider<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeStaticProvider<>(null);
    private final int Z = randomInt();
    private final long TIMESTAMP = randomLong();

    private final UUID UUID = java.util.UUID.randomUUID();

    @Mock private RenderableStack _mockContainingStack;
    @Mock private RenderingBoundaries _mockRenderingBoundaries;

    private SpriteRenderable _spriteRenderableWithMouseEvents;
    private SpriteRenderable _spriteRenderableWithoutMouseEvents;

    @BeforeEach
    void setUp() {
        _mockContainingStack = mock(RenderableStack.class);
        _mockRenderingBoundaries = mock(RenderingBoundaries.class);
        when(_mockRenderingBoundaries.currentBoundaries()).thenReturn(WHOLE_SCREEN);

        _spriteRenderableWithMouseEvents = new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE,
                COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries);
        _spriteRenderableWithoutMouseEvents = new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                _mockContainingStack, _mockRenderingBoundaries);
    }

    @Test
    void testConstructorWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE,
                COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries
        ));
        // NB: These following two constructors should _not_ throw exceptions
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, null, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, null,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, null,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                null, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, null, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, null, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, null
        ));

        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS,
                RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, null, BORDER_COLOR_PROVIDER,
                COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, null,
                COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, null, RENDERING_AREA_PROVIDER, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, null, Z, UUID, _mockContainingStack,
                _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, null,
                _mockContainingStack, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                null, _mockRenderingBoundaries
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFT_PROVIDERS, RENDERING_AREA_PROVIDER, Z, UUID,
                _mockContainingStack, null
        ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(SpriteRenderable.class.getCanonicalName(),
                _spriteRenderableWithMouseEvents.getInterfaceName());
    }

    @Test
    void testGetAndSetSprite() {
        assertSame(SPRITE_SUPPORTING_MOUSE_EVENTS, _spriteRenderableWithMouseEvents.getSprite());
        assertSame(SPRITE_NOT_SUPPORTING_MOUSE_EVENTS,
                _spriteRenderableWithoutMouseEvents.getSprite());

        FakeSprite newSprite = new FakeSprite(new FakeImage(true));

        _spriteRenderableWithMouseEvents.setSprite(newSprite);
        _spriteRenderableWithoutMouseEvents.setSprite(newSprite);

        assertSame(newSprite, _spriteRenderableWithMouseEvents.getSprite());
        assertSame(newSprite, _spriteRenderableWithoutMouseEvents.getSprite());
    }

    @Test
    void testSetSpriteWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setSprite(null));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithoutMouseEvents.setSprite(null));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setSprite(new FakeSprite(new FakeImage(false))));
    }

    @Test
    void testGetAndSetBorderThicknessProvider() {
        assertSame(BORDER_THICKNESS_PROVIDER,
                _spriteRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(BORDER_THICKNESS_PROVIDER,
                _spriteRenderableWithoutMouseEvents.getBorderThicknessProvider());

        FakeProviderAtTime<Float> newBorderThicknessProvider = new FakeProviderAtTime<>();

        _spriteRenderableWithMouseEvents.setBorderThicknessProvider(newBorderThicknessProvider);
        _spriteRenderableWithoutMouseEvents.setBorderThicknessProvider(newBorderThicknessProvider);

        assertSame(newBorderThicknessProvider,
                _spriteRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(newBorderThicknessProvider,
                _spriteRenderableWithoutMouseEvents.getBorderThicknessProvider());
    }

    @Test
    void testSetBorderThicknessProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setBorderThicknessProvider(null));
    }

    @Test
    void testGetAndSetBorderColorProvider() {
        assertSame(BORDER_COLOR_PROVIDER,
                _spriteRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(BORDER_COLOR_PROVIDER,
                _spriteRenderableWithoutMouseEvents.getBorderColorProvider());

        FakeProviderAtTime<Color> newBorderColorProvider = new FakeProviderAtTime<>();

        _spriteRenderableWithMouseEvents.setBorderColorProvider(newBorderColorProvider);
        _spriteRenderableWithoutMouseEvents.setBorderColorProvider(newBorderColorProvider);

        assertSame(newBorderColorProvider,
                _spriteRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(newBorderColorProvider,
                _spriteRenderableWithoutMouseEvents.getBorderColorProvider());
    }

    @Test
    void testSetBorderColorProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setBorderColorProvider(null));
    }

    @Test
    void testGetAndSetCapturesMouseEvents() {
        assertTrue(_spriteRenderableWithMouseEvents.getCapturesMouseEvents());
        assertFalse(_spriteRenderableWithoutMouseEvents.getCapturesMouseEvents());

        _spriteRenderableWithMouseEvents.setCapturesMouseEvents(false);
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setCapturesMouseEvents(false));

        assertFalse(_spriteRenderableWithMouseEvents.getCapturesMouseEvents());
    }

    @Test
    void testPressAndSetOnPress() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.press(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnPress(2, new FakeAction<>()));

        _spriteRenderableWithMouseEvents.setOnPress(2, ON_PRESS_ACTION);

        _spriteRenderableWithMouseEvents.press(2, TIMESTAMP);
        assertEquals(1, ON_PRESS_ACTION.NumberOfTimesCalled);
        assertEquals(1, ON_PRESS_ACTION.Inputs.size());
        assertEquals(TIMESTAMP, (long) ON_PRESS_ACTION.Inputs.get(0));

        FakeAction<Long> newOnPress = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnPress(2, newOnPress);

        _spriteRenderableWithMouseEvents.press(2, TIMESTAMP + 1);

        assertEquals(1, newOnPress.NumberOfTimesCalled);
        assertEquals(1, newOnPress.Inputs.size());
        assertEquals(TIMESTAMP + 1, (long) newOnPress.Inputs.get(0));

        _spriteRenderableWithMouseEvents.press(0, TIMESTAMP + 2);

        assertEquals(1, newOnPress.NumberOfTimesCalled);
        assertEquals(1, newOnPress.Inputs.size());
        assertEquals(TIMESTAMP + 1, (long) newOnPress.Inputs.get(0));
    }

    @Test
    void testPressActionIds() {
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        _spriteRenderableWithMouseEvents.setOnPress(0, new FakeAction<>(id1));
        _spriteRenderableWithMouseEvents.setOnPress(2, new FakeAction<>(id2));
        _spriteRenderableWithMouseEvents.setOnPress(7, new FakeAction<>(id3));
        _spriteRenderableWithMouseEvents.setOnPress(2, null);

        Map<Integer, String> pressActionIds = _spriteRenderableWithMouseEvents.pressActionIds();

        assertNotNull(pressActionIds);
        assertEquals(2, pressActionIds.size());
        assertEquals(id1, pressActionIds.get(0));
        assertEquals(id3, pressActionIds.get(7));
    }

    @Test
    void testReleaseAndSetOnRelease() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.release(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnRelease(2, new FakeAction<>()));

        _spriteRenderableWithMouseEvents.release(2, TIMESTAMP);

        FakeAction<Long> newOnRelease = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnRelease(2, newOnRelease);

        _spriteRenderableWithMouseEvents.release(2, TIMESTAMP + 1);
        assertEquals(1, newOnRelease.NumberOfTimesCalled);
        assertEquals(1, newOnRelease.Inputs.size());
        assertEquals(TIMESTAMP + 1, (long) newOnRelease.Inputs.get(0));
    }

    @Test
    void testReleaseActionIds() {
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        _spriteRenderableWithMouseEvents.setOnRelease(0, new FakeAction<>(id1));
        _spriteRenderableWithMouseEvents.setOnRelease(2, new FakeAction<>(id2));
        _spriteRenderableWithMouseEvents.setOnRelease(7, new FakeAction<>(id3));
        _spriteRenderableWithMouseEvents.setOnRelease(2, null);

        Map<Integer, String> releaseActionIds =
                _spriteRenderableWithMouseEvents.releaseActionIds();

        assertNotNull(releaseActionIds);
        assertEquals(2, releaseActionIds.size());
        assertEquals(id1, releaseActionIds.get(0));
        assertEquals(id3, releaseActionIds.get(7));
    }

    @Test
    void testPressOrReleaseMethodsWithInvalidButtons() {

        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setOnPress(-1, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setOnRelease(-1, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(-1, TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(-1, TIMESTAMP + 1));

        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setOnPress(8, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setOnRelease(8, new FakeAction<>()));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(8, TIMESTAMP + 2));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(8, TIMESTAMP + 3));
    }

    @Test
    void testMouseOverAndSetOnMouseOver() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseOver(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnMouseOver(ON_MOUSE_OVER));

        _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP);
        assertEquals(1, ON_MOUSE_OVER.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_OVER.Inputs.size());
        assertEquals(TIMESTAMP, (long) ON_MOUSE_OVER.Inputs.get(0));

        FakeAction<Long> newOnMouseOver = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnMouseOver(newOnMouseOver);

        _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP + 1);
        assertEquals(1, newOnMouseOver.NumberOfTimesCalled);
        assertEquals(1, newOnMouseOver.Inputs.size());
        assertEquals(TIMESTAMP + 1, (long) newOnMouseOver.Inputs.get(0));
    }

    @Test
    void testMouseOverActionId() {
        String mouseOverActionId = "mouseOverActionId";

        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseOverActionId());

        _spriteRenderableWithMouseEvents.setOnMouseOver(null);

        assertNull(_spriteRenderableWithMouseEvents.mouseOverActionId());

        _spriteRenderableWithMouseEvents.setOnMouseOver(new FakeAction<>(mouseOverActionId));

        assertEquals(mouseOverActionId, _spriteRenderableWithMouseEvents.mouseOverActionId());
    }

    @Test
    void testMouseLeaveAndSetOnMouseLeave() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseLeave(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnMouseLeave(ON_MOUSE_LEAVE));

        _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP);
        assertEquals(1, ON_MOUSE_LEAVE.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_LEAVE.Inputs.size());
        assertEquals(TIMESTAMP, (long) ON_MOUSE_LEAVE.Inputs.get(0));

        FakeAction<Long> newOnMouseLeave = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnMouseLeave(newOnMouseLeave);

        _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 1);
        assertEquals(1, newOnMouseLeave.NumberOfTimesCalled);
        assertEquals(1, newOnMouseLeave.Inputs.size());
        assertEquals(TIMESTAMP + 1, (long) newOnMouseLeave.Inputs.get(0));
    }

    @Test
    void testMouseLeaveActionId() {
        String mouseLeaveActionId = "mouseLeaveActionId";

        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseLeaveActionId());

        _spriteRenderableWithMouseEvents.setOnMouseLeave(null);

        assertNull(_spriteRenderableWithMouseEvents.mouseLeaveActionId());

        _spriteRenderableWithMouseEvents.setOnMouseLeave(new FakeAction<>(mouseLeaveActionId));

        assertEquals(mouseLeaveActionId, _spriteRenderableWithMouseEvents.mouseLeaveActionId());
    }

    @Test
    void testMouseEventCallsToOutdatedTimestamps() {
        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(0f, 0f, 1f, 1f);

        _spriteRenderableWithMouseEvents.press(0, TIMESTAMP);
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(0, TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.release(0, TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP - 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP - 1));

        _spriteRenderableWithMouseEvents.release(0, TIMESTAMP + 1);
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(0, TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.release(0, TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP));

        _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP + 2);
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(0, TIMESTAMP + 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.release(0, TIMESTAMP + 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP + 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 1));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP + 1));

        _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 3);
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(0, TIMESTAMP + 2));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.release(0, TIMESTAMP + 2));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP + 2));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 2));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP + 2));

        _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP + 4);
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.press(0, TIMESTAMP + 3));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.release(0, TIMESTAMP + 3));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseOver(TIMESTAMP + 3));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.mouseLeave(TIMESTAMP + 3));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.capturesMouseEventAtPoint(0f, 0f, TIMESTAMP + 3));
    }

    @Test
    void testColorShiftProviders() {
        assertSame(COLOR_SHIFT_PROVIDERS,
                _spriteRenderableWithMouseEvents.colorShiftProviders());
        assertSame(COLOR_SHIFT_PROVIDERS,
                _spriteRenderableWithoutMouseEvents.colorShiftProviders());
    }

    @Test
    void testGetAndSetRenderingAreaProvider() {
        assertSame(RENDERING_AREA_PROVIDER,
                _spriteRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(RENDERING_AREA_PROVIDER,
                _spriteRenderableWithoutMouseEvents.getRenderingDimensionsProvider());

        FakeProviderAtTime<FloatBox> newRenderingDimensionsProvider = new FakeProviderAtTime<>();

        _spriteRenderableWithMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);
        _spriteRenderableWithoutMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);

        assertSame(newRenderingDimensionsProvider,
                _spriteRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(newRenderingDimensionsProvider,
                _spriteRenderableWithoutMouseEvents.getRenderingDimensionsProvider());
    }

    @Test
    void testGetAndSetZ() {
        assertEquals(Z, _spriteRenderableWithMouseEvents.getZ());
        assertEquals(Z, _spriteRenderableWithoutMouseEvents.getZ());

        int newZ = 456;

        _spriteRenderableWithMouseEvents.setZ(newZ);
        _spriteRenderableWithoutMouseEvents.setZ(newZ);

        assertEquals(newZ, _spriteRenderableWithMouseEvents.getZ());
        assertEquals(newZ, _spriteRenderableWithoutMouseEvents.getZ());

        verify(_mockContainingStack, times(1)).add(_spriteRenderableWithMouseEvents);
        verify(_mockContainingStack, times(1)).add(_spriteRenderableWithoutMouseEvents);
    }

    @Test
    void testCapturesMouseEventAtPoint() {
        SPRITE_SUPPORTING_MOUSE_EVENTS.LeftX = 250;
        SPRITE_SUPPORTING_MOUSE_EVENTS.RightX = 750;
        SPRITE_SUPPORTING_MOUSE_EVENTS.TopY = 1000;
        SPRITE_SUPPORTING_MOUSE_EVENTS.BottomY = 2500;
        ((FakeImage) SPRITE_SUPPORTING_MOUSE_EVENTS.Image).Width = 1000;
        ((FakeImage) SPRITE_SUPPORTING_MOUSE_EVENTS.Image).Height = 3000;
        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(-0.5f, -2f, 0.75f, 0.5f);

        boolean capturesMouseEventAtPoint = _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(0.123f, 0.456f, TIMESTAMP);

        assertTrue(capturesMouseEventAtPoint);
        ArrayList<Pair<Integer, Integer>> capturesMouseEventsAtPixelInputs =
                ((FakeImage) SPRITE_SUPPORTING_MOUSE_EVENTS.Image).CapturesMouseEventsAtPixelInputs;
        assertEquals(1, capturesMouseEventsAtPixelInputs.size());
        assertEquals(
                (int) ((((0.123f - (-0.5f)) / (0.75f - (-0.5f))) * (750 - 250)) + 250),
                (int) capturesMouseEventsAtPixelInputs.get(0).getItem1());
        assertEquals(
                (int) ((((0.456f - (-2.0f)) / (0.5f - (-2.0f))) * (2500 - 1000)) + 1000),
                (int) capturesMouseEventsAtPixelInputs.get(0).getItem2());
        assertEquals(1, RENDERING_AREA_PROVIDER.TimestampInputs.size());
        assertEquals(TIMESTAMP, (long) RENDERING_AREA_PROVIDER.TimestampInputs.get(0));
    }

    @Test
    void testCapturesMouseEventsAtPointDoesNotExceedRenderingBoundaries() {
        RENDERING_AREA_PROVIDER.ProvidedValue = WHOLE_SCREEN;
        SPRITE_SUPPORTING_MOUSE_EVENTS.Image = new FakeImage(true);
        when(_mockRenderingBoundaries.currentBoundaries()).thenReturn(new FloatBox() {
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

        assertTrue(_spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(0.499f, 0.5f, TIMESTAMP));
        assertFalse(_spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(0.501f, 0.5f, TIMESTAMP));
    }

    @Test
    void testCapturesMouseEventAtPointWithInvalidParams() {
        float verySmallNumber = 0.0001f;

        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.capturesMouseEventAtPoint(.5f, .5f, 0L));

        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(.5f, .5f, 1.5f, 1.5f);

        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(.5f - verySmallNumber, .75f, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(1f + verySmallNumber, .75f, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(.75f, .5f - verySmallNumber, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(.75f, 1.5f + verySmallNumber, 0L));

        RENDERING_AREA_PROVIDER.ProvidedValue = new FakeFloatBox(-0.5f, -0.5f, 0.5f, 0.5f);

        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(0f - verySmallNumber, .25f, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(0.5f + verySmallNumber, .25f, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(.25f, 0f - verySmallNumber, 0L));
        assertThrows(IllegalArgumentException.class, () -> _spriteRenderableWithMouseEvents
                .capturesMouseEventAtPoint(.25f, 0.5f + verySmallNumber, 0L));
    }

    @Test
    void testDelete() {
        _spriteRenderableWithMouseEvents.delete();
        _spriteRenderableWithoutMouseEvents.delete();

        verify(_mockContainingStack, times(1)).remove(_spriteRenderableWithMouseEvents);
        verify(_mockContainingStack, times(1)).remove(_spriteRenderableWithoutMouseEvents);
    }

    @Test
    void testUuid() {
        assertSame(UUID, _spriteRenderableWithMouseEvents.uuid());
        assertSame(UUID, _spriteRenderableWithoutMouseEvents.uuid());
    }
}