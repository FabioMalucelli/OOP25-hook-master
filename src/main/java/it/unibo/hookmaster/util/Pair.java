package it.unibo.hookmaster.util;

/**
 * Generic pair class to hold two objects.
 *
 * @param <T> the type of the first object
 * @param <U> the type of the second object
 * @param first the first object
 * @param second the second object
 */
public record Pair<T, U>(T first, U second) {
    /**
     * Build a pair from two objects.
     *
     * @param <T> the type of the first object
     * @param <U> the type of the second object
     * @param first the first object
     * @param second the second object
     * @return a new pair containing the two objects
     */
    public static <T, U> Pair<T, U> of(final T first, final U second) {
        return new Pair<>(first, second);
    }
}
