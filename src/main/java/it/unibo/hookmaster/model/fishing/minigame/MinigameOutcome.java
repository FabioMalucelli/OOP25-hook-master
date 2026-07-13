package it.unibo.hookmaster.model.fishing.minigame;

/**
 * Possible outcomes of a FishingMinigame.
 */
public enum MinigameOutcome {
    IN_PROGRESS,    //The minigame is still running, no attemt has been made yet
    SUCCESS,        //The player has pressed the button while inside the green target zone
    FAILURE         //The player has pressed the button while outside the target zone
}
