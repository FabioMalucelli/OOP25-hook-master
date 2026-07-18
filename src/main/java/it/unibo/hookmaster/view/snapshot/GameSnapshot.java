package it.unibo.hookmaster.view.snapshot;

import java.util.List;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishing.boat.Boat;
import it.unibo.hookmaster.model.fishing.hook.Hook;

/**
 * Immutable snapshot of the game state, used to pass data to the
 * {@link it.unibo.hookmaster.view.GameView}.
 * 
 * @param fishes the list of fishes and their state.
 * @param boat the state of the boat.
 * @param hook the state of the hook.
 * @param coins the player coin balance.
 */
public record GameSnapshot(List<Fish> fishes, Boat boat, Hook hook, int coins) { }
