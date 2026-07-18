package it.unibo.hookmaster.controller.phase;

public abstract class PhaseController {
    private PhaseGraph phaseGraph;

    abstract void select();
    abstract void tick(long deltaTime);

    void setGraph(final PhaseGraph phaseGraph) {
        this.phaseGraph = phaseGraph;
    }

    protected PhaseGraph getGraph() {
        return this.phaseGraph;
    }
}
