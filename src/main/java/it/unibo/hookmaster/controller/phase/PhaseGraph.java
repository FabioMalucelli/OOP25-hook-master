package it.unibo.hookmaster.controller.phase;

import java.util.EnumMap;
import java.util.Map;

/**
 * Class representing the sequence of phases in the game.
 * It allows to register phase controllers and switch between them.
 * It also allows to tick the currently running phase controller.
 */
public final class PhaseGraph {
    private AbstractPhaseController runningPhase;
    private final Map<Phase, AbstractPhaseController> phaseControllers = new EnumMap<>(Phase.class);

    /**
     * Registers a phase controller for a given phase.
     * 
     * @param phase the phase to register the controller for
     * @param controller the controller to register
     * @throws IllegalStateException if a controller has
     *     already been registered for the given phase
     */
    public void registerPhase(final Phase phase, final AbstractPhaseController controller) {
        if (this.phaseControllers.containsKey(phase)) {
            throw new IllegalStateException("Controller already registered for this phase.");
        }
        controller.setGraph(this);
        this.phaseControllers.put(phase, controller);
    }

    /**
     * Switches the running phase.
     * 
     * @param phase the phase to switch to
     */
    public void selectPhase(final Phase phase) {
        if (!this.phaseControllers.containsKey(phase)) {
            throw new IllegalStateException(
                "Cannot switch to phase "
                + phase.toString()
                + " as no controller has been registered for it."
            );
        }
        this.runningPhase = this.phaseControllers.get(phase);
        this.runningPhase.select();
    }

    /**
     * Ticks the running phase controller.
     * Just a convenient method to avoid having to get the
     * running phase controller and then call tick on it.
     * 
     * @param deltaTime the amount of milliseconds of which the
     *     game state should advance.
     */
    public void tick(final long deltaTime) {
        if (this.runningPhase == null) {
            throw new IllegalStateException("No phase is currently running.");
        }
        this.runningPhase.tick(deltaTime);
    }
}
