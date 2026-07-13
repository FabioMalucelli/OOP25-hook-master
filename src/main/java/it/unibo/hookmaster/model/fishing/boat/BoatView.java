package it.unibo.hookmaster.model.fishing.boat;

/**
 * Read only view of a Boat, exposing only the data needed to render its current position.
 */
public interface BoatView {

    /**
     * Gets the current X position of the boat.
     * 
     * @return the X coordinates in pixels
     */
    double getX();

    /**
     * Gets the fixed Y position of the boat on the water surface.
     * 
     * @return the Y coordinates in pixels
     */
    double getY();
}
