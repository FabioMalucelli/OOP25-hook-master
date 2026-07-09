package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Speed upgrade implementation.
 */
public final class SpeedUpgrade implements Upgrade {

    private static final UpgradeType type = UpgradeType.SPEED;
    private static final String UPGRADE_NAME = "Velocità";
    private static final String UPGRADE_DESCRIPTION = "Aumenta la velocità";
    private int level = 1;
    private final int maxLevel = 10;
    private final UpgradeValueStrategy strategy;

    /**
     * Creates a new speed upgrade instance.
     * 
     * @param strategy the upgrade strategy used by this upgrade instance
     */
    public SpeedUpgrade(final UpgradeValueStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public UpgradeType getType() {
        return type;
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
        return level;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getCost() {
        return strategy.costForLevel(level);
    }

    @Override
    public double getValue() {
        return strategy.valueForLevel(level);
    }

    @Override
    public boolean canUpgrade(int playerCoins) {
        return getLevel() <= getMaxLevel() && playerCoins >= getCost();
    }

    @Override
    public void upgrade() {
        level++;
    }
}
