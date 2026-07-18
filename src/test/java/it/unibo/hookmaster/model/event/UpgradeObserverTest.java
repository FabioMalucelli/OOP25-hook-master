package it.unibo.hookmaster.model.event;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.upgrade.UpgradeType;

class UpgradeObserverTest {

    private static final int LEVEL = 2;
    private static final double VALUE = 30;

    @Test
    void testUpgradeObserver() {
        final UpgradeEvent event = new UpgradeEvent(UpgradeType.MAX_WEIGHT, LEVEL, VALUE);
        final UpgradeEvent[] observerEvent = new UpgradeEvent[1];
        final UpgradeObserver observer = u -> observerEvent[0] = u;

        observer.onUpgrade(event);

        assertSame(event, observerEvent[0]);
    }
}
