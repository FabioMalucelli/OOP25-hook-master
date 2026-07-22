package it.unibo.hookmaster.model.upgrade.upgrades;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.UpgradeValueStrategy;

/**
 * Represents the minigame ease upgrade {@link UpgradeType#MINIGAME_EASE}.
 */
public final class MinigameEaseUpgrade extends AbstractUpgrade {

    private static final UpgradeType TYPE = UpgradeType.MINIGAME_EASE;
    private static final String UPGRADE_NAME = "Minigame Ease";
    private static final String UPGRADE_DESCRIPTION =
            "Reduces the difficulty of minigames, making them easier to complete.";
    private static final int MAX_LEVEL = 5;
    private static final int COST_MULTIPLIER = 20;

    /**
     * Constructs a new minigame ease upgrade using the specified value strategy.
     * 
     * @param strategy the upgrade strategy used by this upgrade.
     */
    public MinigameEaseUpgrade(final UpgradeValueStrategy strategy) {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCost() {
        return super.getLevel() * COST_MULTIPLIER;
    }
}
