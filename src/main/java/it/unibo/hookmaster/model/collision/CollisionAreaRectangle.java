package it.unibo.hookmaster.model.collision;

/**
 * Represents a collision area with a rectangular shape.
 */
public final class CollisionAreaRectangle implements CollisionArea {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    /**
     * Constructs a CollisionAreaRectangle with the specified position and dimensions.
     *
     * @param x The x-coordinate of the top-left corner of the rectangle.
     * @param y The y-coordinate of the top-left corner of the rectangle.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @throws IllegalArgumentException if width or height is negative
     */
    public CollisionAreaRectangle(final double x, final double y, final double width, final double height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Rectangle dimensions cannot be negative.");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the x-coordinate of the top-left corner.
     *
     * @return the x-coordinate of the top-left corner
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of the top-left corner.
     *
     * @return the y-coordinate of the top-left corner
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns the rectangle width.
     *
     * @return the rectangle width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the rectangle height.
     *
     * @return the rectangle height
     */
    public double getHeight() {
        return this.height;
    }
}
