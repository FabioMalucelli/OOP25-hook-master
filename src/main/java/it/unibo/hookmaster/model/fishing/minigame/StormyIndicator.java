package it.unibo.hookmaster.model.fishing.minigame;

/**
 * Strategy Pattern - concrete: the indicator moves at varying speed, simulating rough weather conditions.
 * The speed constantly varies around base speed, so the indicator accelerates and decelerates unpredictably
 * making the QTE harder.
 * 
 * 
 * <p>Used by the weather system to replace the OscillantingIndicator
 * at runtime when a storm is active.</p>
 */
public final class StormyIndicator implements IndicatorStrategy {

    private static final double MIN = 0.0;
    private static final double MAX = 1.0;
    //How much the speed can deviate from the base, as a multiplier.
    private static final double TURBULENCE = 0.6;

    private final double baseSpeed;
    private double position;
    private boolean movingForward;
    private double elapsed;

    /**
     * Constructs the stormy indicator.
     * 
     * @param baseSpeed the average bar-fractions per second(should be harder than the normal strategy, to feel harder)
     */
    public StormyIndicator(final double baseSpeed) {
        this.baseSpeed = baseSpeed;
        this.position = MIN;
        this.movingForward = true;
        this.elapsed = 0.0;
    }

    @Override
    public void update(final double deltaTime) {
        elapsed += deltaTime;
        //Speed oscillates: baseSpeed +/- TURBULANCE * baseSpeed
        final double currentSpeed = baseSpeed * (1.0 + TURBULENCE * Math.sin(elapsed * 3.5));
        final double delta = currentSpeed * deltaTime;
        if (movingForward) {
            position += delta;
            if (position >= MAX) {
                position = MAX;
                movingForward = false;
            }
        } else {
            position -= delta;
            if (position <= MIN) {
                position = MIN;
                movingForward = true;
            }
        }
    }

    @Override
    public double getPosition() {
        return position;
    }
}
