package it.unibo.hookmaster.model.collision;

/**
 * Represents a circular collision area.
 */
public final class CollisionAreaCircle implements CollisionArea {
    private final double centerX;
    private final double centerY;
    private final double radius;

    /**
     * Constructs a circular collision area.
     *
     * @param centerX the X coordinate of the circle center
     * @param centerY the Y coordinate of the circle center
     * @param radius the circle radius
     * @throws IllegalArgumentException if the radius is negative
     */
    public CollisionAreaCircle(final double centerX, final double centerY, final double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Circle radius cannot be negative.");
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    /**
     * @return the X coordinate of the circle center
     */
    public double getCenterX() {
        return this.centerX;
    }

    /**
     * @return the Y coordinate of the circle center
     */
    public double getCenterY() {
        return this.centerY;
    }

    /**
     * @return the circle radius
     */
    public double getRadius() {
        return this.radius;
    }
}
