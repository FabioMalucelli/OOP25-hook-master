package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

import java.util.Random;

/**
 * Moves a fish alternating between straight horizontal swimming and vertical swimming.
 */
public final class MeanderingMovement implements MovementStrategy {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;
    private static final long MIN_STRAIGHT_MILLIS = 1000;
    private static final long MAX_STRAIGHT_MILLIS = 2500;
    private static final long MIN_TURN_MILLIS = 330;
    private static final long MAX_TURN_MILLIS = 830;
    private static final long MIN_PAUSE_MILLIS = 700;
    private static final long MAX_PAUSE_MILLIS = 1700;
    private static final double TURN_VERTICAL_SPEED_FACTOR = 0.5;

    private final Random random = new Random();

    private Phase phase = Phase.STRAIGHT;
    private long millisRemaining = randomStraightDuration();
    private int verticalDirection = 1;

    private enum Phase {
        STRAIGHT, TURNING, PAUSED
    }

    @Override
    public void move(final Fish fish, final double mapWidth, final double mapHeight, final long deltaTime) {
        final double frameScale = MovementTime.frameScale(deltaTime);
        final double minY = mapHeight * MIN_Y_RATIO;
        final double maxY = mapHeight * MAX_Y_RATIO;

        final double newX = fish.getX() + (fish.getSpeed() * fish.getDirection() * frameScale);
        double newY = fish.getY();

        if (phase == Phase.TURNING) {
            final int verticalSpeed = (int) Math.round(fish.getSpeed() * TURN_VERTICAL_SPEED_FACTOR * frameScale);
            newY = fish.getY() + verticalSpeed * verticalDirection;

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
        advancePhase(deltaTime);
    }

    private void startPause() {
        this.phase = Phase.PAUSED;
        this.millisRemaining = MIN_PAUSE_MILLIS
                + (long) (random.nextDouble() * (MAX_PAUSE_MILLIS - MIN_PAUSE_MILLIS));
    }

    private void advancePhase(final long deltaTime) {
        millisRemaining -= deltaTime;
        if (millisRemaining > 0) {
            return;
        }

        switch (phase) {
            case STRAIGHT:
                phase = Phase.TURNING;
                verticalDirection = random.nextBoolean() ? 1 : -1;
                millisRemaining = randomTurnDuration();
                break;
            case TURNING:
                phase = Phase.STRAIGHT;
                millisRemaining = randomStraightDuration();
                break;
            case PAUSED:
                phase = Phase.TURNING;
                millisRemaining = randomTurnDuration();
                break;
        }
    }

    private long randomStraightDuration() {
        return MIN_STRAIGHT_MILLIS + (long) (random.nextDouble() * (MAX_STRAIGHT_MILLIS - MIN_STRAIGHT_MILLIS));
    }

    private long randomTurnDuration() {
        return MIN_TURN_MILLIS + (long) (random.nextDouble() * (MAX_TURN_MILLIS - MIN_TURN_MILLIS));
    }
}
