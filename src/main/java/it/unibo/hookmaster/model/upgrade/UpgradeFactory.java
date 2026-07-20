package it.unibo.hookmaster.model.upgrade;

import java.util.LinkedHashMap;
import java.util.Map;
import it.unibo.hookmaster.model.upgrade.strategies.LinearUpgradeValueStrategy;
import it.unibo.hookmaster.model.upgrade.upgrades.MaxWeightUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.SpeedUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Factory for different upgrades.
 */
public final class UpgradeFactory {

    private static final int MAX_WEIGHT_BASE = 30_000;
    private static final double MAX_WEIGHT_STEP = 20;
    private static final int SPEED_BASE = 30;
    private static final double SPEED_STEP = 1.5;

    /**
     * Prevents instantiation of this class.
     */
    private UpgradeFactory() { }

    /**
     * Factory method that generates the different upgrades.
     *
     * @return an ordered map ({@link LinkedHashMap}) mapping each upgrade type to its upgrade
     *         instance.
     */
    public static Map<UpgradeType, Upgrade> generateUpgrades() {
        final Map<UpgradeType, Upgrade> upgrades = new LinkedHashMap<>();
        upgrades.put(UpgradeType.MAX_WEIGHT, new MaxWeightUpgrade(
                new LinearUpgradeValueStrategy(MAX_WEIGHT_BASE, MAX_WEIGHT_STEP)));
        upgrades.put(UpgradeType.SPEED,
                new SpeedUpgrade(new LinearUpgradeValueStrategy(SPEED_BASE, SPEED_STEP)));
        return upgrades;
    }
}
