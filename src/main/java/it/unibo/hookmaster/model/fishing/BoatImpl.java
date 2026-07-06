package it.unibo.hookmaster.model.fishing;

/**
 * Standard implementation of Boat.
 * Moves horizzontally at a constant speed within defined boundaries.
 * If both movement flags are active simultaneously the boat stays still. 
 */
public final class BoatImpl implements Boat {

    private double x;
    private final double y;         //the boat is always on the same height
    private final double speed;     //pixels per second
    private final double minX;
    private final double maxX;
    private boolean movingLeft;
    private boolean movingRight;

    /**
     * Constructs a new BoatImpl.
     *
     * @param startX   the initial X position
     * @param surfaceY the fixed Y position
     * @param speed    the speed of the boat
     * @param minX     the minimum allowed X boundary
     * @param maxX     the maximum allowed X boundary
     */
    public BoatImpl(final double startX, final double surfaceY, final double speed, final double minX, final double maxX) {
        this.x = startX;
        this.y = surfaceY;
        this.speed = speed;
        this.minX = minX;
        this.maxX = maxX;
    }

    @Override
    public void update(final double deltaTime) {
        if (movingLeft && !movingRight) { 
            x -= speed * deltaTime;
        } else if (movingRight && !movingLeft) {
            x += speed * deltaTime;
        }
        x = Math.max(minX, Math.min(maxX, x));
    }

    @Override
    public void setMovingLeft(final boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    @Override
    public void setMovingRight(final boolean movingRight) { 
        this.movingRight = movingRight;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
