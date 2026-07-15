package it.unibo.hookmaster.view.snapshot;

import java.util.Collection;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

public record ShopSnapshot(Collection<Upgrade> upgrades, int coins) { }
