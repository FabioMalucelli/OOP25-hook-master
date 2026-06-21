package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * Linear upgrade value strategy.
 */
public final class UpgradeValueLinear implements UpgradeValueStrategy {

    private final double base;
    private final double step;

    /**
     * Creates a new linear upgrade value strategy with the provieded base and step.
     * 
     * @param base initial upgrade value
     * @param step upgrade value increment
     */
    public UpgradeValueLinear(final double base, final double step) {
        this.base = base;
        this.step = step;
    }

    @Override
    public double calcValue(final int level) {
        return base + step * (level - 1);
    }

}
