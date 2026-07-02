package it.unibo.hookmaster.model.upgrade;

import it.unibo.hookmaster.model.upgrade.UpgradeFactory.UpgradeType;

/**
 * Event for when the player buys an upgrades.
 */
public final class UpgradeEvent {

    private final UpgradeType type;
    private final int newLevel;
    private final double newValue;

    /**
     * Build the event.
     * 
     * @param type the upgrade type
     * @param newLevel the new level of the upgrade
     * @param newValue the new value of the upgrade
     */
    public UpgradeEvent(final UpgradeType type, final int newLevel, final double newValue) {
        this.type = type;
        this.newLevel = newLevel;
        this.newValue = newValue;
    }

    /**
     * Retrives the upgrade type.
     * 
     * @return the upgrade type
     */
    public UpgradeType getUpgradeType() {
        return type;
    }

    /**
     * Retrives the upgrade level.
     * 
     * @return the upgrade level
     */
    public int getNewLevel() {
        return newLevel;
    }

    /**
     * Retrives the upgrade value.
     * 
     * @return the upgrade value
     */
    public double getNewValue() {
        return newValue;
    }
}
