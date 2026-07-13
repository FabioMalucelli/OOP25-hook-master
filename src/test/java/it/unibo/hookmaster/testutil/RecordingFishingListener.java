package it.unibo.hookmaster.testutil;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.controller.FishingEvent;
import it.unibo.hookmaster.controller.FishingListener;

/**
 * A FishingListener that records every event it recieves,
 * in order, so tests can assert what was fired and how many times. 
 */
public final class RecordingFishingListener implements FishingListener {

    private final List<FishingEvent> recievedEvents = new ArrayList<>();

    @Override
    public void onFishingEvent(final FishingEvent event) {
        recievedEvents.add(event);
    }

    /**
     * @return an unmodifiable list of all events recieved, in order
     */
    public List<FishingEvent> getRecievedEvents() {
        return List.copyOf(recievedEvents);
    }

    /**
     * Checks whether at least one recieved event has the given type.
     * 
     * @param type the event type to look for
     * @return true if at least one matching event was recieved
     */
    public boolean hasRecieved(final FishingEvent.Type type) {
        return recievedEvents.stream().anyMatch(e -> e.getType() == type);
    }
}
