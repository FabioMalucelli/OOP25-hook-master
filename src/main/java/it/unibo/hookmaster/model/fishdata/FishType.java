package it.unibo.hookmaster.model.fishdata;

/**
 * Types of fishes that can spawn.
 */
public enum FishType {

    GREATWHITE("Great White Shark", true, 100, 7, 1.0, 190, false, 48, 32),
    ANCHOVY("Anchovy", false, 5, 2, 0.1, 15, false, 16, 16),
    TUNA("Tuna", false, 20, 7, .55, 15, false, 48, 32),
    MARLIN("Marlin", true, 50, 7, .8, 15, false, 60, 32),
    ZEBRAFISH("Zebra fish", false, 20, 1.5, 5, 25, false, 32, 32),
    BUTTERFLYFISH("Butterfly fish", false, 5, 3, .3, 15, false, 24, 16),
    CLOWNFISH("Clownfish", false, 10, 3, 6, 15, false, 16, 16),
    SAWSHARK("Saw shark", true, 50, 1, 19, 190, false, 48, 32),
    ANGLER("Angler", true, 200, .8, 1, 50, true, 32, 24);

    private final String name;
    private final boolean predator;
    private final int baseEconomicValue;
    private final double speed;
    private final double baseCatchDifficulty;
    private final double baseWeight;
    private final boolean stormOnly;
    private final double baseWidth;
    private final double baseHeight;


    FishType(final String name, final boolean predator, final int baseEconomicValue, final double speed,
            final double baseCatchDifficulty,
            final double baseWeight, final boolean stormOnly, final double baseWidth, final double baseHeight) {

        this.name = name;
        this.predator = predator;
        this.baseEconomicValue = baseEconomicValue;
        this.speed = speed;
        this.baseCatchDifficulty = baseCatchDifficulty;
        this.baseWeight = baseWeight;
        this.stormOnly = stormOnly;
        this.baseWidth = baseWidth;
        this.baseHeight = baseHeight;
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

        /**
     * @return base width for the fish
     */
    public double getBaseWidth() {
        return baseWidth;
    }

        /**
     * @return base height for the fish
     */
    public double getBaseHeight() {
        return baseHeight;
    }
}