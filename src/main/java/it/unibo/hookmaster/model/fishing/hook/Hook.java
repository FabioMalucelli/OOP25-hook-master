package it.unibo.hookmaster.model.fishing.hook;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.upgrade.event.UpgradeObserver;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.model.weather.WeatherObserver;

/**
 * Defines the contract for the hook model.
 */
public interface Hook extends Collidable, UpgradeObserver, WeatherObserver {

    /**
     * Advances the hook position by one frame based on the currtently
     * actvie movement flags, or advances the catching minigame if one is
     * in progress.
     * 
     * @param deltaTime milliseconds passed since the last frame
     */
    void update(long deltaTime);

    /**
     * Activates the hook left movement.
     * 
     * @param moving true to start moving left, false to stop
     */
    void setMovingLeft(boolean moving);

    /**
     * Activates the hook right movement.
     * 
     * @param moving true to start moving right, false to stop
     */
    void setMovingRight(boolean moving);

    /**
     * Activates the hook up movement.
     * 
     * @param moving true to start moving up, false to stop
     */
    void setMovingUp(boolean moving);

    /**
     * Activates the hook down movement.
     * 
     * @param moving true to start moving down, false to stop
     */
    void setMovingDown(boolean moving);

    /**
     * Stops the hook on both axes at once.
     * Equivalent to setMovingLeft, setMovingRight, setMovingUp, setMovingDown with false
     */
    void stopMoving();

    /**
     * Freezes the hook and enters the HookState -> MINIGAME.
     * Should be called when the hook collides with a fish.
     * 
     * @param fish the fish that has been hooked
     * @return true if the fish was hooked
     */
    boolean hookFish(Catchable fish);

    /**
     * Resolves the active catching minigame: checks if the indicator was inside the target zone, updates
     * the hooked-fish,clears the minigame and resumes free movement.
     * 
     * @return true if the catch succeeded
     */
    boolean attemptCatch();

    /**
     * Resolves the minigame and resumes free movement.
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
     * Gets the current Y position of the hook.
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
     * Sets the movement speed on both axes. Invoked by the shop on upgrade purchase.
     * 
     * @param speed new movement speed in pixels/second
     */
    void setSpeed(double speed);

    /**
     * Gets the current maximum weight the hook can catch.
     * 
     * @return the current max weight
     */
    double getMaxWeight();

    /**
     * Sets the maximum weight the hook can catch.
     * 
     * @param maxWeight the new max weight
     */
    void setMaxWeight(double maxWeight);

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
