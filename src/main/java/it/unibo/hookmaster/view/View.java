package it.unibo.hookmaster.view;

/**
 * View interface that every view in the game implements.
 * 
 * @param <S> type of the data snapshot used by the view
 */
public interface View<S> {

    /**
     * Set this view as the scene root.
     */
    void select();

    /**
     * Updates the view.
     * 
     * @param snapshot data snapshot needed by the view
     */
    void update(S snapshot);
}
