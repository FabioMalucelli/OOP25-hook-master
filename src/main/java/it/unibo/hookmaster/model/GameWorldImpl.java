package it.unibo.hookmaster.model;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionManager;
import it.unibo.hookmaster.model.collision.CollisionPredicates;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.FishManager;
import it.unibo.hookmaster.model.fishdata.FishSpawner;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.fishing.hook.HookImpl;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.upgrade.PlayerWallet;
import it.unibo.hookmaster.model.upgrade.Shop;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherSystem;
import it.unibo.hookmaster.model.weather.WeatherSystemImpl;

/**
 * The only implementation of the GameWorld interface. It contains all the game entities and their
 * states, and it is responsible for updating them. The GameWorld is the main entry point for the
 * game logic, and it is used by the GameController to run the game loop.
 */
public class GameWorldImpl implements GameWorld {
    private final FishManager fishManager;
    private final CollisionManager collisionManager =
            CollisionPredicates.prefilledCollisionManager();
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
        this.hook = new HookImpl(x / 2, 0, 1000, 0, x, 0, y, 0);
        final FishSpawner spawner = new FishSpawner(x, y);
        this.fishManager = new FishManager(spawner, weatherSystem, x, y);
        shop.addObserver(hook);
        hook.addListener(e -> {
            switch (e.getType()) {
                case FISH_CAUGHT:
                    playerWallet.addCoins(e.getFish().getEconomicValue());
                case FISH_ESCAPED:
                    fishManager.removeFish((Fish) e.getFish());
                    break;
                default:
                    break;
            }
        });
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
            collisionManager.checkCollisions(getCollidables());
        }
    }

    private List<Collidable> getCollidables() {
        final List<Collidable> collidables = new ArrayList<>(getFishes().size() + 1);
        collidables.add(hook);
        collidables.addAll(getFishes());
        return collidables;
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
    public List<Fish> consumeDeadFishes() {
        return this.fishManager.consumeDeadFishes();
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Weather getWeather() {
        return this.weatherSystem.getCurrentWeather();
    }

    @Override
    public GameWorldImpl.MementoImpl createMemento() {
        return new MementoImpl(this.playerWallet.createMemento(), this.shop.createMemento());
    }

    @Override
    public void restoreFromMemento(GameWorld.Memento memento) {
        if (!(memento instanceof MementoImpl)) {
            throw new IllegalArgumentException("Invalid memento type");
        }
        final MementoImpl m = (MementoImpl) memento;
        this.playerWallet.restoreFromMemento(m.playerWalletMemento);
        this.shop.restoreFromMemento(m.shopMemento);
    }

    private static final class MementoImpl implements GameWorld.Memento {
        private static final long serialVersionUID = 1L;

        private final PlayerWallet.Memento playerWalletMemento;
        private final Shop.Memento shopMemento;

        private MementoImpl(final PlayerWallet.Memento playerWalletMemento, final Shop.Memento shopMemento) {
            this.playerWalletMemento = playerWalletMemento;
            this.shopMemento = shopMemento;
        }
    }
}
