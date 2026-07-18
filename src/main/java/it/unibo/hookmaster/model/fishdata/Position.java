package it.unibo.hookmaster.model.fishdata;

/**
 * Represents the starting position for a fish.
 */

public class Position {

    private final double x;
    private final double y;

    /**
     * Creates a new speed upgrade instance.
     * 
     * @param x x value of the UI.
     * @param y y value of the UI.
     */
    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value.
     * 
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * Returns the y value.
     * 
     * @return y
     */
    public double getY() {
        return y;
    }
}
