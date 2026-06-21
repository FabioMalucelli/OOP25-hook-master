package it.unibo.hookmaster;

import javafx.application.Application;

/**
 * Main application entry-point's class.
 */
public final class App {
    private App() { }

    /**
     * Main application entry-point.
     * 
     * @param args unused
     */
    public static void main(final String[] args) {
        Application.launch(JFXApp.class);
    }
}
