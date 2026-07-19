package it.unibo.hookmaster.model;

import java.util.List;

import it.unibo.hookmaster.model.collision.CollisionManager;
import it.unibo.hookmaster.model.collision.CollisionManagerImpl;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.FishManager;
import it.unibo.hookmaster.model.fishdata.FishSpawner;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.upgrade.PlayerWallet;
import it.unibo.hookmaster.model.upgrade.Shop;
import it.unibo.hookmaster.model.weather.WeatherSystem;
import it.unibo.hookmaster.model.weather.WeatherSystemImpl;
import javafx.application.Platform;

/**
 * The only implementation of the GameWorld interface.
 * It contains all the game entities and their states, and it is responsible for updating them.
 * The GameWorld is the main entry point for the game logic, and it is used by the GameController to run the game loop.
 */
public class GameWorldImpl implements GameWorld {
    private final FishManager fishManager;
    private final CollisionManager collisionManager = new CollisionManagerImpl();
    private final Hook hook;
    private final Shop shop = new Shop();
    private final PlayerWallet playerWallet = new PlayerWallet();
    private final WeatherSystem weatherSystem = new WeatherSystemImpl();

    /**
     * Creates a new GameWorldImpl with the given map dimensions.
     * 
     * @param x the width of the map
     * @param y the height of the map
     */
    public GameWorldImpl(final double x, final double y) {
        this.hook = new HookImpl(x / 2, 0, 100, 0, x, 0, y, 1e6);
        final FishSpawner spawner = new FishSpawner(x, y);
        this.fishManager = new FishManager(spawner, weatherSystem, x, y);
        fishManager.spawnFish();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        hook.update(deltaTime);
        if (hook.getCurrentState() != HookState.MINIGAME) {
            weatherSystem.update(deltaTime);
            fishManager.update(deltaTime);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Fish> getFishes() {
        return this.fishManager.getFishes();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Hook getHook() {
        return this.hook;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Shop getShop() {
        return this.shop;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerWallet getPlayerWallet() {
        return this.playerWallet;
    }
}
