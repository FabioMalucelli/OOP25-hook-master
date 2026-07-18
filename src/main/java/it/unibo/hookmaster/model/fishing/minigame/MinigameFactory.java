package it.unibo.hookmaster.model.fishing.minigame;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Factory Method Pattern : handles the creation of FishingMinigame instances,
 * ensuring callers interact only with the interface rather than concrete classes.
 * 
 */
public final class MinigameFactory {

    private static final double BASE_SPEED = 0.8;
    private static final double STORM_SPEED_MULTIPLIER = 1.5;

    private MinigameFactory() { }

    /**
     * Creates a minigame with the strategy appropriate for current weather.
     * 
     * @param fish      the fish to be caught
     * @param isStormy  true if stormy weather is active
     * @return          a fully configured FishingMinigame
     */
    public static FishingMinigame create(final Catchable fish, final boolean isStormy) {
        final IndicatorStrategy strategy = isStormy 
            ? new StormyIndicator(BASE_SPEED * STORM_SPEED_MULTIPLIER)
            : new OscillatingIndicator(BASE_SPEED);
        return new FishingMinigameImpl(fish, strategy);
    }

    /**
     * Creates a minigame with calm weather conditions.
     * 
     * @param fish  the fish to be caught
     * @return      a fully configured FishingMinigame
     */
    public static FishingMinigame create(final Catchable fish) {
        return create(fish, false);
    }
}
