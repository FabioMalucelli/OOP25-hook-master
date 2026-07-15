package it.unibo.hookmaster.controller;

/**
 * This is the main game controller interface.
 * It drives the game loop and connects the
 * model and the view.
 */
public interface GameController {
    /**
     * Runs the game loop.
     * This method blocks until the game is closed.
     */
    void run();
}
