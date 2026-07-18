package it.unibo.hookmaster.model.event;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

/**
 * Represents an event triggered when the player levels up an upgrade.
 */
public final class UpgradeEvent {

    private final UpgradeType type;
    private final int newLevel;
    private final double newValue;

    /**
     * Constructs a new upgrade event.
     * 
     * @param type the upgrade type.
     * @param newLevel the new level of the upgrade.
     * @param newValue the new value of the upgrade.
     */
    public UpgradeEvent(final UpgradeType type, final int newLevel, final double newValue) {
        this.type = type;
        this.newLevel = newLevel;
        this.newValue = newValue;
    }

    /**
     * Retrives the upgrade type.
     * 
     * @return the upgrade type.
     */
    public UpgradeType getUpgradeType() {
        return this.type;
    }

    /**
     * Retrives the upgrade level.
     * 
     * @return the upgrade level.
     */
    public int getNewLevel() {
        return this.newLevel;
    }

    /**
     * Retrives the upgrade value.
     * 
     * @return the upgrade value.
     */
    public double getNewValue() {
        return this.newValue;
    }
}
