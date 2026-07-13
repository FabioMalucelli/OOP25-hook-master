package it.unibo.hookmaster;

import it.unibo.hookmaster.controller.ShopController;
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
    private final Scene scene = new Scene(new Label("Loading..."));

    private final ShopController controller = new ShopController(bounds.getWidth(), bounds.getHeight());

    @Override
    public void start(final Stage primaryStage) throws Exception {
        controller.showShop(scene);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }
}
