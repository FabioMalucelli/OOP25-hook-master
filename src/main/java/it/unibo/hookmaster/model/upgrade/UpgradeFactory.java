package it.unibo.hookmaster.model.upgrade;

import java.util.Map;
import it.unibo.hookmaster.model.upgrade.strategies.LinearUpgradeValueStrategy;
import it.unibo.hookmaster.model.upgrade.upgrades.MaxWeightUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.SpeedUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Factory for different upgrades.
 */
public final class UpgradeFactory {

    private static final int MAX_WEIGHT_BASE = 30;
    private static final double MAX_WEIGHT_STEP = 20;
    private static final int SPEED_BASE = 1;
    private static final double SPEED_STEP = 1.5;

    /**
     * Private constructor to avoid class usage.
     */
    private UpgradeFactory() { }

    /**
     * Static method to create the different upgrades.
     *
     * @return a map containg the upgrade type and its upgrade
     */
    public static Map<UpgradeType, Upgrade> createUpgrades() {
        return Map.of(
                UpgradeType.MAX_WEIGHT,
                new MaxWeightUpgrade(new LinearUpgradeValueStrategy(MAX_WEIGHT_BASE, MAX_WEIGHT_STEP)),
                UpgradeType.SPEED,
                new SpeedUpgrade(new LinearUpgradeValueStrategy(SPEED_BASE, SPEED_STEP))
        );
    }
}
