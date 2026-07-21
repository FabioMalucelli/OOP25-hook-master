package it.unibo.hookmaster.model.fishing.minigame;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Factory Method Pattern : handles the creation of FishingMinigame instances, ensuring callers
 * interact only with the interface rather than concrete classes.
 */
public final class MinigameFactory {

    private static final double STORM_SPEED_MULTIPLIER = 1.5;

    private MinigameFactory() {}

    /**
     * Creates a minigame with the strategy appropriate for current weather.
     * 
     * @param fish the fish to be caught
     * @param minigameEase the ease of the minigame
     * @param isStormy true if stormy weather is active
     * @return a fully configured FishingMinigame
     */
    public static FishingMinigame create(final Catchable fish, final double minigameEase,
            final boolean isStormy) {
        final IndicatorStrategy strategy =
                isStormy ? new StormyIndicator(minigameEase * STORM_SPEED_MULTIPLIER)
                        : new OscillatingIndicator(minigameEase);
        return new FishingMinigameImpl(fish, strategy);
    }

    /**
     * Creates a minigame with calm weather conditions.
     * 
     * @param fish the fish to be caught
     * @param minigameEase the ease of the minigame
     * @return a fully configured FishingMinigame
     */
    public static FishingMinigame create(final Catchable fish, final double minigameEase) {
        return create(fish, minigameEase, false);
    }
}
