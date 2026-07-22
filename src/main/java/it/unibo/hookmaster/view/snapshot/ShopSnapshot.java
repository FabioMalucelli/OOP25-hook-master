package it.unibo.hookmaster.view.snapshot;

import java.util.Collection;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

/**
 * Immutable snapshot of the shop state, used to pass data to the
 * {@link it.unibo.hookmaster.view.ShopView}.
 * 
 * @param upgrades the list of upgrades.
 * @param coins the player coin balance.
 */
@SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification = "The snapshot is only used for reading the state of the shop."
)
public record ShopSnapshot(Collection<Upgrade> upgrades, int coins) { }
