package it.unibo.hookmaster;

import it.unibo.hookmaster.controller.GameControllerImpl;
import it.unibo.hookmaster.controller.phase.menu.MenuInputHandler;
import it.unibo.hookmaster.controller.phase.minigame.MinigameInputHandler;
import it.unibo.hookmaster.controller.phase.shop.ShopInputHandler;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.model.GameWorldImpl;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import it.unibo.hookmaster.view.snapshot.MinigameSnapshot;
import it.unibo.hookmaster.view.snapshot.ShopSnapshot;
import it.unibo.hookmaster.view.GameView;
import it.unibo.hookmaster.view.MenuView;
import it.unibo.hookmaster.view.ShopView;
import it.unibo.hookmaster.view.MinigameView;
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

    public static final double SKY_RATIO = 64.0 / 360.0;
    private static final double SEABED_RATIO = 14.0 / 360.0;

    private final Rectangle2D bounds = Screen.getPrimary().getBounds();
    private final Scene scene =
            new Scene(new Label("Loading..."), bounds.getWidth(), bounds.getHeight());

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();

        GameWorld gameWorld = new GameWorldImpl(bounds.getWidth(),
                (bounds.getHeight() * (1 - SKY_RATIO)) - (bounds.getHeight() * SEABED_RATIO));
        View<MenuSnapshot, MenuInputHandler> menuView = new MenuView(scene);
        GameView gameView = new GameView(scene);
        View<MinigameSnapshot, MinigameInputHandler> minigameView = new MinigameView(gameView);
        View<ShopSnapshot, ShopInputHandler> shopView = new ShopView(scene);
        new GameControllerImpl(gameWorld, menuView, gameView, minigameView, shopView).run();
    }
}
