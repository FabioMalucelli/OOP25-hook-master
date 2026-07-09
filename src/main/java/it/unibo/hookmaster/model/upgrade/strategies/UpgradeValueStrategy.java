package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * Strategy for the upgrade value.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface UpgradeValueStrategy {

    /**
     * Compute the upgrade value.
     * 
     * @param level the upgrade level
     * @return the new upgrade value
     */
    double valueForLevel(int level);

    int costForLevel(int level);

}
