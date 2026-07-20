package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

public class PredatorFishImpl implements Fish {
    private final FishManager fishManager;
    private final Fish fish;

    public PredatorFishImpl(final FishManager fishManager, final Fish fish) {
        this.fishManager = fishManager;
        this.fish = fish;
    }

    @Override
    public CollisionAreaRectangle getCollisionArea() {
        return this.fish.getCollisionArea();
    }

    @Override
    public FishType getType() {
        return this.fish.getType();
    }

    @Override
    public double getSpeed() {
        return this.fish.getSpeed();
    }

    @Override
    public void setSpeedMultiplier(final double speedMultiplier) {
        this.fish.setSpeedMultiplier(speedMultiplier);
    }

    @Override
    public Position getPosition() {
        return this.fish.getPosition();
    }

    @Override
    public double getX() {
        return this.fish.getX();
    }

    @Override
    public double getY() {
        return this.fish.getY();
    }

    @Override
    public void setPosition(final Position newPosition) {
        this.fish.setPosition(newPosition);
    }

    @Override
    public int getDirection() {
        return this.fish.getDirection();
    }

    @Override
    public void setDirection(final int direction) {
        this.fish.setDirection(direction);
    }

    @Override
    public int getEconomicValue() {
        return this.fish.getEconomicValue();
    }

    @Override
    public double getCatchDifficulty() {
        return this.fish.getCatchDifficulty();
    }

    @Override
    public String getName() {
        return this.fish.getName();
    }

    @Override
    public double getWeight() {
        return this.fish.getWeight();
    }

    @Override
    public boolean onCollision(Collidable other) {
        boolean fishRemoved = false;
        if (other instanceof Fish) {
            final Fish otherFish = (Fish) other;
            if (otherFish.getWeight() < this.fish.getWeight()) {
                this.fishManager.removeFish(otherFish);
                fishRemoved = true;
            }
        }
        return this.fish.onCollision(other) || fishRemoved;
    }

    @Override
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        this.fish.setMovementStrategy(movementStrategy);
    }

    @Override
    public void update(double mapWidth, double mapHeight, long deltaTime) {
        this.fish.update(mapWidth, mapHeight, deltaTime);
    }

}
