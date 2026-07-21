package it.unibo.hookmaster.testutil;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishing.Catchable;

/**
 * Minimal, fully controllable test implementig both Catchable and Collidable.
 */
public final class FakeCatchable implements Catchable, Collidable {

    private static final double DEFAULT_WEIGHT = 1.0;

    private final int economicValue;
    private final double catchDifficulty;
    private final String name;
    private final double weight;

    /**
     * Constructs a fake fish with the given proprieties.
     * 
     * @param economicValue     value returned by getEconomicValue()
     * @param catchDifficulty   value returned by getCatchDifficulty()
     * @param name              value returned by getName()
     * @param weight            value returned bt getWeight()
     */
    public FakeCatchable(final int economicValue, final double catchDifficulty, final String name, final double weight) {
        this.economicValue = economicValue;
        this.catchDifficulty = catchDifficulty;
        this.name = name;
        this.weight = weight;
    }

    /**
     * Constructs a fake fish with the given proprieties and a default always catchable weight.
     * 
     * @param economicValue     value returned by getEconomicValue()
     * @param catchDifficulty   value returned by getCatchDifficulty()
     * @param name              value returned by getName()
     */
    public FakeCatchable(final int economicValue, final double catchDifficulty, final String name) {
        this(economicValue, catchDifficulty, name, DEFAULT_WEIGHT);
    }

    /**
     * Constructs a fake fish with default values, useful when only the
     * identity ot the fish matters to the test, not its attributes.
     */
    public FakeCatchable() {
        this(10, 0.5, "TestFish", DEFAULT_WEIGHT);
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
    public double getWeight() {
        return weight;
    }

    @Override
    public CollisionArea getCollisionArea() {
        return new CollisionAreaRectangle(0, 0, 0, 0);
    }

    @Override
    public boolean onCollision(final Collidable other) {
        //Intentionally empty
        return false;
    }
}
