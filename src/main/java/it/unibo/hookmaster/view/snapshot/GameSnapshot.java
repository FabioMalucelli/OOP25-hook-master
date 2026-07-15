package it.unibo.hookmaster.view.snapshot;

import it.unibo.hookmaster.model.fishing.boat.Boat;
import it.unibo.hookmaster.model.fishing.hook.Hook;

/**
 * Read only record to pass data to the game view.
 * 
 * @param boat the boat
 * @param hook the hook
 * @param coins player coins
 */
public record GameSnapshot(Boat boat, Hook hook, int coins) { }
