package it.unibo.hookmaster.model;

/**
 * Receives notifications about changes in the fish population.
 */
public interface FishListener {

    /**
     * When a fish spawns.
     *
     * @param fish the fish that was spawned
     */
    void onFishSpawned(Fish fish);

    /**
     * When a fish's position is updated.
     *
     * @param fish the fish that moved
     */
    void onFishMoved(Fish fish);

    /**
     * When a fish exist the map.
     *
     * @param fish the fish that was removed
     */
    void onFishRemoved(Fish fish);
}
