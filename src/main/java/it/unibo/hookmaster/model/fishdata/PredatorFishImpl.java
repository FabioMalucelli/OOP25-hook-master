package it.unibo.hookmaster.model.fishdata;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

/**
 * Implementation of a predator fish.
 */
public final class PredatorFishImpl implements Fish {
    private final FishManager fishManager;
    private final Fish fish;

    /**
     * Creates a new predator fish.
     *
     * @param fishManager the fish manager, used to remove dead fish
     * @param fish the underlying fish instance
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "The fish is updated by the fish manager and should be exposed by the predator fish.")
    public PredatorFishImpl(final FishManager fishManager, final Fish fish) {
        this.fishManager = fishManager;
        this.fish = fish;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionAreaRectangle getCollisionArea() {
        return this.fish.getCollisionArea();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FishType getType() {
        return this.fish.getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSpeed() {
        return this.fish.getSpeed();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeedMultiplier(final double speedMultiplier) {
        this.fish.setSpeedMultiplier(speedMultiplier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getPosition() {
        return this.fish.getPosition();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getX() {
        return this.fish.getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return this.fish.getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(final Position newPosition) {
        this.fish.setPosition(newPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getDirection() {
        return this.fish.getDirection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(final int direction) {
        this.fish.setDirection(direction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getEconomicValue() {
        return this.fish.getEconomicValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getCatchDifficulty() {
        return this.fish.getCatchDifficulty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.fish.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getWeight() {
        return this.fish.getWeight();
    }

    /**
     * Checks for collision with another collidable object. If the other object is a fish and is
     * smaller than this predator fish, it will be removed from the simulation.
     */
    @Override
    public boolean onCollision(final Collidable other) {
        boolean fishRemoved = false;
        if (other instanceof Fish) {
            final Fish otherFish = (Fish) other;
            if (otherFish.getWeight() < this.fish.getWeight()
                    && otherFish.getDirection() != this.fish.getDirection()) {
                this.fishManager.removeDeadFish(otherFish);
                fishRemoved = true;
            }
        }
        return this.fish.onCollision(other) || fishRemoved;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMovementStrategy(final MovementStrategy movementStrategy) {
        this.fish.setMovementStrategy(movementStrategy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double mapWidth, final double mapHeight, final long deltaTime) {
        this.fish.update(mapWidth, mapHeight, deltaTime);
    }

}
