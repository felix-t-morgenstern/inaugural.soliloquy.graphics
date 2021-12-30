package inaugural.soliloquy.graphics.renderables.providers.factories;

import inaugural.soliloquy.tools.Check;
import inaugural.soliloquy.tools.generic.CanGetInterfaceName;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.renderables.providers.LoopingMovingProvider;
import soliloquy.specs.graphics.renderables.providers.factories.LoopingLinearMovingProviderFactory;

import java.util.Map;
import java.util.function.Function;

public class LoopingLinearMovingProviderFactoryImpl implements LoopingLinearMovingProviderFactory {
    /** @noinspection rawtypes*/
    private final Map<String, Function<EntityUuid, Function<Integer, Function<Integer,
            Function<Map, Function<Long, Function<Long, Function<Object,
                    LoopingMovingProvider>>>>>>>> FACTORIES;

    private static final CanGetInterfaceName CAN_GET_INTERFACE_NAME = new CanGetInterfaceName();

    public LoopingLinearMovingProviderFactoryImpl(
            @SuppressWarnings({"rawtypes", "ConstantConditions"}) Map<String, Function<EntityUuid,
                    Function<Integer, Function<Integer, Function<Map, Function<Long, Function<Long,
                            Function<Object, LoopingMovingProvider>>>>>>>> factories) {
        Check.ifNull(factories, "factories");
        factories.forEach((type, factory) -> {
            Check.ifNullOrEmpty(type, "type within factories");
            Check.ifNull(factory, "factory within factories");
        });
        FACTORIES = factories;
    }

    @Override
    public <T> LoopingMovingProvider<T> make(EntityUuid id, int periodDuration,
                                             int periodModuloOffset,
                                             Map<Integer, T> valuesWithinPeriod,
                                             Long mostRecentTimestamp, Long pausedTimestamp,
                                             T archetype) throws IllegalArgumentException {
        String type = CAN_GET_INTERFACE_NAME.getProperTypeName(archetype);
        //noinspection rawtypes
        Function<EntityUuid, Function<Integer, Function<Integer, Function<Map, Function<Long,
                Function<Long, Function<Object, LoopingMovingProvider>>>>>>> factory =
                FACTORIES.get(type);
        //noinspection unchecked
        return (LoopingMovingProvider<T>)factory
                .apply(id)
                .apply(periodDuration)
                .apply(periodModuloOffset)
                .apply(valuesWithinPeriod)
                .apply(mostRecentTimestamp)
                .apply(pausedTimestamp)
                .apply(archetype);
    }

    @Override
    public String getInterfaceName() {
        return LoopingLinearMovingProviderFactory.class.getCanonicalName();
    }
}
