package it.unibo.hookmaster.util;

/**
 * Utility for converting the millisecond deltaTime.
 */
public final class TimeUtils {

    private static final double MILLIS_PER_SECOND = 1000.0;

    private TimeUtils() { }

    /**
     * Converts a millisecond duration to seconds.
     * 
     * @param millis the duration in milliseconds
     * @return       the equivalent duration in seconds
     */
    public static double toSeconds(final long millis) {
        return millis / MILLIS_PER_SECOND;
    }
}
