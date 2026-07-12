package it.unibo.hookmaster.model;

/**
 * The player coins.
 */
public final class Player {

    private int coins = 1000;

    /**
     * Returns the playr number of coins.
     * 
     * @return the number of coins
     */
    public int getCoins() {
        return coins;
    }

    /**
     * Adds coins to the playr.
     * 
     * @param amount the amount to add
     */
    public void addCoins(final int amount) {
        coins += amount;
    }

    /**
     * Spends player coins.
     * 
     * @param amount the amount to spend
     */
    public void spendCoins(final int amount) {
        if (amount > coins) {
            return;
        }
        coins -= amount;
    }
}
