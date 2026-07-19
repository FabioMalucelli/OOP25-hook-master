package it.unibo.hookmaster.model.fishing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.collision.CollisionPredicate;
import it.unibo.hookmaster.model.collision.CollisionPredicates;
import it.unibo.hookmaster.model.upgrade.event.UpgradeEvent;
import it.unibo.hookmaster.model.fishing.hook.FishingEvent;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.testutil.FakeCatchable;
import it.unibo.hookmaster.testutil.FakeCollidable;
import it.unibo.hookmaster.testutil.RecordingFishingListener;

/**
 * Unit tests for HookImpl : the state Pattern lifecycle and
 * the Observer pattern handling of collision events.
 */
class HookImplTest {

    private static final double START_X = 50.0;
    private static final double START_Y = 0.0;
    private static final double SPEED = 10.0;
    private static final double MIN_X = 0.0;
    private static final double MAX_X = 200.0;
    private static final double MIN_Y = -200.0;
    private static final double MAX_DEPTH = 100.0;
    private static final double MAX_WEIGHT = 100.0;
    private static final double HEAVY_FISH_WEIGHT = 150.0;
    private static final double LIGHT_FISH_WEIGHT = 50.0;
    private static final double DELTA = 1e-9;
    private static final long ONE_SECOND = 1000L;
    private static final long FIVE_SECONDS = 5000L;
    private static final long LARGE_TIME_STEP = 1_000_000L;
    private static final double HOOK_TEST_X = 100.0;
    private static final double HOOK_TEST_Y = 100.0;
    private static final double OVERLAPPING_RECT_ORIGIN = 95.0;
    private static final double OVERLAPPING_RECT_SIZE = 10.0;
    private static final double FARAWAY_RECT_ORIGIN = 1000.0;
    private static final double FARAWAY_RECT_SIZE = 5.0;

    private static final int UPGRADE_NEW_LEVEL = 2;
    private static final double UPGRADE_NEW_SPEED = 50.0;

    private HookImpl hook;

    @BeforeEach
    void setUp() {
        hook = new HookImpl(START_X, START_Y, SPEED, MIN_X, MAX_X, MIN_Y, MAX_DEPTH, MAX_WEIGHT);
    }

    @Test
    void startsInMovingState() {
        assertEquals(HookState.MOVING, hook.getCurrentState());
    }

    @Test
    void staysStillWhenNoDirectionIsActive() {
        hook.update(ONE_SECOND);
        assertEquals(START_X, hook.getX(), DELTA);
        assertEquals(START_Y, hook.getY(), DELTA);
    }

    @Test
    void movesRightWhenOnlyMovingRightIsActive() {
        hook.setMovingRight(true);
        hook.update(ONE_SECOND);
        assertEquals(START_X + SPEED, hook.getX(), DELTA);
    }

    @Test
    void movesLeftWhenOnlyMovingLeftIsActive() {
        hook.setMovingLeft(true);
        hook.update(ONE_SECOND);
        assertEquals(START_X - SPEED, hook.getX(), DELTA);
    }

    @Test
    void movesUpWhenOnlyMovingUpIsActive() {
        hook.setMovingUp(true);
        hook.update(ONE_SECOND);
        assertEquals(START_Y - SPEED, hook.getY(), DELTA);
    }

    @Test
    void staysStillVerticallyWhenBothDirectionAreActive() {
        hook.setMovingUp(true);
        hook.setMovingDown(true);
        hook.update(ONE_SECOND);
        assertEquals(START_Y, hook.getY(), DELTA);
    }

    @Test
    void clampsAtMaxDepthWhenMovingDown() {
        hook.setMovingDown(true);
        hook.update(LARGE_TIME_STEP);
        assertEquals(MAX_DEPTH, hook.getY(), DELTA);
    }

    @Test
    void doesNotClampAboveTheFieldWhenMovingUp() {
        hook.setMovingUp(true);
        hook.update(LARGE_TIME_STEP);
        assertTrue(hook.getY() < 0);
    }

    @Test
    void movesDiagonallyWhenHorizontalAndVerticalAreActiveTogether() {
        hook.setMovingRight(true);
        hook.setMovingDown(true);
        hook.update(ONE_SECOND);
        assertEquals(START_X + SPEED, hook.getX(), DELTA);
        assertEquals(START_Y + SPEED, hook.getY(), DELTA);
    }

    @Test
    void hookFishWhileMovingEntersMinigameState() {
        final FakeCatchable fish = new FakeCatchable();
        hook.hookFish(fish);
        assertEquals(HookState.MINIGAME, hook.getCurrentState());
        assertEquals(fish, hook.getHookedFish());
        assertNotNull(hook.getCurrentMinigame());
        assertEquals(fish, hook.getCurrentMinigame().getTarget());
    }

    @Test void hookFishWhileInMinigameHasNoEffect() {
        final FakeCatchable firstFish = new FakeCatchable();
        hook.hookFish(firstFish);

        final FakeCatchable secondFish = new FakeCatchable(999, 0.9, "SecondFish");
        hook.hookFish(secondFish);

        assertEquals(firstFish, hook.getHookedFish());
    }

    @Test
    void positionFreezesDuringMinigame() {
        hook.setMovingRight(true);
        hook.update(ONE_SECOND);
        final double xBeforeMinigame = hook.getX();
        final double yBeforeMinigame = hook.getY();

        hook.hookFish(new FakeCatchable());
        hook.update(FIVE_SECONDS);

        assertEquals(xBeforeMinigame, hook.getX(), DELTA);
        assertEquals(yBeforeMinigame, hook.getX(), DELTA);
    }

    @Test
    void attemptCatchReturnsFalseWhenNotInMinigame() {
        assertFalse(hook.attemptCatch());
    }

    @Test
    void attemptCatchResolvesMinigameRegardlessOfOutcome() {
        hook.hookFish(new FakeCatchable());

        hook.attemptCatch();

        assertEquals(HookState.MOVING, hook.getCurrentState());
        assertNull(hook.getCurrentMinigame());
    }

    @Test
    void completeMinigameWithSuccessKeepsFishAndResumesMoving() {
        hook.hookFish(new FakeCatchable());
        hook.completeMinigame(true);
        hook.clearHookedFish();
        assertNull(hook.getHookedFish());
    }

    @Test
    void collisionAreaIsCenteredOnCurrentPosition() {
        final HookImpl positionedHook = new HookImpl(HOOK_TEST_X, HOOK_TEST_Y, SPEED, MIN_X, MAX_X, MIN_Y, MAX_DEPTH, MAX_WEIGHT);
        final CollisionAreaCircle area = positionedHook.getCollisionArea();
        assertNotNull(area);
        assertEquals(HOOK_TEST_X, area.getCenterX(), DELTA);
        assertEquals(HOOK_TEST_Y, area.getCenterY(), DELTA);

        final CollisionPredicate rectCirclePredicate = new CollisionPredicates.RectangleCircleCollisionPredicate();

        //A rectangle covering the exact hook area must intersect its hitbox.
        final CollisionArea overlapping = new CollisionAreaRectangle(OVERLAPPING_RECT_ORIGIN, OVERLAPPING_RECT_ORIGIN, 
            OVERLAPPING_RECT_SIZE, OVERLAPPING_RECT_SIZE);
        assertTrue(rectCirclePredicate.test(overlapping, area));

        //A rectangle faraway must not intersect.
        final CollisionArea faraway = new CollisionAreaRectangle(FARAWAY_RECT_ORIGIN, FARAWAY_RECT_ORIGIN, 
            FARAWAY_RECT_SIZE, FARAWAY_RECT_SIZE);
        assertTrue(rectCirclePredicate.test(faraway, area));
    }

    @Test
    void collisionWithCatchableWhileMovingHooksFishDirectly() {
        final FakeCatchable fish = new FakeCatchable();
        hook.onCollision(fish);
        assertEquals(HookState.MINIGAME, hook.getCurrentState());
        assertEquals(fish, hook.getHookedFish());
    }

    @Test
    void collsionWithNonCatchableIsIgnored() {
        hook.onCollision(new FakeCollidable());
        assertEquals(HookState.MOVING, hook.getCurrentState());
        assertNull(hook.getCurrentMinigame());
    }

    @Test
    void collisionIsIgnoredDuringMinigame() {
        final FakeCatchable firstFish = new FakeCatchable();
        hook.onCollision(firstFish);

        final FakeCatchable secondFish = new FakeCatchable(999, 0.9, "SecondFish");
        hook.onCollision(secondFish);

        //The second fish must not replace the fish already being caught.
        assertEquals(firstFish, hook.getHookedFish());
    }

    @Test
    void hookFishFiresFishHookedEvent() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        hook.addListener(listener);
        hook.hookFish(new FakeCatchable());
        assertTrue(listener.hasRecieved(FishingEvent.Type.FISH_HOOKED));
    }

    @Test
    void attemptCatchFiresCaughtOrEscapedEvent() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        hook.hookFish(new FakeCatchable());
        hook.addListener(listener);

        hook.attemptCatch();

        final boolean firedCaughtOrEscaped = listener.hasRecieved(FishingEvent.Type.FISH_CAUGHT)
            || listener.hasRecieved(FishingEvent.Type.FISH_ESCAPED);
        assertTrue(firedCaughtOrEscaped);
    }

    @Test
    void removedListenerNoLongerRecievesEvents() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        hook.addListener(listener);
        hook.removeListener(listener);
        hook.hookFish(new FakeCatchable());
        assertTrue(listener.getRecievedEvents().isEmpty());
    }

    @Test
    void onUpgradeWithSpeedTypeUpdatesSpeed() {
        hook.onUpgrade(new UpgradeEvent(UpgradeType.SPEED, UPGRADE_NEW_LEVEL, UPGRADE_NEW_SPEED));

        hook.setMovingDown(true);
        hook.update(ONE_SECOND);

        assertEquals(START_Y + UPGRADE_NEW_SPEED, hook.getY(), DELTA);
    }

    @Test
    void onUpgradeWithMaxWeightTypeDoesNotAffectSpeed() {
        hook.onUpgrade(new UpgradeEvent(UpgradeType.MAX_WEIGHT, UPGRADE_NEW_LEVEL, UPGRADE_NEW_SPEED));

        hook.setMovingDown(true);
        hook.update(ONE_SECOND);

        assertEquals(START_Y + SPEED, hook.getY(), DELTA);
    }

    @Test
    void hookFishRejectsFishHeavierThanMaxWeight() {
        final FakeCatchable heavyFish = new FakeCatchable(10, 0.5, "HeavyFish", HEAVY_FISH_WEIGHT);
        hook.hookFish(heavyFish);
        assertEquals(HookState.MOVING, hook.getCurrentState());
        assertNull(hook.getHookedFish());
        assertNull(hook.getCurrentMinigame());
    }

    @Test
    void hookFishAcceptFishAtOrBelowMaxWeight() {
        final FakeCatchable lightFish = new FakeCatchable(10, 0.5, "HeavyFish", LIGHT_FISH_WEIGHT);
        hook.hookFish(lightFish);
        assertEquals(HookState.MINIGAME, hook.getCurrentState());
        assertEquals(lightFish, hook.getHookedFish());
    }

    @Test
    void hookFishAcceptsFishExactlyAtMaxWeight() {
        final FakeCatchable borderLineFish = new FakeCatchable(10, 0.5, "BorderlineFish", MAX_WEIGHT);
        hook.hookFish(borderLineFish);
        assertEquals(HookState.MINIGAME, hook.getCurrentState());
    }

    @Test
    void hookFishFiresFishTooHeavyEventForRejectedFish() {
        final RecordingFishingListener listener = new RecordingFishingListener();
        hook.addListener(listener);
        final FakeCatchable heavyFish = new FakeCatchable(10, 0.5, "BorderlineFish", MAX_WEIGHT);

        hook.hookFish(heavyFish);

        assertTrue(listener.hasRecieved(FishingEvent.Type.FISH_TOO_HEAVY));
        assertTrue(listener.hasRecieved(FishingEvent.Type.FISH_HOOKED));
    }

    @Test
    void onUpgradeWithMaxWeighTypeUpdatesMaxWeight() {
        hook.onUpgrade(new UpgradeEvent(UpgradeType.MAX_WEIGHT, UPGRADE_NEW_LEVEL, HEAVY_FISH_WEIGHT));

        //A fish that used to be too heavy is now catchable after the upgrade.
        final FakeCatchable fish = new FakeCatchable(10, 0.5, "PreviouslyTooHeavy", HEAVY_FISH_WEIGHT);
        hook.hookFish(fish);

        assertEquals(HookState.MINIGAME, hook.getCurrentState());
    }

    @Test
    void maxWeightReturnsCurrentValue() {
        assertEquals(MAX_WEIGHT, hook.getMaxWeight(), DELTA);
        hook.setMaxWeight(HEAVY_FISH_WEIGHT);
        assertEquals(HEAVY_FISH_WEIGHT, hook.getMaxWeight(), DELTA);
    }
}
