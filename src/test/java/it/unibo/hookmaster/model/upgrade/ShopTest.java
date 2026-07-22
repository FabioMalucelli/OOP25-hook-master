package it.unibo.hookmaster.model.upgrade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopTest {

    private static final int UPGRADES_NUMBER = 3;
    private static final int COINS = 50;
    private static final int EXPECTED_COINS = 0;
    private static final int EXPECTED_LEVEL = 2;

    private Shop shop;
    private PlayerWallet playerWallet;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        playerWallet = new PlayerWallet();
    }

    @Test
    void testShopInitialization() {
        assertEquals(UPGRADES_NUMBER, shop.getUpgrades().size());
    }

    @Test
    void testBuyUpgrade() {
        playerWallet.addCoins(COINS);

        final Upgrade upgrade = shop.getUpgrades().stream()
                .filter(u -> u.getType() == UpgradeType.SPEED).findFirst().orElseThrow();

        shop.buy(UpgradeType.SPEED, playerWallet);

        assertEquals(EXPECTED_COINS, playerWallet.getCoins());
        assertEquals(EXPECTED_LEVEL, upgrade.getLevel());

        shop.buy(UpgradeType.SPEED, playerWallet);

        assertEquals(EXPECTED_COINS, playerWallet.getCoins());
        assertEquals(EXPECTED_LEVEL, upgrade.getLevel());
        assertFalse(upgrade.canUpgrade(playerWallet.getCoins()));
    }
}
