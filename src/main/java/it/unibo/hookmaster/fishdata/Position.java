package it.unibo.hookmaster.fishdata;

/**
 * Represents the starting position for a fish.
 */

public class Position {

    private final int x;
    private final int y;

    /**
     * Creates a new speed upgrade instance.
     * 
     * @param x x value of the UI.
     * @param y y value of the UI.
     */
    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value.
     * 
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value.
     * 
     * @return y
     */
    public int getY() {
        return y;
    }
}
