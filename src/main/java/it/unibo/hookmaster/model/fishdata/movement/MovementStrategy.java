package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.fishdata.Fish;

/**
 * Defines how a fish moves within the map.
 */
@FunctionalInterface
public interface MovementStrategy {

    /**
     * Computes and applies the next position of the given fish.
     *
     * @param fish             the fish to move
     * @param mapWidth         the horizontal size of the map
     * @param mapHeight        the vertical size of the map
     * @param deltaTime  milliseconds elapsed since the last update
     */
    void move(Fish fish, double mapWidth, double mapHeight, long deltaTime);
}
