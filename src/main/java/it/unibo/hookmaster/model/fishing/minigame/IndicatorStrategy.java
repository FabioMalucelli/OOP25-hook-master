package it.unibo.hookmaster.model.fishing.minigame;

/**
 * Strategy Pattern: defines how the QTE indicator moves along the bar.
 * Diffrent implementations can model diffrent weather conditions or
 * difficulty modes without touching FishingMinigame.
 * 
 */
public interface IndicatorStrategy {

    /**
     * Advances the indicator by one frame.
     * 
     * @param deltaTime milliseconds passed since the last frame
     */
    void update(long deltaTime);

    /**
     * Gets the curent position of the idicator on the bar.
     * 
     * @return a value in [0.0, 1.0]
     */
    double getPosition();

}
