package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Read only view of a Hook, exposing only the data needed to render its position, current state and the hooked fish.
 */
public interface HookView {

    /**
     * Gets the current state of the hook.
     * 
     * @return the current HookState
     */
    HookState getCurrentState();

    /**
     * Gets the fish currently hooked, if any.
     * 
     * @return the hooked Catchable, or null if none
     */
    Catchable getHookedFish();

    /**
     * Gets the current X position of the hook.
     * 
     * @return the X coordinates in pixels
     */
    double getX();

    /**
     * Gets the current Y position of the hook.
     * 
     * @return the Y coordinates in pixels
     */
    double getY();
}
