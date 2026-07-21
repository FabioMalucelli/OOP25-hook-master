package it.unibo.hookmaster.model.save;

/**
 * Represents a type that can create and restore mementos of its state.
 * In other words, a type that can be saved and restored.
 * 
 * @param <M> the type of memento that this originator can create and restore
 */
public interface Originator<M extends Memento> {
    M createMemento();

    void restoreFromMemento(M memento);
}
