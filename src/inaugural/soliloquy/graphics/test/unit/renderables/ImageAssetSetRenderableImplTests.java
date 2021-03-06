package inaugural.soliloquy.graphics.test.unit.renderables;

import inaugural.soliloquy.graphics.renderables.ImageAssetSetRenderableImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.entities.Action;
import soliloquy.specs.graphics.assets.ImageAssetSet;
import soliloquy.specs.graphics.renderables.ImageAssetSetRenderable;
import soliloquy.specs.graphics.renderables.Renderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.rendering.FloatBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class ImageAssetSetRenderableImplTests {
    private final ImageAssetSet IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS =
            new FakeImageAssetSet(true);
    private final ImageAssetSet IMAGE_ASSET_SET_NOT_SUPPORTING_MOUSE_EVENTS =
            new FakeImageAssetSet(false);
    private final String TYPE = "type";
    private final String DIRECTION = "direction";
    private final HashMap<Integer, Action<Long>> ON_PRESS_ACTIONS = new HashMap<>();
    private final FakeAction<Long> ON_PRESS_ACTION = new FakeAction<>();
    private final FakeAction<Long> ON_MOUSE_OVER = new FakeAction<>();
    private final FakeAction<Long> ON_MOUSE_LEAVE = new FakeAction<>();
    private final ArrayList<ColorShift> COLOR_SHIFTS = new ArrayList<>();
    private final FakeProviderAtTime<Float> BORDER_THICKNESS_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<Color> BORDER_COLOR_PROVIDER = new FakeProviderAtTime<>();
    private final FakeProviderAtTime<FloatBox> RENDERING_AREA_PROVIDER =
            new FakeProviderAtTime<>();
    private final int Z = 123;
    private final FakeEntityUuid UUID = new FakeEntityUuid();
    private final Consumer<Renderable>
            IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER =
            renderable ->
                    _imageAssetSetRenderableWithMouseEventsUpdateZIndexInConsumerInput =
                            renderable;
    private final Consumer<Renderable>
            IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER =
            renderable ->
                    _imageAssetSetRenderableWithoutMouseEventsUpdateZIndexInConsumerInput =
                            renderable;
    private final Consumer<Renderable>
            IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER =
            renderable ->
                    _imageAssetSetRenderableWithMouseEventsRemoveFromConsumerInput = renderable;
    private final Consumer<Renderable>
            IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER =
            renderable ->
                    _imageAssetSetRenderableWithoutMouseEventsRemoveFromConsumerInput = renderable;

    private static Renderable
            _imageAssetSetRenderableWithMouseEventsUpdateZIndexInConsumerInput;
    private static Renderable
            _imageAssetSetRenderableWithoutMouseEventsUpdateZIndexInConsumerInput;
    private static Renderable _imageAssetSetRenderableWithMouseEventsRemoveFromConsumerInput;
    private static Renderable _imageAssetSetRenderableWithoutMouseEventsRemoveFromConsumerInput;

    private ImageAssetSetRenderable _imageAssetSetRenderableWithMouseEvents;
    private ImageAssetSetRenderable _imageAssetSetRenderableWithoutMouseEvents;

    @BeforeEach
    void setUp() {
        ON_PRESS_ACTIONS.put(2, ON_PRESS_ACTION);

        _imageAssetSetRenderableWithMouseEvents = new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER);

        _imageAssetSetRenderableWithoutMouseEvents = new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_NOT_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                null, TYPE, DIRECTION, ON_PRESS_ACTIONS, null, ON_MOUSE_OVER,
                ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER,
                RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_NOT_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS,
                null, ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, null, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        // NB: The following two constructors should _not_ throw exceptions
        new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, null, BORDER_COLOR_PROVIDER,
                RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        );
        new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, null, null,
                RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        );
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                null, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, null, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, null,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                null,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, ON_PRESS_ACTIONS, null,
                ON_MOUSE_OVER, ON_MOUSE_LEAVE, COLOR_SHIFTS, BORDER_THICKNESS_PROVIDER,
                BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITH_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                null
        ));

        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                null, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, null,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        // NB: The following two constructors should _not_ throw exceptions
        new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                null, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        );
        new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                null, null, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        );
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, null, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, null, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, null,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                null,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_REMOVE_FROM_CONSUMER
        ));
        assertThrows(IllegalArgumentException.class, () -> new ImageAssetSetRenderableImpl(
                IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS, TYPE, DIRECTION, COLOR_SHIFTS,
                BORDER_THICKNESS_PROVIDER, BORDER_COLOR_PROVIDER, RENDERING_AREA_PROVIDER, Z, UUID,
                IMAGE_ASSET_SET_RENDERABLE_WITHOUT_MOUSE_EVENTS_UPDATE_Z_INDEX_IN_CONSUMER,
                null
        ));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(ImageAssetSetRenderable.class.getCanonicalName(),
                _imageAssetSetRenderableWithMouseEvents.getInterfaceName());
        assertEquals(ImageAssetSetRenderable.class.getCanonicalName(),
                _imageAssetSetRenderableWithoutMouseEvents.getInterfaceName());
    }

    @Test
    void testGetAndSetImageAssetSet() {
        assertSame(IMAGE_ASSET_SET_SUPPORTING_MOUSE_EVENTS,
                _imageAssetSetRenderableWithMouseEvents.getImageAssetSet());
        assertSame(IMAGE_ASSET_SET_NOT_SUPPORTING_MOUSE_EVENTS,
                _imageAssetSetRenderableWithoutMouseEvents.getImageAssetSet());

        FakeImageAssetSet newImageAssetSet = new FakeImageAssetSet(true);

        _imageAssetSetRenderableWithMouseEvents.setImageAssetSet(newImageAssetSet);
        _imageAssetSetRenderableWithoutMouseEvents.setImageAssetSet(newImageAssetSet);

        assertSame(newImageAssetSet, _imageAssetSetRenderableWithMouseEvents.getImageAssetSet());
        assertSame(newImageAssetSet,
                _imageAssetSetRenderableWithoutMouseEvents.getImageAssetSet());
    }

    @Test
    void testSetImageAssetSetWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithMouseEvents.setImageAssetSet(null));
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setImageAssetSet(null));
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithMouseEvents.setImageAssetSet(
                        new FakeImageAssetSet(false)));
    }

    @Test
    void testGetAndSetType() {
        assertEquals(TYPE, _imageAssetSetRenderableWithMouseEvents.getType());
        assertEquals(TYPE, _imageAssetSetRenderableWithoutMouseEvents.getType());

        _imageAssetSetRenderableWithMouseEvents.setType("");
        _imageAssetSetRenderableWithoutMouseEvents.setType("");

        assertNull(_imageAssetSetRenderableWithMouseEvents.getType());
        assertNull(_imageAssetSetRenderableWithoutMouseEvents.getType());
    }

    @Test
    void testGetAndSetDirection() {
        assertEquals(DIRECTION, _imageAssetSetRenderableWithMouseEvents.getDirection());
        assertEquals(DIRECTION, _imageAssetSetRenderableWithoutMouseEvents.getDirection());

        _imageAssetSetRenderableWithMouseEvents.setDirection("");
        _imageAssetSetRenderableWithoutMouseEvents.setDirection("");

        assertNull(_imageAssetSetRenderableWithMouseEvents.getDirection());
        assertNull(_imageAssetSetRenderableWithoutMouseEvents.getDirection());
    }

    @Test
    void testGetAndSetBorderThicknessProvider() {
        assertSame(BORDER_THICKNESS_PROVIDER,
                _imageAssetSetRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(BORDER_THICKNESS_PROVIDER,
                _imageAssetSetRenderableWithoutMouseEvents.getBorderThicknessProvider());

        FakeProviderAtTime<Float> newBorderThicknessProvider = new FakeProviderAtTime<>();

        _imageAssetSetRenderableWithMouseEvents.setBorderThicknessProvider(newBorderThicknessProvider);
        _imageAssetSetRenderableWithoutMouseEvents.setBorderThicknessProvider(newBorderThicknessProvider);

        assertSame(newBorderThicknessProvider,
                _imageAssetSetRenderableWithMouseEvents.getBorderThicknessProvider());
        assertSame(newBorderThicknessProvider,
                _imageAssetSetRenderableWithoutMouseEvents.getBorderThicknessProvider());
    }

    @Test
    void testSetBorderThicknessProviderWithInvalidParams() {
        _imageAssetSetRenderableWithMouseEvents.setBorderThicknessProvider(null);
        _imageAssetSetRenderableWithoutMouseEvents.setBorderThicknessProvider(null);
        _imageAssetSetRenderableWithMouseEvents.setBorderColorProvider(null);
        _imageAssetSetRenderableWithoutMouseEvents.setBorderColorProvider(null);

        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithMouseEvents
                        .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER));
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents
                        .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER));
    }

    @Test
    void testGetAndSetBorderColorProvider() {
        assertSame(BORDER_COLOR_PROVIDER,
                _imageAssetSetRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(BORDER_COLOR_PROVIDER,
                _imageAssetSetRenderableWithoutMouseEvents.getBorderColorProvider());

        FakeProviderAtTime<Color> newBorderColorProvider = new FakeProviderAtTime<>();

        _imageAssetSetRenderableWithMouseEvents.setBorderColorProvider(newBorderColorProvider);
        _imageAssetSetRenderableWithoutMouseEvents.setBorderColorProvider(newBorderColorProvider);

        assertSame(newBorderColorProvider,
                _imageAssetSetRenderableWithMouseEvents.getBorderColorProvider());
        assertSame(newBorderColorProvider,
                _imageAssetSetRenderableWithoutMouseEvents.getBorderColorProvider());
    }

    @Test
    void testSetBorderColorProviderWithInvalidParams() {
        _imageAssetSetRenderableWithMouseEvents
                .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER);
        _imageAssetSetRenderableWithoutMouseEvents
                .setBorderThicknessProvider(BORDER_THICKNESS_PROVIDER);

        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithMouseEvents.setBorderColorProvider(null));
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setBorderColorProvider(null));
    }

    @Test
    void testGetAndSetCapturesMouseEvents() {
        assertTrue(_imageAssetSetRenderableWithMouseEvents.getCapturesMouseEvents());
        assertFalse(_imageAssetSetRenderableWithoutMouseEvents.getCapturesMouseEvents());

        _imageAssetSetRenderableWithMouseEvents.setCapturesMouseEvents(false);
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setCapturesMouseEvents(false));

        assertFalse(_imageAssetSetRenderableWithMouseEvents.getCapturesMouseEvents());
    }

    @Test
    void testPressAndSetOnPress() {
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.press(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setOnPress(2, new FakeAction<>()));

        long timestamp = 456456L;
        _imageAssetSetRenderableWithMouseEvents.press(2, timestamp);
        assertEquals(1, ON_PRESS_ACTION.NumberOfTimesCalled);
        assertEquals(1, ON_PRESS_ACTION.Inputs.size());
        assertEquals(timestamp, (long)ON_PRESS_ACTION.Inputs.get(0));

        FakeAction<Long> newOnPress = new FakeAction<>();
        _imageAssetSetRenderableWithMouseEvents.setOnPress(2, newOnPress);

        _imageAssetSetRenderableWithMouseEvents.press(2, timestamp + 1);
        assertEquals(1, newOnPress.NumberOfTimesCalled);
        assertEquals(1, newOnPress.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnPress.Inputs.get(0));
    }

    @Test
    void testReleaseAndSetOnRelease() {
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.release(2, 0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setOnRelease(2, new FakeAction<>()));

        long timestamp = 456456L;
        _imageAssetSetRenderableWithMouseEvents.release(2, timestamp);

        FakeAction<Long> newOnRelease = new FakeAction<>();
        _imageAssetSetRenderableWithMouseEvents.setOnRelease(2, newOnRelease);

        _imageAssetSetRenderableWithMouseEvents.release(2, timestamp + 1);
        assertEquals(1, newOnRelease.NumberOfTimesCalled);
        assertEquals(1, newOnRelease.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnRelease.Inputs.get(0));
    }

    @Test
    void testMouseOverAndSetOnMouseOver() {
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.mouseOver(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setOnMouseOver(ON_MOUSE_OVER));

        long timestamp = 456456L;
        _imageAssetSetRenderableWithMouseEvents.mouseOver(timestamp);
        assertEquals(1, ON_MOUSE_OVER.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_OVER.Inputs.size());
        assertEquals(timestamp, (long)ON_MOUSE_OVER.Inputs.get(0));

        FakeAction<Long> newOnMouseOver = new FakeAction<>();
        _imageAssetSetRenderableWithMouseEvents.setOnMouseOver(newOnMouseOver);

        _imageAssetSetRenderableWithMouseEvents.mouseOver(timestamp + 1);
        assertEquals(1, newOnMouseOver.NumberOfTimesCalled);
        assertEquals(1, newOnMouseOver.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnMouseOver.Inputs.get(0));
    }

    @Test
    void testMouseLeaveAndSetOnMouseLeave() {
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.mouseLeave(0L));
        assertThrows(UnsupportedOperationException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setOnMouseLeave(ON_MOUSE_LEAVE));

        long timestamp = 456456L;
        _imageAssetSetRenderableWithMouseEvents.mouseLeave(timestamp);
        assertEquals(1, ON_MOUSE_LEAVE.NumberOfTimesCalled);
        assertEquals(1, ON_MOUSE_LEAVE.Inputs.size());
        assertEquals(timestamp, (long)ON_MOUSE_LEAVE.Inputs.get(0));

        FakeAction<Long> newOnMouseLeave = new FakeAction<>();
        _imageAssetSetRenderableWithMouseEvents.setOnMouseLeave(newOnMouseLeave);

        _imageAssetSetRenderableWithMouseEvents.mouseLeave(timestamp + 1);
        assertEquals(1, newOnMouseLeave.NumberOfTimesCalled);
        assertEquals(1, newOnMouseLeave.Inputs.size());
        assertEquals(timestamp + 1, (long)newOnMouseLeave.Inputs.get(0));
    }

    @Test
    void testColorShifts() {
        assertSame(COLOR_SHIFTS, _imageAssetSetRenderableWithMouseEvents.colorShifts());
        assertSame(COLOR_SHIFTS, _imageAssetSetRenderableWithoutMouseEvents.colorShifts());
    }

    @Test
    void testGetAndSetRenderingDimensionsProvider() {
        assertSame(RENDERING_AREA_PROVIDER,
                _imageAssetSetRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(RENDERING_AREA_PROVIDER,
                _imageAssetSetRenderableWithoutMouseEvents.getRenderingDimensionsProvider());

        FakeProviderAtTime<FloatBox> newRenderingDimensionsProvider = new FakeProviderAtTime<>();

        _imageAssetSetRenderableWithMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);
        _imageAssetSetRenderableWithoutMouseEvents
                .setRenderingDimensionsProvider(newRenderingDimensionsProvider);

        assertSame(newRenderingDimensionsProvider,
                _imageAssetSetRenderableWithMouseEvents.getRenderingDimensionsProvider());
        assertSame(newRenderingDimensionsProvider,
                _imageAssetSetRenderableWithoutMouseEvents.getRenderingDimensionsProvider());
    }

    @Test
    void testSetRenderingDimensionsProviderWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithMouseEvents.setRenderingDimensionsProvider(null));
        assertThrows(IllegalArgumentException.class, () ->
                _imageAssetSetRenderableWithoutMouseEvents.setRenderingDimensionsProvider(null));
    }

    @Test
    void testGetAndSetZ() {
        assertSame(Z, _imageAssetSetRenderableWithMouseEvents.getZ());
        assertSame(Z, _imageAssetSetRenderableWithoutMouseEvents.getZ());

        int newZ = 456;

        _imageAssetSetRenderableWithMouseEvents.setZ(newZ);
        _imageAssetSetRenderableWithoutMouseEvents.setZ(newZ);

        assertEquals(newZ, _imageAssetSetRenderableWithMouseEvents.getZ());
        assertEquals(newZ, _imageAssetSetRenderableWithoutMouseEvents.getZ());

        assertSame(_imageAssetSetRenderableWithMouseEvents,
                _imageAssetSetRenderableWithMouseEventsUpdateZIndexInConsumerInput);
        assertSame(_imageAssetSetRenderableWithoutMouseEvents,
                _imageAssetSetRenderableWithoutMouseEventsUpdateZIndexInConsumerInput);
    }

    @Test
    void testDelete() {
        _imageAssetSetRenderableWithMouseEvents.delete();
        assertSame(_imageAssetSetRenderableWithMouseEvents,
                _imageAssetSetRenderableWithMouseEventsRemoveFromConsumerInput);

        _imageAssetSetRenderableWithoutMouseEvents.delete();
        assertSame(_imageAssetSetRenderableWithoutMouseEvents,
                _imageAssetSetRenderableWithoutMouseEventsRemoveFromConsumerInput);
    }

    @Test
    void testUuid() {
        assertSame(UUID, _imageAssetSetRenderableWithMouseEvents.uuid());
        assertSame(UUID, _imageAssetSetRenderableWithoutMouseEvents.uuid());
    }
}
