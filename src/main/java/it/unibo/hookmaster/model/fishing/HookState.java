package it.unibo.hookmaster.model.fishing;

/**
 * State Pattern (simplified via enum)
 * states of the Hook.
 */
public enum HookState {
    IDLE,       //The hook is on the boat
    DROPPING,   //The hook is sinking to the bottom
    REELING,    //The hook is being retrived
    MINIGAME    //The hook has caught a fish
}
