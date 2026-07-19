package it.unibo.hookmaster.model.fishing;

/**
 * Minimal contract that any "thing that can be hooked" must satisfy.
 */
public interface Catchable {

    /**
     * Gets the economic value awarded when this entity is successfully caught.
     * 
     * @return the value in coins
     */
    int getEconomicValue();

    /**
     * Gets how hard this entity is to catch, used to size the green target zone
     * of the catching minigame (higher the difficulty -> smaller the zone).
     * Expected range -> [0.0, 1.0]
     * 
     * @return the difficulty factor
     */
    double getCatchDifficulty();

    /**
     * Gets a display name, useful for UI feedback.
     * 
     * @return the name of the species
     */
    String getName();

    /**
     * Gets the weight of this entity.
     * 
     * @return the weight
     */
    double getWeight();
}
