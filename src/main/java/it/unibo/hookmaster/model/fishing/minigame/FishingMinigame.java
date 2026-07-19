package it.unibo.hookmaster.model.fishing.minigame;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Defines the contract for the catching minigame(QTE).
 */
public interface FishingMinigame {

    /**
     * Advances the minigame state by one frame.
     * 
     * @param deltaTime milliseconds elapsed since the last frame
     */
    void update(long deltaTime);

    /**
     * Called when the player presses the catch button.
     * Determines success or failure and freezes the outcome.
     * 
     * @return true if the catch has succeeded
     */
    boolean attemptCatch();

    /**
     * Gets the current indicator position on the normalized bar.
     * 
     * @return a value in [0.0, 1.0]
     */
    double getIndicatorPosition();

    /**
     * Gets the start of the green target zone.
     * 
     * @return a value in [0.0, 1.0]
     */
    double getTargetStart();

    /**
     * Gets the end of the green target zone.
     * 
     * @return a value in [0.0, 1.0]
     */
    double getTargetEnd();

    /**
     * Gets the current outcome of the minigame.
     * 
     * @return the current MinigameOutcome
     */
    MinigameOutcome getOutcome();

    /**
     * Gets the fish being targeted by this minigame.
     * 
     * @return the target (Catchable)
     */
    Catchable getTarget();
}
