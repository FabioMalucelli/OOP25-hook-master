package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherEvent;
import it.unibo.hookmaster.model.weather.WeatherObserver;
import it.unibo.hookmaster.model.weather.WeatherSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FishManagerTest {

    private static final double MAP_WIDTH = 800;
    private static final double MAP_HEIGHT = 600;
    private static final int TARGET_FISH_COUNT = 20;
    private static final long DELTA_TIME = 200;

    private StubWeatherSystem weatherSystem;
    private FishManager manager;

    @BeforeEach
    void setUp() {
        weatherSystem = new StubWeatherSystem();
        final FishSpawner spawner = new FishSpawner(MAP_WIDTH, MAP_HEIGHT);
        manager = new FishManager(spawner, weatherSystem, MAP_WIDTH, MAP_HEIGHT);
    }

    @Test
    void managerIsPopulatedToTheTargetCountOnCreation() {
        assertEquals(TARGET_FISH_COUNT, manager.getFishes().size());
    }

    @Test
    void fishesReturnsAnUnmodifiableView() {
        final List<Fish> fishes = manager.getFishes();
        assertThrows(UnsupportedOperationException.class, () -> fishes.add(null));
    }

    @Test
    void removingAFishTriggersReplenishment() {
        final Fish toRemove = manager.getFishes().get(0);
        manager.removeFish(toRemove);
        assertEquals(TARGET_FISH_COUNT, manager.getFishes().size());
        assertFalse(manager.getFishes().contains(toRemove));
    }

    @Test
    void removingAFishNotManagedDoesNothing() {
        final Fish notManaged = new FishImpl(FishType.ANCHOVY, new Position(0, 0), new LinearMovement());
        final int sizeBefore = manager.getFishes().size();
        manager.removeFish(notManaged);
        assertEquals(sizeBefore, manager.getFishes().size());
    }

    @Test
    void removeDeadFishMovesTheFishToTheDeadListAndReplenishes() {
        final Fish toKill = manager.getFishes().get(0);
        manager.removeDeadFish(toKill);

        assertFalse(manager.getFishes().contains(toKill));
        assertEquals(TARGET_FISH_COUNT, manager.getFishes().size());

        final List<Fish> dead = manager.consumeDeadFishes();
        assertEquals(1, dead.size());
        assertTrue(dead.contains(toKill));
    }

    @Test
    void consumeDeadFishesClearsTheInternalDeadList() {
        final Fish toKill = manager.getFishes().get(0);
        manager.removeDeadFish(toKill);
        manager.consumeDeadFishes();
        assertTrue(manager.consumeDeadFishes().isEmpty());
    }

    @Test
    void updateKeepsThePopulationAtTheTargetCountOverTime() {
        for (int i = 0; i < 100; i++) {
            manager.update(DELTA_TIME);
        }
        assertEquals(TARGET_FISH_COUNT, manager.getFishes().size());
    }

    @Test
    void weatherChangeToStormyAppliesTheStormSpeedMultiplier() {
        weatherSystem.changeWeather(Weather.STORMY);
        for (final Fish fish : manager.getFishes()) {
            if (!fish.getType().isStormOnly()) {
                final double expected = Math.round(fish.getType().getSpeed() * 2.5);
                assertEquals(expected, fish.getSpeed());
            }
        }
    }

    @Test
    void weatherChangeBackToClearRestoresTheBaseSpeed() {
        weatherSystem.changeWeather(Weather.STORMY);
        weatherSystem.changeWeather(Weather.CLEAR);
        for (final Fish fish : manager.getFishes()) {
            if (!fish.getType().isStormOnly()) {
                final double expected = Math.round(fish.getType().getSpeed());
                assertEquals(expected, fish.getSpeed());
            }
        }
    }

    private static final class StubWeatherSystem implements WeatherSystem {
        private Weather currentWeather = Weather.CLEAR;
        private final List<WeatherObserver> observers = new ArrayList<>();

        @Override
        public void update(final long deltaTime) {
            // not needed for these tests
        }

        @Override
        public Weather getCurrentWeather() {
            return currentWeather;
        }

        @Override
        public void addObserver(final WeatherObserver observer) {
            observers.add(observer);
        }

        @Override
        public void removeObserver(final WeatherObserver observer) {
            observers.remove(observer);
        }

        void changeWeather(final Weather newWeather) {
            this.currentWeather = newWeather;
            final WeatherEvent event = new WeatherEvent(newWeather);
            for (final WeatherObserver observer : new ArrayList<>(observers)) {
                observer.onWeatherChanged(event);
            }
        }
    }
}
