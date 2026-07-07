package it.unibo.hookmaster.model.fishing;

import it.unibo.hookmaster.model.fishing.minigame.IndicatorStrategy;

/**
 * Standard implementation of FishingMinigame.
 * A Quick Time Event where an indicator
 * moves along a normalized bar [0.0, 1.0] and the player must press a 
 * button while the indicator is inside the green target zone.
 * 
 * <p>Uses the Strategy Pattern to decouple the indicators movement behaviour from this class.</p>
 * 
 */
public class FishingMinigameImpl implements FishingMinigame {
    private final IndicatorStrategy indicator;
    private final double targetStart;
    private final double targetEnd;
    private final Catchable target;

    private MinigameOutcome outcome;

    /**
     * Constructs a new catching minigame.
     * The target zone is sized from the fish catch difficulty.
     * 
     * @param target        the fish being caught
     * @param indicator     the movement strategy for the QTE indicator
     */
    public FishingMinigameImpl(final Catchable target, final IndicatorStrategy indicator) {
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

    @Override
    public void update(final double deltaTime) {
        if (outcome == MinigameOutcome.IN_PROGRESS) {
            indicator.update(deltaTime);
        }
    }

    @Override
    public boolean attemptCatch() {
        if (outcome != MinigameOutcome.IN_PROGRESS) {
            return outcome == MinigameOutcome.SUCCESS;
        }
        final double pos = indicator.getPosition();
        final boolean success = pos >= targetStart && pos <= targetEnd;
        outcome = success ? MinigameOutcome.SUCCESS : MinigameOutcome.FAILURE;
        return success;
    }

    @Override
    public double getIndicatorPosition() {
        return indicator.getPosition();
    }

    @Override
    public double getTargetStart() {
        return targetStart;
    }

    @Override
    public double getTargetEnd() {
        return targetEnd;
    }

    @Override
    public MinigameOutcome getOutcome() {
        return outcome;
    }

    @Override
    public Catchable getTarget() {
        return target;
    }
}
