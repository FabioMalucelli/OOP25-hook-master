package it.unibo.hookmaster.controller;

import java.util.ArrayList;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.fishing.boat.Boat;
import it.unibo.hookmaster.model.fishing.boat.BoatSnapshot;
import it.unibo.hookmaster.model.fishing.boat.BoatView;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.fishing.hook.HookCollisionListener;
import it.unibo.hookmaster.model.fishing.hook.HookSnapshot;
import it.unibo.hookmaster.model.fishing.hook.HookView;
import it.unibo.hookmaster.model.event.UpgradeEvent;
import it.unibo.hookmaster.model.event.UpgradeObserver;

/**
 * Coordinates Boat, Hook, and FishingMinigame.
 * The View only comunicates with this class and reads its getters.
 * Event Broadcaster : sends fishing events to register listeners
 * Collision handler : listens for hook collisions and decides whether to trigger a minigame.
 * Upgrade Applier : when an upgrade is bought in the shop this class applies the new values to the Boat or Hook.
 * Minigame istances : are created using the minigame factory.
 */
public final class FishingController implements HookCollisionListener, UpgradeObserver {

    private final Boat boat;
    private final Hook hook;

    private final List<FishingListener> listeners = new ArrayList<>();

    /**
     * Constructs the controller and wires itself as the hook collision listener.
     * 
     * @param boat  the boat model
     * @param hook  the hook model 
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "Boat and Hook are mutable game entities updated every frame by the game loop"
            + "the controller must hold the same live istances"
    )
    public FishingController(final Boat boat, final Hook hook) {
        this.boat = boat;
        this.hook = hook;
        this.hook.setCollisionListener(this);
    }

    @Override
    public void onHookCollision(final Collidable other) {
        if (other instanceof Catchable) {
            final Catchable fish = (Catchable) other;
            hook.hookFish(fish);
            fireEvent(new FishingEvent(FishingEvent.Type.FISH_HOOKED, fish));
        }
    }

    @Override
    public void onUpgrade(final UpgradeEvent event) {
        hook.onUpgrade(event);
        fireEvent(new FishingEvent(FishingEvent.Type.UPGRADE_APPLIED, null));
    }

    /**
     * Advances the simulation by one frame. Called once per frame by the game  loop.
     * 
     * @param deltaTime second elapsed since the last frame
     */
    public void update(final double deltaTime) {
        boat.update(deltaTime);
        hook.update(deltaTime, boat.getX(), boat.getY());
    }

    /**
     * Sets whether the boat should move left.
     * 
     * @param moving true to move left, flase to stop
     */
    public void setBoatMovingLeft(final boolean moving) {
        boat.setMovingLeft(moving);
    }

    /**
     * Sets whether the boat should move right.
     * 
     * @param moving true to move right, false to stop
     */
    public void setBoatMovingRight(final boolean moving) {
        boat.setMovingRight(moving);
    }

    /**
     * Casts the hook into the water.
     */
    public void castHook() {
        if (hook.cast()) {
            fireEvent(new FishingEvent(FishingEvent.Type.HOOK_CAST, null));
        }
    }

    /**
     * Manually starts reeling the hook in.
     */
    public void reelInHook() {
        if (hook.reelIn()) {
            fireEvent(new FishingEvent(FishingEvent.Type.HOOK_REELING, null));
        }
    }

    /**
     * Called when the player presses the catch button during the QTE.
     * 
     * @return true if the catch succeeded
     */
    public boolean attemptCatch() {
        final Catchable fish = hook.getHookedFish();
        final boolean success = hook.attemptCatch();
        if (fish != null) {
            fireEvent(new FishingEvent(success ? FishingEvent.Type.FISH_CAUGHT : FishingEvent.Type.FISH_ESCAPED, fish));
        }
        return success;
    }

    /**
     * Sets stormy weather mode.Called by the weather system(TODO).
     * 
     * @param stormy true to activate storm conditions
     */
    public void setStormy(final boolean stormy) {
        hook.setStormy(stormy);
    }

    /**
     * Registers a listener to recieve fishing events.
     * 
     * @param listener the observer to add
     */
    public void addListener(final FishingListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes a previously registered listener.
     * 
     * @param listener the observer to remove
     */
    public void removeListener(final FishingListener listener) {
        listeners.remove(listener);
    }

    /**
     * Gets an immutable snapshot of the boat current position for the View to render.
     * 
     * @return an immutable snapshot of the boat position
     */
    public BoatView getBoat() {
        return new BoatSnapshot(boat.getX(), boat.getY());
    }

    /**
     * Gets an immutable snapshot of the hook current position and state for the View to render.
     * 
     * @return an immutable snapshot of the hook position and state
     */
    public HookView getHook() {
        return new HookSnapshot(hook.getX(), hook.getY(), hook.getCurrentState(), hook.getHookedFish());
    }

    /**
     * @return the active minigame, or null
     */
    public FishingMinigame getCurrentMinigame() {
        return hook.getCurrentMinigame();
    }

    private void fireEvent(final FishingEvent event) {
        for (final FishingListener l : listeners) {
            l.onFishingEvent(event);
        }
    }
}
