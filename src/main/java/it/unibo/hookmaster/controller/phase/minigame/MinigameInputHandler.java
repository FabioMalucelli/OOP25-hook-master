package it.unibo.hookmaster.controller.phase.minigame;

/**
 * Interface representing the input handler for the minigame phase.
 */
public interface MinigameInputHandler {
    /**
     * Called when the user presses the ESC key in the minigame.
     */
    void pressEsc();

    /**
     * Called when the user presses the SPACE key in the minigame.
     */
    void pressSpace();
}
