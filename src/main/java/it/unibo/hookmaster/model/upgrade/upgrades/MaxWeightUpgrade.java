package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Represents the max weight upgrade {@link UpgradeType#MAX_WEIGHT}.
 */
public final class MaxWeightUpgrade extends AbstractUpgrade {

    private static final UpgradeType TYPE = UpgradeType.MAX_WEIGHT;
    private static final String UPGRADE_NAME = "Max Weight";
    private static final String UPGRADE_DESCRIPTION =
            "Increases the maximum weight the hook can catch, allowing for larger fish to be caught.";
    private static final int MAX_LEVEL = 30;

    /**
     * Constructs a new max weight upgrade using the specified value strategy.
     * 
     * @param strategy the upgrade strategy used by this upgrade.
     */
    public MaxWeightUpgrade(final UpgradeValueStrategy strategy) {
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
