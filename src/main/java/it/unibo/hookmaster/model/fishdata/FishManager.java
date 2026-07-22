package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.fishdata.boids.BoidsManager;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherEvent;
import it.unibo.hookmaster.model.weather.WeatherObserver;
import it.unibo.hookmaster.model.weather.WeatherSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Handles the population of fishes, their movements, spawn and removal.
 */
public class FishManager implements WeatherObserver {

    private static final int TARGET_FISH_COUNT = 30;
    private static final double STORM_SPEED_MULTIPLIER = 2.5;
    private static final double CLEAR_SPEED_MULTIPLIER = 1.0;

    private final List<Fish> fishes = new ArrayList<>();
    private final List<Fish> deadFishes = new ArrayList<>();
    private final FishSpawner spawner;
    private final double mapWidth;
    private final double mapHeight;
    private Weather currentWeather;

    private final Random random = new Random();
    private final BoidsManager boidsManager;

    /**
     * Creates a new fish manager and populates it.
     *
     * @param spawner the spawner used to create new fish
     * @param weatherSystem the weather system driving spawn eligibility
     * @param mapWidth the horizontal size of the map
     * @param mapHeight the vertical size of the map
     */
    public FishManager(final FishSpawner spawner, final WeatherSystem weatherSystem,
            final double mapWidth, final double mapHeight) {
        this.spawner = Objects.requireNonNull(spawner);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.currentWeather = weatherSystem.getCurrentWeather();
        weatherSystem.addObserver(this);
        this.boidsManager = new BoidsManager(mapHeight, spawner, this);
        replenish();
    }

    /**
     * Reacts accordingly when the weather changes.
     *
     * @param event The type of weather event
     */
    @Override
    public void onWeatherChanged(final WeatherEvent event) {
        this.currentWeather = event.getWeather();
        for (final Fish fish : this.fishes) {
            applyWeatherSpeedEffect(fish);
        }
    }

    /**
     * @return a view of the currently live fish.
     */
    public List<Fish> getFishes() {
        return Collections.unmodifiableList(this.fishes);
    }

    /**
     * Consumes all dead fishes and returns them.
     * 
     * @return the list of dead fishes
     */
    public List<Fish> consumeDeadFishes() {
        final List<Fish> consumedFishes = new ArrayList<>(this.deadFishes);
        this.deadFishes.clear();
        return consumedFishes;
    }

    /**
     * Spawns a new fish and applies it's speed.
     */
    private void spawnFish() {
        final Fish fish = this.spawner.spawnFish(this, this.currentWeather, false);
        applyWeatherSpeedEffect(fish);
        this.fishes.add(fish);
    }

    /**
     * Adds a fish to the manager.
     * 
     * @param fish the fish to add
     */
    public void addFish(final Fish fish) {
        this.fishes.add(fish);
    }

    /**
     * Moves every fish and removes those that have left the map.
     * 
     * @param deltaTime the time
     */
    public void update(final long deltaTime) {
        this.boidsManager.update(deltaTime);
        final Iterator<Fish> iterator = this.fishes.iterator();
        while (iterator.hasNext()) {
            final Fish fish = iterator.next();
            if (fish instanceof FishImpl || fish instanceof PredatorFishImpl) {
                fish.update(this.mapWidth, this.mapHeight, deltaTime);
            }

            if (isOutOfBounds(fish)) {
                iterator.remove();
            }
        }
        replenish();
    }

    /**
     * Removes a specific fish.
     *
     * @param fish the fish to remove
     */
    public void removeFish(final Fish fish) {
        if (this.fishes.remove(fish)) {
            replenish();
        }
    }

    /**
     * Removes a specific dead fish.
     *
     * @param fish the dead fish to remove
     */
    public void removeDeadFish(final Fish fish) {
        if (this.fishes.contains(fish)) {
            this.deadFishes.add(fish);
            this.fishes.remove(fish);
            replenish();
        }
    }

    /**
     * Replenishes the fish population to maintain a target count.
     */
    private void replenish() {
        while (this.fishes.size() - this.boidsManager.getBoids().size() < TARGET_FISH_COUNT) {
            final double randomValue = this.random.nextDouble();
            if (randomValue < 0.3) {
                this.boidsManager.spawnBoids();
            } else {
                spawnFish();
            }
        }
    }

    private void applyWeatherSpeedEffect(final Fish fish) {
        if (fish.getType().isStormOnly()) {
            return;
        }
        final double multiplier =
                currentWeather == Weather.STORMY ? STORM_SPEED_MULTIPLIER : CLEAR_SPEED_MULTIPLIER;
        fish.setSpeedMultiplier(multiplier);
    }

    private boolean isOutOfBounds(final Fish fish) {
        return fish.getX() < -fish.getCollisionArea().getWidth()
                || fish.getX() > this.mapWidth + fish.getCollisionArea().getWidth();
    }
}
