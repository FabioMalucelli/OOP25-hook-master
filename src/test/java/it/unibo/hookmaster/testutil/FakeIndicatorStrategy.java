package it.unibo.hookmaster.testutil;

import it.unibo.hookmaster.model.fishing.minigame.IndicatorStrategy;

/**
 * A fake IndicatorStrategy that lets tests set the position directly
 * and counts how many times update() is called.
 */
public final class FakeIndicatorStrategy implements IndicatorStrategy {

    private double position;
    private int updateCallCount;

    /**
     * Constructs the fake indicator starting at the given postition.
     * 
     * @param startPosition the initial position on the bar
     */
    public FakeIndicatorStrategy(final double startPosition) {
        this.position = startPosition;
    }

    @Override
    public void update(final long deltaTime) {
        updateCallCount++;
    }

    @Override
    public double getPosition() {
        return position;
    }

    /**
     * Directly sets the indicator position bypassing update().
     * 
     * @param position the new position to report via getPosition()
     */
    public void setPosition(final double position) {
        this.position = position;
    }

    /**
     * @return how many times update() was called
     */
    public int getUpdateCallCount() {
        return updateCallCount;
    }
}
