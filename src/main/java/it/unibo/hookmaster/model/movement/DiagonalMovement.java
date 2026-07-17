package it.unibo.hookmaster.model.movement;

import it.unibo.hookmaster.model.Fish;
import it.unibo.hookmaster.model.Position;

/**
 * Moves a fish in a straight diagonal line: horizontal speed from the
 * fish's own speed, vertical speed as a fixed fraction of it.
 */
public final class DiagonalMovement implements MovementStrategy {

    private final double verticalRatio;

    /**
     * Creates a diagonal movement strategy.
     *
     * @param verticalRatio fraction of horizontal speed applied vertically (the smaller the ratio the gentler the curve)
     */
    public DiagonalMovement(final double verticalRatio) {
        this.verticalRatio = verticalRatio;
    }

    @Override
    public void move(final Fish fish, final int mapWidth, final int mapHeight) {
        final int newX = fish.getX() + fish.getSpeed() * fish.getDirection();
        final int verticalSpeed = (int) Math.round(fish.getSpeed() * verticalRatio);
        int newY = fish.getY() + verticalSpeed;

        // Rimbalza sui bordi verticali della mappa invece di uscirne.
        if (newY < 0 || newY > mapHeight) {
            newY = Math.max(0, Math.min(newY, mapHeight));
        }

        fish.setPosition(new Position(newX, newY));
    }
}
