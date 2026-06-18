package it.unibo.hookmaster.model.collision;

import java.util.List;

/**
 * Naive implementation of a collision manager that
 * checks for collisions between each pair of
 * Collidables.
 */
public final class CollisionManagerImpl implements CollisionManager {
    /*
     * @inheritDoc
     */
    @Override
    public void checkCollisions(final List<Collidable> collidables) {
        for (final Collidable c1 : collidables) {
            for (final Collidable c2 : collidables) {
                if (!c1.equals(c2) && c1.getCollisionArea().intersects(c2.getCollisionArea())) {
                    c1.onCollision(c2);
                    c2.onCollision(c1);
                }
            }
        }
    }
}
