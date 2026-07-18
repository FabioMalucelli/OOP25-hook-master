package it.unibo.hookmaster.model.upgrade;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlayerTest {

    private static final int INITIAL_COINS = 0;
    private static final int EXTRA_COINS = 5;
    private static final int EXPECTED_TOTAL_COINS = 5;
    private static final int FIRST_SPENDING = 4;
    private static final int EXPECTED_COINS_AFTER_FIRST_SPENDING = 1;
    private static final int SECOND_SPENDING = 7;

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player();
    }

    @Test
    void testInitialCoins() {
        assertEquals(INITIAL_COINS, player.getCoins());
    }

    @Test
    void testAddCoins() {
        player.addCoins(EXTRA_COINS);
        assertEquals(EXPECTED_TOTAL_COINS, player.getCoins());
    }

    @Test
    void testSpendCoinsOnlyWhenEnoughBalance() {
        player.addCoins(EXTRA_COINS);

        player.spendCoins(FIRST_SPENDING);
        assertEquals(EXPECTED_COINS_AFTER_FIRST_SPENDING, player.getCoins());

        player.spendCoins(SECOND_SPENDING);
        assertEquals(EXPECTED_COINS_AFTER_FIRST_SPENDING, player.getCoins());
    }
}
