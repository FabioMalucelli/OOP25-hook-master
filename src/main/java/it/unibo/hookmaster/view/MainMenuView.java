package it.unibo.hookmaster.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Main menu view.
 */
public final class MainMenuView extends VBox implements View {

    private final Scene scene;
    private final double width;
    private final double height;

    /**
     * Builder for the main menu view.
     * 
     * @param scene scene
     * @param width view width
     * @param height view height
     */
    public MainMenuView(final Scene scene, final double width, final double height) {
        this.scene = scene;
        this.width = width;
        this.height = height;
    }

    @Override
    public void select() {
        scene.setRoot(this);
    }

    @Override
    public void render(Object snapshot) {
        setPrefSize(width, height);
        setSpacing(15);
        setAlignment(Pos.CENTER);

        ImageView logo =
                new ImageView(new Image(MainMenuView.class.getResourceAsStream("/logo.png"), 550, 0, true, true));
        Button btnPlay = new Button("Play");
        btnPlay.setTextFill(Color.WHITE);
        btnPlay.setFont(Font.font("", FontWeight.BOLD, 18));
        btnPlay.setPadding(new Insets(14, 26,
                14, 26));
        btnPlay.setBackground(new Background(
                new BackgroundFill(Color.web("#1d5f9e"), CornerRadii.EMPTY, Insets.EMPTY)));
        Button btnLoad = new Button("Load");
        btnLoad.setTextFill(Color.WHITE);
        btnLoad.setFont(Font.font("", FontWeight.BOLD, 18));
        btnLoad.setPadding(new Insets(14, 26,
                14, 26));
        btnLoad.setBackground(new Background(
                new BackgroundFill(Color.web("#1d5f9e"), CornerRadii.EMPTY, Insets.EMPTY)));
        Button btnExit = new Button("Exit");
        btnExit.setTextFill(Color.WHITE);
        btnExit.setFont(Font.font("", FontWeight.BOLD, 18));
        btnExit.setPadding(new Insets(14, 26,
                14, 26));
        btnExit.setBackground(new Background(
                new BackgroundFill(Color.web("#1d5f9e"), CornerRadii.EMPTY, Insets.EMPTY)));

        getChildren().addAll(logo, btnPlay, btnLoad, btnExit);
    }
}
