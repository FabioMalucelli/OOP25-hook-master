package it.unibo.hookmaster.model.fishdata.boids;

import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.fishdata.AbstractFishDecorator;

/**
 * Represents a boid in the simulation.
 */
public final class Boid extends AbstractFishDecorator {

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
        super(fish);
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
    public void setVelocityX(final double velocityX) {
        this.velocityX = velocityX;
    }

    /**
     * Sets the velocity of the boid in the Y direction.
     * 
     * @param velocityY the new velocity in the Y direction.
     */
    public void setVelocityY(final double velocityY) {
        this.velocityY = velocityY;
    }

    /**
     * Not supported for Boid. Boids have their own movement strategy.
     */
    @Override
    public void setMovementStrategy(final MovementStrategy movementStrategy) {
        throw new UnsupportedOperationException("Boids have their own movement strategy.");
    }

    /**
     * Not supported for Boid. Boids have their own movement strategy.
     */
    @Override
    public void update(final double mapWidth, final double mapHeight, final long deltaTime) {
        throw new UnsupportedOperationException("Boids have their own movement strategy.");
    }
}
