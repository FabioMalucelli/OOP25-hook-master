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
import it.unibo.hookmaster.model.weather.Weather;
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
    private final Canvas canvas;
    private final GraphicsContext content;
    private final Label weatherLabel;
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
        this.canvas = new Canvas(scene.getWidth(), scene.getHeight());
        this.content = canvas.getGraphicsContext2D();

        this.offset = JFXApp.SKY_RATIO * canvas.getHeight();

        this.coinsLabel = new Label("0 C");
        this.coinsLabel.setTextFill(COINS_TEXT_COLOR);
        this.coinsLabel.setFont(Font.font("", FontWeight.NORMAL, COINS_LABEL_FONT_SIZE));
        this.coinsLabel.setBackground(new Background(new BackgroundFill(DARK_BACKGROUND_COLOR,
                new CornerRadii(CORNER_RADII), Insets.EMPTY)));
        this.coinsLabel.setPadding(new Insets(COINS_LABEL_PADDING));

        this.weatherLabel = new Label("Weather: ");
        this.weatherLabel.setTextFill(TEXT_COLOR);
        this.weatherLabel.setFont(Font.font("", FontWeight.NORMAL, COINS_LABEL_FONT_SIZE));
        this.weatherLabel.setBackground(new Background(new BackgroundFill(DARK_BACKGROUND_COLOR,
                new CornerRadii(CORNER_RADII), Insets.EMPTY)));
        this.weatherLabel.setPadding(new Insets(COINS_LABEL_PADDING));

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
        hud.getChildren().addAll(coinsLabel, weatherLabel, shopButton);

        setAlignment(hud, Pos.TOP_RIGHT);

        this.getChildren().addAll(canvas, hud);

        this.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case W -> inputHandler.pressW();
                case A -> inputHandler.pressA();
                case S -> inputHandler.pressS();
                case D -> inputHandler.pressD();
                case ESCAPE -> inputHandler.pressEsc();
                default -> {
                }
            }
        });

        this.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case W -> inputHandler.releaseW();
                case A -> inputHandler.releaseA();
                case S -> inputHandler.releaseS();
                case D -> inputHandler.releaseD();
                default -> {
                }
            }
        });

        shopButton.setOnAction(e -> inputHandler.pressShopBtn());
    }

    private void renderMapWithWeather(final Weather weather) {
        content.drawImage(new Image("/map/background.png", canvas.getWidth(), canvas.getHeight(),
                false, true), 0, 0);
        content.drawImage(
                new Image("/map/sand.png", canvas.getWidth(), canvas.getHeight(), false, true), 0,
                0);

        if (weather == Weather.STORMY) {
            content.setFill(Color.web("#13376191"));
            content.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        }
    }

    private void renderFish(final Fish fish, final boolean isDead) {
        final String path = FISHES_IMAGES_PATH
                + fish.getType().name().toLowerCase(Locale.getDefault()) + ".png";
        final Image image = imagesCache.computeIfAbsent(path,
                i -> new Image(path, canvas.getWidth(), 0, true, true));
        final CollisionAreaRectangle collisionArea = fish.getCollisionArea();
        content.save();
        content.translate(
                fish.getDirection() == 1 ? fish.getX() : fish.getX() + collisionArea.getWidth(),
                fish.getY() + offset);
        content.scale(fish.getDirection(), 1);
        if (isDead) {
            content.setFill(Color.RED);
            content.fillRect(0, 0, collisionArea.getWidth(), collisionArea.getHeight());
        }
        content.drawImage(image, 0, 0, collisionArea.getWidth(), collisionArea.getHeight());
        content.restore();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select() {
        this.scene.setRoot(this);
        if (this.getChildren().size() == 3) {
            this.getChildren().remove(this.getChildren().size() - 1);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final GameSnapshot snapshot) {
        content.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        renderMapWithWeather(snapshot.weather());

        for (final Fish fish : snapshot.fishes()) {
            renderFish(fish, false);
        }

        for (final Fish fish : snapshot.deadFishes()) {
            renderFish(fish, true);
        }

        final Hook hook = snapshot.hook();
        final CollisionAreaCircle collisionArea = hook.getCollisionArea();
        final double size = collisionArea.getRadius() * 2;

        final Image boatImage = imagesCache.computeIfAbsent("/boat.png",
                i -> new Image("/boat.png", canvas.getWidth(), canvas.getHeight(), true, true));
        final double boatWidth = canvas.getWidth() * 0.1;
        final double boatHeight = boatWidth / (boatImage.getWidth() / boatImage.getHeight());
        content.drawImage(boatImage, hook.getX() - (boatWidth / 2), offset - boatHeight, boatWidth,
                boatHeight);

        content.setLineWidth(2);
        content.strokeLine(hook.getX(), offset - (boatHeight / 2),
                hook.getX() - collisionArea.getRadius() + (size / 2),
                hook.getY() - collisionArea.getRadius() + offset + (size / 2));

        final Image hookImage = imagesCache.computeIfAbsent("/hook.png",
                i -> new Image("/hook.png", canvas.getWidth(), canvas.getHeight(), true, true));
        content.drawImage(hookImage, hook.getX() - collisionArea.getRadius(),
                hook.getY() - collisionArea.getRadius() + offset, size, size);

        weatherLabel
                .setText("Weather: " + snapshot.weather().name().toLowerCase(Locale.getDefault()));
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
