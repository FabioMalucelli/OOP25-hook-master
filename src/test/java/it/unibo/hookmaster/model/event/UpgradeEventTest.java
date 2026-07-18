package it.unibo.hookmaster.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

class UpgradeEventTest {

    private static final int LEVEL = 3;
    private static final double VALUE = 4.5;

    @Test
    void testUpgradeEvent() {
        final UpgradeEvent event = new UpgradeEvent(UpgradeType.SPEED, LEVEL, VALUE);

        assertEquals(UpgradeType.SPEED, event.getUpgradeType());
        assertEquals(LEVEL, event.getNewLevel());
        assertEquals(VALUE, event.getNewValue());
    }
}
