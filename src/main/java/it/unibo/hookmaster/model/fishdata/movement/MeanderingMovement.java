package it.unibo.hookmaster.model.fishdata.movement;

import java.util.Random;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.Position;

/**
 * Moves a fish alternating between straight horizontal swimming and brief vertical turns.
 */
public final class MeanderingMovement implements MovementStrategy {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;
    private static final int MIN_STRAIGHT_FRAMES = 60;
    private static final int MAX_STRAIGHT_FRAMES = 150;
    private static final int MIN_TURN_FRAMES = 20;
    private static final int MAX_TURN_FRAMES = 50;
    private static final int MIN_PAUSE_FRAMES = 40;
    private static final int MAX_PAUSE_FRAMES = 100;
    private static final double TURN_VERTICAL_SPEED_FACTOR = 0.5;

    private final Random random = new Random();

    private Phase phase = Phase.STRAIGHT;
    private int framesRemaining = randomStraightDuration();
    private int verticalDirection = 1;

    private enum Phase {
        STRAIGHT, TURNING, PAUSED
    }

    @Override
    public void move(final Fish fish, final int mapWidth, final int mapHeight) {
        final int minY = (int) Math.round(mapHeight * MIN_Y_RATIO);
        final int maxY = (int) Math.round(mapHeight * MAX_Y_RATIO);

        final double newX = fish.getX() + fish.getSpeed() * fish.getDirection();
        double newY = fish.getY();

        if (phase == Phase.TURNING) {
            final int verticalSpeed = (int) Math.round(fish.getSpeed() * TURN_VERTICAL_SPEED_FACTOR);
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
        advancePhase();
    }

    private void startPause() {
        this.phase = Phase.PAUSED;
        this.framesRemaining = MIN_PAUSE_FRAMES + random.nextInt(MAX_PAUSE_FRAMES - MIN_PAUSE_FRAMES);
    }

    private void advancePhase() {
        framesRemaining--;
        if (framesRemaining > 0) {
            return;
        }

        switch (phase) {
            case STRAIGHT:
                phase = Phase.TURNING;
                verticalDirection = random.nextBoolean() ? 1 : -1;
                framesRemaining = randomTurnDuration();
                break;
            case TURNING:
                phase = Phase.STRAIGHT;
                framesRemaining = randomStraightDuration();
                break;
            case PAUSED:
                phase = Phase.TURNING;
                framesRemaining = randomTurnDuration();
                break;
        }
    }

    private int randomStraightDuration() {
        return MIN_STRAIGHT_FRAMES + random.nextInt(MAX_STRAIGHT_FRAMES - MIN_STRAIGHT_FRAMES);
    }

    private int randomTurnDuration() {
        return MIN_TURN_FRAMES + random.nextInt(MAX_TURN_FRAMES - MIN_TURN_FRAMES);
    }
}
