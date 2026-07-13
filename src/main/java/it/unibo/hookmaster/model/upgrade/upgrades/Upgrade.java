package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

/**
 * Interface that models an upgrade.
 */
public interface Upgrade {

    /**
     * Retrives the upgrade type.
     * 
     * @return the upgrade type
     */
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

    /**
     * Retrives the upgrade max level.
     * 
     * @return the upgrade max level
     */
    int getMaxLevel();

    /**
     * Retrives the upgrade cost.
     * 
     * @return the upgrade cost
     */
    int getCost();

    /**
     * Retrives the upgrade value.
     * 
     * @return the upgrade value
     */
    double getValue();

    /**
     * Check if the player can upgrade.
     * 
     * @param playerCoins the player coins
     * @return whether the player can or can't upgrade
     */
    boolean canUpgrade(int playerCoins);

    /**
     * Increase the upgrade level.
     */
    void upgrade();

}
