package it.unibo.hookmaster.view.snapshot;

import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.weather.Weather;

/**
 * Immutable snapshot of the game state, used to pass data to the
 * {@link it.unibo.hookmaster.view.GameView}.
 * 
 * @param fishes the list of fishes and their state.
 * @param deadFishes the list of fishes that have been eaten and are dead.
 * @param hook the state of the hook.
 * @param coins the player coin balance.
 * @param weather the current weather state.
 */
@SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "The snapshot is only used for reading the state of the game."
)
public record GameSnapshot(List<Fish> fishes, List<Fish> deadFishes, Hook hook, int coins, Weather weather) { }
