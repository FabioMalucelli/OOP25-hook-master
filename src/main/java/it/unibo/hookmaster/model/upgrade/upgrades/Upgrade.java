package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.save.Originator;
import it.unibo.hookmaster.model.upgrade.UpgradeType;

/**
 * Represents a general upgrade in the game.
 */
public interface Upgrade extends Originator<Upgrade.Memento> {

    /**
     * Retrives the type of the upgrade.
     * 
     * @return the upgrade type.
     */
    UpgradeType getType();

    /**
     * Retrives the name of the upgrade.
     * 
     * @return the upgrade name.
     */
    String getName();

    /**
     * Retrives the description of the upgrade.
     * 
     * @return the upgrade description.
     */
    String getDescription();

    /**
     * Retrives the current level of the upgrade.
     * 
     * @return the current upgrade level.
     */
    int getLevel();

    /**
     * Retrives the max level for this upgrade.
     * 
     * @return the max level.
     */
    int getMaxLevel();

    /**
     * Retrives the cost to reach the next level.
     * 
     * @return the cost of the next upgrade.
     */
    int getCost();

    /**
     * Retrives the current value of the upgrade.
     * 
     * @return the current upgrade value.
     */
    double getValue();

    /**
     * Check if the player can level up this upgrade.
     * 
     * @param playerCoins the amount of coins the player has.
     * @return {@code true} if the player can upgrade, {@code false} otherwise.
     */
    boolean canUpgrade(int playerCoins);

    /**
     * Increases the upgrade level, use {@link #canUpgrade(int)} to check if upgarde can actually be
     * levelled up.
     */
    void upgrade();

    /**
     * Memento class for saving and restoring the state of the Upgrade.
     */
    interface Memento extends it.unibo.hookmaster.model.save.Memento {
    }
}
