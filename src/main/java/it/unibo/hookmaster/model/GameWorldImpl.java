package it.unibo.hookmaster.model;

import java.util.List;

import it.unibo.hookmaster.model.collision.CollisionManager;
import it.unibo.hookmaster.model.collision.CollisionManagerImpl;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.FishManager;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.upgrade.PlayerWallet;
import it.unibo.hookmaster.model.upgrade.Shop;

/**
 * The only implementation of the GameWorld interface.
 * It contains all the game entities and their states, and it is responsible for updating them.
 * The GameWorld is the main entry point for the game logic, and it is used by the GameController to run the game loop.
 */
public class GameWorldImpl implements GameWorld {
    private /* final */ FishManager fishManager;
    private final CollisionManager collisionManager = new CollisionManagerImpl();
    private final Hook hook;
    private final Shop shop = new Shop();
    private final PlayerWallet playerBank = new PlayerWallet();

    /**
     * Creates a new GameWorldImpl with the given map dimensions.
     * 
     * @param x the width of the map
     * @param y the height of the map
     */
    public GameWorldImpl(final double x, final double y) {
        this.hook = new HookImpl(x / 2, 0, 100, 0, x, y);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime) {
        hook.update(deltaTime);
        if (this.hook.getCurrentState() != HookState.MINIGAME) {
            // If the hook is in the minigame state, we don't update the game world.
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
}
