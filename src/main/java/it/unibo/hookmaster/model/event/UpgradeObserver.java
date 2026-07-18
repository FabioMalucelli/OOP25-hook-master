package it.unibo.hookmaster.model.event;

/**
 * Observer interface for listening to upgrade events.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface UpgradeObserver {

    /**
     * Invoked when an upgarde event is triggered.
     * 
     * @param event the event details.
     */
    void onUpgrade(UpgradeEvent event);
}
