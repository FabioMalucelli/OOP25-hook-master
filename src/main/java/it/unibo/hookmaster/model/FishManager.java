package it.unibo.hookmaster.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Owns the population of live fish, drives their movement each tick,
 * handles spawning and removal of fishes.
 */
public class FishManager {

    private final List<Fish> fishes = new ArrayList<>();
    private final List<FishListener> listeners = new ArrayList<>();
    private final FishSpawner spawner;
    private final int mapWidth;
    private final int mapHeight;

    /**
     * Creates a new fish manager.
     *
     * @param spawner   the spawner used to create new fish
     * @param mapWidth  the X size of the map
     * @param mapHeight the Y size of the map
     */
    public FishManager(final FishSpawner spawner, final int mapWidth, final int mapHeight) {
        this.spawner = Objects.requireNonNull(spawner);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
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
     * Spawns a new fish via the spawner and notifies listeners.
     */
    public void spawnFish() {
        final Fish fish = this.spawner.spawnFish();
        this.fishes.add(fish);
        for (final FishListener listener : this.listeners) {
            listener.onFishSpawned(fish);
        }
    }

    /**
     * Moves all fishes and removes those out of bounds.
     */
    public void update() {
        final Iterator<Fish> iterator = this.fishes.iterator();
        while (iterator.hasNext()) {
            final Fish fish = iterator.next();
            fish.update(this.mapWidth, this.mapHeight);

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
     * Removes a specific fish (for example because it was caught).
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

    private boolean isOutOfBounds(final Fish fish) {
        final int margin = 100;
        return fish.getX() < -margin || fish.getX() > this.mapWidth + margin;
    }
}
