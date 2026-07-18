package it.unibo.hookmaster.model.upgrade.strategies;

/**
 * {@link UpgradeValueStrategy} implementation that scales values and costs linearly.
 */
public final class LinearUpgradeValueStrategy implements UpgradeValueStrategy {

    private final int base;
    private final double step;

    /**
     * Constructs a linear upgrade strategy with the provieded base and step.
     * 
     * @param base the initial upgrade value and cost at level 1.
     * @param step the amount added for each level increment.
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
