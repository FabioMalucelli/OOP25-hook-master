package it.unibo.hookmaster.controller.phase;

/**
 * Abstract class representing a controller for a specific phase of the game.
 * Each phase controller is responsible for handling the user input and
 * updating the view for its corresponding phase.
 */
public abstract class AbstractPhaseController {
    private PhaseGraph phaseGraph;

    /**
     * Called when the phase is selected.
     */
    protected abstract void select();

    /**
     * Called on every tick of the game loop
     * while this phase is selected.
     * 
     * @param deltaTime the amount of milliseconds of which the
     *     game state should advance.
     */
    protected abstract void tick(long deltaTime);

    /**
     * Sets the phase graph to which this controller belongs.
     * 
     * @param graph the phase graph.
     */
    void setGraph(final PhaseGraph graph) {
        this.phaseGraph = graph;
    }

    /**
     * Returns the phase graph to which this controller belongs.
     * 
     * @return the phase graph.
     */
    protected PhaseGraph getGraph() {
        return this.phaseGraph;
    }
}
