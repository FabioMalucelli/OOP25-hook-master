package it.unibo.hookmaster;

/**
 * Simplified State Pattern  with an Enum.
 */
public enum HookState {
    IDLE,       //The hook is on the boat
    DROPPING,   //The hook is sinking to the bottom
    REELING,    //The hook is being retrived
    MINIGAME    //The hook has caught a fish
}
