package it.unibo.hookmaster.model.fishing.hook;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.event.UpgradeEvent;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.model.fishing.minigame.MinigameFactory;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherEvent;

/**
 * Standard implementation of Hook.
 */
public final class HookImpl implements Hook {

    /**
     * Radius of the circular hitbox around the hook tip(in pixels).
     */
    private static final double HOOK_RADIUS = 5.0;

    private double x;
    private double y;

    //Game attributes (whitch can then be modified using the shop)
    private double dropSpeed;
    private double reelSpeed;
    private final double maxDepth;

    private HookState currentState;
    private Catchable hookedFish;
    private FishingMinigame currentMinigame;
    private boolean stormy;

    private final List<FishingListener> listeners = new ArrayList<>();

    /**
     * Constructs a new HookImpl.
     *
     * @param startX        the initial X position (usually the boat's X)
     * @param startY        the initial Y position (usually the boat's Y)
     * @param dropSpeed     the speed at which the hook drops
     * @param reelSpeed     the speed at which the hook is reeled in
     * @param maxDepth      the maximum depth the hook can reach
     */
    public HookImpl(final double startX, final double startY, 
        final double dropSpeed, final double reelSpeed, final double maxDepth) {
        this.x = startX;
        this.y = startY;
        this.dropSpeed = dropSpeed;
        this.reelSpeed = reelSpeed;
        this.maxDepth = maxDepth;
        this.currentState = HookState.IDLE;
    }

    @Override
    public void update(final double deltaTime, final double boatX, final double boatY) {
        switch (currentState) {
            case IDLE:
                this.x = boatX;
                this.y = boatY;
                break;
            case DROPPING:
                y += dropSpeed * deltaTime;
                if (y >= maxDepth) {
                    y = maxDepth;
                    currentState = HookState.REELING;
                }
                break;
            case REELING:
                y -= reelSpeed * deltaTime;
                if (y <= boatY) {
                    y = boatY;
                    currentState = HookState.IDLE;
                }
                break;
            case MINIGAME:
                //Position frozen while the QTE is running.
                //still needs to advance(indicator movement).
                if (currentMinigame != null) {
                    currentMinigame.update(deltaTime);
                }
                break;
        }
    }

    @Override
    public boolean cast() {
        if (currentState == HookState.IDLE) {
            currentState = HookState.DROPPING;
            fireEvent(new FishingEvent(FishingEvent.Type.HOOK_CAST, null));
            return true;
        }
        return false;
    }

    @Override
    public boolean reelIn() {
        if (currentState == HookState.DROPPING) {
            currentState = HookState.REELING;
            fireEvent(new FishingEvent(FishingEvent.Type.HOOK_REELING, null));
            return true;
        }
        return false;
    }

    @Override
    public void hookFish(final Catchable fish) {
        if ((currentState == HookState.DROPPING || currentState == HookState.REELING) && hookedFish == null) {
            this.hookedFish = fish;
            this.currentMinigame = MinigameFactory.create(fish, stormy);
            this.currentState = HookState.MINIGAME;
            fireEvent(new FishingEvent(FishingEvent.Type.FISH_HOOKED, fish));
        }
    }

    @Override
    public boolean attemptCatch() {
        if (currentState != HookState.MINIGAME || currentMinigame == null) {
            return false;
        }

        final Catchable fish = hookedFish;
        final boolean success = currentMinigame.attemptCatch();
        completeMinigame(success);
        currentMinigame = null;
        fireEvent(new FishingEvent(success ? FishingEvent.Type.FISH_CAUGHT : FishingEvent.Type.FISH_ESCAPED, fish));
        return success;
    }

    @Override
    public void completeMinigame(final boolean success) {
        if (currentState == HookState.MINIGAME) {
            if (!success) {
                hookedFish = null;
            }
            currentState = HookState.REELING;
        }
    }

    @Override
    public void clearHookedFish() {
        this.hookedFish = null;
    }

    @Override
    public FishingMinigame getCurrentMinigame() {
        return currentMinigame;
    }

    @Override
    public void setStormy(final boolean stormy) {
        this.stormy = stormy;
    }

    @Override
    public void onUpgrade(final UpgradeEvent event) {
        if (event.getUpgradeType() == UpgradeType.SPEED) {
            this.dropSpeed = event.getNewValue();
            this.reelSpeed = event.getNewValue();
        }
    }

    @Override
    public void onWeatherChanged(final WeatherEvent event) {
        setStormy(event.getWeather() == Weather.STORMY);
    }

    @Override
    public CollisionAreaCircle getCollisionArea() {
        return new CollisionAreaCircle(x, y, HOOK_RADIUS);
    }

    @Override
    public void onCollision(final Collidable other) {
        if ((currentState == HookState.DROPPING || currentState == HookState.REELING) && other instanceof Catchable) {
            hookFish((Catchable) other);
        }
    }

    @Override
    public HookState getCurrentState() {
        return currentState;
    }

    @Override
    public Catchable getHookedFish() {
        return hookedFish;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setDropSpeed(final double dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    @Override
    public void setReelSpeed(final double reelSpeed) {
        this.reelSpeed = reelSpeed;
    }

    @Override
    public void addListener(final FishingListener listener) {
        listeners.add(listener);
    }

    public void removeListener(final FishingListener listener) {
        listeners.remove(listener);
    }

    private void fireEvent(final FishingEvent event) {
        for (final FishingListener listener : listeners) {
            listener.onFishingEvent(event);
        }
    }
}
