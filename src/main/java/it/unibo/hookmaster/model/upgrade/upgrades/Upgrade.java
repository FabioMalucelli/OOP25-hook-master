package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

/**
 * Interface that models an upgrade.
 */
public interface Upgrade {

    UpgradeType getType();

    /**
     * Retrives the upgrade name.
     * 
     * @return the upgrade name
     */
    String getName();

    /**
     * Retrives the upgrade description.
     * 
     * @return the upgrade description
     */
    String getDescription();

    /**
     * Retrives the upgrade level.
     * 
     * @return the upgrade level
     */
    int getLevel();

    int getMaxLevel();

    int getCost();

    /**
     * Retrives the upgrade value.
     * 
     * @return the upgrade value
     */
    double getValue();

    boolean canUpgrade(int playerCoins);

    /**
     * Increase the upgrade level.
     */
    void upgrade();

}
