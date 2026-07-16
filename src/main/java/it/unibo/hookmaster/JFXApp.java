package it.unibo.hookmaster;

import it.unibo.hookmaster.controller.GameControllerImpl;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import it.unibo.hookmaster.view.MenuView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX applicaton.
 */
public final class JFXApp extends Application {

    private final Rectangle2D bounds = Screen.getPrimary().getBounds();
    private final Scene scene = new Scene(new Label("Loading..."), bounds.getWidth(), bounds.getHeight());

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();

        View<MenuSnapshot> menuView = new MenuView(scene);
        new GameControllerImpl(menuView).run();
    }
}
