package it.unibo.hookmaster.view;

import java.io.File;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.menu.MenuInputHandler;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;

/**
 * Represents the menu view.
 */
public final class MenuView extends VBox implements View<MenuSnapshot, MenuInputHandler> {

    private static final Color BACKGROUND_COLOR = Color.web("#3971b1");
    private static final Color BUTTON_COLOR = Color.web("#4d1b0d");
    private static final Color BUTTON_HOVER_COLOR = Color.web("#f5bc46");
    private static final Color TEXT_COLOR = Color.web("#ffffff");

    private static final String LOGO_PATH = "/logo.png";
    private static final double LOGO_WIDTH_RATIO = 0.3;

    private static final double BUTTON_FONT_SIZE = 18;
    private static final double BUTTON_PADDING_VERTICAL = 14;
    private static final double BUTTONS_SPACING_RATIO = 0.01;
    private static final double BUTTONS_WIDTH_RATIO = 0.2;
    private static final double CORNER_RADII = 7;

    private final Scene scene;
    private final Button btnStart;

    private MenuInputHandler inputHandler;

    /**
     * Contructs the main menu view.
     * 
     * @param scene the main game scene.
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP",
        justification = "The scene is the main game scene."
    )
    public MenuView(final Scene scene) {
        this.scene = scene;

        this.setAlignment(Pos.CENTER);
        this.setSpacing(scene.getHeight() * BUTTONS_SPACING_RATIO);
        this.setBackground(new Background(
                new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        final ImageView logo = new ImageView(
                new Image(LOGO_PATH, scene.getWidth() * LOGO_WIDTH_RATIO, 0, true, true));

        final VBox btnList = new VBox(scene.getHeight() * BUTTONS_SPACING_RATIO);
        this.btnStart = buildButton("Start game");
        final Button btnLoad = buildButton("Load save");
        final Button btnSave = buildButton("Save game");
        final Button btnExit = buildButton("Exit game");

        btnStart.setOnAction(e -> inputHandler.pressPlayButton());
        btnLoad.setOnAction(e -> {
            final File selectedFile = getSelectedFile("Select a file to load", false);
            if (selectedFile == null) {
                return;
            }
            try {
                inputHandler.pressLoadButton(selectedFile);
            } catch (final IllegalArgumentException ex) {
                new Alert(AlertType.ERROR, ex.getMessage()).showAndWait();
            }
        });
        btnSave.setOnAction(e -> {
            final File selectedFile = getSelectedFile("Select a file to save", true);
            if (selectedFile == null) {
                return;
            }
            inputHandler.pressSaveButton(selectedFile);
        });
        btnExit.setOnAction(e -> inputHandler.pressExitButton());

        btnList.setMaxWidth(scene.getWidth() * BUTTONS_WIDTH_RATIO);
        btnList.setAlignment(Pos.TOP_CENTER);
        btnList.setFillWidth(true);
        btnList.getChildren().addAll(btnStart, btnLoad, btnSave, btnExit);

        this.getChildren().addAll(logo, btnList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void select() {
        this.scene.setRoot(this);
    }

    /**
     * {@inheritDoc}
     */
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

        btn.setOnMouseEntered(e -> btn.setBackground(backgroundHover));
        btn.setOnMouseExited(e -> btn.setBackground(background));

        btn.setCursor(Cursor.HAND);

        return btn;
    }

    private File getSelectedFile(final String title, final boolean save) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return save ? fileChooser.showSaveDialog(this.scene.getWindow()) : fileChooser.showOpenDialog(this.scene.getWindow());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final MenuInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
