package it.unibo.hookmaster.model.upgrade.strategies;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LinearUpgradeValueStrategyTest {

    private static final int BASE_VALUE = 10;
    private static final double STEP_VALUE = 1.5;
    private static final int FIRST_LEVEL = 1;
    private static final int SECOND_LEVEL = 2;
    private static final int FOURTH_LEVEL = 4;
    private static final double SECOND_LEVEL_UPGRADE_VALUE = 11.5;
    private static final double FOURTH_LEVEL_UPGRADE_VALUE = 14.5;
    private static final int SECOND_LEVEL_UPGRADE_COST = 12;
    private static final int FOURTH_LEVEL_UPGRADE_COST = 16;

    @Test
    void testStrategyCalculations() {
        final LinearUpgradeValueStrategy strategy =
                new LinearUpgradeValueStrategy(BASE_VALUE, STEP_VALUE);

        assertEquals(BASE_VALUE, strategy.valueForLevel(FIRST_LEVEL));
        assertEquals(BASE_VALUE, strategy.costForLevel(FIRST_LEVEL));

        assertEquals(SECOND_LEVEL_UPGRADE_VALUE, strategy.valueForLevel(SECOND_LEVEL));
        assertEquals(SECOND_LEVEL_UPGRADE_COST, strategy.costForLevel(SECOND_LEVEL));

        assertEquals(FOURTH_LEVEL_UPGRADE_VALUE, strategy.valueForLevel(FOURTH_LEVEL));
        assertEquals(FOURTH_LEVEL_UPGRADE_COST, strategy.costForLevel(FOURTH_LEVEL));
    }
}
