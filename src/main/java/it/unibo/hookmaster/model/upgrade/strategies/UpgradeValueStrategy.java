package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * Strategy for the upgrade value and cost.
 */
public interface UpgradeValueStrategy {

    /**
     * Calculates the upgrade value at the spcified level.
     * 
     * @param level the target upgrade level.
     * @return the calculated upgrade value.
     */
    double valueForLevel(int level);

    /**
     * Calculates the upgrade cost for the spcified level.
     * 
     * @param level the target upgrade level.
     * @return the calculated upgrade cost.
     */
    int costForLevel(int level);
}
