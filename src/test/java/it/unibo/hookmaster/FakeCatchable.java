package it.unibo.hookmaster;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishing.Catchable;

public final class FakeCatchable implements Catchable, Collidable {
    
    private final int economicValue;
    private final double catchDifficulty;
    private final String name;
    
    /**
     * Constructs a fake fish with the given proprieties.
     * 
     * @param economicValue     value returned by getEconomicValue()
     * @param catchDifficulty   value returned by getCatchDifficulty()
     * @param name              value returned by getName()
     */
    public FakeCatchable(final int economicValue, final double catchDifficulty, final String name) {
        this.economicValue = economicValue;
        this.catchDifficulty = catchDifficulty;
        this.name = name;
    }

    /**
     * Constructs a fake fish with default values, useful when only the
     * identity ot the fish matters to the test, not its attributes.
     */
    public FakeCatchable() {
        this(10, 0.5, "TestFish");
    }

    @Override
    public int getEconomicValue() {
        return economicValue;
    }

    @Override
    public double getCatchDifficulty() {
        return catchDifficulty;
    }

    @Override 
    public String getName() {
        return name;
    }

    @Override
    public CollisionArea getCollisionArea() {
        return new CollisionAreaRectangle(0, 0, 0, 0);
    }

    @Override
    public void onCollision(final Collidable other) {
        //Intentionally empty : this double is only the target of collisions in tests, it never need to react to one itself.
    }
}
