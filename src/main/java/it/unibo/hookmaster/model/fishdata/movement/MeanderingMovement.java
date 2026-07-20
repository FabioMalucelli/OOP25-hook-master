package it.unibo.hookmaster.model.fishdata.movement;

import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

import java.util.Random;

/**
 * Moves a fish in differing directions.
 */
public final class MeanderingMovement implements MovementStrategy {

    private static final long MIN_STRAIGHT_MILLIS = 1000;
    private static final long MAX_STRAIGHT_MILLIS = 2500;
    private static final long MIN_TURN_MILLIS = 330;
    private static final long MAX_TURN_MILLIS = 830;
    private static final double TURN_VERTICAL_SPEED_FACTOR = 0.5;

    private final Random random = new Random();

    private Phase phase = Phase.STRAIGHT;
    private long millisRemaining = randomStraightDuration();
    private int verticalDirection = 1;

    private enum Phase {
        STRAIGHT, TURNING
    }

    @Override
    public void move(final Fish fish, final double mapWidth, final double mapHeight, final long deltaTime) {
        final double frameScale = MovementTime.frameScale(deltaTime);

        final double newX = fish.getX() + (fish.getSpeed() * fish.getDirection() * frameScale);
        double newY = fish.getY();

        if (phase == Phase.TURNING) {
            final CollisionAreaRectangle collisionArea = fish.getCollisionArea();
            final double maxY = mapHeight - collisionArea.getHeight();

            final int verticalSpeed = (int) Math.round(fish.getSpeed() * TURN_VERTICAL_SPEED_FACTOR * frameScale);
            newY = fish.getY() + verticalSpeed * verticalDirection;

            if (newY <= 0) {
                newY = 0;
                verticalDirection = 1;
            } else if (newY >= maxY) {
                newY = maxY;
                verticalDirection = -1;
            }
        }

        fish.setPosition(new Position(newX, newY));
        advancePhase(deltaTime);
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
            default:
                throw new IllegalStateException("Unexpected phase: " + phase);
        }
    }

    private long randomStraightDuration() {
        return MIN_STRAIGHT_MILLIS + (long) (random.nextDouble() * (MAX_STRAIGHT_MILLIS - MIN_STRAIGHT_MILLIS));
    }

    private long randomTurnDuration() {
        return MIN_TURN_MILLIS + (long) (random.nextDouble() * (MAX_TURN_MILLIS - MIN_TURN_MILLIS));
    }
}
