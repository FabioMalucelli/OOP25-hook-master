package it.unibo.hookmaster.model;

import it.unibo.hookmaster.model.movement.LinearMovement;
import it.unibo.hookmaster.model.movement.MovementStrategy;

/**
 * Types of fishes that can spawn.
 */
public enum FishType {

    GREATWHITE("Shark", true, 100, 8, 1.0),
    ANCHOVY("Anchovy", false, 5, 2, 0.1),
    CLOWNFISH("Clownfish", false, 10, 3, 0.1),
    TUNA("Tuna", false, 20, 6, .55),
    MARLIN("Marlin", true, 50, 4, .8);

    private final String name;
    private final boolean predator;
    private final int economicValue;
    private final int speed;
    private final double catchDifficulty;

    FishType(final String name, final boolean predator, final int economicValue,
             final int speed, final double catchDifficulty) {
        this.name = name;
        this.predator = predator;
        this.economicValue = economicValue;
        this.speed = speed;
        this.catchDifficulty = catchDifficulty;
    }

    /**
     * @return the name of the fish
     */
    public String getName() {
        return name;
    }

    /**
     * @return if the fish is a predator
     */
    public boolean isPredator() {
        return predator;
    }

    /**
     * @return the value of the fish
     */
    public double getEconomicValue() {
        return economicValue;
    }

    /**
     * @return the speed of the fish
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the catch difficulty of the fish
     */
    public double getCatchDifficulty() {
        return catchDifficulty;
    }

    /**
     * Creates the default movement strategy for this species.
     * Predators start with a straight-line patrol, can later switch to chase when a prey is detected.
     *
     * @return a new movement strategy instance
     */
    public MovementStrategy createDefaultMovementStrategy() {
        return new LinearMovement();
    }
}
