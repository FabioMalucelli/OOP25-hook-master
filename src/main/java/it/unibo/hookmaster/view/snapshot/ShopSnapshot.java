package it.unibo.hookmaster.view.snapshot;

import java.util.Collection;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Read only record to pass data to the shop view.
 * 
 * @param upgrades list of upgrades
 * @param coins player coins
 */
public record ShopSnapshot(Collection<Upgrade> upgrades, int coins) { }
