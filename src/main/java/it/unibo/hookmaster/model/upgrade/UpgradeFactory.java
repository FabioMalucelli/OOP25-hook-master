package it.unibo.hookmaster.model.upgrade;

import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueLinear;
import it.unibo.hookmaster.model.upgrade.upgrades.MaxWeightUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.SpeedUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Factory for different upgrades.
 */
public final class UpgradeFactory {

    private static final double MAX_WEIGHT_BASE = 30;
    private static final double MAX_WEIGHT_STEP = 20;
    private static final double SPEED_BASE = 1;
    private static final double SPEED_STEP = 1.5;

    /**
     * Private constructor to avoid class usage.
     */
    private UpgradeFactory() { }

    /**
     * Static method to create a new upgrade.
     * 
     * @param type the new upgrade type
     * @return a new upgrade instance
     */
    public static Upgrade create(final UpgradeType type) {
        return switch (type) {
            case MAX_WEIGHT -> new MaxWeightUpgrade(
                    new UpgradeValueLinear(MAX_WEIGHT_BASE, MAX_WEIGHT_STEP));
            case SPEED -> new SpeedUpgrade(new UpgradeValueLinear(SPEED_BASE, SPEED_STEP));
        };
    }
}
