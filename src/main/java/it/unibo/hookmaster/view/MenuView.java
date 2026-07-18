package it.unibo.hookmaster.view;

import it.unibo.hookmaster.controller.phase.menu.MenuInputHandler;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main menu view.
 */
public final class MenuView extends VBox implements View<MenuSnapshot, MenuInputHandler> {

    private static final Color BACKGROUND_COLOR = Color.web("#64aafa");
    private static final Color BUTTON_COLOR = Color.web("#f5bc46");
    private static final Color BUTTON_HOVER_COLOR = Color.web("#f8c663");
    private static final Color TEXT_COLOR = Color.web("#8f480a");
    private static final Color BORDER = Color.web("#4d1b0d");

    private static final String LOGO_PATH = "/logo.png";
    private static final double LOGO_WIDTH_RATIO = 0.3;

    private static final double BUTTON_FONT_SIZE = 18;
    private static final double BUTTON_PADDING_VERTICAL = 14;
    private static final double BUTTONS_SPACING_RATIO = 0.01;
    private static final double BUTTONS_WIDTH_RATIO = 0.2;

    private static final double CORNER_RADII = 7;
    private static final double BORDER_WIDTH = 2;

    private final Scene scene;
    private final Button btnStart;

    private MenuInputHandler inputHandler;

    /**
     * Builder for the main menu view.
     * 
     * @param scene scene
     */
    public MenuView(final Scene scene) {
        this.scene = scene;

        setPrefSize(scene.getWidth(), scene.getHeight());
        setAlignment(Pos.CENTER);
        setSpacing(scene.getHeight() * BUTTONS_SPACING_RATIO);
        setBackground(new Background(
                new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        final ImageView logo = new ImageView(
                new Image(LOGO_PATH, scene.getWidth() * LOGO_WIDTH_RATIO, 0, true, true));

        final VBox btnList = new VBox(scene.getHeight() * BUTTONS_SPACING_RATIO);
        this.btnStart = buildButton("Start game");
        final Button btnLoad = buildButton("Load save");
        final Button btnExit = buildButton("Exit game");

        btnExit.setOnAction(e -> inputHandler.pressExitButton());

        btnList.setMaxWidth(scene.getWidth() * BUTTONS_WIDTH_RATIO);
        btnList.setAlignment(Pos.TOP_CENTER);
        btnList.setFillWidth(true);
        btnList.getChildren().addAll(btnStart, btnLoad, btnExit);

        getChildren().addAll(logo, btnList);
    }

    @Override
    public void select() {
        scene.setRoot(this);
    }

    @Override
    public void update(final MenuSnapshot snapshot) {
        this.btnStart.setText(snapshot.inGame() ? "Resume game" : "Start game");
    }

    private Button buildButton(final String text) {
        final Button btn = new Button(text);
        btn.setTextFill(TEXT_COLOR);
        btn.setFont(Font.font("", FontWeight.NORMAL, BUTTON_FONT_SIZE));
        btn.setPadding(new Insets(BUTTON_PADDING_VERTICAL, 0, BUTTON_PADDING_VERTICAL, 0));
        btn.setMaxWidth(Double.MAX_VALUE);

        final CornerRadii radii = new CornerRadii(CORNER_RADII);
        final Background background =
                new Background(new BackgroundFill(BUTTON_COLOR, radii, Insets.EMPTY));
        final Background backgroundHover =
                new Background(new BackgroundFill(BUTTON_HOVER_COLOR, radii, Insets.EMPTY));
        btn.setBackground(background);

        btn.setBorder(new Border(new BorderStroke(BORDER, BorderStrokeStyle.SOLID, radii,
                new BorderWidths(BORDER_WIDTH))));

        btn.setOnMouseEntered(e -> btn.setBackground(backgroundHover));
        btn.setOnMouseExited(e -> btn.setBackground(background));

        btn.setCursor(Cursor.HAND);

        return btn;
    }

    @Override
    public void setInputHandler(MenuInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
