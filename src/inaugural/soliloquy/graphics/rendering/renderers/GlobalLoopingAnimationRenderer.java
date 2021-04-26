package inaugural.soliloquy.graphics.rendering.renderers;

import inaugural.soliloquy.tools.Check;
import soliloquy.specs.common.valueobjects.EntityUuid;
import soliloquy.specs.graphics.assets.AnimationFrameSnippet;
import soliloquy.specs.graphics.renderables.GlobalLoopingAnimationRenderable;
import soliloquy.specs.graphics.renderables.colorshifting.ColorShift;
import soliloquy.specs.graphics.renderables.providers.ProviderAtTime;
import soliloquy.specs.graphics.rendering.FloatBox;
import soliloquy.specs.graphics.rendering.RenderingBoundaries;
import soliloquy.specs.graphics.rendering.factories.FloatBoxFactory;

import java.awt.*;
import java.util.List;

public class GlobalLoopingAnimationRenderer extends CanRenderSnippets<GlobalLoopingAnimationRenderable> {
    public GlobalLoopingAnimationRenderer(RenderingBoundaries renderingBoundaries,
                                          FloatBoxFactory floatBoxFactory) {
        super(renderingBoundaries, floatBoxFactory, ARCHETYPE);
    }

    @Override
    public void render(GlobalLoopingAnimationRenderable globalLoopingAnimationRenderable,
                       long timestamp)
            throws IllegalArgumentException {
        Check.ifNull(globalLoopingAnimationRenderable, "globalLoopingAnimationRenderable");

        FloatBox renderingArea =
                Check.ifNull(globalLoopingAnimationRenderable.renderingAreaProvider(),
                        "globalLoopingAnimationRenderable.renderingAreaProvider()")
                        .provide(timestamp);

        validateRenderableWithAreaMembers(renderingArea,
                globalLoopingAnimationRenderable.colorShifts(),
                globalLoopingAnimationRenderable.id(), "globalLoopingAnimationRenderable");

        Check.ifNull(globalLoopingAnimationRenderable.loopingAnimation(),
                "globalLoopingAnimationRenderable.loopingAnimation()");

        validateTimestamp(timestamp, "GlobalLoopingAnimationRenderer");

        super.render(renderingArea,
                globalLoopingAnimationRenderable.loopingAnimation().provide(timestamp),
                Color.WHITE);
    }

    private final static GlobalLoopingAnimationRenderable ARCHETYPE =
            new GlobalLoopingAnimationRenderable() {
                @Override
                public EntityUuid id() {
                    return null;
                }

                @Override
                public ProviderAtTime<FloatBox> renderingAreaProvider() {
                    return null;
                }

                @Override
                public int z() {
                    return 0;
                }

                @Override
                public void delete() {

                }

                @Override
                public boolean capturesMouseEvents() {
                    return false;
                }

                @Override
                public void click() throws UnsupportedOperationException {

                }

                @Override
                public void mouseOver() throws UnsupportedOperationException {

                }

                @Override
                public void mouseLeave() throws UnsupportedOperationException {

                }

                @Override
                public List<ColorShift> colorShifts() {
                    return null;
                }

                @Override
                public ProviderAtTime<AnimationFrameSnippet> loopingAnimation() {
                    return null;
                }

                @Override
                public String getInterfaceName() {
                    return GlobalLoopingAnimationRenderable.class.getCanonicalName();
                }
            };
}
