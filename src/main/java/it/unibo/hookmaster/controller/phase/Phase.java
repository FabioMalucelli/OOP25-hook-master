package it.unibo.hookmaster.controller.phase;

/**
 * Represents the different controller phases
 * of the game.
 * Two phases are considered different if
 * there is a difference in how the user input
 * or the model visualization is handled.
 */
public enum Phase {
    MENU,
    GAME,
    MINIGAME,
    SHOP,
}
