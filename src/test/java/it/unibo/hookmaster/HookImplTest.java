package it.unibo.hookmaster;

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
import it.unibo.hookmaster.model.fishing.HookImpl;
import it.unibo.hookmaster.model.fishing.HookState;

/**
 * Unit tests for HookImpl : the state Pattern lifecycle and
 * the Observer pattern handling of collision events
 */
class HookImplTest {

    private static final double START_X = 50.0;
    private static final double BOAT_Y = 0.0;
    private static final double DROP_SPEED = 10.0;
    private static final double REEL_SPEED = 5.0;
    private static final double MAX_DEPTH = 100.0;
    private static final double DELTA = 1e-9;

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
        hook.update(1.0, 123.0, 45.0);
        assertEquals(123.0, hook.getX(), DELTA);
        assertEquals(45.0, hook.getY(), DELTA);
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
    void reelInWhileIdleFails(){
        final boolean reeled = hook.reelIn();
        assertFalse(reeled);
        assertEquals(HookState.IDLE, hook.getCurrentState());
    }

    @Test
    void droppingIncreasesDepthOverTime() {
        hook.cast();
        hook.update(1.0, START_X, BOAT_Y);
        assertEquals(BOAT_Y + DROP_SPEED, hook.getY(), DELTA);
    }

    @Test
    void reelingDecreasesDepthAndReturnsToIdleAtSurface() {
        hook.cast();
        hook.update(1000.0, START_X, BOAT_Y);// reach REELING at maxDepth
        hook.update(1000.0, START_X, BOAT_Y);// enought time to fully reel in
        assertEquals(HookState.IDLE, hook.getCurrentState());
        assertEquals(BOAT_Y, hook.getY(), DELTA);
    }

    @Test
    void xPositionStaysFixedWhileDropping() {
        hook.cast();
        hook.update(1.0, START_X, BOAT_Y);
        // Boat moves, but the hook X must not follow while DROPPING.
        hook.update(1.0, START_X + 999.0, BOAT_Y);
        assertEquals(START_X, hook.getX(), DELTA);
    }

    @Test
    void hookFishWhileDroppingEntersMinigameState() {
        hook.cast();
        final FakeCatchable fish  = new FakeCatchable();
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
        hook.update(1.0, START_X, BOAT_Y);
        final double yBeforeMinigame = hook.getY();
        hook.hookFish(new FakeCatchable());
        hook.update(5.0, START_X, BOAT_Y);
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
        hook.update(1.0, 100.0, 100.0);
        final CollisionArea area = hook.getCollisionArea();
        assertNotNull(area);

        final CollisionPredicate rectRectPredicate = new CollisionPredicates.RectangleRectangleCollisionPredicate();

        //A rectangle covering the exact hook area must intersect its hitbox.
        final CollisionArea overlapping = new CollisionAreaRectangle(95.0, 95.0, 10.0, 10.0);
        assertTrue(rectRectPredicate.test(area, overlapping));
        //A rectangle far away must not intersect
        final CollisionArea faraway = new CollisionAreaRectangle( 1000.0, 1000.0, 5.0, 5.0);
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