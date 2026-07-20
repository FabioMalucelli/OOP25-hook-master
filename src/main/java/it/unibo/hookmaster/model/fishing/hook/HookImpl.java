package it.unibo.hookmaster.model.fishing.hook;

import java.util.ArrayList;
import java.util.List;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.upgrade.event.UpgradeEvent;
import it.unibo.hookmaster.model.fishing.Catchable;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.model.fishing.minigame.MinigameFactory;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherEvent;
import it.unibo.hookmaster.util.TimeUtils;

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
    private double speed;
    private double maxWeight;

    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxDepth;

    private boolean movingLeft;
    private boolean movingRight;
    private boolean movingUp;
    private boolean movingDown;

    private HookState currentState;
    private Catchable hookedFish;
    private FishingMinigame currentMinigame;
    private boolean stormy;

    private final List<FishingListener> listeners = new ArrayList<>();

    /**
     * Constructs a new HookImpl.
     *
     * @param startX        the initial X position
     * @param startY        the initial Y position
     * @param speed         the movement speed in pixels/second, on both axes
     * @param minX          the left horizontal boundary
     * @param maxX          the right horizontal boundary
     * @param minY          the minimum Y the hook can reach
     * @param maxDepth      the maximum depth the hook can reach
     * @param maxWeight     the initial maximum weight the hook can catch
     */
    public HookImpl(final double startX, final double startY, 
        final double speed, final double minX, final double maxX, final double minY,
            final double maxDepth, final double maxWeight) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxDepth = maxDepth;
        this.maxWeight = maxWeight;
        this.currentState = HookState.MOVING;
    }

    @Override
    public void update(final long deltaTime) {
        if (currentState == HookState.MINIGAME) {
            if (currentMinigame != null) {
                currentMinigame.update(deltaTime);
            }
            return;
        }

        final double deltaSeconds = TimeUtils.toSeconds(deltaTime);

        if (movingLeft && !movingRight) {
            x -= speed * deltaSeconds;
        } else if (movingRight && !movingLeft) {
            x += speed * deltaSeconds;
        }
        x = Math.clamp(x, minX, maxX);

        if (movingUp && !movingDown) {
            y -= speed * deltaSeconds;
        } else if (movingDown && !movingUp) {
            y += speed * deltaSeconds;
        }
        if (y > maxDepth) {
            y = maxDepth;
        }
        y = Math.clamp(y, minY, maxDepth);
 
    }

    @Override
    public void setMovingLeft(final boolean moving) {
        this.movingLeft = moving;
    }

    @Override
    public void setMovingRight(final boolean moving) {
        this.movingRight = moving;
    }

    @Override
    public void setMovingUp(final boolean moving) {
        this.movingUp = moving;
    }

    @Override
    public void setMovingDown(final boolean moving) {
        this.movingDown = moving;
    }

    @Override
    public void stopMoving() {
        this.movingLeft = false;
        this.movingRight = false;
        this.movingUp = false;
        this.movingDown = false;
    }

    @Override
    public boolean hookFish(final Catchable fish) {
        if (currentState != HookState.MOVING && hookedFish != null) {
            return false;
        }
        if (fish.getWeight() > maxWeight) {
            fireEvent(new FishingEvent(FishingEvent.Type.FISH_TOO_HEAVY, fish));
            return false;
        }
        this.hookedFish = fish;
        this.currentMinigame = MinigameFactory.create(fish, stormy);
        this.currentState = HookState.MINIGAME;
        fireEvent(new FishingEvent(FishingEvent.Type.FISH_HOOKED, fish));
        return true;
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
            currentState = HookState.MOVING;
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
        switch (event.getUpgradeType()) {
            case SPEED:
                this.speed = event.getNewValue();
                break;
            case MAX_WEIGHT:
                this.maxWeight = event.getNewValue();
                break;
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
    public boolean onCollision(final Collidable other) {
        if (currentState == HookState.MOVING && other instanceof Catchable) {
            return hookFish((Catchable) other);
        }
        return false;
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
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    @Override
    public double getMaxWeight() {
        return maxWeight;
    }

    @Override
    public void setMaxWeight(final double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public void addListener(final FishingListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(final FishingListener listener) {
        listeners.remove(listener);
    }

    private void fireEvent(final FishingEvent event) {
        for (final FishingListener listener : listeners) {
            listener.onFishingEvent(event);
        }
    }
}
