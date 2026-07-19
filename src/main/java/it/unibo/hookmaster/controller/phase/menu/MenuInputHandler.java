package it.unibo.hookmaster.controller.phase.menu;

/**
 * Interface for handling user input in the menu
 * phase of the game.
 */
public interface MenuInputHandler {
    /**
     * Called when the user presses the play button in the menu.
     */
    void pressPlayButton();

    /**
     * Called when the user presses the load button in the menu.
     */
    void pressLoadButton();

    /**
     * Called when the user presses the exit button in the menu.
     */
    void pressExitButton();
}
