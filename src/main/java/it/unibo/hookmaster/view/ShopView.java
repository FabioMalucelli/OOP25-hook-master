package it.unibo.hookmaster.view;

import it.unibo.hookmaster.controller.phase.shop.ShopInputHandler;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;
import it.unibo.hookmaster.view.snapshot.ShopSnapshot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents the shop view.
 */
public final class ShopView extends BorderPane implements View<ShopSnapshot, ShopInputHandler> {

    private static final Color BACKGROUND_COLOR = Color.web("#3971b1");
    private static final Color DARK_BACKGROUND_COLOR = Color.web("#182030");
    private static final Color BUY_BUTTON_COLOR = Color.web("#1da75b");
    private static final Color BUY_BUTTON_HOVER_COLOR = Color.web("#1fb864");
    private static final Color CLOSE_BUTTON_COLOR = Color.web("#d14532");
    private static final Color CLOSE_BUTTON_HOVER_COLOR = Color.web("#e74d38");
    private static final Color COINS_TEXT_COLOR = Color.web("#f5bc46");
    private static final Color TEXT_COLOR = Color.web("#ffffff");

    private static final double MARGIN_RATIO = 0.2;
    private static final double COINS_BOX_SPACING = 5;
    private static final double CORNER_RADII = 7;
    private static final double COINS_BOX_PADDING = 20;
    private static final double COINS_LABEL_FONT_SIZE = 18;
    private static final double TITLE_FONT_SIZE = 48;
    private static final double UPGRADES_CONTAINER_SPACING = 25;
    private static final double UPGRADES_CONTAINER_PADDING = 40;
    private static final double UPGRADES_CONTAINER_MARGIN_VERTICAL = 30;
    private static final double BUTTON_FONT_SIZE = 18;
    private static final double BUTTON_PADDING_VERTICAL = 14;
    private static final double BUTTON_PREF_WIDTH = 200;
    private static final double UPGRADE_TEXT_SPACING = 5;
    private static final double UPGRADE_TITLE_FONT_SIZE = 20;
    private static final double UPGRADE_DESC_FONT_SIZE = 18;

    private final Scene scene;
    private final Label coinsValueLabel;
    private final VBox upgradesContainer;

    private ShopInputHandler inputHandler;

    /**
     * Contructs the shop view.
     * 
     * @param scene the main game scene.
     */
    public ShopView(final Scene scene) {
        this.scene = scene;

        setBackground(new Background(
                new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(scene.getHeight() * MARGIN_RATIO, scene.getWidth() * MARGIN_RATIO,
                scene.getHeight() * MARGIN_RATIO, scene.getWidth() * MARGIN_RATIO));

        final BorderPane header = new BorderPane();

        final VBox coinsBox = new VBox(COINS_BOX_SPACING);
        coinsBox.setBackground(new Background(new BackgroundFill(DARK_BACKGROUND_COLOR,
                new CornerRadii(CORNER_RADII), Insets.EMPTY)));
        coinsBox.setPadding(new Insets(COINS_BOX_PADDING));

        final Label coinsTitleLabel = new Label("Total Coins: ");
        coinsTitleLabel.setTextFill(COINS_TEXT_COLOR);
        coinsTitleLabel.setFont(Font.font("", FontWeight.BOLD, COINS_LABEL_FONT_SIZE));

        coinsValueLabel = new Label("0 C");
        coinsValueLabel.setTextFill(TEXT_COLOR);
        coinsValueLabel.setFont(Font.font("", FontWeight.NORMAL, COINS_LABEL_FONT_SIZE));

        coinsBox.getChildren().addAll(coinsTitleLabel, coinsValueLabel);
        header.setLeft(coinsBox);
        setAlignment(coinsBox, Pos.CENTER_LEFT);

        final Label titleLabel = new Label("Upgrades");
        titleLabel.setTextFill(TEXT_COLOR);
        titleLabel.setFont(Font.font("", FontWeight.BOLD, TITLE_FONT_SIZE));
        header.setCenter(titleLabel);

        final Button closeButton =
                buildButton("Close shop", CLOSE_BUTTON_COLOR, CLOSE_BUTTON_HOVER_COLOR);
        header.setRight(closeButton);
        setAlignment(closeButton, Pos.CENTER_RIGHT);

        this.setTop(header);

        upgradesContainer = new VBox(UPGRADES_CONTAINER_SPACING);
        upgradesContainer.setBackground(new Background(new BackgroundFill(DARK_BACKGROUND_COLOR,
                new CornerRadii(CORNER_RADII), Insets.EMPTY)));
        upgradesContainer.setPadding(new Insets(UPGRADES_CONTAINER_PADDING));
        setMargin(upgradesContainer, new Insets(UPGRADES_CONTAINER_MARGIN_VERTICAL, 0,
                UPGRADES_CONTAINER_MARGIN_VERTICAL, 0));

        this.setCenter(upgradesContainer);
    }

    private Button buildButton(final String text, final Color baseColor, final Color hoverColor) {
        final Button btn = new Button(text);
        btn.setTextFill(TEXT_COLOR);
        btn.setFont(Font.font("", FontWeight.NORMAL, BUTTON_FONT_SIZE));
        btn.setPadding(new Insets(BUTTON_PADDING_VERTICAL, 0, BUTTON_PADDING_VERTICAL, 0));
        btn.setPrefWidth(BUTTON_PREF_WIDTH);

        final CornerRadii radii = new CornerRadii(CORNER_RADII);
        final Background background =
                new Background(new BackgroundFill(baseColor, radii, Insets.EMPTY));
        final Background backgroundHover =
                new Background(new BackgroundFill(hoverColor, radii, Insets.EMPTY));
        btn.setBackground(background);

        btn.setOnMouseEntered(e -> btn.setBackground(backgroundHover));
        btn.setOnMouseExited(e -> btn.setBackground(background));

        btn.setCursor(Cursor.HAND);

        return btn;
    }

    private HBox buildUpgradeRow(final String title, final String description, final int level,
            final int maxLevel, final int cost, final boolean canUpgrade) {
        final HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);

        final VBox textBox = new VBox(UPGRADE_TEXT_SPACING);
        final Label titleLabel = new Label(title + " (" + level + "/" + maxLevel + ")");
        titleLabel.setTextFill(TEXT_COLOR);
        titleLabel.setFont(Font.font("", FontWeight.BOLD, UPGRADE_TITLE_FONT_SIZE));

        final Label descLabel = new Label(description);
        descLabel.setTextFill(TEXT_COLOR);
        descLabel.setFont(Font.font("", FontWeight.NORMAL, UPGRADE_DESC_FONT_SIZE));
        descLabel.setWrapText(true);

        textBox.getChildren().addAll(titleLabel, descLabel);

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        final Button upgradeButton =
                buildButton("Upgrade (" + cost + " C)", BUY_BUTTON_COLOR, BUY_BUTTON_HOVER_COLOR);

        if (!canUpgrade) {
            upgradeButton.setDisable(true);
        }

        row.getChildren().addAll(textBox, spacer, upgradeButton);
        return row;
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
    public void update(final ShopSnapshot snapshot) {
        this.coinsValueLabel.setText(snapshot.coins() + " C");

        for (final Upgrade upgrade : snapshot.upgrades()) {
            upgradesContainer.getChildren()
                    .add(buildUpgradeRow(upgrade.getName(), upgrade.getDescription(),
                            upgrade.getLevel(), upgrade.getMaxLevel(), upgrade.getCost(),
                            upgrade.canUpgrade(snapshot.coins())));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputHandler(final ShopInputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
