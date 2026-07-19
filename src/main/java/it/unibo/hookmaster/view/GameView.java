package it.unibo.hookmaster.view;

import java.util.Locale;
import it.unibo.hookmaster.controller.phase.game.GameInputHandler;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.view.snapshot.GameSnapshot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the game view.
 */
public final class GameView extends StackPane implements View<GameSnapshot, GameInputHandler> {

    private static final Color DARK_BACKGROUND_COLOR = Color.web("#4d1b0d");
    private static final Color BUTTON_COLOR = Color.web("#4d1b0d");
    private static final Color BUTTON_HOVER_COLOR = Color.web("#f5bc46");
    private static final Color TEXT_COLOR = Color.web("#ffffff");
    private static final Color COINS_TEXT_COLOR = Color.web("#f5bc46");

    private static final double BUTTON_FONT_SIZE = 18;
    private static final double BUTTON_PADDING = 14;
    private static final double SPACING_RATIO = 0.01;
    private static final double CORNER_RADII = 7;
    private static final double COINS_LABEL_FONT_SIZE = 18;
    private static final double COINS_LABEL_PADDING = 15;
    private static final double HUD_PADDING = 15;

    private static final double OFFSET_TOP = 20;

    private final Scene scene;
    private final Canvas mapCanvas;
    private final Canvas fishesCanvas;
    private final GraphicsContext fishesGc;
    private final Label coinsLabel;

    /**
     * Contructs the main game view.
     * 
     * @param scene the main game scene.
     */
    public GameView(final Scene scene) {
        this.scene = scene;
        this.mapCanvas = new Canvas(scene.getWidth(), scene.getHeight());
        this.fishesCanvas = new Canvas(scene.getWidth(), scene.getHeight());
        this.fishesGc = fishesCanvas.getGraphicsContext2D();

        coinsLabel = new Label("0 C");
        coinsLabel.setTextFill(COINS_TEXT_COLOR);
        coinsLabel.setFont(Font.font("", FontWeight.BOLD, COINS_LABEL_FONT_SIZE));
        coinsLabel.setBackground(new Background(new BackgroundFill(DARK_BACKGROUND_COLOR,
                new CornerRadii(CORNER_RADII), Insets.EMPTY)));
        coinsLabel.setPadding(new Insets(COINS_LABEL_PADDING));

        final Button shopButton = new Button("Open shop");
        shopButton.setTextFill(TEXT_COLOR);
        shopButton.setFont(Font.font("", FontWeight.NORMAL, BUTTON_FONT_SIZE));
        shopButton.setPadding(new Insets(BUTTON_PADDING));
        shopButton.setMaxWidth(Double.MAX_VALUE);

        final CornerRadii radii = new CornerRadii(CORNER_RADII);
        final Background background =
                new Background(new BackgroundFill(BUTTON_COLOR, radii, Insets.EMPTY));
        final Background backgroundHover =
                new Background(new BackgroundFill(BUTTON_HOVER_COLOR, radii, Insets.EMPTY));
        shopButton.setBackground(background);

        shopButton.setOnMouseEntered(e -> shopButton.setBackground(backgroundHover));
        shopButton.setOnMouseExited(e -> shopButton.setBackground(background));

        shopButton.setCursor(Cursor.HAND);

        final HBox hud = new HBox(scene.getWidth() * SPACING_RATIO);
        hud.setAlignment(Pos.CENTER_RIGHT);
        hud.setPadding(new Insets(HUD_PADDING));
        hud.setMaxSize(scene.getWidth(), scene.getHeight() * 0.1);
        hud.getChildren().addAll(coinsLabel, shopButton);

        setAlignment(hud, Pos.TOP_RIGHT);

        final GraphicsContext gc = mapCanvas.getGraphicsContext2D();
        gc.drawImage(loadImage("/map/background.png", scene.getWidth(), scene.getHeight()), 0, 0);
        gc.drawImage(loadImage("/map/sand.png", scene.getWidth(), scene.getHeight()), 0, 0);

        this.getChildren().addAll(mapCanvas, fishesCanvas, hud);
    }

    private Image loadImage(final String path, final double width, final double height) {
        return new Image(GameView.class.getResourceAsStream(path), width, height, true, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select() {
        this.scene.setRoot(this);
        if (this.getChildren().size() == 4) {
            this.getChildren().remove(this.getChildren().size() - 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameSnapshot snapshot) {
        // Tieni conto del offest sopra e sotto
        fishesGc.clearRect(0, 0, fishesCanvas.getWidth(), fishesCanvas.getHeight());

        for (final Fish fish : snapshot.fishes()) {
            fishesGc.drawImage(
                    loadImage("/fishes/" + fish.getType().name().toLowerCase(Locale.getDefault()) + ".png", 50, 0),
                    fish.getX(), fish.getY() + OFFSET_TOP);
        }

        /*fishesGc.drawImage(loadImage("/boat.png", 300, 150), snapshot.boat().getX(),
                snapshot.boat().getY());

        fishesGc.setLineWidth(2.0);
        fishesGc.strokeLine(snapshot.boat().getX() + (300 / 2), snapshot.boat().getY() + (150 / 2),
                snapshot.hook().getX() + (50 / 2), snapshot.hook().getY() + (50 / 2));

        fishesGc.drawImage(loadImage("/hook.png", 50, 50), snapshot.hook().getX(),
                snapshot.hook().getY());*/

        coinsLabel.setText(snapshot.coins() + " C");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final GameInputHandler inputHandler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInputHandler'");
    }
}
