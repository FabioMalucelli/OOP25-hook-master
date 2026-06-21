package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueLinear;
import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Speed upgrade implementation.
 */
public final class SpeedUpgrade implements Upgrade {

    private static final String UPGRADE_NAME = "Velocità";
    private static final String UPGRADE_DESCRIPTION = "Aumenta la velocità";
    private int level = 1;
    private final UpgradeValueStrategy strategy;

    /**
     * Creates a new speed upgrade instance.
     * 
     * @param strategy the upgrade strategy used by this upgrade instance
     */
    public SpeedUpgrade(final UpgradeValueLinear strategy) {
        this.strategy = strategy;
    }

    @Override
    public String getName() {
        return UPGRADE_NAME;
    }

    @Override
    public String getDescription() {
        return UPGRADE_DESCRIPTION;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public double getValue() {
        return this.strategy.calcValue(level);
    }

    @Override
    public void upgrade() {
        this.level++;
    }
}
