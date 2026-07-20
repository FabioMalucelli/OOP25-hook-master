package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.fishing.Catchable;

public interface Fish extends Catchable, Collidable {
    /**
     * {@inheritDoc}
     */
    @Override
    CollisionAreaRectangle getCollisionArea();

    /**
     * @return the fish type
     */
    FishType getType();

    /**
     * @return the effective fish speed, including any active multiplier
     */
    double getSpeed();

    /**
     * Sets a multiplier applied to the base speed.
     *
     * @param speedMultiplier the multiplier, 1.0 meaning no change
     */
    void setSpeedMultiplier(double speedMultiplier);

    /**
     * @return the current position
     */
    Position getPosition();

    /**
     * @return the x position
     */
    double getX();

    /**
     * @return the y position
     */
    double getY();

    /**
     * Updates the fish position.
     *
     * @param newPosition the new position
     */
    void setPosition(Position newPosition);

    /**
     * @return the direction the fish is going
     */
    int getDirection();

    /**
     * Sets the direction the fish will go.
     *
     * @param direction the direction the fish is going
     */
    void setDirection(int direction);

    /**
     * Replaces the current movement strategy.
     *
     * @param movementStrategy the new strategy
     */
    void setMovementStrategy(MovementStrategy movementStrategy);

    /**
     * Advances this fish by one simulation step.
     *
     * @param mapWidth  the horizontal size of the map
     * @param mapHeight the vertical size of the map
     * @param deltaTime the time
     */
    void update(double mapWidth, double mapHeight, long deltaTime);
}
