package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.collision.Collidable;

/**
 * Defines how a fish reacts to a collision with another collidable
 * object (e.g. a hook or a predator). The concrete reaction is
 * decided by whoever manages that specific interaction, not by Fish
 * itself.
 */
@FunctionalInterface
public interface CollisionReaction {

    /**
     * Reacts to a collision with the given collidable.
     *
     * @param other the other collidable involved in the collision
     */
    void react(Collidable other);
}
