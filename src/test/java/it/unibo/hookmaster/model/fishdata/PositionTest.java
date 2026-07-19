package it.unibo.hookmaster.model.fishdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PositionTest {

    private static final double[] X_VALUES = {15.5, -50, 0};
    private static final double[] Y_VALUES = {30.2, -100, 0};
    private static final double DELTA = 1e-9;

    @Test
    void xMatchesConstructorValue() {
        final Position position = new Position(X_VALUES[0], Y_VALUES[0]);
        assertEquals(X_VALUES[0], position.getX(), DELTA);
    }

    @Test
    void yMatchesConstructorValue() {
        final Position position = new Position(X_VALUES[0], Y_VALUES[0]);
        assertEquals(Y_VALUES[0], position.getY(), DELTA);
    }

    @Test
    void supportsNegativeCoordinates() {
        final Position position = new Position(X_VALUES[1], Y_VALUES[1]);
        assertEquals(X_VALUES[1], position.getX(), DELTA);
        assertEquals(Y_VALUES[1], position.getY(), DELTA);
    }

    @Test
    void supportsZeroCoordinates() {
        final Position position = new Position(X_VALUES[2], Y_VALUES[2]);
        assertEquals(X_VALUES[2], position.getX(), DELTA);
        assertEquals(Y_VALUES[2], position.getY(), DELTA);
    }
}
