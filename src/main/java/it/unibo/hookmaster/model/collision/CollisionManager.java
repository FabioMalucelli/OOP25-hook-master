package it.unibo.hookmaster.model.collision;

import java.util.List;

/**
 * Interface for managing collisions between Collidable objects.
 */
@FunctionalInterface
public interface CollisionManager {
    /**
     * Checks for collisions between all Collidable objects.
     * If a collision is detected, the onCollision method of the involved objects is called.
     *
     * @param collidables the list of Collidable objects to check for collisions. Be aware
     *     that the specific implementation may require the collidables to be registered
     *     before hand in order to keep a state for optimization purposes.
     */
    void checkCollisions(List<Collidable> collidables);
}
