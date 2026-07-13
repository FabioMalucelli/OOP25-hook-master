package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Immutable snapshot of a Hook position state at a given instant.
 * 
 * @param x             the hook X coordinate in pixels at the time of the snapshot
 * @param y             the hook Y coordinate in pixels at the time of the snapshot
 * @param state         the hook state at the time of the snapshot
 * @param hookedFish    the fish hooked at the time of the snapshot, or null if none
 */
public record HookSnapshot(double x, double y, HookState state, Catchable hookedFish) implements HookView {

    @Override
    public HookState getCurrentState() {
        return state;
    }

    @Override
    public Catchable getHookedFish() {
        return hookedFish;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
