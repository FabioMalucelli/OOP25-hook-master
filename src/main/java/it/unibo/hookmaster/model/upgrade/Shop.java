package it.unibo.hookmaster.model.upgrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import it.unibo.hookmaster.model.upgrade.event.UpgradeEvent;
import it.unibo.hookmaster.model.upgrade.event.UpgradeObserver;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Represents the upgrades shop.
 */
public final class Shop {

    private final Map<UpgradeType, Upgrade> upgrades;
    private final List<UpgradeObserver> observers = new ArrayList<>();

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
     * Registers an observer to listen for upgrade events and notifies it to sync the current
     * upgrades states.
     * 
     * @param observer the observer to be registered.
     */
    public void addObserver(final UpgradeObserver observer) {
        this.observers.add(observer);
        this.upgrades.forEach((type, upgrade) -> {
            observer.onUpgrade(new UpgradeEvent(type, upgrade.getLevel(), upgrade.getValue()));
        });
    }

    /**
     * Notifies all registered observers.
     * 
     * @param event the event containing the details of the upgrade.
     */
    public void notifyObserves(final UpgradeEvent event) {
        this.observers.forEach(observer -> observer.onUpgrade(event));
    }

    /**
     * Attempts to purchase the specified upgrade and spends the player coins.
     * 
     * @param type the type of upgrade to buy.
     * @param playerWallet the player balance.
     */
    public void buy(final UpgradeType type, final PlayerWallet playerWallet) {
        final Upgrade upgrade = this.upgrades.get(type);
        if (upgrade.canUpgrade(playerWallet.getCoins())) {
            playerWallet.spendCoins(upgrade.getCost());
            upgrade.upgrade();
            notifyObserves(new UpgradeEvent(type, upgrade.getLevel(), upgrade.getValue()));
        }
    }
}
