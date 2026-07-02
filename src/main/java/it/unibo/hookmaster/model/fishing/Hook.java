package it.unibo.hookmaster.model.fishing;

/**
 * Model of the Hook.
 */
public final class Hook {
    private double x;
    private double y;

    //Game attributes (whitch can then be modified using the shop)
    private double dropSpeed;
    private double reelSpeed;
    private final double maxDepth;

    private HookState currenState;

    /**
     * Constructs a new Hook with specified intial postion, dropSpeed, reelSpeed, maxDepth.
     *
     * @param startX        the initial X position (usually the boat's X)
     * @param startY        the initial Y position (usually the boat's Y)
     * @param dropSpeed     the speed at which the hook drops
     * @param reelSpeed     the speed at which the hook is reeled in
     * @param maxDepth      the maximum depth the hook can reach
     */
    public Hook(final double startX, final double startY, final double dropSpeed, final double reelSpeed, final double maxDepth) {
        this.x = startX;
        this.y = startY;
        this.dropSpeed = dropSpeed;
        this.reelSpeed = reelSpeed;
        this.maxDepth = maxDepth;
        this.currenState = HookState.IDLE;
    }

    /**
     * Updates the position and the logic of the hook based on the current state.
     * 
     * @param deltaTime the time passed since the last frame
     * @param boatX     the current X position of the boat
     * @param boatY     the current Y position of the boat
     */
    public void update(final double deltaTime, final double boatX, final double boatY) {
        switch (currenState) {
            case IDLE:
                this.x = boatX;
                this.y = boatY;
                break;
            case DROPPING:
                y += dropSpeed * deltaTime;
                if (y >= maxDepth) {
                    y = maxDepth;
                    currenState = HookState.REELING;
                }
                break;
            case REELING:
                y -= reelSpeed * deltaTime;
                if (y <= boatY) {
                    y = boatY;
                    currenState = HookState.IDLE;
                }
                break;
            case MINIGAME:
                //TODO
                break;
        }
    }

    /**
     * Throws the hook if you are on the boat.
     */
    public void cast() {
        if (currenState == HookState.IDLE) {
            currenState = HookState.DROPPING;
        }
    }

    /**
     * Starts recolling the hook manually.
     */
    public void reelIn() {
        if (currenState == HookState.DROPPING) {
            currenState = HookState.REELING;
        }
    }

    //Gettetrs e Setters for the Shop upgrades

    /**
     * Gets the current state of the Hook.
     * 
     * @return the current HookState of the hook 
     */
    public HookState getCurrentState() {
        return currenState;
    }

    /**
     * Sets the current state of the Hook.
     * 
     * @param state the new HookState to set
     */
    public void setCurrentState(final HookState state) {
        this.currenState = state;
    }

    /**
     * Gets the current X position of the hook.
     * 
     * @return the X coordinate
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the current Y position (depth) of the hook.
     * 
     * @return the Y coordinate
     */
    public double getY() {
        return y;
    }

    /**
     * Sets the dropping speed of the hook.
     * Typically invoked when purchasing upgrades from the shop.
     * 
     * @param dropSpeed the new descending speed in pixels per second
     */
    public void setDropSpeed(final double dropSpeed) {
        this.dropSpeed = dropSpeed;
    }

    /**
     * Sets the reeling speed of the hook.
     * Typically invoked when purchasing upgrades from the shop.
     * 
     * @param reelSpeed the new ascending speed in pixels per second
     */
    public void setReelSpeed(final double reelSpeed) {
        this.reelSpeed = reelSpeed;
    }
}
