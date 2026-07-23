package it.unibo.hookmaster.testutil;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;

/**
 * A Collidable test double that is intentionally NOT a Catchable.
 * Used to verify that Hook.onCollision ignores collisions with nonFish objects.
 */
public final class FakeCollidable implements Collidable {

    @Override
    public CollisionArea getCollisionArea() {
        return new CollisionAreaRectangle(0, 0, 0, 0);
    }

    @Override public boolean onCollision(final Collidable other) {
        //Intentionally empty.
        //it never reacts to collision itself.
        return false;
    }
}
