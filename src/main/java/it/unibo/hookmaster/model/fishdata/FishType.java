package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

/**
 * Types of fishes that can spawn.
 */
public enum FishType {

    GREATWHITE("Shark", true, 100, .6, 1.0, false),
    ANCHOVY("Anchovy", false, 5, .9, 0.1, false),
    CLOWNFISH("Clownfish", false, 10, 1, 0.1, false),
    TUNA("Tuna", false, 20, 1.5, .55, false),
    MARLIN("Marlin", true, 50, 1.5, .8, false),
    ZEBRAFISH("Zebra fish", false, 20, 1.5, .3, false),
    BUTTERFLYFISH("Butterfly fish", false, 5, .8, .3, false),
    SAWSHARK("Saw shark", true, 50, 1, .9, false),
    ANGLER("Angler", true, 200, .8, 1, true);

    private final String name;
    private final boolean predator;
    private final int economicValue;
    private final double speed;
    private final double catchDifficulty;
    private final boolean stormOnly;

    FishType(final String name, final boolean predator, final int economicValue,
            final double speed, final double catchDifficulty, final boolean stormOnly) {
        this.name = name;
        this.predator = predator;
        this.economicValue = economicValue;
        this.speed = speed;
        this.catchDifficulty = catchDifficulty;
        this.stormOnly = stormOnly;
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
    public int getEconomicValue() {
        return economicValue;
    }

    /**
     * @return the speed of the fish
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return the catch difficulty of the fish
     */
    public double getCatchDifficulty() {
        return catchDifficulty;
    }

    /**
     * @return true if this species only spawns during stormy weather
     */
    public boolean isStormOnly() {
        return stormOnly;
    }

    /**
     * Creates the default movement strategy for this species.
     * Predators start with a straight-line patrol, can later switch to chase when a
     * prey is detected.
     *
     * @return a new movement strategy instance
     */
    public MovementStrategy createDefaultMovementStrategy() {
        return new LinearMovement();
    }
}
