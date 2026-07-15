package it.unibo.hookmaster.view.snapshot;

import it.unibo.hookmaster.model.fishing.boat.Boat;
import it.unibo.hookmaster.model.fishing.hook.Hook;

public record GameSnapshot(Boat boat, Hook hook, int coins) { }
