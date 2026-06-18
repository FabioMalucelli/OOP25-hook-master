package it.unibo.hookmaster.model.collision;

/**
 * Represents a collision area with a rectangular shape.
 */
public class CollisionAreaRectangle implements CollisionArea {
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
     */
    public CollisionAreaRectangle(final double x, final double y, final double width, final double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean intersects(final CollisionArea other) {
        if (other instanceof CollisionAreaRectangle) {
            final CollisionAreaRectangle otherRect = (CollisionAreaRectangle) other;
            return this.x < otherRect.x + otherRect.width
                && this.x + this.width > otherRect.x
                && this.y < otherRect.y + otherRect.height
                && this.y + this.height > otherRect.y;
        }
        return false;
    }
}

