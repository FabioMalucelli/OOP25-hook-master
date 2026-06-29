package it.unibo.hookmaster;

/**
 * Model of the Boat. It moves only horizzontaly on the surface.
 */
public final class Boat {

    private double x;
    private final double y;         //the boat is always on the same height
    private final double speed;     //pixels per second
    private final double minX;
    private final double maxX;
    private boolean movingLeft;
    private boolean movingRight;

    /**
     * Constructs a new Boat with specified position, speed, and boundaries.
     *
     * @param startX   the initial X position
     * @param surfaceY the fixed Y position
     * @param speed    the speed of the boat
     * @param minX     the minimum allowed X boundary
     * @param maxX     the maximum allowed X boundary
     */
    public Boat(final double startX, final double surfaceY, final double speed, final double minX, final double maxX) {
        this.x = startX;
        this.y = surfaceY;
        this.speed = speed;
        this.minX = minX;
        this.maxX = maxX;
    }

    /**
     * Updates the boat position based on the current input.
     * Called once per frame of the GameLoop.
     *
     * @param deltaTime the time that has passed since the last frame
     */
    public void update(final double deltaTime) {
        if (movingLeft && !movingRight) { 
            x -= speed * deltaTime;
        } else if (movingRight && !movingLeft) {
            x += speed * deltaTime;
        }
        //containment within the limits of the map 
        if (x < minX) {
            x = minX;
        }
        if (x > maxX) {
            x = maxX;
        }
    }

    /**
     * Sets whether the boat is moving left.
     * 
     * @param movingLeft movingLeft true if moving left, false otherwise
     */
    public void setMovingLeft(final boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     * Sets whether the boat is moving right.
     *
     * @param movingRight true if moving right, false otherwise
     */
    public void setMovingRight(final boolean movingRight) { 
        this.movingRight = movingRight;
    }

    /**
     * Gets the current X position of the boat.
     *
     * @return the X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the fixed Y position of the boat.
     *
     * @return the Y coordinate
     */
    public double getY() {
        return y;
    }
}
