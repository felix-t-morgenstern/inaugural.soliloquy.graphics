package inaugural.soliloquy.graphics.io;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.timing.TimestampValidator;
import soliloquy.specs.common.valueobjects.Vertex;
import soliloquy.specs.graphics.io.MouseEventHandler;
import soliloquy.specs.graphics.io.MouseListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static inaugural.soliloquy.graphics.api.Constants.ALL_SUPPORTED_MOUSE_BUTTONS;

public class MouseListenerImpl implements MouseListener {
    private final MouseEventHandler MOUSE_EVENT_HANDLER;
    private final TimestampValidator TIMESTAMP_VALIDATOR;
    private final HashMap<Integer, Boolean> MOUSE_BUTTON_STATES;

    public MouseListenerImpl(MouseEventHandler mouseEventHandler) {
        MOUSE_EVENT_HANDLER = Check.ifNull(mouseEventHandler, "mouseEventHandler");

        TIMESTAMP_VALIDATOR = new TimestampValidator(null);

        MOUSE_BUTTON_STATES = new HashMap<>();
        for(int button : ALL_SUPPORTED_MOUSE_BUTTONS) {
            MOUSE_BUTTON_STATES.put(button, false);
        }
    }

    @Override
    public void registerMousePositionAndButtonStates(Vertex position,
                                                     Map<Integer, Boolean> mouseButtonPressStates,
                                                     long timestamp)
            throws IllegalArgumentException {
        TIMESTAMP_VALIDATOR.validateTimestamp(timestamp);
        Check.ifNull(position, "position");
        Check.throwOnLtValue(position.X, 0f, "position.X");
        Check.throwOnGtValue(position.X, 1f, "position.X");
        Check.throwOnLtValue(position.Y, 0f, "position.Y");
        Check.throwOnGtValue(position.Y, 1f, "position.Y");
        Check.ifNull(mouseButtonPressStates, "mouseButtonPressStates");
        if (mouseButtonPressStates.size() != ALL_SUPPORTED_MOUSE_BUTTONS.length) {
            throw new IllegalArgumentException(
                    "MouseListenerImpl.registerMousePositionAndButtonStates: not all supported " +
                            "mouse buttons' states reported");
        }

        HashMap<Integer, MouseEventHandler.EventType> mouseButtonEvents = new HashMap<>();
        mouseButtonPressStates.forEach((button, buttonIsPressedNow) -> {
            Check.ifNull(button, "button");
            if (Arrays.stream(ALL_SUPPORTED_MOUSE_BUTTONS).noneMatch(button::equals)) {
                throw new IllegalArgumentException(
                        "MouseListenerImpl.registerMousePositionAndButtonStates: unsupported " +
                                "mouse button reported (" +
                                button + ")");
            }
            Check.ifNull(buttonIsPressedNow, "buttonIsPressedNow");

            if (MOUSE_BUTTON_STATES.get(button) != buttonIsPressedNow) {
                if (buttonIsPressedNow) {
                    mouseButtonEvents.put(button, MouseEventHandler.EventType.PRESS);
                }
                else {
                    mouseButtonEvents.put(button, MouseEventHandler.EventType.RELEASE);
                }
                MOUSE_BUTTON_STATES.put(button, buttonIsPressedNow);
            }
        });

        MOUSE_EVENT_HANDLER.actOnMouseLocationAndEvents(position, mouseButtonEvents, timestamp);
    }

    @Override
    public String getInterfaceName() {
        return MouseListener.class.getCanonicalName();
    }
}
