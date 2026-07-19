package it.unibo.hookmaster.model.fishdata.movement;

/**
 * Utility for time-based movement scaling.
 */
public final class MovementTime {

    private static final long REFERENCE_FRAME_MILLIS = 16;

    private MovementTime() { }

    /**
     * Calculates the time elapsed since last frame.
     *
     * @param deltaTime milliseconds elapsed since the last update
     * @return the scale factor
     */
    public static double frameScale(final long deltaTime) {
        return deltaTime / (double) REFERENCE_FRAME_MILLIS;
    }
}
