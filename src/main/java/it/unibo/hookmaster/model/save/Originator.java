package it.unibo.hookmaster.model.save;

/**
 * Represents a type that can create and restore mementos of its state.
 * In other words, a type that can be saved and restored.
 * 
 * @param <M> the type of memento that this originator can create and restore
 */
public interface Originator<M extends Memento> {

    /**
     * Creates a memento of the current state of this originator.
     * 
     * @return a memento of the current state of this originator
     */
    M createMemento();

    /**
     * Restores the state of this originator from the given memento.
     * 
     * @param memento the memento to restore the state from
     */
    void restoreFromMemento(M memento);
}
