package it.unibo.hookmaster.model.movement;

import it.unibo.hookmaster.model.Fish;

/**
 * Defines how a fish moves within the map.
 */
@FunctionalInterface
public interface MovementStrategy {

    /**
     * Computes and applies the next position of the given fish.
     *
     * @param fish      the fish to move
     * @param mapWidth  the x size
     * @param mapHeight the y size of the map
     */
    void move(Fish fish, int mapWidth, int mapHeight);
}
