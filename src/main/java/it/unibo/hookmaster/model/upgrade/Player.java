package it.unibo.hookmaster.model.upgrade;

/**
 * Class for holding the player state.
 */
public final class Player {

    private int coins;

    /**
     * Retrieves the player current coin balance.
     * 
     * @return the amount of coins the player has.
     */
    public int getCoins() {
        return this.coins;
    }

    /**
     * Adds the specified amount of coins to the player balance.
     * 
     * @param amount the number of coins to add.
     */
    public void addCoins(final int amount) {
        this.coins += amount;
    }

    /**
     * Spends the specified amount of player coins.
     * 
     * @param amount the number of coins to spend.
     */
    public void spendCoins(final int amount) {
        if (amount <= this.coins) {
            this.coins -= amount;
        }
    }
}
