package it.unibo.hookmaster.model.upgrade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.upgrade.upgrades.MaxWeightUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.SpeedUpgrade;
import it.unibo.hookmaster.model.upgrade.upgrades.Upgrade;

class UpgradeFactoryTest {

    private static final int UPGRADES_NUMBER = 2;

    @Test
    void testUpgradeFactory() {
        final Map<UpgradeType, Upgrade> upgrades = UpgradeFactory.generateUpgrades();

        assertEquals(UPGRADES_NUMBER, upgrades.size());
        assertTrue(upgrades.containsKey(UpgradeType.MAX_WEIGHT));
        assertTrue(upgrades.containsKey(UpgradeType.SPEED));

        assertInstanceOf(MaxWeightUpgrade.class, upgrades.get(UpgradeType.MAX_WEIGHT));
        assertInstanceOf(SpeedUpgrade.class, upgrades.get(UpgradeType.SPEED));
    }
}
