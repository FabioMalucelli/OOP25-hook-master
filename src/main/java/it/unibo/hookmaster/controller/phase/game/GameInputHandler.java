package it.unibo.hookmaster.controller.phase.game;

/**
 * Interface for handling user input in the game
 * phase of the game.
 */
public interface GameInputHandler {
    /**
     * Called when the user presses the W key.
     */
    void pressW();

    /**
     * Called when the user releases the W key.
     */
    void releaseW();

    /**
     * Called when the user presses the A key.
     */
    void pressA();

    /**
     * Called when the user releases the A key.
     */
    void releaseA();

    /**
     * Called when the user presses the S key.
     */
    void pressS();

    /**
     * Called when the user releases the S key.
     */
    void releaseS();

    /**
     * Called when the user presses the D key.
     */
    void pressD();

    /**
     * Called when the user releases the D key.
     */
    void releaseD();

    /**
     * Called when the user presses the shop button.
     */
    void pressShopBtn();

    /**
     * Called when the user presses the escape button.
     */
    void pressEsc();
}
