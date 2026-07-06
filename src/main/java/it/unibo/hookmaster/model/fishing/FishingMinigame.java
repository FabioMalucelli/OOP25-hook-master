package it.unibo.hookmaster.model.fishing;

import it.unibo.hookmaster.model.fishing.minigame.IndicatorStrategy;

/**
 * Logic of the catching minigame: a Quick Time Event where an indicator
 * moves along a normalized bar [0.0, 1.0] and the player must press a 
 * button while the indicator is inside the green target zone.
 * 
 * <p>Uses the Strategy Pattern to decouple the indicators movement behaviour from this class.
 * By injecting a diffrent strategy at construction,you can alter the moving dynamics(calm/stormy weather)
 * without modifing this code.</p>
 * 
 */
public class FishingMinigame {
    private final IndicatorStrategy indicator;
    private final double targetStart;
    private final double targetEnd;
    private final Catchable target;

    private MinigameOutcome outcome;

    /**
     * Constructs a new catching minigame.
     * The target zone is delivered from the fish catch difficulty.
     * 
     * @param target        the fish being caught
     * @param indicator     the movement strategy for the QTE indicator
     */
    public FishingMinigame(final Catchable target, final IndicatorStrategy indicator) {
        this.target = target;
        this.indicator = indicator;
        this.outcome = MinigameOutcome.IN_PROGRESS;

        final double zoneWidth = computeZoneWidth(target.getCatchDifficulty());
        final double margin = zoneWidth / 2.0;
        final double center = margin + Math.random() * (1.0 - zoneWidth);
        this.targetStart = center - margin;
        this.targetEnd = center + margin;
    }

    /**
     * Maps a difficulty in [0.0, 1.0] to a target zone width.
     * Easy fish get a wide easy zone, Hard fish get a narrow one.
     * 
     * @param difficulty catch difficulty in [0.0, 1.0]
     * @return zone width as a fraction of the bar
     */
    private double computeZoneWidth(final double difficulty) {
        final double maxWidth = 0.45;
        final double minWidth = 0.12;
        final double clamped = Math.max(0.0, Math.min(1.0, difficulty));
        return maxWidth - clamped * (maxWidth - minWidth);
    }

    /**
     * Updates the indicator via its strategy.
     * Does nothing once the minigame has reached an outcome.
     * 
     * @param deltaTime seconds elapsed since the last frame
     */
    public void update(final double deltaTime) {
        if (outcome == MinigameOutcome.IN_PROGRESS) {
            indicator.update(deltaTime);
        }
    }

    /**
     * Called when the player presses the catch button.
     * Checks whether the indicator is in the target zone and freezes the outcome.
     * Has no effect if the minigame has already ended.
     * 
     * @return true if the catch succeeded
     */
    public boolean attemptCatch() {
        if (outcome != MinigameOutcome.IN_PROGRESS) {
            return outcome == MinigameOutcome.SUCCESS;
        }
        final double pos = indicator.getPosition();
        final boolean success = pos >= targetStart && pos <= targetEnd;
        outcome = success ? MinigameOutcome.SUCCESS : MinigameOutcome.FAILURE;
        return success;
    }

    /**
     * Gets the current indicator position on the bar.
     * 
     * @return a value in [0.0, 1.0]
     */
    public double getIndicatorPosition() {
        return indicator.getPosition();
    }

    /**
     * Gets the start of the green target zone.
     * 
     * @return a value in [0.0, 1.0]
     */
    public double getTargetStart() {
        return targetStart;
    }

    /**
     * Gets the end of the green target zone.
     * 
     * @return a value in [0.0, 1.0]
     */
    public double getTargetEnd() {
        return targetEnd;
    }

    /**
     * Gets the current outcome of the minigame.
     * 
     * @return the current MinigameOutcome
     */
    public MinigameOutcome gOutcome() {
        return outcome;
    }

    /**
     * Gets the fish being targeted by this minigame.
     * 
     * @return the target fish
     */
    public Catchable getTarget() {
        return target;
    }
}
