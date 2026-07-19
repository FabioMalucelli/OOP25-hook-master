package it.unibo.hookmaster.model.fishing.hook;

/**
 * State Pattern (simplified via enum)
 * states of the Hook.
 */
public enum HookState {
    MOVING,     //The hook moves freely in response to directional input and can hook a fish
    MINIGAME    //The hook is frozen because the catching minigame is in progress
}
