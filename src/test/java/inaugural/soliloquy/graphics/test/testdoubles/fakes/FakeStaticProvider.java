package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.graphics.renderables.providers.StaticProvider;

import java.util.ArrayList;
import java.util.UUID;

public class FakeStaticProvider<T> implements StaticProvider<T> {
    public T ProvidedValue;
    public UUID Uuid;
    public ArrayList<Long> TimestampInputs = new ArrayList<>();
    public Long MostRecentTimestamp;

    public FakeStaticProvider(T providedValue) {
        ProvidedValue = providedValue;
    }

    public FakeStaticProvider(T providedValue, UUID uuid) {
        ProvidedValue = providedValue;
        Uuid = uuid;
    }

    // TODO: Ensure in unit tests that Renderers are providing timestamps
    @Override
    public T provide(long timestamp) throws IllegalArgumentException {
        TimestampInputs.add(timestamp);
        return ProvidedValue;
    }

    @Override
    public Object representation() {
        return null;
    }

    @Override
    public void reportPause(long l) throws IllegalArgumentException {

    }

    @Override
    public void reportUnpause(long l) throws IllegalArgumentException {

    }

    @Override
    public Long pausedTimestamp() {
        return null;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }

    @Override
    public T archetype() {
        return ProvidedValue;
    }

    @Override
    public UUID uuid() {
        return Uuid;
    }

    @Override
    public Long mostRecentTimestamp() {
        return MostRecentTimestamp;
    }
}
