package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.weather.Weather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FishSpawnerTest {

    private static final double MAP_WIDTH = 800;
    private static final double MAP_HEIGHT = 600;
    private static final int SAMPLES = 100;

    // FishManager is only needed by PredatorFishImpl when a predator actually collides
    // with another fish, which never happens in these tests: null is a safe stand-in.
    private final FishSpawner spawner = new FishSpawner(MAP_WIDTH, MAP_HEIGHT);

    @Test
    void spawnedFishIsPositionedOutsideTheHorizontalBounds() {
        for (int i = 0; i < SAMPLES; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.CLEAR, false);
            assertTrue(fish.getX() <= 0 || fish.getX() >= MAP_WIDTH);
        }
    }

    @Test
    void spawnedFishDirectionIsCoherentWithTheSpawnSide() {
        for (int i = 0; i < SAMPLES; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.CLEAR, false);
            if (fish.getX() <= 0) {
                assertEquals(1, fish.getDirection());
            } else {
                assertEquals(-1, fish.getDirection());
            }
        }
    }

    @Test
    void spawnedFishStaysWithinTheVerticalBand() {
        for (int i = 0; i < SAMPLES; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.CLEAR, false);
            final double maxY = MAP_HEIGHT - fish.getCollisionArea().getHeight();
            assertTrue(fish.getY() >= 0 && fish.getY() <= maxY);
        }
    }

    @Test
    void stormOnlySpeciesNeverSpawnDuringClearWeather() {
        for (int i = 0; i < 4 * SAMPLES; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.CLEAR, false);
            assertFalse(fish.getType().isStormOnly());
        }
    }

    @Test
    void stormOnlySpeciesCanSpawnDuringAStorm() {
        boolean found = false;
        for (int i = 0; i < 10 * SAMPLES && !found; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.STORMY, false);
            found = fish.getType().isStormOnly();
        }
        assertTrue(found);
    }

    @Test
    void predatorTypesAreWrappedInPredatorFishImpl() {
        boolean found = false;
        for (int i = 0; i < 4 * SAMPLES && !found; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.STORMY, false);
            if (fish.getType().isPredator()) {
                assertInstanceOf(PredatorFishImpl.class, fish);
                found = true;
            }
        }
        assertTrue(found);
    }

    @Test
    void nonPredatorTypesAreNotWrapped() {
        boolean found = false;
        for (int i = 0; i < SAMPLES && !found; i++) {
            final Fish fish = spawner.spawnFish(null, Weather.CLEAR, false);
            if (!fish.getType().isPredator()) {
                assertFalse(fish instanceof PredatorFishImpl);
                found = true;
            }
        }
        assertTrue(found);
    }
}
