package it.unibo.hookmaster.view;

/**
 * View interface that every view in the game implements.
 * 
 * @param <Snapshot> type of the data snapshot used by the view
 */
public interface View<Snapshot> {

    /**
     * Set this view as the scene root.
     */
    void select();

    /**
     * Renders the view.
     * 
     * @param snapshot data snapshot needed by the view
     */
    void render(Snapshot snapshot);
}
