package it.unibo.hookmaster.view;

import javafx.scene.paint.Color;
import it.unibo.hookmaster.controller.phase.game.GameInputHandler;
import it.unibo.hookmaster.view.snapshot.MinigameSnapshot;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Represents the mini game view.
 */
public final class MinigameView extends StackPane
        implements View<MinigameSnapshot, GameInputHandler> {

    private final StackPane gameView;

    private final Rectangle backgroundBar;
    private final Rectangle targetZone;
    private final Rectangle indicator;

    public MinigameView(final StackPane gameView) {
        this.gameView = gameView;

        this.backgroundBar = new Rectangle(500, 40, Color.ALICEBLUE);
        this.targetZone = new Rectangle(0, 40.0, Color.GREEN);
        this.indicator = new Rectangle(10, 60, Color.RED);
        this.indicator.setY(-10);

        this.getChildren().addAll(backgroundBar, targetZone, indicator);

        this.setFocusTraversable(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select() {
        this.gameView.getChildren().add(this);
        Platform.runLater(this::requestFocus);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(MinigameSnapshot snapshot) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(GameInputHandler inputHandler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInputHandler'");
    }
}
