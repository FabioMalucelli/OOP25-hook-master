package it.unibo.hookmaster.model.fishing.minigame;

import it.unibo.hookmaster.util.TimeUtils;

/**
 * Strategy Pattern - concrete: the indicator moves linearly
 * back and forth between 0.0 and 1.0 at a constant speed.
 * This is the default indicator.
 */
public final class OscillatingIndicator implements IndicatorStrategy {

    private static final double MIN = 0.0;
    private static final double MAX = 1.0;

    private final double speed;
    private double position;
    private boolean movingForward;

    /**
     * Constructs the indicator starting at the left end, moving right.
     * 
     * @param speed bar-fractions per second
     */
    public OscillatingIndicator(final double speed) {
        this.speed = speed;
        this.position = MIN;
        this.movingForward = true;
    }

    @Override
    public void update(final long deltaTime) {
        final double deltaSeconds = TimeUtils.toSeconds(deltaTime);
        final double delta = speed * deltaSeconds;
        if (movingForward) {
            position += delta;
            if (position >= MAX) {
                position = MAX;
                movingForward = false;
            }
        } else {
            position -= delta;
            if (position <= MIN) {
                position = MIN;
                movingForward = true;
            }
        }
    }

    @Override
    public double getPosition() {
        return position;
    }
}
