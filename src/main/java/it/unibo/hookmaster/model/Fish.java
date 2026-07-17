package it.unibo.hookmaster.model;

import java.util.Objects;

import it.unibo.hookmaster.model.movement.MovementStrategy;

/**
 * Represents a fish instance.
 */
public class Fish {

    private final FishType type;
    private Position position;
    private int direction = 1;
    private MovementStrategy movementStrategy;

    /**
     * Creates a new fish with the default movement strategy for its type.
     *
     * @param type the fish species
     * @param x    initial X position
     * @param y    initial Y position
     */
    public Fish(final FishType type, final int x, final int y) {
        this(type, new Position(x, y), type.createDefaultMovementStrategy());
    }

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
    public double getEconomicValue() {
        return this.type.getEconomicValue();
    }

    /**
     * @return the fish speed
     */
    public int getSpeed() {
        return this.type.getSpeed();
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
    public int getX() {
        return this.position.getX();
    }

    /**
     * @return the y position
     */
    public int getY() {
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
     */
    public void update(final int mapWidth, final int mapHeight) {
        this.movementStrategy.move(this, mapWidth, mapHeight);
    }
}
