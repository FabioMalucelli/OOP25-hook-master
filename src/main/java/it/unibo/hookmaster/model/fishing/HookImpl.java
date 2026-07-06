package it.unibo.hookmaster.model.fishing;

/**
 * Model of the Hook.
 */
public final class HookImpl implements Hook {
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
    public HookImpl(final double startX, final double startY, final double dropSpeed, final double reelSpeed, final double maxDepth) {
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
                
                break;
        }
    }

    @Override
    public boolean cast() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cast'");
    }

    @Override
    public boolean reelIn() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reelIn'");
    }

    @Override
    public void hookFish(Catchable fish) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'hookFish'");
    }

    @Override
    public void completeMinigame(boolean success) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'completeMinigame'");
    }

    @Override
    public void clearHookedFish() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearHookedFish'");
    }

    @Override
    public HookState getCurrentState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentState'");
    }

    @Override
    public Catchable getHookedFish() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getHookedFish'");
    }

    @Override
    public double getX() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getX'");
    }

    @Override
    public double getY() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getY'");
    }

    @Override
    public void setDropSpeed(double dropSpeed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDropSpeed'");
    }

    @Override
    public void setReelSpeed(double reelSpeed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setReelSpeed'");
    }
}
