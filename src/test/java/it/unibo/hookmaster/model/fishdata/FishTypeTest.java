package it.unibo.hookmaster.model.fishdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FishTypeTest {

    private static final String FISH_NAME = "Tuna";
    private static final int ECONOMIC_VALUE = 20;
    private static final double SPEED = 10;
    private static final double BASE_CATCH_DIFFICULTY = .55;
    private static final double BASE_WEIGHT = 5;
    private static final double DELTA = 1e-9;

    @Test
    void tunaHasExpectedBaseAttributes() {
        assertEquals(FISH_NAME, FishType.TUNA.getName());
        assertFalse(FishType.TUNA.isPredator());
        assertEquals(ECONOMIC_VALUE, FishType.TUNA.getBaseEconomicValue());
        assertEquals(SPEED, FishType.TUNA.getSpeed());
        assertEquals(BASE_CATCH_DIFFICULTY, FishType.TUNA.getBaseCatchDifficulty(), DELTA);
        assertEquals(BASE_WEIGHT, FishType.TUNA.getBaseWeight(), DELTA);
        assertFalse(FishType.TUNA.isStormOnly());
    }

    @Test
    void greatWhiteIsPredator() {
        assertTrue(FishType.GREATWHITE.isPredator());
    }

    @Test
    void marlinNotStormOnly() {
        assertFalse(FishType.MARLIN.isStormOnly());
    }

    @Test
    void anglerStormOnly() {
        assertTrue(FishType.ANGLER.isStormOnly());
    }

    @Test
    void anchovyStormOnly() {
        assertFalse(FishType.ANCHOVY.isStormOnly());
    }

    @Test
    void defaultMovementStrategExists() {
        for (final FishType type : FishType.values()) {
            assertNotNull(type.createDefaultMovementStrategy());
        }
    }

    @Test
    void alwaysPositiveBaseWeight() {
        for (final FishType type : FishType.values()) {
            assertTrue(type.getBaseWeight() > 0);
        }
    }

    @Test
    void alwaysValidCatchDifficulty() {
        for (final FishType type : FishType.values()) {
            assertTrue(type.getBaseCatchDifficulty() >= 0.0 && type.getBaseCatchDifficulty() <= 1.0);
        }
    }
}
