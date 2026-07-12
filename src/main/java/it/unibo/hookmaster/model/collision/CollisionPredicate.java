package it.unibo.hookmaster.model.collision;

/**
 * Tests whether two collision areas intersect.
 */
@FunctionalInterface
public interface CollisionPredicate {
    /**
     * Tests whether two collision areas intersect.
     *
     * @param first the first collision area
     * @param second the second collision area
     * @return {@code true} if the two areas intersect, {@code false} otherwise
     */
    boolean test(CollisionArea first, CollisionArea second);
}
