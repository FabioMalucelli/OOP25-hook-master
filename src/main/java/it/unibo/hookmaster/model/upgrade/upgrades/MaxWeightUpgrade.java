package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Max weight upgrade implementation.
 */
public final class MaxWeightUpgrade implements Upgrade {

    private static final String UPGRADE_NAME = "Peso massimo";
    private static final String UPGRADE_DESCRIPTION = "Aumenta il peso massimo";
    private int level = 1;
    private final UpgradeValueStrategy strategy;

    /**
     * Creates a new max weight upgrade instance.
     * 
     * @param strategy the upgrade strategy used by this upgrade instance
     */
    public MaxWeightUpgrade(final UpgradeValueStrategy strategy) {
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
        return strategy.calcValue(this.level);
    }

    @Override
    public void upgrade() {
        this.level++;
    }
}
