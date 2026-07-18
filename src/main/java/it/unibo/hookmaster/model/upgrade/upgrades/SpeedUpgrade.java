package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Represents the speed upgrade {@link UpgradeType#SPEED}.
 */
public final class SpeedUpgrade extends AbstractUpgrade {

    private static final UpgradeType TYPE = UpgradeType.SPEED;
    private static final String UPGRADE_NAME = "Velocità";
    private static final String UPGRADE_DESCRIPTION = "Aumenta la velocità";
    private static final int MAX_LEVEL = 10;

    /**
     * Constructs a new speed upgrade using the specified value strategy.
     * 
     * @param strategy the upgrade strategy used by this upgrade.
     */
    public SpeedUpgrade(final UpgradeValueStrategy strategy) {
        super(strategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UpgradeType getType() {
        return TYPE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return UPGRADE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return UPGRADE_DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }
}
