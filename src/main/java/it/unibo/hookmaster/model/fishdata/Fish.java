package it.unibo.hookmaster.model.fishdata;

import java.util.Objects;
import java.util.Random;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Represents a fish instance.
 */
public class Fish implements Catchable, Collidable {

    private static final double MIN_WEIGHT_RATIO = 1.0;
    private static final double MAX_WEIGHT_RATIO = 2.0;
    private static final double MIN_CATCH_DIFFICULTY = 0.1;
    private static final double MAX_CATCH_DIFFICULTY = 1.0;

    private CollisionReaction collisionReaction = other -> {
    };
    private final double weight;
    private final FishType type;
    private Position position;
    private int direction = 1;
    private MovementStrategy movementStrategy;
    private double speedMultiplier = 1.0;

    private final Random random = new Random();

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
        this.weight = generateWeight();
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
    @Override
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
     * @return the value of the fish based on it's weight
     */
    @Override
    public int getEconomicValue() {
        final double ratio = this.weight / this.type.getBaseWeight();
        return (int) Math.round(this.type.getBaseEconomicValue() * ratio);
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
     * @return the catch difficulty of the fish
     */
    @Override
    public double getCatchDifficulty() {
        final double ratio = this.weight / this.type.getBaseWeight();
        final double rawDifficulty = this.type.getBaseCatchDifficulty() * ratio;
        return Math.max(MIN_CATCH_DIFFICULTY, Math.min(MAX_CATCH_DIFFICULTY, rawDifficulty));
    }

    /**
     * @return the fish's weight ratio
     */
    @Override
    public double getWeight() {
        return weight;
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
     * Replaces the current movement strategy.
     *
     * @param movementStrategy the new strategy
     */
    public void setMovementStrategy(final MovementStrategy movementStrategy) {
        this.movementStrategy = Objects.requireNonNull(movementStrategy);
    }

    /**
     * Advances this fish by one simulation step.
     *
     * @param mapWidth  the horizontal size of the map
     * @param mapHeight the vertical size of the map
     * @param deltaTime the time
     */
    public void update(final double mapWidth, final double mapHeight, final long deltaTime) {
        this.movementStrategy.move(this, mapWidth, mapHeight, deltaTime);
    }

    /**
     * Generates a random weight between the min weight and the max weight possible.
     * 
     * @return random value between the lowest weight and the highest weight
     */
    private double generateWeight() {
        final double baseWeight = this.type.getBaseWeight();
        final double minWeight = baseWeight * MIN_WEIGHT_RATIO;
        final double maxWeight = baseWeight * MAX_WEIGHT_RATIO;
        return minWeight + random.nextDouble() * (maxWeight - minWeight);
    }

    /**
     * @return the hitbox
     */
    @Override
    public CollisionAreaRectangle getCollisionArea() {
        final double baseArea = type.getBaseWidth() * type.getBaseHeight();
        final double targetArea = weight * 50;
        final double scaleFacotr = Math.sqrt(targetArea / baseArea);
        final double width = type.getBaseWidth() * scaleFacotr;
        final double height = type.getBaseHeight() * scaleFacotr;
        return new CollisionAreaRectangle(this.getX(), this.getY(), width, height);
    }

    /**
     * @param other reaction to a collision
     */
    @Override
    public void onCollision(final Collidable other) {
        this.collisionReaction.react(other);
    }

    /**
     * Sets the reaction to trigger when this fish collides with another collidable.
     *
     * @param collisionReaction the reaction to apply on collision
     */
    public void setCollisionReaction(final CollisionReaction collisionReaction) {
        this.collisionReaction = Objects.requireNonNull(collisionReaction);
    }
}
