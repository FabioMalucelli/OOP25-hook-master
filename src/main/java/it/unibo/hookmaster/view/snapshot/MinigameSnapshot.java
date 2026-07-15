package it.unibo.hookmaster.view.snapshot;

/**
 * Read only record to pass data to the minigame view.
 * 
 * @param indicatorPos indicator position, between [0.0, 1.0]
 * @param targetStartPos green target zone start position, between [0.0, 1.0]
 * @param targetEndPos green target zone end position, between [0.0, 1.0]
 */
public record MinigameSnapshot(double indicatorPos, double targetStartPos, double targetEndPos) {
}
