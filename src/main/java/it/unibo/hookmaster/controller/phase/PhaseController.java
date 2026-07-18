package it.unibo.hookmaster.controller.phase;

public abstract class PhaseController {
    private PhaseGraph phaseGraph;

    protected abstract void select();
    protected abstract void tick(long deltaTime);

    void setGraph(final PhaseGraph phaseGraph) {
        this.phaseGraph = phaseGraph;
    }

    protected PhaseGraph getGraph() {
        return this.phaseGraph;
    }
}
