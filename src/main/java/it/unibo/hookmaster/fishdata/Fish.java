package it.unibo.hookmaster.fishdata;

/**
 * Represents a fish instance in the game world.
 */
public class Fish {

    private final FishType type;
    private int x;
    private int y;
    private int direction = 1;

    /**
     * Creates a new fish.
     *
     * @param type the fish species
     * @param x    initial x position
     * @param y    initial y position
     */
    public Fish(final FishType type, final int x, final int y) {
        this.type = type;
        this.x = x;
        this.y = y;
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
     * @return the fish weight
     */
    public double getCatchDifficulty() {
        return this.type.getCatchDifficulty();
    }

    /**
     * @return the x position
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return the y position
     */
    public int getY() {
        return this.y;
    }

    /**
     * Updates the fish position.
     *
     * @param newX new x position
     * @param newY new y position
     */
    public void setPosition(final int newX, final int newY) {
        this.x = newX;
        this.y = newY;
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
}
