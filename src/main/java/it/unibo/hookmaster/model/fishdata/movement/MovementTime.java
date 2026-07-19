package it.unibo.hookmaster.model.fishdata.movement;

/**
 * Utility for time-based movement scaling, ensuring consistent speed
 * regardless of frame rate.
 */
public final class MovementTime {

    private static final long REFERENCE_FRAME_MILLIS = 16;

    private MovementTime() { }

    /**
     * Computes the scale factor to apply to per-frame speed values,
     * given the actual time elapsed since the last frame.
     *
     * @param deltaTime milliseconds elapsed since the last update
     * @return the scale factor (1.0 at the reference frame rate)
     */
    public static double frameScale(final long deltaTime) {
        return deltaTime / (double) REFERENCE_FRAME_MILLIS;
    }
}
