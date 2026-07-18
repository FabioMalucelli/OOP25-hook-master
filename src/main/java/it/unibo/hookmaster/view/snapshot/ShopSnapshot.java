package it.unibo.hookmaster.view.snapshot;

import java.util.Collection;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Immutable snapshot of the shop state, used to pass data to the
 * {@link it.unibo.hookmaster.view.ShopView}.
 * 
 * @param upgrades the list of upgrades.
 * @param coins the player coin balance.
 */
public record ShopSnapshot(Collection<Upgrade> upgrades, int coins) { }
