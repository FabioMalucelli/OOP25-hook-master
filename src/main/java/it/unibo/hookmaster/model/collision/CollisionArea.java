package it.unibo.hookmaster.model.collision;

/**
 * Represents the area that is considered occupied
 * by an object for collision detection purposes.
 */
@FunctionalInterface
public interface CollisionArea {
    /**
     * Checks if this collision area intersects with another collision area.
     *
     * @param other the other collision area
     *
     * @return true if the two areas intersect, false otherwise
     */
    boolean intersects(CollisionArea other);
}
