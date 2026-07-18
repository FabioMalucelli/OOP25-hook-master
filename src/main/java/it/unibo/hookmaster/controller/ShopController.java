package it.unibo.hookmaster.controller;

import java.util.Collection;
import it.unibo.hookmaster.model.upgrade.Player;
import it.unibo.hookmaster.model.upgrade.Shop;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;
import it.unibo.hookmaster.view.ShopView;
import javafx.scene.Scene;

/**
 * ShopController.
 */
public final class ShopController {

    private final Shop shop;
    private final Player player;
    private final ShopView view;

    /**
     * Builds a ShopController.
     * 
     * @param width view width
     * @param height view height
     */
    public ShopController(final double width, final double height) {
        this.shop = new Shop();
        this.player = new Player();
        this.view = new ShopView(width, height, this);
    }

    /**
     * Returns the list of upgrades.
     * 
     * @return the list of upgardes
     */
    public Collection<Upgrade> getUpgrades() {
        return shop.getUpgrades();
    }

    /**
     * Returns number of coins the player has.
     * 
     * @return the numbr of coins
     */
    public int getCoins() {
        return player.getCoins();
    }

    /**
     * Builds and shows the view.
     * 
     * @param scene the scene to display the view
     */
    public void showShop(final Scene scene) {
        view.build();
        scene.setRoot(view);
    }

    /**
     * Buys the upgarde and updates th view.
     * 
     * @param type the upgrade type
     */
    public void buyUpgrade(final UpgradeType type) {
        shop.buy(type, player);
        view.build();
    }
}
