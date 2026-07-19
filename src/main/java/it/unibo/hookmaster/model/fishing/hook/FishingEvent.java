package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Immutable data object passed to FishingListener observers
 * when a notable fishing event occurs.
 */
public final class FishingEvent {

    /**
     * Enumerates the kind of events the fishing system can produce.
     */
    public enum Type {
        FISH_HOOKED,        //A fish has been hooked and the QTE minigame has started
        FISH_CAUGHT,        //The player succeeded in the QTE and caught the fish
        FISH_ESCAPED,       //The player missed the QTE and the fish escaped
    }

    private final Type type;
    private final Catchable fish; //may be null for events not related to a specific fish

    /**
     * Constructs a fishing event, optionally carrying the involved fish.
     * 
     * @param type the kind of event
     * @param fish the fish involved, or null if not applicable
     */
    public FishingEvent(final Type type, final Catchable fish) {
        this.type = type;
        this.fish = fish;
    }

    /**
     * Gets the type of this event.
     * 
     * @return the event type
     */
    public Type getType() {
        return type;
    }

    /**
     * Gets the fish involved in this event, if any.
     * 
     * @return the fish, or null
     */
    public Catchable getFish() {
        return fish;
    }
}
