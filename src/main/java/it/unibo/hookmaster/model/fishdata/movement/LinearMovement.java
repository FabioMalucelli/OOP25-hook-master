package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

/**
 * Moves a fish in a straight horizontal line at its own speed according to its direction.
 */
public final class LinearMovement implements MovementStrategy {

    @Override
    public void move(final Fish fish, final double mapWidth, final double mapHeight, final long deltaTime) {
        final double frameScale = MovementTime.frameScale(deltaTime);
        final double newX = fish.getX() + (fish.getSpeed() * fish.getDirection() * frameScale);
        fish.setPosition(new Position(newX, fish.getY()));
    }
}
