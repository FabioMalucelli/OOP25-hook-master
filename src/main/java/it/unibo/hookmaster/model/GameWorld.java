package it.unibo.hookmaster.model;

import java.util.List;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.upgrade.PlayerWallet;
import it.unibo.hookmaster.model.upgrade.Shop;
import it.unibo.hookmaster.model.weather.Weather;

/**
 * This interface represents the game world, which is the main model of the game. It contains all
 * the game entities and their states, and it is responsible for updating them. The GameWorld is the
 * main entry point for the game logic, and it is used by the GameController to run the game loop.
 */
public interface GameWorld {
    /**
     * Advances the game state by the given delta time. This method is called by the game loop to
     * update the game world. It updates all the game entities and their states.
     * 
     * @param deltaTime the amount of milliseconds of which the game world should advance. This is
     *        the time elapsed since the last frame.
     */
    void update(long deltaTime);

    /**
     * Returns the list of fishes currently in the game world.
     * 
     * @return the list of fishes
     */
    List<Fish> getFishes();

    /**
     * Consume and returns the list of dead fishes currently in the game world.
     * 
     * @return the list of dead fishes
     */
    List<Fish> consumeDeadFishes();

    /**
     * Returns the hook currently in the game world.
     * 
     * @return the hook
     */
    Hook getHook();

    /**
     * Returns the shop currently in the game world.
     * 
     * @return the shop
     */
    Shop getShop();

    /**
     * Returns the player wallet currently in the game world.
     * 
     * @return the player wallet
     */
    PlayerWallet getPlayerWallet();

    /**
     * Returns the weather currently in the game world.
     * 
     * @return the weather
     */
    Weather getWeather();
}
