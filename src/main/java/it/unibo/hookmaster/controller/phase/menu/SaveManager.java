package it.unibo.hookmaster.controller.phase.menu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import it.unibo.hookmaster.model.save.Memento;

/**
 * Utility class for saving and loading mementos to and from files.
 */
public final class SaveManager {

    /**
     * Private constructor to prevent instantiation of this class.
     */
    private SaveManager() { }

    /**
     * Saves a memento to a file.
     * 
     * @param memento the memento to be saved.
     * @param saveTo the file to save the memento to.
     * @throws IOException if an I/O error occurs while saving the memento.
     */
    public static void save(final Memento memento, final File saveTo) throws IOException {
        try (var fos = new FileOutputStream(saveTo)) {
            try (var oos = new ObjectOutputStream(fos)) {
                oos.writeObject(memento);
            }
        }
    }

    /**
     * Loads a memento from a file.
     *
     * @param loadFrom the file to load the memento from.
     * @return the loaded memento.
     * @throws IOException if an I/O error occurs while loading the memento.
     * @throws ClassNotFoundException if the class of a serialized object cannot be found.
     * @throws ClassCastException if the loaded object cannot be cast to a Memento.
     */
    public static Memento load(final File loadFrom)
            throws IOException, ClassNotFoundException {
        try (var fis = new java.io.FileInputStream(loadFrom)) {
            try (var ois = new java.io.ObjectInputStream(fis)) {
                return (Memento) ois.readObject();
            }
        }
    }
}
