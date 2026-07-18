package it.unibo.hookmaster.model.upgrade.upgrades;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.model.upgrade.strategies.LinearUpgradeValueStrategy;

class UpgradeTest {

    private static final int BASE = 1;
    private static final double STEP = 1.0;
    private static final int START_LEVEL = 1;
    private static final int START_COST = 1;
    private static final double START_VALUE = 1.0;
    private static final int ENOUGH_COINS = 1;
    private static final int NOT_ENOUGH_COINS = 0;
    private static final int NEW_LEVEL = 2;
    private static final int MAX_WEIGHT_MAX_LEVEL = 10;
    private static final int SPEED_MAX_LEVEL = 10;

    @Test
    void testMaxWeightUpgrade() {
        final Upgrade upgrade = new MaxWeightUpgrade(new LinearUpgradeValueStrategy(BASE, STEP));

        assertEquals(UpgradeType.MAX_WEIGHT, upgrade.getType());
        assertEquals("Peso massimo", upgrade.getName());
        assertEquals("Aumenta il peso massimo", upgrade.getDescription());
        assertEquals(MAX_WEIGHT_MAX_LEVEL, upgrade.getMaxLevel());

        assertEquals(START_LEVEL, upgrade.getLevel());
        assertEquals(START_COST, upgrade.getCost());
        assertEquals(START_VALUE, upgrade.getValue());

        assertTrue(upgrade.canUpgrade(ENOUGH_COINS));
        assertFalse(upgrade.canUpgrade(NOT_ENOUGH_COINS));

        upgrade.upgrade();
        assertEquals(NEW_LEVEL, upgrade.getLevel());
    }

    @Test
    void testSpeedtUpgrade() {
        final Upgrade upgrade = new SpeedUpgrade(new LinearUpgradeValueStrategy(BASE, STEP));

        assertEquals(UpgradeType.SPEED, upgrade.getType());
        assertEquals("Velocità", upgrade.getName());
        assertEquals("Aumenta la velocità", upgrade.getDescription());
        assertEquals(SPEED_MAX_LEVEL, upgrade.getMaxLevel());

        assertEquals(START_LEVEL, upgrade.getLevel());
        assertEquals(START_COST, upgrade.getCost());
        assertEquals(START_VALUE, upgrade.getValue());

        assertTrue(upgrade.canUpgrade(ENOUGH_COINS));
        assertFalse(upgrade.canUpgrade(NOT_ENOUGH_COINS));

        upgrade.upgrade();
        assertEquals(NEW_LEVEL, upgrade.getLevel());
    }

    @Test
    void testMaxLevelLimit() {
        final Upgrade upgrade = new SpeedUpgrade(new LinearUpgradeValueStrategy(BASE, STEP));

        while (upgrade.getLevel() < upgrade.getMaxLevel()) {
            assertTrue(upgrade.canUpgrade(Integer.MAX_VALUE));
            upgrade.upgrade();
        }

        assertFalse(upgrade.canUpgrade(Integer.MAX_VALUE));
        assertEquals(upgrade.getMaxLevel(), upgrade.getLevel());
    }
}
