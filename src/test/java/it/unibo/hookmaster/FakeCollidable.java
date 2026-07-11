package it.unibo.hookmaster;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;

/**
 * A Collidable test double that is intentionally NOT a Catchable.
 * Used to verify that onHookCollision ignores collisions with nonFish objects.
 */
public final class FakeCollidable implements Collidable {
    
    @Override
    public CollisionArea getCollisionArea() {
        return other -> false;
    }

    @Override
    public void onCollision(final Collidable other) {
        //Intentionally empty : this double only need to exists as a nonCatchable  Collidable
        //it never reacts to collsions itself.
    }
}
