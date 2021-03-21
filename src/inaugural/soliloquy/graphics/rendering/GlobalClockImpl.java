package inaugural.soliloquy.graphics.rendering;

import soliloquy.specs.graphics.rendering.GlobalClock;

import java.util.Calendar;
import java.util.TimeZone;

public class GlobalClockImpl implements GlobalClock {
    @SuppressWarnings("FieldCanBeLocal")
    private final String TIME_ZONE = "GMT";

    @Override
    public long globalTimestamp() throws UnsupportedOperationException {
        return Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE)).getTimeInMillis();
    }

    @Override
    public String getInterfaceName() {
        return GlobalClock.class.getCanonicalName();
    }
}
