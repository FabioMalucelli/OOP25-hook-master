package it.unibo.hookmaster.view;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import it.unibo.hookmaster.JFXApp;
import it.unibo.hookmaster.controller.phase.game.GameInputHandler;
import it.unibo.hookmaster.model.collision.CollisionAreaCircle;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishing.hook.Hook;
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

    private static final String FISHES_IMAGES_PATH = "/fishes/";

    private final Scene scene;
    private final Canvas mapCanvas;
    private final Canvas fishesCanvas;
    private final GraphicsContext fishesGc;
    private final Label coinsLabel;

    private final Map<String, Image> imagesCache = new HashMap<>();
    private final double offset;
    private GameInputHandler inputHandler;

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

        this.offset = JFXApp.SKY_RATIO * fishesCanvas.getHeight();

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
        fishesGc.clearRect(0, 0, fishesCanvas.getWidth(), fishesCanvas.getHeight());

        for (final Fish fish : snapshot.fishes()) {
            final String path = FISHES_IMAGES_PATH
                    + fish.getType().name().toLowerCase(Locale.getDefault()) + ".png";
            final Image image = imagesCache.computeIfAbsent(path,
                    i -> new Image(path, fishesCanvas.getWidth(), 0, true, true));
            final CollisionAreaRectangle collisionArea = fish.getCollisionArea();
            fishesGc.save();
            fishesGc.translate(
                    fish.getDirection() == 1 ? fish.getX() : fish.getX() + collisionArea.getWidth(),
                    fish.getY() + offset);
            fishesGc.scale(fish.getDirection(), 1);
            fishesGc.drawImage(image, 0, 0, collisionArea.getWidth(), collisionArea.getHeight());
            fishesGc.restore();
        }

        final Hook hook = snapshot.hook();
        final CollisionAreaCircle collisionArea = hook.getCollisionArea();
        final double size = collisionArea.getRadius() * 2;

        final Image boatImage = imagesCache.computeIfAbsent("/boat.png", i -> new Image("/boat.png",
                fishesCanvas.getWidth(), fishesCanvas.getHeight(), true, true));
        final double boatWidth = fishesCanvas.getWidth() * 0.1;
        final double boatHeight = boatWidth / (boatImage.getWidth() / boatImage.getHeight());
        fishesGc.drawImage(boatImage, hook.getX() - (boatWidth / 2), offset - boatHeight, boatWidth,
                boatHeight);

        fishesGc.setLineWidth(2.5);
        fishesGc.strokeLine(hook.getX(), offset - (boatHeight / 2), hook.getX() + (size / 2),
                hook.getY() + offset + (size / 2));

        final Image hookImage = imagesCache.computeIfAbsent("/hook.png", i -> new Image("/hook.png",
                fishesCanvas.getWidth(), fishesCanvas.getHeight(), true, true));
        fishesGc.drawImage(hookImage, hook.getX(), hook.getY() + offset, size, size);

        coinsLabel.setText(snapshot.coins() + " C");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final GameInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
