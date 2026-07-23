package it.unibo.hookmaster.view;

/**
 * The base interface implemented by all views in the game.
 * 
 * @param <S> the type of the data snapshot used by the view.
 * @param <I> the type of input handler used by the view to interact with the controller.
 */
public interface View<S, I> {

    /**
     * Set this view as the active scene root.
     */
    void select();

    /**
     * Updates the views state using the provided data snapshot.
     * 
     * @param snapshot the data snapshot containing the state to be displayed.
     */
    void update(S snapshot);

    /**
     * Sets the input handler for this view.
     * 
     * @param inputHandler the input handler for this view.
     */
    void setInputHandler(I inputHandler);
}
