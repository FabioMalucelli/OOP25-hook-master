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

    @Override
    public AbstractUpgrade.Memento createMemento() {
        return new AbstractUpgrade.Memento(this.level);
    }

    @Override
    public void restoreFromMemento(final Upgrade.Memento memento) {
        if (!(memento instanceof AbstractUpgrade.Memento)) {
            throw new IllegalArgumentException("Invalid memento type");
        }
        this.level = ((AbstractUpgrade.Memento) memento).level;
    }


    public static final class Memento implements Upgrade.Memento {
        private static final long serialVersionUID = 1L;
        private final int level;

        private Memento(final int level) {
            this.level = level;
        }
    }
}
