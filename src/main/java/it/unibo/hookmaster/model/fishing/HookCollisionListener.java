package it.unibo.hookmaster.model.fishing;

import it.unibo.hookmaster.model.collision.Collidable;

/**
 * Observer Pattern - observer interface used exclusively by Hook.
 * 
 * <p>Prevents a circular dependency between Hook and FishingController.</p>
 */
@FunctionalInterface
public interface HookCollisionListener {

    /**
     * Called by Hook when it collides with another Collidable 
     * while in a state where catching is possible (DROPPING or REELING).
     * 
     * @param other the other object involved in the collision
     */
    void onHookCollision(Collidable other);
}
