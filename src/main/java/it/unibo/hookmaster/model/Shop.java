package it.unibo.hookmaster.model;

import java.util.Collection;
import java.util.Map;
import it.unibo.hookmaster.model.upgrade.UpgradeFactory;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * The upgrade shop.
 */
public final class Shop {

    private final Map<UpgradeType, Upgrade> upgrades = UpgradeFactory.createUpgrades();

    /**
     * Returns the list of upgrades.
     * 
     * @return the list of upgrades
     */
    public Collection<Upgrade> getUpgrades() {
        return upgrades.values();
    }

    /**
     * Attempts to buy the upgrade and spends the player money.
     * 
     * @param type the upgrade type
     * @param player the player coins
     */
    public void buy(final UpgradeType type, final Player player) {
        final Upgrade upgrade = upgrades.get(type);
        if (upgrade.canUpgrade(player.getCoins())) {
            player.spendCoins(upgrade.getCost());
            upgrade.upgrade();
        }
    }
}
