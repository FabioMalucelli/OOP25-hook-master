package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;
import java.util.Random;

/**
 * Moves a fish in a diagonal line.
 */
public final class DiagonalMovement implements MovementStrategy {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;
    private static final long MIN_PAUSE_MILLIS = 700;
    private static final long MAX_PAUSE_MILLIS = 1700;

    private final double verticalRatio;
    private final Random random = new Random();

    private int verticalDirection = 1;
    private boolean paused;
    private long pauseMillisRemaining;

    /**
     * Creates a diagonal movement strategy.
     *
     * @param verticalRatio fraction of horizontal speed applied vertically
     */
    public DiagonalMovement(final double verticalRatio) {
        this.verticalRatio = verticalRatio;
    }

    @Override
    public void move(final Fish fish, final double mapWidth, final double mapHeight, final long deltaTime) {
        final double frameScale = MovementTime.frameScale(deltaTime);
        final double newX = fish.getX() + (fish.getSpeed() * fish.getDirection() * frameScale);
        double newY = fish.getY();

        if (paused) {
            pauseMillisRemaining -= deltaTime;
            if (pauseMillisRemaining <= 0) {
                paused = false;
            }
        } else {
            final int verticalSpeed = (int) Math.round(fish.getSpeed() * verticalRatio * frameScale);
            newY = fish.getY() + verticalSpeed * verticalDirection;

            final double minY = mapHeight * MIN_Y_RATIO;
            final double maxY = mapHeight * MAX_Y_RATIO;

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
        this.pauseMillisRemaining = MIN_PAUSE_MILLIS
                + (long) (random.nextDouble() * (MAX_PAUSE_MILLIS - MIN_PAUSE_MILLIS));
    }
}
