package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * Strategy for the upgrade value and cost.
 */
public interface UpgradeValueStrategy {

    /**
     * Calculate the upgrade value.
     * 
     * @param level the upgrade level
     * @return the new upgrade value
     */
    double valueForLevel(int level);

    /**
     * Calculate the upgrade cost.
     * 
     * @param level the upgrade level
     * @return the new upgrade cost
     */
    int costForLevel(int level);
}
