package it.unibo.hookmaster.view.snapshot;

/**
 * Immutable snapshot of the mini game state, used to pass data to the
 * {@link it.unibo.hookmaster.view.MinigameView}.
 * 
 * @param indicatorPos the indicator position, in the range [0.0, 1.0].
 * @param targetStartPos the green target zone start position, in the range [0.0, 1.0].
 * @param targetEndPos the green target zone end position, in the range [0.0, 1.0].
 */
public record MinigameSnapshot(double indicatorPos, double targetStartPos, double targetEndPos) { }
