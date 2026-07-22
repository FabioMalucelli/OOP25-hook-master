package it.unibo.hookmaster.model.upgrade.upgrades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.LinearUpgradeValueStrategy;

class UpgradeTest {

    private static final int MAX_WEIGHT_BASE = 50;
    private static final double MAX_WEIGHT_STEP = 10;
    private static final int SPEED_BASE = 50;
    private static final double SPEED_STEP = 5;
    private static final int MINIGAME_EASE_BASE = 1;
    private static final double MINIGAME_EASE_STEP = -0.1;
    private static final int START_LEVEL = 1;
    private static final int START_MAX_WEIGHT_COST = 50;
    private static final int START_SPEED_COST = 50;
    private static final double START_STRATEGY_VALUE = 50;
    private static final int START_MINIGAME_VALUE = 1;
    private static final int ENOUGH_COINS = 50;
    private static final int NOT_ENOUGH_COINS = 0;
    private static final int NEW_LEVEL = 2;
    private static final int MAX_WEIGHT_MAX_LEVEL = 30;
    private static final int SPEED_MAX_LEVEL = 10;
    private static final int MINIGAME_EASE_MAX_LEVEL = 5;
    private static final int MINIGAME_EASE_COST_MULTIPLIER = 20;
    private static final int START_MINIGAME_EASE_COST = START_LEVEL * MINIGAME_EASE_COST_MULTIPLIER;

    @Test
    void testMaxWeightUpgrade() {
        final Upgrade upgrade = new MaxWeightUpgrade(
                new LinearUpgradeValueStrategy(MAX_WEIGHT_BASE, MAX_WEIGHT_STEP));

        assertEquals(UpgradeType.MAX_WEIGHT, upgrade.getType());
        assertEquals("Max Weight", upgrade.getName());
        assertEquals(
                "Increases the maximum weight the hook can catch, allowing for larger fish to be caught.",
                upgrade.getDescription());
        assertEquals(MAX_WEIGHT_MAX_LEVEL, upgrade.getMaxLevel());

        assertEquals(START_LEVEL, upgrade.getLevel());
        assertEquals(START_MAX_WEIGHT_COST, upgrade.getCost());
        assertEquals(START_STRATEGY_VALUE, upgrade.getValue());

        assertTrue(upgrade.canUpgrade(ENOUGH_COINS));
        assertFalse(upgrade.canUpgrade(NOT_ENOUGH_COINS));

        upgrade.upgrade();
        assertEquals(NEW_LEVEL, upgrade.getLevel());
    }

    @Test
    void testSpeedUpgrade() {
        final Upgrade upgrade =
                new SpeedUpgrade(new LinearUpgradeValueStrategy(SPEED_BASE, SPEED_STEP));

        assertEquals(UpgradeType.SPEED, upgrade.getType());
        assertEquals("Speed", upgrade.getName());
        assertEquals("Increases the speed of the hook, allowing for faster movement.",
                upgrade.getDescription());
        assertEquals(SPEED_MAX_LEVEL, upgrade.getMaxLevel());

        assertEquals(START_LEVEL, upgrade.getLevel());
        assertEquals(START_SPEED_COST, upgrade.getCost());
        assertEquals(START_STRATEGY_VALUE, upgrade.getValue());

        assertTrue(upgrade.canUpgrade(ENOUGH_COINS));
        assertFalse(upgrade.canUpgrade(NOT_ENOUGH_COINS));

        upgrade.upgrade();
        assertEquals(NEW_LEVEL, upgrade.getLevel());
    }

    @Test
    void testMinigameEaseUpgrade() {
        final Upgrade upgrade = new MinigameEaseUpgrade(
                new LinearUpgradeValueStrategy(MINIGAME_EASE_BASE, MINIGAME_EASE_STEP));

        assertEquals(UpgradeType.MINIGAME_EASE, upgrade.getType());
        assertEquals("Minigame Ease", upgrade.getName());
        assertEquals("Reduces the difficulty of minigames, making them easier to complete.",
                upgrade.getDescription());
        assertEquals(MINIGAME_EASE_MAX_LEVEL, upgrade.getMaxLevel());

        assertEquals(START_LEVEL, upgrade.getLevel());
        assertEquals(START_MINIGAME_EASE_COST, upgrade.getCost());
        assertEquals(START_MINIGAME_VALUE, upgrade.getValue());

        assertTrue(upgrade.canUpgrade(ENOUGH_COINS));
        assertFalse(upgrade.canUpgrade(NOT_ENOUGH_COINS));

        upgrade.upgrade();
        assertEquals(NEW_LEVEL, upgrade.getLevel());
    }

    @Test
    void testMaxLevelLimit() {
        final Upgrade upgrade =
                new SpeedUpgrade(new LinearUpgradeValueStrategy(SPEED_BASE, SPEED_STEP));

        while (upgrade.getLevel() < upgrade.getMaxLevel()) {
            assertTrue(upgrade.canUpgrade(Integer.MAX_VALUE));
            upgrade.upgrade();
        }

        assertFalse(upgrade.canUpgrade(Integer.MAX_VALUE));
        assertEquals(upgrade.getMaxLevel(), upgrade.getLevel());
    }
}
