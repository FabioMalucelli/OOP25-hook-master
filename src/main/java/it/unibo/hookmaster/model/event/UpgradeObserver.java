package it.unibo.hookmaster.model.event;

/**
 * Observer interface for upgrade event.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface UpgradeObserver {

    /**
     * Called on upgrade.
     * 
     * @param event the upgrade event
     */
    void onUpgrade(UpgradeEvent event);
}
