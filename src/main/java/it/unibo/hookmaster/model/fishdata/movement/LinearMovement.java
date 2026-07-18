package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

/**
 * Moves a fish in a straight horizontal line.
 */
public final class LinearMovement implements MovementStrategy {

    @Override
    public void move(final Fish fish, final int mapWidth, final int mapHeight) {
        final double newX = fish.getX() + fish.getSpeed() * fish.getDirection();
        fish.setPosition(new Position(newX, fish.getY()));
    }
}
