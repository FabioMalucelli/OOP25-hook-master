package it.unibo.hookmaster.model.fishdata;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.hookmaster.model.weather.Weather;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class FishSpawnerTest {

    private static final double MAP_WIDTH = 800;
    private static final double MAP_HEIGHT = 600;
    private static final double TOP_BORDER = 0.3;
    private static final double BOTTOM_BORDER = 0.95;
    private static final int TEST_REPEATS = 20;
    private static final int TOTAL_FOR_TRIES = 100;

    private final FishSpawner spawner = new FishSpawner(MAP_WIDTH, MAP_HEIGHT);

    @RepeatedTest(TEST_REPEATS)
    void spawnedFishIsWithinBorders() {
        final Fish fish = spawner.spawnFish(Weather.CLEAR);
        final double minY = MAP_HEIGHT * TOP_BORDER;
        final double maxY = MAP_HEIGHT * BOTTOM_BORDER;
        assertTrue(fish.getY() >= minY && fish.getY() <= maxY);
    }

    @RepeatedTest(TEST_REPEATS)
    void stormOnlyFishNeverSpawnsInClearWeather() {
        final Fish fish = spawner.spawnFish(Weather.CLEAR);
        assertFalse(fish.getType().isStormOnly());
    }

    @Test
    void stormOnlyFishCanSpawnDuringStorm() {
        boolean stormFishFound = false;
        for (int i = 0; i < TOTAL_FOR_TRIES; i++) {
            final Fish fish = spawner.spawnFish(Weather.STORMY);
            if (fish.getType().isStormOnly()) {
                stormFishFound = true;
                break;
            }
        }
        assertTrue(stormFishFound);
    }
}
