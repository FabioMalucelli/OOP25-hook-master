package it.unibo.hookmaster.model.fishdata.boids;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.FishType;
import it.unibo.hookmaster.model.fishdata.Position;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

/**
 * Represents a boid in the simulation.
 */
public final class Boid implements Fish {

    private Fish fish;
    private double velocityX;
    private double velocityY;

    /**
     * Creates a new Boid with the given fish and initial velocity.
     * 
     * @param fish the fish associated with this boid.
     * @param velocityX the initial velocity in the X direction.
     * @param velocityY the initial velocity in the Y direction.
     */
    public Boid(final Fish fish, final double velocityX, final double velocityY) {
        this.fish = fish;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    /**
     * Returns the velocity of the boid in the X direction.
     * 
     * @return the velocity in the X direction.
     */
    public double getVelocityX() {
        return this.velocityX;
    }

    /**
     * Returns the velocity of the boid in the Y direction.
     * 
     * @return the velocity in the Y direction.
     */
    public double getVelocityY() {
        return this.velocityY;
    }

    /**
     * Sets the velocity of the boid in the X direction.
     * 
     * @param velocityX the new velocity in the X direction.
     */
    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Sets the velocity of the boid in the Y direction.
     * 
     * @param velocityY the new velocity in the Y direction.
     */
    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
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
     * {@inheritDoc}
     */
    @Override
    public boolean onCollision(Collidable other) {
        return this.fish.onCollision(other);
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
    public void setSpeedMultiplier(double speedMultiplier) {
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
        return this.fish.getPosition().getX();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getY() {
        return this.fish.getPosition().getY();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Position newPosition) {
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
    public void setDirection(int direction) {
        this.fish.setDirection(direction);
    }

    /**
     * Not supported for Boid. Boids have their own movement strategy.
     */
    @Override
    public void setMovementStrategy(MovementStrategy movementStrategy) {
        throw new UnsupportedOperationException("Boids have their own movement strategy.");
    }

    /**
     * Not supported for Boid. Boids have their own movement strategy.
     */
    @Override
    public void update(double mapWidth, double mapHeight, long deltaTime) {
        throw new UnsupportedOperationException("Boids have their own movement strategy.");
    }
}
