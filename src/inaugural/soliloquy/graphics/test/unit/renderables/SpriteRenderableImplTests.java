package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.SpriteRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.SpriteRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

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
    private final ArrayList<ColorShift> COLOR_SHIFTS = new ArrayList<>();
    private final FakeProviderAtTime<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeProviderAtTime<>();
    private final int Z = 123;
    private final FakeEntityUuid UUID = new FakeEntityUuid();
    private final Consumer<Renderable>
            SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER =
            renderable -> _spriteRenderableWithMouseEventsUpdateZIndexInContainerInput =
                    renderable;
    private final Consumer<Renderable>
            SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER =
            renderable -> _spriteRenderableWithoutMouseEventsUpdateZIndexInContainerInput =
                    renderable;
    private final Consumer<Renderable> SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER =
            renderable -> _spriteRenderableWithMouseEventsRemoveFromContainerInput = renderable;
    private final Consumer<Renderable>
            SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER =
            renderable -> _spriteRenderableWithoutMouseEventsRemoveFromContainerInput = renderable;

    private static Renderable _spriteRenderableWithMouseEventsRemoveFromContainerInput;
    private static Renderable _spriteRenderableWithoutMouseEventsRemoveFromContainerInput;
    private static Renderable _spriteRenderableWithMouseEventsUpdateZIndexInContainerInput;
    private static Renderable _spriteRenderableWithoutMouseEventsUpdateZIndexInContainerInput;

    private SpriteRenderable _spriteRenderableWithMouseEvents;
    private SpriteRenderable _spriteRenderableWithoutMouseEvents;

    @BeforeEach
    void setUp() {
        _spriteRenderableWithMouseEvents = new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, 
                ON_MOUSE_LEAVE, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER);
        _spriteRenderableWithoutMouseEvents = new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER);
    }

    @Test
    void testConstructorWithInvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, 
                COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        // NB: These following two constructors should _not_ throw exceptions
        new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, null, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        );
        new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, null, null,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        );
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, null,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, null, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                null, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, null, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                null,
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                ON_PRESS_ACTIONS, null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, 
                RENDERING_AREA_PROVIDER, Z, UUID, 
                SPRITE_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        // NB: These following two constructors should _not_ throw exceptions
        new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, null,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        );
        new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, null,
                null, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        );
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                null, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, null, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, null, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, null,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONTAINER,
                null
        ));
        assertThrows(IllegalArgumentException.class, () -> new SpriteRenderableImpl(
                SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, COLOR_SHIFTS, RENDERING_AREA_PROVIDER, Z, UUID,
                null,
                SPRITE_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONTAINER
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
        assertSame(SPRITE_NOT_SUPPORTING_MOUSE_EVENTS, _spriteRenderableWithoutMouseEvents.getSprite());

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
        _spriteRenderableWithMouseEvents.setBorderThicknessProvider(null);
        _spriteRenderableWithoutMouseEvents.setBorderThicknessProvider(null);
        _spriteRenderableWithMouseEvents.setBorderColorProvider(null);
        _spriteRenderableWithoutMouseEvents.setBorderColorProvider(null);

        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents
                        .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithoutMouseEvents
                        .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER));
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
        _spriteRenderableWithMouseEvents.setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER);
        _spriteRenderableWithoutMouseEvents.setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER);

        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithMouseEvents.setBorderColorProvider(null));
        assertThrows(IllegalArgumentException.class, () ->
                _spriteRenderableWithoutMouseEvents.setBorderColorProvider(null));
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

        long timestamp = 456456L;
        _spriteRenderableWithMouseEvents.press(2, timestamp);
        assertEquals(1, ON_PRESS_ACTION.NumberOfTimesCalled);
        assertEquals(1, ON_PRESS_ACTION.Inputs.size());
        assertEquals(timestamp, (long)ON_PRESS_ACTION.Inputs.get(0));

        FakeAction<Long> newOnPress = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnPress(2, newOnPress);

        _spriteRenderableWithMouseEvents.press(2, timestamp + 1);
        assertEquals(1, newOnPress.NumberOfTimesCalled);
        assertEquals(1, newOnPress.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnPress.Inputs.get(0));
    }

    @Test
    void testReleaseAndSetOnRelease() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.release(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnRelease(2, new FakeAction<>()));

        long timestamp = 456456L;
        _spriteRenderableWithMouseEvents.release(2, timestamp);

        FakeAction<Long> newOnRelease = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnRelease(2, newOnRelease);

        _spriteRenderableWithMouseEvents.release(2, timestamp + 1);
        assertEquals(1, newOnRelease.NumberOfTimesCalled);
        assertEquals(1, newOnRelease.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnRelease.Inputs.get(0));
    }

    @Test
    void testMouseOverAndSetOnMouseOver() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseOver(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnMouseOver(ON_MOUSE_OVER));

        long timestamp = 456456L;
        _spriteRenderableWithMouseEvents.mouseOver(timestamp);
        assertEquals(1, ON_MOUSE_OVER.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_OVER.Inputs.size());
        assertEquals(timestamp, (long)ON_MOUSE_OVER.Inputs.get(0));

        FakeAction<Long> newOnMouseOver = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnMouseOver(newOnMouseOver);

        _spriteRenderableWithMouseEvents.mouseOver(timestamp + 1);
        assertEquals(1, newOnMouseOver.NumberOfTimesCalled);
        assertEquals(1, newOnMouseOver.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnMouseOver.Inputs.get(0));
    }

    @Test
    void testMouseLeaveAndSetOnMouseLeave() {
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.mouseLeave(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _spriteRenderableWithoutMouseEvents.setOnMouseLeave(ON_MOUSE_LEAVE));

        long timestamp = 456456L;
        _spriteRenderableWithMouseEvents.mouseLeave(timestamp);
        assertEquals(1, ON_MOUSE_LEAVE.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_LEAVE.Inputs.size());
        assertEquals(timestamp, (long)ON_MOUSE_LEAVE.Inputs.get(0));

        FakeAction<Long> newOnMouseLeave = new FakeAction<>();
        _spriteRenderableWithMouseEvents.setOnMouseLeave(newOnMouseLeave);

        _spriteRenderableWithMouseEvents.mouseLeave(timestamp + 1);
        assertEquals(1, newOnMouseLeave.NumberOfTimesCalled);
        assertEquals(1, newOnMouseLeave.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnMouseLeave.Inputs.get(0));
    }

    @Test
    void testColorShifts() {
        assertSame(COLOR_SHIFTS, _spriteRenderableWithMouseEvents.colorShifts());
        assertSame(COLOR_SHIFTS, _spriteRenderableWithoutMouseEvents.colorShifts());
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
        assertSame(Z, _spriteRenderableWithMouseEvents.getZ());
        assertSame(Z, _spriteRenderableWithoutMouseEvents.getZ());

        int newZ = 456;

        _spriteRenderableWithMouseEvents.setZ(newZ);
        _spriteRenderableWithoutMouseEvents.setZ(newZ);

        assertEquals(newZ, _spriteRenderableWithMouseEvents.getZ());
        assertEquals(newZ, _spriteRenderableWithoutMouseEvents.getZ());

        assertSame(_spriteRenderableWithMouseEvents,
                _spriteRenderableWithMouseEventsUpdateZIndexInContainerInput);
        assertSame(_spriteRenderableWithoutMouseEvents,
                _spriteRenderableWithoutMouseEventsUpdateZIndexInContainerInput);
    }

    @Test
    void testDelete() {
        _spriteRenderableWithMouseEvents.delete();
        assertSame(_spriteRenderableWithMouseEvents,
                _spriteRenderableWithMouseEventsRemoveFromContainerInput);

        _spriteRenderableWithoutMouseEvents.delete();
        assertSame(_spriteRenderableWithoutMouseEvents,
                _spriteRenderableWithoutMouseEventsRemoveFromContainerInput);
    }

    @Test
    void testUuid() {
        assertSame(UUID, _spriteRenderableWithMouseEvents.uuid());
        assertSame(UUID, _spriteRenderableWithoutMouseEvents.uuid());
    }
}
