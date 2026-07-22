package it.unibo.hookmaster.view;

import javafx.scene.paint.Color;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.minigame.MinigameInputHandler;
import it.unibo.hookmaster.view.snapshot.MinigameSnapshot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the mini game view.
 */
public final class MinigameView extends VBox
        implements View<MinigameSnapshot, MinigameInputHandler> {

    private static final Color BACKGROUND_COLOR = Color.web("#3971b1");
    private static final Color INDICATOR_COLOR = Color.web("#d63a3a");
    private static final Color TARGET_COLOR = Color.web("#56c253");
    private static final Color BAR_COLOR = Color.web("#182030");

    private static final double SIZE_RATIO = 0.3;
    private static final double BAR_RATIO = 0.2;
    private static final double INDICATOR_RATIO = 0.02;
    private static final double PADDING = 15;
    private static final double SPACING = 15;
    private static final double FONT_SIZE = 18;
    private static final double CORNER_RADII = 7;

    private final StackPane gameView;

    private final Rectangle backgroundBar;
    private final Rectangle targetZone;
    private final Rectangle indicator;

    private MinigameInputHandler inputHandler;

    /**
     * Contructs the mini game view.
     * 
     * @param scene the scene to which the game view belongs.
     * @param gameView the game view to append this view to.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The game view is the container for the mini game view."
    )
    public MinigameView(final Scene scene, final StackPane gameView) {
        this.gameView = gameView;

        this.setMaxSize(scene.getWidth() * SIZE_RATIO, scene.getHeight() * SIZE_RATIO);
        this.setSpacing(SPACING);
        this.setPadding(new Insets(PADDING));
        this.setAlignment(Pos.CENTER);
        final CornerRadii radii = new CornerRadii(CORNER_RADII);
        this.setBackground(
                new Background(new BackgroundFill(BACKGROUND_COLOR, radii, Insets.EMPTY)));

        final Label infoLabel = new Label("Press SPACE when the indicator is in the green area.");
        infoLabel.setTextFill(Color.WHITE);
        infoLabel.setFont(Font.font("", FontWeight.BOLD, FONT_SIZE));

        final StackPane minigamStackPane = new StackPane();
        this.backgroundBar =
                new Rectangle(this.getMaxWidth(), this.getMaxHeight() * BAR_RATIO, BAR_COLOR);
        this.targetZone = new Rectangle(0, backgroundBar.getHeight(), TARGET_COLOR);
        this.indicator = new Rectangle(backgroundBar.getWidth() * INDICATOR_RATIO,
                backgroundBar.getHeight(), INDICATOR_COLOR);
        minigamStackPane.getChildren().addAll(this.backgroundBar, this.targetZone, this.indicator);

        this.getChildren().addAll(infoLabel, minigamStackPane);

        this.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ESCAPE -> inputHandler.pressEsc();
                case SPACE -> inputHandler.pressSpace();
                default -> {
                }
            }
            e.consume();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select() {
        this.gameView.getChildren().add(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final MinigameSnapshot snapshot) {
        this.requestFocus();
        final double barWidth = backgroundBar.getWidth();
        final double barWidthHalf = barWidth / 2;
        targetZone.setTranslateX(
                barWidth * ((snapshot.targetStartPos() + snapshot.targetEndPos()) / 2)
                        - barWidthHalf);
        targetZone.setWidth(barWidth * (snapshot.targetEndPos() - snapshot.targetStartPos()));
        indicator.setTranslateX((barWidth * snapshot.indicatorPos()) - barWidthHalf);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final MinigameInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
