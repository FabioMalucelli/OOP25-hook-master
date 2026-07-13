package it.unibo.hookmaster.model.fishing.boat;

/**
 * Defines the contract for the boat model.
 * The boat moves horizzontally along the water surface and acts
 * as the anchor point from which the hook is cast.
 */
public interface Boat extends BoatView {

    /**
     * Advances the boat postion by one frame.
     * 
     * @param deltaTime seconds elapsed since the last frame
     */
    void update(double deltaTime);

    /**
     * Sets if the boat should move left.
     * 
     * @param movingLeft true to move left, false to stop
     */
    void setMovingLeft(boolean movingLeft);

    /**
     * Sets if the boat should move right.
     * 
     * @param movingRight true to move right, flase to stop
     */
    void setMovingRight(boolean movingRight);

    @Override
    double getX();

    @Override
    double getY();
}
