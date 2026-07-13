package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * Linear upgrade value strategy.
 */
public final class LinearUpgradeValueStrategy implements UpgradeValueStrategy {

    private final int base;
    private final double step;

    /**
     * Creates a new linear upgrade value strategy with the provieded base and step.
     * 
     * @param base initial upgrade value
     * @param step upgrade value increment
     */
    public LinearUpgradeValueStrategy(final int base, final double step) {
        this.base = base;
        this.step = step;
    }

    @Override
    public double valueForLevel(final int level) {
        return base + step * (level - 1);
    }

    @Override
    public int costForLevel(final int level) {
        return base + (int) Math.round(step) * (level - 1);
    }
}
