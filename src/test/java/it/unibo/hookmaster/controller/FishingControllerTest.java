package it.unibo.hookmaster.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.event.UpgradeEvent;
import it.unibo.hookmaster.model.event.UpgradeObserver;
import it.unibo.hookmaster.model.fishing.boat.BoatImpl;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.testutil.FakeCatchable;
import it.unibo.hookmaster.testutil.FakeCollidable;
import it.unibo.hookmaster.testutil.RecordingFishingListener;

/**
 * Unit tests for FishingController.
 */
class FishingControllerTest {

    private static final double START_X = 0.0;
    private static final double SURFACE_Y = 0.0;
    private static final double BOAT_SPEED = 10.0;
    private static final double MIN_X = 0.0;
    private static final double MAX_X = 200.0;
    private static final double DROP_SPEED = 10.0;
    private static final double REEL_SPEED = 5.0;
    private static final double MAX_DEPTH = 100.0;
    private static final double DELTA = 1e-9;
    private static final double ONE_SECOND = 1.0;

    private static final int UPGRADE_NEW_LEVEL = 2;
    private static final double UPGRADE_NEW_SPEED = 999.0;
    private static final double UPGRADE_NEW_MAX_WEIGHT_VALUE = 50.0;

    private BoatImpl boat;
    private HookImpl hook;
    private FishingController controller;

    @BeforeEach
    void setUp() {
        boat = new BoatImpl(START_X, SURFACE_Y, BOAT_SPEED, MIN_X, MAX_X);
        hook = new HookImpl(START_X, SURFACE_Y, DROP_SPEED, REEL_SPEED, MAX_DEPTH);
        controller = new FishingController(boat, hook);
    }

    @Test
    void constructorRegistersItselAsHookCollisionListener() {
        hook.cast();
        hook.onCollision(new FakeCatchable());
        assertEquals(HookState.MINIGAME, controller.getHook().getCurrentState());
    }

    @Test
    void setBoatMovingLeftDelegatesToBoat() {
        controller.setBoatMovingLeft(true);
        controller.update(ONE_SECOND);
        assertEquals(MIN_X, controller.getBoat().getX(), DELTA);
    }

    @Test
    void setBoatMovingRightDelegatesToBoat() {
        controller.setBoatMovingRight(true);
        controller.update(ONE_SECOND);
        assertEquals(START_X + BOAT_SPEED, controller.getBoat().getX(), DELTA);
    }

    @Test
    void castHookDelegatesToHookAndFiresEvent() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);

        controller.castHook();

        assertEquals(HookState.DROPPING, controller.getHook().getCurrentState());
        assertTrue(listener.hasRecieved(FishingEvent.Type.HOOK_CAST));
    }

    @Test
    void castHookTwiceOnlyFiresEventOnce() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);

        controller.castHook();
        controller.castHook();

        final long castEvents = listener.getRecievedEvents().stream()
            .filter(e -> e.getType() == FishingEvent.Type.HOOK_CAST).count();
        assertEquals(1, castEvents);
    }

    @Test
    void reelInHookDelegatesToHookAndFiresEvent() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);

        controller.castHook();
        controller.reelInHook();

        assertEquals(HookState.REELING, controller.getHook().getCurrentState());
        assertTrue(listener.hasRecieved(FishingEvent.Type.HOOK_REELING));
    }

    @Test
    void collisionWithNonCatchableIsIgnored() {
        controller.castHook();
        controller.onHookCollision(new FakeCollidable());

        assertEquals(HookState.DROPPING, controller.getHook().getCurrentState());
        assertNull(controller.getCurrentMinigame());
    }

    @Test
    void attemptCatchResolvesTheMinigameRegardlessOfOutcome() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);

        controller.castHook();
        controller.onHookCollision(new FakeCatchable());
        controller.attemptCatch();

        assertEquals(HookState.REELING, controller.getHook().getCurrentState());
        assertNull(controller.getCurrentMinigame());
        final boolean firedCaughtOrEscaped =
            listener.hasRecieved(FishingEvent.Type.FISH_CAUGHT) || listener.hasRecieved(FishingEvent.Type.FISH_ESCAPED);
        assertTrue(firedCaughtOrEscaped);
    }

    @Test
    void removedListenerNoLongerRecievesEvents() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);
        controller.removeListener(listener);

        controller.castHook();

        assertTrue(listener.getRecievedEvents().isEmpty());
    }

    @Test
    void speedUpdatesBothDropAndReelSpeed() {
        final UpgradeEvent event = new UpgradeEvent(UpgradeType.SPEED, UPGRADE_NEW_LEVEL, UPGRADE_NEW_SPEED);

        final UpgradeObserver observer = controller;
        observer.onUpgrade(event);

        controller.castHook();
        controller.update(ONE_SECOND);
        assertEquals(HookState.REELING, controller.getHook().getCurrentState());
    }

    @Test
    void maxWeightUpgradeFiresUpgradeAppliedEvent() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        controller.addListener(listener);
        final UpgradeEvent event = new UpgradeEvent(UpgradeType.MAX_WEIGHT, UPGRADE_NEW_LEVEL, UPGRADE_NEW_MAX_WEIGHT_VALUE);

        final UpgradeObserver observer = controller;
        observer.onUpgrade(event);

        assertTrue(listener.hasRecieved(FishingEvent.Type.UPGRADE_APPLIED));
    }

    @Test
    void boatReturnsCurrentPositionSnapshot() {
        controller.setBoatMovingRight(true);
        controller.update(ONE_SECOND);
        assertEquals(boat.getX(), controller.getBoat().getX(), DELTA);
        assertEquals(boat.getY(), controller.getBoat().getY(), DELTA);
    }

    @Test
    void hookReturnsCurrentStateSnapshot() {
        controller.castHook();
        assertEquals(hook.getCurrentState(), controller.getHook().getCurrentState());
        assertEquals(hook.getX(), controller.getHook().getX(), DELTA);
        assertEquals(hook.getY(), controller.getHook().getY(), DELTA);
    }
}
