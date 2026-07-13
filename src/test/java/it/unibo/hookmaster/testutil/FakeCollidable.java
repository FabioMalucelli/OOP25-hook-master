package it.unibo.hookmaster.testutil;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;

/**
 * A Collidable test double that is intentionally NOT a Catchable.
 * Used to verify that onHookCollision ignores collisions with nonFish objects.
 */
public final class FakeCollidable implements Collidable {

    @Override
    public CollisionArea getCollisionArea() {
        return new CollisionAreaRectangle(0, 0, 0, 0);
    }

    @Override
    public void onCollision(final Collidable other) {
        //Intentionally empty : this double only need to exists as a nonCatchable  Collidable
        //it never reacts to collsions itself.
    }
}
