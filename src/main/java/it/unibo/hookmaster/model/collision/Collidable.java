package it.unibo.hookmaster.model.collision;

/**
 * Interface representing an object that can participate in collisions.
 */
public interface Collidable {
    /**
     * Gets the CollisionBox associated with this Collidable object.
     * Two Collidable objects are considered to be colliding if their CollisionBoxes intersect.
     *
     * @return The CollisionBox of this object.
     */
    CollisionArea getCollisionArea();

    /**
     * Method called when a collision occurs with another Collidable object.
     *
     * @param other The other Collidable object involved in the collision.
     * @return true if the other object should be removed from the game, false otherwise.
     *     (the event is still fired on the other object, even if this method returns true)
     */
    boolean onCollision(Collidable other);
}
