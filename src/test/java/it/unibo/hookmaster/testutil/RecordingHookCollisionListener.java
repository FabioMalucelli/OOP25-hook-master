package it.unibo.hookmaster.testutil;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.fishing.HookCollisionListener;

/**
 * A fake HookCollisionListener that counts calls and saves the last collision.
 * Used in tests to check if, how many times and with what data onHookCollision was called.
 */
public final class RecordingHookCollisionListener implements HookCollisionListener {

    private Collidable lastCollision;
    private int callCount;

    @Override
    public void onHookCollision(final Collidable other) {
        this.lastCollision = other;
        this.callCount++;
    }

    /**
     * @return the argument passed to the most recent onHookCollision call, or null if it was never called
     */
    public Collidable getLastCollision() {
        return lastCollision;
    }

    /**
     * @return how many times onHookCollision was called
     */
    public int getCallCount() {
        return callCount;
    }
}
