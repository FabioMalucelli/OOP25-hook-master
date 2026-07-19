package it.unibo.hookmaster.model.fishdata;

import java.util.Objects;

import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Represents a fish instance.
 */
public class Fish implements Catchable {

    private final FishType type;
    private Position position;
    private int direction = 1;
    private MovementStrategy movementStrategy;
    private double speedMultiplier = 1.0;

    /**
     * Creates a new fish with an explicit movement strategy.
     *
     * @param type             the fish species
     * @param position         initial position
     * @param movementStrategy the movement strategy of the fish
     */
    public Fish(final FishType type, final Position position, final MovementStrategy movementStrategy) {
        this.type = type;
        this.position = position;
        this.movementStrategy = Objects.requireNonNull(movementStrategy);
    }

    /**
     * @return the fish type
     */
    public FishType getType() {
        return this.type;
    }

    /**
     * @return the fish name
     */
    public String getName() {
        return this.type.getName();
    }

    /**
     * @return true if the fish is a predator
     */
    public boolean isPredator() {
        return this.type.isPredator();
    }

    /**
     * @return the fish value
     */
    public int getEconomicValue() {
        return this.type.getEconomicValue();
    }

    /**
     * @return the effective fish speed, including any active multiplier
     */
    public double getSpeed() {
        return Math.round(this.type.getSpeed() * this.speedMultiplier);
    }

    /**
     * Sets a multiplier applied to the base speed.
     *
     * @param speedMultiplier the multiplier, 1.0 meaning no change
     */
    public void setSpeedMultiplier(final double speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    /**
     * @return the fish catch difficulty
     */
    public double getCatchDifficulty() {
        return this.type.getCatchDifficulty();
    }

    /**
     * @return the current position
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * @return the x position
     */
    public double getX() {
        return this.position.getX();
    }

    /**
     * @return the y position
     */
    public double getY() {
        return this.position.getY();
    }

    /**
     * Updates the fish position.
     *
     * @param newPosition the new position
     */
    public void setPosition(final Position newPosition) {
        this.position = Objects.requireNonNull(newPosition);
    }

    /**
     * @return the direction the fish is going
     */
    public int getDirection() {
        return direction;
    }

    /**
     * Sets the direction the fish will go.
     *
     * @param direction the direction the fish is going
     */
    public void setDirection(final int direction) {
        this.direction = direction;
    }

    /**
     * Replaces the current movement strategy (a predator entering
     * "chase mode", or a fish joining a school).
     *
     * @param movementStrategy the new strategy
     */
    public void setMovementStrategy(final MovementStrategy movementStrategy) {
        this.movementStrategy = Objects.requireNonNull(movementStrategy);
    }

    /**
     * Advances this fish by one simulation step, delegating to the
     * current movement strategy.
     *
     * @param mapWidth  the horizontal size of the map
     * @param mapHeight the vertical size of the map
     * @param deltaTime the time
     */
    public void update(final double mapWidth, final double mapHeight, final long deltaTime) {
        this.movementStrategy.move(this, mapWidth, mapHeight, deltaTime);
    }
}
