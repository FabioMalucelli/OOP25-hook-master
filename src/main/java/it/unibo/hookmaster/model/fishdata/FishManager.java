package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherEvent;
import it.unibo.hookmaster.model.weather.WeatherObserver;
import it.unibo.hookmaster.model.weather.WeatherSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Owns the population of live fish, drives their movement each tick,
 * handles spawning/removal, and notifies registered listeners of any
 * change. Also reacts to weather changes, adjusting fish speed and
 * filtering which species are eligible to spawn.
 */
public class FishManager implements WeatherObserver {

    private static final double STORM_SPEED_MULTIPLIER = 1.5;
    private static final double CLEAR_SPEED_MULTIPLIER = 1.0;
    private static final int OUT_OF_BOUNDS_MARGIN = 100;

    private final List<Fish> fishes = new ArrayList<>();
    private final List<FishListener> listeners = new ArrayList<>();
    private final FishSpawner spawner;
    private final double mapWidth;
    private final double mapHeight;
    private Weather currentWeather;

    /**
     * Creates a new fish manager.
     *
     * @param spawner       the spawner used to create new fish
     * @param weatherSystem the weather system driving spawn eligibility and fish
     *                      speed
     * @param mapWidth      the horizontal size of the map
     * @param mapHeight     the vertical size of the map
     */
    public FishManager(final FishSpawner spawner, final WeatherSystem weatherSystem,
            final double mapWidth, final double mapHeight) {
        this.spawner = Objects.requireNonNull(spawner);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.currentWeather = weatherSystem.getCurrentWeather();
        weatherSystem.addObserver(this);
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
     * Registers a listener that will be notified of future changes.
     *
     * @param listener the listener to add
     */
    public void addListener(final FishListener listener) {
        this.listeners.add(Objects.requireNonNull(listener));
    }

    /**
     * Unregisters a previously added listener.
     *
     * @param listener the listener to remove
     */
    public void removeListener(final FishListener listener) {
        this.listeners.remove(listener);
    }

    /**
     * @return an unmodifiable view of the currently live fish
     */
    public List<Fish> getFishes() {
        return Collections.unmodifiableList(this.fishes);
    }

    /**
     * Spawns a new fish via the spawner, applies the current weather's
     * speed effect, and notifies listeners.
     */
    public void spawnFish() {
        final Fish fish = this.spawner.spawnFish(this.currentWeather);
        applyWeatherSpeedEffect(fish);
        this.fishes.add(fish);
        for (final FishListener listener : this.listeners) {
            listener.onFishSpawned(fish);
        }
    }

    /**
     * Advances the simulation by one tick: moves every fish and removes those that have left the map.
     * 
     * @param deltaTime the time
     */
    public void update(final long deltaTime) {
        final Iterator<Fish> iterator = this.fishes.iterator();
        while (iterator.hasNext()) {
            final Fish fish = iterator.next();
            fish.update(this.mapWidth, this.mapHeight, deltaTime);

            if (isOutOfBounds(fish)) {
                iterator.remove();
                for (final FishListener listener : this.listeners) {
                    listener.onFishRemoved(fish);
                }
            } else {
                for (final FishListener listener : this.listeners) {
                    listener.onFishMoved(fish);
                }
            }
        }
    }

    /**
     * Removes a specific fish (e.g. because it was caught) and
     * notifies listeners.
     *
     * @param fish the fish to remove
     */
    public void removeFish(final Fish fish) {
        if (this.fishes.remove(fish)) {
            for (final FishListener listener : this.listeners) {
                listener.onFishRemoved(fish);
            }
        }
    }

    private void applyWeatherSpeedEffect(final Fish fish) {
        if (fish.getType().isStormOnly()) {
            return;
        }
        final double multiplier = currentWeather == Weather.STORMY
                ? STORM_SPEED_MULTIPLIER
                : CLEAR_SPEED_MULTIPLIER;
        fish.setSpeedMultiplier(multiplier);
    }

    private boolean isOutOfBounds(final Fish fish) {
        return fish.getX() < -OUT_OF_BOUNDS_MARGIN || fish.getX() > this.mapWidth + OUT_OF_BOUNDS_MARGIN;
    }
}
