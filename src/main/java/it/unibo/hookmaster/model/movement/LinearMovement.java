package it.unibo.hookmaster.model.movement;

import it.unibo.hookmaster.model.Fish;
import it.unibo.hookmaster.model.Position;

/**
 * Moves a fish in a straight horizontal line.
 */
public final class LinearMovement implements MovementStrategy {

    @Override
    public void move(final Fish fish, final int mapWidth, final int mapHeight) {
        final int newX = fish.getX() + fish.getSpeed() * fish.getDirection();
        fish.setPosition(new Position(newX, fish.getY()));
    }
}
