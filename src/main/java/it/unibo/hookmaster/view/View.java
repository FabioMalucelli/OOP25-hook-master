package it.unibo.hookmaster.view;

/**
 * View interface that every view in the game implements.
 * 
 * @param <S> type of the data snapshot used by the view
 * @param <T> type of input handler used by the view to interact with the controller.
 */
public interface View<S, T> {

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

    void setInputHandler(T inputHandler);
}
