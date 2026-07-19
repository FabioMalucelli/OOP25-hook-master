package it.unibo.hookmaster.model.upgrade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerWalletTest {

    private static final int INITIAL_COINS = 0;
    private static final int EXTRA_COINS = 5;
    private static final int EXPECTED_TOTAL_COINS = 5;
    private static final int FIRST_SPENDING = 4;
    private static final int EXPECTED_COINS_AFTER_FIRST_SPENDING = 1;
    private static final int SECOND_SPENDING = 7;

    private PlayerWallet playerWallet;

    @BeforeEach
    void setUp() {
        playerWallet = new PlayerWallet();
    }

    @Test
    void testInitialCoins() {
        assertEquals(INITIAL_COINS, playerWallet.getCoins());
    }

    @Test
    void testAddCoins() {
        playerWallet.addCoins(EXTRA_COINS);
        assertEquals(EXPECTED_TOTAL_COINS, playerWallet.getCoins());
    }

    @Test
    void testSpendCoinsOnlyWhenEnoughBalance() {
        playerWallet.addCoins(EXTRA_COINS);

        playerWallet.spendCoins(FIRST_SPENDING);
        assertEquals(EXPECTED_COINS_AFTER_FIRST_SPENDING, playerWallet.getCoins());

        playerWallet.spendCoins(SECOND_SPENDING);
        assertEquals(EXPECTED_COINS_AFTER_FIRST_SPENDING, playerWallet.getCoins());
    }
}
