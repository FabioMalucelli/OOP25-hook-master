package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.event.UpgradeObserver;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;

/**
 * Defines the contract for the hook model.
 */
public interface Hook extends HookView, UpgradeObserver {

    /**
     * Advances the hook position by one frame.
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

    @Override
    HookState getCurrentState();

    @Override
    Catchable getHookedFish();

    @Override
    double getX();

    @Override
    double getY();

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
     * Registers the listener nitified when this hook collides with
     * another Collidable while in a cathcable state(DROPPING or REELING).
     * 
     * @param listener the collision listener to register
     */
    void setCollisionListener(HookCollisionListener listener);
}
