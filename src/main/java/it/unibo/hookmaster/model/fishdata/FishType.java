package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

/**
 * Types of fishes that can spawn.
 */
public enum FishType {

    GREATWHITE("Great White Shark", true, 100, 7, 1.0, 20.0, false),
    ANCHOVY("Anchovy", false, 5, 2, 0.1, 2, false),
    CLOWNFISH("Clownfish", false, 10, 3, 5, 0.03, false),
    TUNA("Tuna", false, 20, 7, .55, 15.0, false),
    MARLIN("Marlin", true, 50, 7, .8, 15.0, false),
    ZEBRAFISH("Zebra fish", false, 20, 1.5, 5, 5, false),
    BUTTERFLYFISH("Butterfly fish", false, 5, 3, .3, 5, false),
    SAWSHARK("Saw shark", true, 50, 1, 19, 5, false),
    ANGLER("Angler", true, 200, .8, 1, 15, true);

    private final String name;
    private final boolean predator;
    private final int baseEconomicValue;
    private final double speed;
    private final double baseCatchDifficulty;
    private final double baseWeight;
    private final boolean stormOnly;

    FishType(final String name, final boolean predator, final int baseEconomicValue, final double speed,
            final double baseCatchDifficulty,
            final double baseWeight, final boolean stormOnly) {

        this.name = name;
        this.predator = predator;
        this.baseEconomicValue = baseEconomicValue;
        this.speed = speed;
        this.baseCatchDifficulty = baseCatchDifficulty;
        this.baseWeight = baseWeight;
        this.stormOnly = stormOnly;
    }

    /**
     * @return the name of the species
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
     * @return the base value of the fish
     */
    public int getBaseEconomicValue() {
        return baseEconomicValue;
    }

    /**
     * @return the speed for the fish
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * @return the base catch difficulty for the fish
     */
    public double getBaseCatchDifficulty() {
        return baseCatchDifficulty;
    }

    /**
     * @return the reference weight for this fish in kg
     */
    public double getBaseWeight() {
        return baseWeight;
    }

    /**
     * @return if the fish only spawns during a storm
     */
    public boolean isStormOnly() {
        return stormOnly;
    }
}
