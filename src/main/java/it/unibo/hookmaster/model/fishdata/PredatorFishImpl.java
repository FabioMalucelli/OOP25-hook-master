package it.unibo.hookmaster.model.fishdata;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.collision.Collidable;

/**
 * Implementation of a predator fish.
 */
public final class PredatorFishImpl extends AbstractFishDecorator {
    private final FishManager fishManager;

    /**
     * Creates a new predator fish.
     *
     * @param fishManager the fish manager, used to remove dead fish
     * @param fish the underlying fish instance
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP",
            justification = "The fish is updated by the fish manager and should be exposed by the predator fish.")
    public PredatorFishImpl(final FishManager fishManager, final Fish fish) {
        super(fish);
        this.fishManager = fishManager;
    }

    /**
     * Checks for collision with another collidable object. If the other object is a fish and is
     * smaller than this predator fish, it will be removed from the simulation.
     */
    @Override
    public boolean onCollision(final Collidable other) {
        boolean fishRemoved = false;
        if (other instanceof Fish) {
            final Fish otherFish = (Fish) other;
            if (otherFish.getWeight() < getWeight()
                    && otherFish.getDirection() != getDirection()) {
                this.fishManager.removeDeadFish(otherFish);
                fishRemoved = true;
            }
        }
        return super.onCollision(other) || fishRemoved;
    }
}
