package it.unibo.hookmaster.model.fishing.hook;

/**
 * Observer Pattern - observer interface.
 * Any class interested in fishing events must implement this interface.
 */
@FunctionalInterface
public interface FishingListener {

    /**
     * Called by the Hook when a notable event occurs.
     * 
     * @param event the event carrying type and optional fish data
     */
    void onFishingEvent(FishingEvent event);
}
