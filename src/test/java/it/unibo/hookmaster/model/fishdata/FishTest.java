package it.unibo.hookmaster.model.fishdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class FishTest {

    private static final double MIN_WEIGHT_MULTIPLIER = .5;
    private static final double MAX_WEIGHT_MULTIPLIER = 2;
    private static final double MIN_DIFFICULTY = .1;
    private static final double MAX_DIFFICULTY = 1;
    private static final int DEFAULT_DIRECTION = 1;
    private static final double SPEED_MULTIPLIER = 2;
    private static final double DELTA = 1e-9;

    private Fish fish;

    @BeforeEach
    void setUp() {
        fish = new Fish(FishType.TUNA, new Position(0, 0), new LinearMovement());
    }

    @Test
    void weightInRange() {
        final double baseWeight = FishType.TUNA.getBaseWeight();
        assertTrue(fish.getWeight() >= baseWeight * MIN_WEIGHT_MULTIPLIER);
        assertTrue(fish.getWeight() <= baseWeight * MAX_WEIGHT_MULTIPLIER);
    }

    @Test
    void catchDifficultyAlwaysUnderRange() {
        assertTrue(fish.getCatchDifficulty() <= MAX_DIFFICULTY);
    }

    @Test
    void catchDifficultyAlwaysOverRange() {
        assertTrue(fish.getCatchDifficulty() >= MIN_DIFFICULTY);
    }

    @Test
    void speedMultiplierAffectsEffectiveSpeed() {
        final double baseSpeed = fish.getSpeed();
        fish.setSpeedMultiplier(SPEED_MULTIPLIER);
        assertEquals(baseSpeed * SPEED_MULTIPLIER, fish.getSpeed(), DELTA);
    }

    @Test
    void directionDefaultsToPositive() {
        assertEquals(DEFAULT_DIRECTION, fish.getDirection());
    }

    @RepeatedTest(10)
    void weightIsAlwaysWithinRangeAcrossManyInstances() {
        final Fish repeated = new Fish(FishType.TUNA, new Position(0, 0), new LinearMovement());
        final double baseWeight = FishType.TUNA.getBaseWeight();
        assertTrue(repeated.getWeight() >= baseWeight * MIN_WEIGHT_MULTIPLIER);
        assertTrue(repeated.getWeight() <= baseWeight * MAX_WEIGHT_MULTIPLIER);
    }
}
