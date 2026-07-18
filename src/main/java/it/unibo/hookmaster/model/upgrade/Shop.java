package it.unibo.hookmaster.model.upgrade;

import java.util.Collection;
import java.util.Map;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Represents the upgrades shop.
 */
public final class Shop {

    private final Map<UpgradeType, Upgrade> upgrades;

    /**
     * Constructs a new Shop.
     */
    public Shop() {
        this.upgrades = UpgradeFactory.generateUpgrades();
    }

    /**
     * Retrives available upgrades.
     * 
     * @return a {@link Collection} of upgrades.
     */
    public Collection<Upgrade> getUpgrades() {
        return this.upgrades.values();
    }

    /**
     * Attempts to purchase the specified upgrade and spends the player coins.
     * 
     * @param type the type of upgrade to buy.
     * @param player the player balance.
     */
    public void buy(final UpgradeType type, final Player player) {
        final Upgrade upgrade = this.upgrades.get(type);
        if (upgrade.canUpgrade(player.getCoins())) {
            player.spendCoins(upgrade.getCost());
            upgrade.upgrade();
        }
    }
}
