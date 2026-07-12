package it.unibo.hookmaster.view;

import it.unibo.hookmaster.controller.ShopController;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
 * View for the shop.
 */
public final class ShopView extends BorderPane {

    private static final Color BACKGROUND_COLOR = Color.web("#1d5f9e");
    private static final Color DARK_BACKGROUND_COLOR = Color.web("#132038");
    private static final Color COIN_COLOR = Color.web("#ffd900");
    private static final Color CLOSE_BTN_COLOR = Color.web("#e8703a");
    private static final Color BUY_BUTTON_COLOR = Color.web("#3f8f3f");
    private static final Color BUY_BUTTON_DISABLED_COLOR = Color.web("#7a817a");

    private static final double SIDE_MARGIN_RATIO = 0.08;

    private static final int CONTENT_WRAPPER_SPACING = 25;
    private static final int CONTENT_WRAPPER_SPACING_TOP = 10;
    private static final int CONTENT_WRAPPER_SPACING_BOTTOM = 20;
    private static final int UPGRADES_LIST_SPACING = 18;
    private static final int UPGRADES_LIST_PADDING_TOP = 5;

    private static final int COINS_BOX_SPACING = 4;
    private static final int COINS_BOX_PADDING_VERTICAL = 12;
    private static final int COINS_BOX_PADDING_HORIZONTAL = 22;
    private static final int COINS_LABEL_FONT_SIZE = 22;
    private static final int COINS_VALUE_FONT_SIZE = 20;

    private static final int TITLE_FONT_SIZE = 38;

    private static final int BUTTON_FONT_SIZE = 18;
    private static final int BUTTON_PADDING_VERTICAL = 14;
    private static final int BUTTON_PADDING_HORIZONTAL = 26;
    private static final int BUY_BUTTON_MIN_WIDTH = 150;

    private static final int UPGRADE_BOX_SPACING = 20;
    private static final int UPGRADE_BOX_PADDING_VERTICAL = 20;
    private static final int UPGRADE_BOX_PADDING_HORIZONTAL = 25;
    private static final int UPGRADE_INFO_SPACING = 6;
    private static final int UPGRADE_TITLE_FONT_SIZE = 22;
    private static final int UPGRADE_DESCRIPTION_FONT_SZIE = 18;

    private final double width;
    private final double height;
    private final ShopController controller;

    /**
     * View constructor.
     * 
     * @param width view width
     * @param height view height
     * @param controller shop controller
     */
    public ShopView(final double width, final double height, final ShopController controller) {
        this.width = width;
        this.height = height;
        this.controller = controller;
        build();
    }

    /**
     * Builds the view.
     */
    public void build() {
        setPrefSize(width, height);
        setBackground(new Background(
                new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        final double sideMargin = width * SIDE_MARGIN_RATIO;

        final VBox contentWrapper = new VBox(CONTENT_WRAPPER_SPACING);
        contentWrapper.setPadding(new Insets(CONTENT_WRAPPER_SPACING_TOP, sideMargin,
                CONTENT_WRAPPER_SPACING_BOTTOM, sideMargin));

        contentWrapper.getChildren().addAll(buildHeader(), buildUpgradesList());

        setCenter(contentWrapper);
    }

    private HBox buildHeader() {
        final VBox coinsBox = new VBox(COINS_BOX_SPACING);
        coinsBox.setPadding(new Insets(COINS_BOX_PADDING_VERTICAL, COINS_BOX_PADDING_HORIZONTAL,
                COINS_BOX_PADDING_VERTICAL, COINS_BOX_PADDING_HORIZONTAL));
        coinsBox.setBackground(new Background(
                new BackgroundFill(DARK_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        final Label coinsLabel = new Label("Total coins: ");
        coinsLabel.setTextFill(COIN_COLOR);
        coinsLabel.setFont(Font.font("", FontWeight.BOLD, COINS_LABEL_FONT_SIZE));
        final Label coinsValue = new Label(controller.getCoins() + " C");
        coinsValue.setTextFill(Color.WHITE);
        coinsValue.setFont(Font.font("", FontWeight.NORMAL, COINS_VALUE_FONT_SIZE));

        coinsBox.getChildren().addAll(coinsLabel, coinsValue);

        final Label titleLabel = new Label("Upgrades shop");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("", FontWeight.BOLD, TITLE_FONT_SIZE));
        titleLabel.setMaxWidth(width);
        titleLabel.setAlignment(Pos.CENTER);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);

        final Button closeShopBtn = new Button("Close shop");
        closeShopBtn.setTextFill(Color.WHITE);
        closeShopBtn.setFont(Font.font("", FontWeight.BOLD, BUTTON_FONT_SIZE));
        closeShopBtn.setPadding(new Insets(BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL,
                BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL));
        closeShopBtn.setBackground(new Background(
                new BackgroundFill(CLOSE_BTN_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        final HBox header = new HBox();
        header.setPrefWidth(width);
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(coinsBox, titleLabel, closeShopBtn);

        return header;
    }

    private HBox buildUpgradeBox(final UpgradeType type, final String name,
            final String description, final int level, final int maxLevel, final int price,
            final boolean canUpgrade) {
        final Label titleLabel = new Label(name + "(" + level + "/" + maxLevel + ")");
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setFont(Font.font("", FontWeight.BOLD, UPGRADE_TITLE_FONT_SIZE));

        final Label descriptionLabel = new Label(description);
        descriptionLabel.setTextFill(Color.WHITE);
        descriptionLabel.setFont(Font.font("", FontWeight.NORMAL, UPGRADE_DESCRIPTION_FONT_SZIE));
        descriptionLabel.setWrapText(true);

        final VBox infoBox = new VBox(UPGRADE_INFO_SPACING);
        infoBox.getChildren().addAll(titleLabel, descriptionLabel);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        final Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        final Button buyBtn = new Button("Buy (" + price + " C)");
        buyBtn.setTextFill(Color.WHITE);
        buyBtn.setFont(Font.font("", FontWeight.BOLD, BUTTON_FONT_SIZE));
        buyBtn.setPadding(new Insets(BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL,
                BUTTON_PADDING_VERTICAL, BUTTON_PADDING_HORIZONTAL));
        buyBtn.setMinWidth(BUY_BUTTON_MIN_WIDTH);

        if (canUpgrade) {
            buyBtn.setBackground(new Background(
                    new BackgroundFill(BUY_BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            buyBtn.setBackground(new Background(new BackgroundFill(BUY_BUTTON_DISABLED_COLOR,
                    CornerRadii.EMPTY, Insets.EMPTY)));
            buyBtn.setDisable(true);
        }

        buyBtn.setOnAction(e -> {
            controller.buyUpgrade(type);
        });

        final HBox upgradeBox = new HBox(UPGRADE_BOX_SPACING);
        upgradeBox.setAlignment(Pos.CENTER_LEFT);
        upgradeBox
                .setPadding(new Insets(UPGRADE_BOX_PADDING_VERTICAL, UPGRADE_BOX_PADDING_HORIZONTAL,
                        UPGRADE_BOX_PADDING_VERTICAL, UPGRADE_BOX_PADDING_HORIZONTAL));
        upgradeBox.setBackground(new Background(
                new BackgroundFill(DARK_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));

        upgradeBox.getChildren().addAll(infoBox, spacer, buyBtn);

        return upgradeBox;
    }

    private VBox buildUpgradesList() {
        final VBox listBox = new VBox(UPGRADES_LIST_SPACING);
        listBox.setPadding(new Insets(UPGRADES_LIST_PADDING_TOP, 0, 0, 0));

        for (final Upgrade upgrade : controller.getUpgrades()) {
            listBox.getChildren()
                    .add(buildUpgradeBox(upgrade.getType(), upgrade.getName(),
                            upgrade.getDescription(), upgrade.getLevel(), upgrade.getMaxLevel(),
                            upgrade.getCost(), upgrade.canUpgrade(controller.getCoins())));
        }

        return listBox;
    }
}
