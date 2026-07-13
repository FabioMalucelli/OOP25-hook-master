package it.unibo.hookmaster.model.fishing.boat;

/**
 * Immutable snapshot of a boat position at a given instant.
 * 
 * @param x the boats X coordinate in pixels at the time of the snapshot
 * @param y the boats Y coordinate in pixels at the time of the snapshot
 */
public record BoatSnapshot(double x, double y) implements BoatView {

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
