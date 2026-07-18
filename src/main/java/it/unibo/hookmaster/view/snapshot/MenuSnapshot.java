package it.unibo.hookmaster.view.snapshot;

/**
 * Immutable snapshot of the menu state, used to pass data to the
 * {@link it.unibo.hookmaster.view.MenuView}.
 * 
 * @param inGame whether the menu is the main menu or the puase in game menu.
 */
public record MenuSnapshot(boolean inGame) { }
