package it.unibo.hookmaster.model.fishdata.movement;

import java.util.Random;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

/**
 * Moves a fish in a diagonal line, stopping at the edges.
 */
public final class DiagonalMovement implements MovementStrategy {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;
    private static final int MIN_PAUSE_FRAMES = 40;
    private static final int MAX_PAUSE_FRAMES = 100;

    private final double verticalRatio;
    private final Random random = new Random();

    private int verticalDirection = 1;
    private boolean paused;
    private int pauseFramesRemaining;

    /**
     * Creates a diagonal movement strategy.
     *
     * @param verticalRatio fraction of horizontal speed applied vertically
     *                      (e.g. 0.3 means a gentle diagonal, 1.0 a 45-degree one)
     */
    public DiagonalMovement(final double verticalRatio) {
        this.verticalRatio = verticalRatio;
    }

    @Override
    public void move(final Fish fish, final int mapWidth, final int mapHeight) {
        final double newX = fish.getX() + fish.getSpeed() * fish.getDirection();
        double newY = fish.getY();

        if (paused) {
            pauseFramesRemaining--;
            if (pauseFramesRemaining <= 0) {
                paused = false;
            }
        } else {
            final int verticalSpeed = (int) Math.round(fish.getSpeed() * verticalRatio);
            newY = fish.getY() + verticalSpeed * verticalDirection;

            final int minY = (int) Math.round(mapHeight * MIN_Y_RATIO);
            final int maxY = (int) Math.round(mapHeight * MAX_Y_RATIO);

            if (newY <= minY) {
                newY = minY;
                verticalDirection = 1;
                startPause();
            } else if (newY >= maxY) {
                newY = maxY;
                verticalDirection = -1;
                startPause();
            }
        }

        fish.setPosition(new Position(newX, newY));
    }

    private void startPause() {
        this.paused = true;
        this.pauseFramesRemaining = MIN_PAUSE_FRAMES + random.nextInt(MAX_PAUSE_FRAMES - MIN_PAUSE_FRAMES);
    }
}
