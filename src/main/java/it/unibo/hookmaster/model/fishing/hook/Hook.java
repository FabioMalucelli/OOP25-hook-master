package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.event.UpgradeObserver;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.model.weather.WeatherObserver;

/**
 * Defines the contract for the hook model.
 */
public interface Hook extends Collidable, UpgradeObserver, WeatherObserver {

    /**
     * Advances the hook position by one frame,and if a minigame is active 
     * the minigame advances by one frame.
     * 
     * @param deltaTime seconds passed since the last frame
     * @param boatX     current X of the boat
     * @param boatY     current Y of the boat
     */
    void update(double deltaTime, double boatX, double boatY);

    /**
     * Casts the hook into the water if its current HookState is IDLE.
     * 
     * @return true if the cast was performed
     */
    boolean cast();

    /**
     * Starts reeling the hook if its current HookState is DROPPING.
     * 
     * @return true if the reeling was started.
     */
    boolean reelIn();

    /**
     * Freezes the hook and enters the HookState -> MINIGAME.
     * Should be called when the hook collides with a fish.
     * 
     * @param fish the fish that has been hooked
     */
    void hookFish(Catchable fish);

    /**
     * Resolves the active catching minigame: checks if the indicator was inside the target zone, updates
     * the hooked-fish,clears the minigame, and resumes reeling.
     * 
     * @return true if the catch succeeded
     */
    boolean attemptCatch();

    /**
     * Resolves the minigame and starts reeling.
     * On failure the fish reference is cleared.
     * 
     * @param success true if the player caught the fish
     */
    void completeMinigame(boolean success);

    /**
     * Clears the hooked-fish reference after the reward has been collected.
     */
    void clearHookedFish();

    /**
     * Gets the current states of the hook.
     * 
     * @return the current HookState
     */
    HookState getCurrentState();

    /**
     * Gets the fish currently hooked, if any.
     * 
     * @return the hooked Catchable, or null if none
     */
    Catchable getHookedFish();

    /**
     * Gets the current X position of the hook.
     * 
     * @return the X coordinate in pixels
     */
    double getX();

    /**
     * Gets the current Y(depth) position of the hook
     * 
     * @return the Y coordinate in pixels
     */
    double getY();
    
    @Override
    CollisionAreaCircle getCollisionArea();

    /**
     * Gets the catching minigame currently active, if any.
     * 
     * @return the active FishingMinigame, or null if no minigame is in progress
     */
    FishingMinigame getCurrentMinigame();

    /**
     * Sets whether stormy weather is active, affecting the difficulty of the catching minigame created by the next call.
     * 
     * @param stormy true to activate stormy conditions
     */
    void setStormy(boolean stormy);

    /**
     * Sets the dropping speed. Invoked by the shop on upgrade purchase.
     * 
     * @param dropSpeed new sinking speed in pixels/second
     */
    void setDropSpeed(double dropSpeed);

    /**
     * Sets the reeling speed. Invoked by the shop on upgrade purchase.
     * 
     * @param reelSpeed new reel speed in pixels/second
     */
    void setReelSpeed(double reelSpeed);

    /**
     * Registers a listener to recieve fishing events fired by this hook.
     * 
     * @param listener the observer to add
     */
    void addListener(FishingListener listener);

    /**
     * Removes a previously registerd litener.
     * 
     * @param listener the observer to remove
     */
    void removeListener(FishingListener listener);
}
