package inaugural.soliloquy.graphics.test.unit.renderables.providers;

import inaugural.soliloquy.graphics.renderables.providers.GlobalLoopingAnimationImpl;
import inaugural.soliloquy.graphics.test.testdoubles.fakes.FakeAnimation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soliloquy.specs.common.infrastructure.Pair;
import soliloquy.specs.graphics.assets.AnimationFrameSnippet;
import soliloquy.specs.graphics.renderables.providers.GlobalLoopingAnimation;

import static org.junit.jupiter.api.Assertions.*;

class GlobalLoopingAnimationImplTests {
    private final int MS_DURATION = 456;
    private final int PERIOD_MODULO_OFFSET = 123;
    private final String ANIMATION_ID = "animationId";
    private final FakeAnimation ANIMATION = new FakeAnimation(ANIMATION_ID, MS_DURATION, true);

    private GlobalLoopingAnimation _globalLoopingAnimation;

    @BeforeEach
    void setUp() {
        _globalLoopingAnimation = new GlobalLoopingAnimationImpl(ANIMATION, PERIOD_MODULO_OFFSET);
    }

    @Test
    void testConstructorWithInvalidParams() {
        assertThrows(IllegalArgumentException.class, () ->
                new GlobalLoopingAnimationImpl(null, PERIOD_MODULO_OFFSET));
        assertThrows(IllegalArgumentException.class, () ->
                new GlobalLoopingAnimationImpl(ANIMATION, -1));
        assertThrows(IllegalArgumentException.class, () ->
                new GlobalLoopingAnimationImpl(ANIMATION, MS_DURATION));
    }

    @Test
    void testGetInterfaceName() {
        assertEquals(GlobalLoopingAnimation.class.getCanonicalName(),
                _globalLoopingAnimation.getInterfaceName());
    }

    @Test
    void testAnimationId() {
        assertEquals(ANIMATION_ID, _globalLoopingAnimation.animationId());
    }

    @Test
    void testSupportsMouseEvents() {
        assertTrue(_globalLoopingAnimation.supportsMouseEvents());

        ANIMATION.SupportsMouseEventCapturing = false;

        assertFalse(_globalLoopingAnimation.supportsMouseEvents());
    }

    @Test
    void testPeriodModuloOffset() {
        assertEquals(PERIOD_MODULO_OFFSET, _globalLoopingAnimation.periodModuloOffset());
    }

    @Test
    void testProvide() {
        long timestamp = 789789L;
        int expectedFrame = (int)((timestamp + PERIOD_MODULO_OFFSET) % MS_DURATION);

        AnimationFrameSnippet providedSnippet = _globalLoopingAnimation.provide(timestamp);

        assertEquals(1, ANIMATION.SnippetsProvided.size());
        Pair<Integer, AnimationFrameSnippet> providedSnippetWithFrame =
                ANIMATION.SnippetsProvided.get(0);
        assertEquals(expectedFrame, (int)providedSnippetWithFrame.getItem1());
        assertSame(providedSnippetWithFrame.getItem2(), providedSnippet);
    }

    @Test
    void testGetArchetype() {
        assertThrows(UnsupportedOperationException.class, _globalLoopingAnimation::getArchetype);
    }

    @Test
    void testReportPauseAndUnpause() {
        long pauseTimestamp = 10000L;
        long unpauseTimestamp = 10001L;
        int expectedPeriodModuloOffset =
                (PERIOD_MODULO_OFFSET - (int)(unpauseTimestamp - pauseTimestamp) + MS_DURATION)
                        % MS_DURATION;

        assertNull(_globalLoopingAnimation.pausedTimestamp());

        _globalLoopingAnimation.reportPause(pauseTimestamp);

        assertEquals(pauseTimestamp, (long) _globalLoopingAnimation.pausedTimestamp());

        _globalLoopingAnimation.reportUnpause(unpauseTimestamp);

        assertNull(_globalLoopingAnimation.pausedTimestamp());
        assertEquals(expectedPeriodModuloOffset, _globalLoopingAnimation.periodModuloOffset());
    }

    @Test
    void testReportPauseAndUnpauseWithInvalidParams() {
        long timestamp = 123L;

        assertThrows(IllegalArgumentException.class,
                () -> _globalLoopingAnimation.reportUnpause(timestamp));

        _globalLoopingAnimation.reportPause(timestamp);

        assertThrows(IllegalArgumentException.class,
                () -> _globalLoopingAnimation.reportUnpause(timestamp - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _globalLoopingAnimation.reportPause(timestamp));

        _globalLoopingAnimation.reportUnpause(timestamp);


        assertThrows(IllegalArgumentException.class,
                () -> _globalLoopingAnimation.reportPause(timestamp - 1));
        assertThrows(IllegalArgumentException.class,
                () -> _globalLoopingAnimation.reportUnpause(timestamp));
    }

    @Test
    void testTimestampSentToProviderWhenPausedAndUnpaused() {
        long pauseTimestamp = 10000L;
        int expectedMsSentToProviderWhilePaused =
                (int)((pauseTimestamp  + PERIOD_MODULO_OFFSET) % MS_DURATION);
        long unpauseTimestamp = 10500L;
        int periodModuloOffsetAfterUnpaused =
                (PERIOD_MODULO_OFFSET - (int)(unpauseTimestamp - pauseTimestamp) + MS_DURATION)
                        % MS_DURATION;
        long providedTimestampAfterUnpaused = 10525L;
        int expectedMsSentToProviderAfterUnpaused =
                (int)((providedTimestampAfterUnpaused + periodModuloOffsetAfterUnpaused)
                        % MS_DURATION);

        _globalLoopingAnimation.reportPause(pauseTimestamp);

        assertEquals(PERIOD_MODULO_OFFSET, _globalLoopingAnimation.periodModuloOffset());

        AnimationFrameSnippet providedSnippetWhilePaused =
                _globalLoopingAnimation.provide(pauseTimestamp + 1);

        assertEquals(1, ANIMATION.SnippetsProvided.size());
        Pair<Integer, AnimationFrameSnippet> providedSnippetAndMsPositionWhilePaused =
                ANIMATION.SnippetsProvided.get(0);
        assertEquals(expectedMsSentToProviderWhilePaused,
                (int)providedSnippetAndMsPositionWhilePaused.getItem1());
        assertSame(providedSnippetWhilePaused, providedSnippetAndMsPositionWhilePaused.getItem2());

        _globalLoopingAnimation.reportUnpause(unpauseTimestamp);

        assertEquals(periodModuloOffsetAfterUnpaused,
                _globalLoopingAnimation.periodModuloOffset());

        AnimationFrameSnippet providedSnippetAfterUnpaused =
                _globalLoopingAnimation.provide(providedTimestampAfterUnpaused);

        assertEquals(2, ANIMATION.SnippetsProvided.size());
        Pair<Integer, AnimationFrameSnippet> providedSnippetAndMsPositionAfterUnpaused =
                ANIMATION.SnippetsProvided.get(1);
        assertEquals(expectedMsSentToProviderAfterUnpaused,
                (int)providedSnippetAndMsPositionAfterUnpaused.getItem1());
        assertSame(providedSnippetAfterUnpaused,
                providedSnippetAndMsPositionAfterUnpaused.getItem2());
    }

    @Test
    void testReportPauseOrProvideWithOutdatedTimestamp() {
        long timestamp = 123123L;

        _globalLoopingAnimation.provide(timestamp);

        assertThrows(IllegalArgumentException.class, () ->
                _globalLoopingAnimation.provide(timestamp - 1));
        assertThrows(IllegalArgumentException.class, () ->
                _globalLoopingAnimation.reportPause(timestamp - 1));

        _globalLoopingAnimation.reportPause(timestamp + 1);

        assertThrows(IllegalArgumentException.class, () ->
                _globalLoopingAnimation.provide(timestamp));
        assertThrows(IllegalArgumentException.class, () ->
                _globalLoopingAnimation.reportUnpause(timestamp));
    }
}
