package it.unibo.hookmaster.controller.phase;

import java.util.HashMap;
import java.util.Map;

public final class PhaseGraph {
    private PhaseController runningPhase = null;

    private final Map<Phase, PhaseController> phaseControllers = new HashMap<>();

    public PhaseController getRunningPhase() {
        return runningPhase;
    }

    public void registerPhase(Phase phase, PhaseController controller) {
        if (this.phaseControllers.containsKey(phase))
            throw new IllegalStateException("Controller already registered for this phase.");
        controller.setGraph(this);
        this.phaseControllers.put(phase, controller);
    }

    public void selectPhase(Phase phase) {
        if (!this.phaseControllers.containsKey(phase))
            throw new IllegalStateException("Cannot switch to phase " + phase.toString() + " as no controller has been registered for it.");
        this.runningPhase = this.phaseControllers.get(phase);
        this.runningPhase.select();
    }
    
    public void tick(long deltaTime) {
        this.runningPhase.tick(deltaTime);
    }
}
