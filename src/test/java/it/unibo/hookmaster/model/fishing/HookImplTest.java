package it.unibo.hookmaster.model.fishing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.collision.CollisionPredicate;
import it.unibo.hookmaster.model.collision.CollisionPredicates;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.testutil.FakeCatchable;
import it.unibo.hookmaster.testutil.FakeCollidable;
import it.unibo.hookmaster.testutil.RecordingHookCollisionListener;

/**
 * Unit tests for HookImpl : the state Pattern lifecycle and
 * the Observer pattern handling of collision events.
 */
class HookImplTest {

    private static final double START_X = 50.0;
    private static final double BOAT_Y = 0.0;
    private static final double DROP_SPEED = 10.0;
    private static final double REEL_SPEED = 5.0;
    private static final double MAX_DEPTH = 100.0;
    private static final double DELTA = 1e-9;
    private static final double ONE_SECOND = 1.0;
    private static final double FIVE_SECONDS = 5.0;
    private static final double LARGE_TIME_STEP = 1000.0;
    private static final double SOME_BOAT_X = 123.0;
    private static final double SOME_BOAT_Y = 45.0;
    private static final double LARGE_X_OFFSET = 999.0;
    private static final double HOOK_TEST_X = 100.0;
    private static final double HOOK_TEST_Y = 100.0;
    private static final double OVERLAPPING_RECT_ORIGIN = 95.0;
    private static final double OVERLAPPING_RECT_SIZE = 10.0;
    private static final double FARAWAY_RECT_ORIGIN = 1000.0;
    private static final double FARAWAY_RECT_SIZE = 5.0;

    private HookImpl hook;

    @BeforeEach
    void setUp() {
        hook = new HookImpl(START_X, BOAT_Y, DROP_SPEED, REEL_SPEED, MAX_DEPTH);
    }

    @Test
    void startsInIdleState() {
        assertEquals(HookState.IDLE, hook.getCurrentState());
    }

    @Test
    void idleHookFollowsBoatPosition() {
        hook.update(ONE_SECOND, SOME_BOAT_X, SOME_BOAT_Y);
        assertEquals(SOME_BOAT_X, hook.getX(), DELTA);
        assertEquals(SOME_BOAT_Y, hook.getY(), DELTA);
    }

    @Test
    void castFromIdleSucceedsAndTransitionsToDropping() {
        final boolean casted = hook.cast();
        assertTrue(casted);
        assertEquals(HookState.DROPPING, hook.getCurrentState());
    }

    @Test
    void castWhileNotIdleFails() {
        hook.cast();
        final boolean secondCast = hook.cast();
        assertFalse(secondCast);
        assertEquals(HookState.DROPPING, hook.getCurrentState());
    }

    @Test
    void reelInWhileDroppingSucceedsAndTransitionsToReeling() {
        hook.cast();
        final boolean reeled = hook.reelIn();
        assertTrue(reeled);
        assertEquals(HookState.REELING, hook.getCurrentState());
    }

    @Test
    void reelInWhileIdleFails() {
        final boolean reeled = hook.reelIn();
        assertFalse(reeled);
        assertEquals(HookState.IDLE, hook.getCurrentState());
    }

    @Test
    void droppingIncreasesDepthOverTime() {
        hook.cast();
        hook.update(ONE_SECOND, START_X, BOAT_Y);
        assertEquals(BOAT_Y + DROP_SPEED, hook.getY(), DELTA);
    }

    @Test
    void reelingDecreasesDepthAndReturnsToIdleAtSurface() {
        hook.cast();
        hook.update(LARGE_TIME_STEP, START_X, BOAT_Y); // reach REELING at maxDepth
        hook.update(LARGE_TIME_STEP, START_X, BOAT_Y); // enought time to fully reel in
        assertEquals(HookState.IDLE, hook.getCurrentState());
        assertEquals(BOAT_Y, hook.getY(), DELTA);
    }

    @Test
    void xPositionStaysFixedWhileDropping() {
        hook.cast();
        hook.update(ONE_SECOND, START_X, BOAT_Y);
        // Boat moves, but the hook X must not follow while DROPPING.
        hook.update(ONE_SECOND, START_X + LARGE_X_OFFSET, BOAT_Y);
        assertEquals(START_X, hook.getX(), DELTA);
    }

    @Test
    void hookFishWhileDroppingEntersMinigameState() {
        hook.cast();
        final FakeCatchable fish = new FakeCatchable();
        hook.hookFish(fish);
        assertEquals(HookState.MINIGAME, hook.getCurrentState());
        assertEquals(fish, hook.getHookedFish());
    }

    @Test
    void hookFishWhileIdleHasNoEffect() {
        final FakeCatchable fish = new FakeCatchable();
        hook.hookFish(fish);
        assertEquals(HookState.IDLE, hook.getCurrentState());
        assertNull(hook.getHookedFish());
    }

    @Test
    void positionFreezesDuringMinigame() {
        hook.cast();
        hook.update(ONE_SECOND, START_X, BOAT_Y);
        final double yBeforeMinigame = hook.getY();
        hook.hookFish(new FakeCatchable());
        hook.update(FIVE_SECONDS, START_X, BOAT_Y);
        assertEquals(yBeforeMinigame, hook.getY(), DELTA);
    }

    @Test
    void completeMinigameWithSuccessKeepsFIshAndResumesReeling() {
        hook.cast();
        final FakeCatchable fish = new FakeCatchable();
        hook.hookFish(fish);
        hook.completeMinigame(true);
        assertEquals(HookState.REELING, hook.getCurrentState());
        assertEquals(fish, hook.getHookedFish());
    }

    @Test
    void completeMinigameWithFailureClearsFishAndResumesReeling() {
        hook.cast();
        hook.hookFish(new FakeCatchable());
        hook.completeMinigame(false);
        assertEquals(HookState.REELING, hook.getCurrentState());
        assertNull(hook.getHookedFish());
    }

    @Test
    void clearHookedFishRemovesReference() {
        hook.cast();
        hook.hookFish(new FakeCatchable());
        hook.completeMinigame(true);
        hook.clearHookedFish();
        assertNull(hook.getHookedFish());
    }

    @Test
    void collisionAreaIsCenteredOnCurrentPosition() {
        hook.update(ONE_SECOND, HOOK_TEST_X, HOOK_TEST_Y);
        final CollisionArea area = hook.getCollisionArea();
        assertNotNull(area);

        final CollisionPredicate rectRectPredicate = new CollisionPredicates.RectangleRectangleCollisionPredicate();

        //A rectangle covering the exact hook area must intersect its hitbox.
        final CollisionArea overlapping = new CollisionAreaRectangle(OVERLAPPING_RECT_ORIGIN, OVERLAPPING_RECT_ORIGIN, 
            OVERLAPPING_RECT_SIZE, OVERLAPPING_RECT_SIZE);
        assertTrue(rectRectPredicate.test(area, overlapping));
        //A rectangle far away must not intersect
        final CollisionArea faraway = new CollisionAreaRectangle(FARAWAY_RECT_ORIGIN, FARAWAY_RECT_ORIGIN, 
            FARAWAY_RECT_SIZE, FARAWAY_RECT_SIZE);
        assertFalse(rectRectPredicate.test(area, faraway));
    }

    @Test
    void collisionIsForwardedToListenerWhileDropping() {
        final RecordingHookCollisionListener listener = new RecordingHookCollisionListener();
        hook.setCollisionListener(listener);
        hook.cast();
        final FakeCollidable other = new FakeCollidable();
        hook.onCollision(other);
        assertEquals(1, listener.getCallCount());
        assertEquals(other, listener.getLastCollision());
    }

    @Test
    void collisionIsForwardedToListenerWhileReeling() {
        final RecordingHookCollisionListener listener = new RecordingHookCollisionListener();
        hook.setCollisionListener(listener);
        hook.cast();
        hook.reelIn();
        hook.onCollision(new FakeCollidable());
        assertEquals(1, listener.getCallCount());
    }

    @Test
    void collisionIsIgnoredWhileIdle() {
        final RecordingHookCollisionListener listener = new RecordingHookCollisionListener();
        hook.setCollisionListener(listener);
        hook.onCollision(new FakeCollidable());
        assertEquals(0, listener.getCallCount());
    }

    @Test
    void collisionIsIgnoredDuringMinigame() {
        final RecordingHookCollisionListener listener = new RecordingHookCollisionListener();
        hook.setCollisionListener(listener);
        hook.cast();
        hook.hookFish(new FakeCatchable());
        hook.onCollision(new FakeCollidable());
        assertEquals(0, listener.getCallCount());
    }

    @Test
    void collisionIsIgnoredWhenNoListenerIsRegistered() {
        hook.cast();
        hook.onCollision(new FakeCollidable());
        assertEquals(HookState.DROPPING, hook.getCurrentState());
    }
}
