package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Abstract implementation of an {@link Upgrade} that handles common logic.
 */
public abstract class AbstractUpgrade implements Upgrade {

    private int level = 1;
    private final UpgradeValueStrategy strategy;

    /**
     * Constructor for the abstract upgrade.
     * 
     * @param strategy the upgrade strategy used by this upgrade.
     */
    protected AbstractUpgrade(final UpgradeValueStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLevel() {
        return this.level;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return this.strategy.costForLevel(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return this.strategy.valueForLevel(level);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUpgrade(final int playerCoins) {
        return this.getLevel() < this.getMaxLevel() && playerCoins >= this.getCost();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upgrade() {
        this.level++;
    }
}
