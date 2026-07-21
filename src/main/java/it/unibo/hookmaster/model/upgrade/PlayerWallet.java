package it.unibo.hookmaster.model.upgrade;

import it.unibo.hookmaster.model.save.Originator;

/**
 * Class for holding the player balance.
 */
public final class PlayerWallet implements Originator<PlayerWallet.Memento> {

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

    @Override
    public PlayerWallet.Memento createMemento() {
        return new PlayerWallet.Memento(this.coins);
    }

    @Override
    public void restoreFromMemento(PlayerWallet.Memento memento) {
        this.coins = memento.coins;
    }

    public static final class Memento implements it.unibo.hookmaster.model.save.Memento {
        private static final long serialVersionUID = 1L;
        private final int coins;

        private Memento(final int coins) {
            this.coins = coins;
        }
    }
}
