package inaugural.soliloquy.graphics.renderables.providers;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.NearestFloorAndCeilingTree;
import soliloquy.specs.graphics.renderables.providers.AnimatedMouseCursorProvider;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnimatedMouseCursorProviderImpl extends AbstractLoopingProvider<Long>
        implements AnimatedMouseCursorProvider {
    private final String ID;
    private final HashMap<Integer, Long> CURSORS_AT_MS;
    private final NearestFloorAndCeilingTree MS_POSITIONS;

    private final static UUID PLACEHOLDER_UUID = new UUID(0, 0);

    public AnimatedMouseCursorProviderImpl(String id, Map<Integer, Long> cursorsAtMs,
                                           int periodDuration, int periodModuloOffset,
                                           Long pauseTimestamp, Long mostRecentTimestamp) {
        super(PLACEHOLDER_UUID, periodDuration, periodModuloOffset, pauseTimestamp,
                mostRecentTimestamp, 0L);
        ID = Check.ifNullOrEmpty(id, "id");
        Check.ifNull(cursorsAtMs, "cursorsAtMs");
        if (cursorsAtMs.isEmpty()) {
            throw new IllegalArgumentException(
                    "AnimatedMouseCursorProviderImpl: cursorAtMs cannot be empty");
        }
        if (!cursorsAtMs.containsKey(0)) {
            throw new IllegalArgumentException(
                    "AnimatedMouseCursorProviderImpl: A value must be present at 0ms");
        }
        cursorsAtMs.forEach((ms, cursor) -> {
            Check.ifNull(ms, "ms within cursorsAtMs");
            Check.ifNull(cursor, "cursor within cursorsAtMs");
        });
        CURSORS_AT_MS = new HashMap<>(cursorsAtMs);
        MS_POSITIONS = NearestFloorAndCeilingTree.FromIntegers(cursorsAtMs.keySet());
        Check.throwOnSecondLte(MS_POSITIONS.MaximumValue, periodDuration,
                "maximum ms within cursorsAtMs", "periodDuration");
    }

    @Override
    protected Long provideValueAtMsWithinPeriod(int msWithinPeriod) {
        return CURSORS_AT_MS.get((int) MS_POSITIONS.getNearestFloor(msWithinPeriod));
    }

    @Override
    public String getInterfaceName() {
        return ProviderAtTime.class.getCanonicalName() + "<" + long.class.getCanonicalName() + ">";
    }

    @Override
    public String id() throws IllegalStateException {
        return ID;
    }

    @Override
    public UUID uuid() throws IllegalArgumentException, UnsupportedOperationException {
        throw new UnsupportedOperationException(
                "AnimatedMouseCursorProviderImpl supports id instead of uuid");
    }

    @Override
    public Object representation() {
        return new HashMap<>(CURSORS_AT_MS);
    }
}
