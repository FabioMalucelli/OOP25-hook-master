package it.unibo.hookmaster.controller.phase.shop;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

/**
 * Interface representing the input handler for the shop phase.
 */
public interface ShopInputHandler {
    /**
     * Called when the user presses the buy button for an upgrade
     * in the shop.
     * 
     * @param upgradeType the type of upgrade the user wants to buy
     */
    void pressBuyBtn(UpgradeType upgradeType);
}
