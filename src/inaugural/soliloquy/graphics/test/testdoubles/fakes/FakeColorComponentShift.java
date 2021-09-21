package inaugural.soliloquy.graphics.test.testdoubles.fakes;

import soliloquy.specs.graphics.renderables.colorshifting.ColorComponent;
import soliloquy.specs.graphics.renderables.colorshifting.ColorComponentShift;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;

public class FakeColorComponentShift implements ColorComponentShift {
    public ColorComponent ColorComponent;
    public ProviderAtTime<Float> ShiftAmountProvider;
    public boolean OverridesPriorShiftsOfSameType;

    public FakeColorComponentShift(ColorComponent colorComponent, Float value,
                                   boolean overridesPriorShiftsOfSameType) {
        ColorComponent = colorComponent;
        ShiftAmountProvider = new FakeStaticProviderAtTime<>(value);
        OverridesPriorShiftsOfSameType = overridesPriorShiftsOfSameType;
    }

    @Override
    public ColorComponent colorComponent() {
        return ColorComponent;
    }

    @Override
    public ProviderAtTime<Float> shiftAmountProvider() {
        return ShiftAmountProvider;
    }

    @Override
    public boolean overridesPriorShiftsOfSameType() {
        return OverridesPriorShiftsOfSameType;
    }

    @Override
    public String getInterfaceName() {
        return null;
    }
}
