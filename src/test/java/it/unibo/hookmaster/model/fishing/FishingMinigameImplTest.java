package it.unibo.hookmaster.model.fishing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.fishing.minigame.FishingMinigameImpl;
import it.unibo.hookmaster.model.fishing.minigame.MinigameOutcome;
import it.unibo.hookmaster.testutil.FakeCatchable;
import it.unibo.hookmaster.testutil.FakeIndicatorStrategy;

/**
 * Unit tests for FishingMinigameImpl.
 * Uses FakeIndicatorStrategy to control the indicator position.
 */
class FishingMinigameImplTest {

    @Test
    void startsInProgress() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);
        assertEquals(MinigameOutcome.IN_PROGRESS, minigame.getOutcome());
    }

    @Test
    void attemptCatchSucceedsWhenIndicatorIsInsideTargetZone() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);

        //Place the indicator exactly at the middle of the target zone.
        final double midpoint = (minigame.getTargetStart() + minigame.getTargetEnd()) / 2.0;
        indicator.setPosition(midpoint);

        final boolean success = minigame.attemptCatch();
        assertTrue(success);
        assertEquals(MinigameOutcome.SUCCESS, minigame.getOutcome());
    }

    @Test
    void attemptCatchFailsWhenIndicatorIsOutsideTargetZone() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);

        //The target zone is always fully contained within [0.0, 1.0].
        //so a position outside that range is garanteed to be a miss.
        indicator.setPosition(-1.0);

        final boolean success = minigame.attemptCatch();
        assertFalse(success);
        assertEquals(MinigameOutcome.FAILURE, minigame.getOutcome());
    }

    @Test
    void outcomeFreezesAfterFirstAttempt() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);
        final double midpoint = (minigame.getTargetStart() + minigame.getTargetEnd()) / 2.0;
        indicator.setPosition(midpoint);

        final boolean firstAttempt = minigame.attemptCatch();
        //Move the indicator out of the zone after the outcome was alredy frozen
        indicator.setPosition(-1.0);
        final boolean secondAttempt = minigame.attemptCatch();

        assertEquals(firstAttempt, secondAttempt);
        assertTrue(secondAttempt);
    }

    @Test
    void updateDoesNothingAfterOutcomeIsReached() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);
        minigame.attemptCatch();

        final int callsBefore = indicator.getUpdateCallCount();
        minigame.update(1000L);
        assertEquals(callsBefore, indicator.getUpdateCallCount());
    }

    @Test
    void updateAdvancesIndicatorWhileInProgress() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(), indicator);

        minigame.update(500L);
        assertEquals(1, indicator.getUpdateCallCount());
    }

    @Test
    void harderFishGetNarrowerTargetZone() {
        final FakeIndicatorStrategy easyIndicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl easyMininigame = new FishingMinigameImpl(new FakeCatchable(10, 0.0, "Easy"), easyIndicator);

        final FakeIndicatorStrategy hardIndicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl hardMininigame = new FishingMinigameImpl(new FakeCatchable(10, 1.0, "Hard"), hardIndicator);

        final double easyWidth = easyMininigame.getTargetEnd() - easyMininigame.getTargetStart();
        final double hardWidth = hardMininigame.getTargetEnd() - hardMininigame.getTargetStart();

        assertTrue(easyWidth > hardWidth);
    }

    @Test
    void targetZoneAlwaysFitsWithinTheBar() {
        final FakeIndicatorStrategy indicator = new FakeIndicatorStrategy(0.0);
        final FishingMinigameImpl minigame = new FishingMinigameImpl(new FakeCatchable(10, 0.7, "Fish"), indicator);

        assertTrue(minigame.getTargetStart() >= 0.0);
        assertTrue(minigame.getTargetEnd() <= 1.0);
        assertTrue(minigame.getTargetStart() < minigame.getTargetEnd());
    }

    @Test
    void targetReturnsTheConstructorFish() {
        final FakeCatchable fish = new FakeCatchable();
        final FishingMinigameImpl minigame = new FishingMinigameImpl(fish, new FakeIndicatorStrategy(0.0));
        assertEquals(fish, minigame.getTarget());
    }
}
