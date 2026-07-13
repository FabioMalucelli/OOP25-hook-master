package it.unibo.hookmaster.model.fishing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.hookmaster.model.fishing.boat.BoatImpl;

/**
 * Unit tests for BoatImpl.
 */
class BoatImplTest {

    private static final double START_X = 50.0;
    private static final double SURFACE_Y = 100.0;
    private static final double SPEED = 20.0;
    private static final double MIN_X = 0.0;
    private static final double MAX_X = 100.0;
    private static final double DELTA = 1e-9;
    private static final double ONE_SECOND = 1.0;
    private static final double FIVE_SECONDS = 5.0;
    private static final double LARGE_TIME_STEP = 1000.0;

    private BoatImpl boat;

    @BeforeEach
    void setUp() { 
        boat = new BoatImpl(START_X, SURFACE_Y, SPEED, MIN_X, MAX_X);
    }

    @Test
    void initialPositionMatchesConstructorArguments() {
        assertEquals(START_X, boat.getX(), DELTA);
        assertEquals(SURFACE_Y, boat.getY(), DELTA);
    }

    @Test
    void movesRightWhenOnlyMovingRightIsTrue() {
        boat.setMovingRight(true);
        boat.update(ONE_SECOND);
        assertEquals(START_X + SPEED, boat.getX(), DELTA);
    }

    @Test
    void movesRightWhenOnlyMovingLeftIsTrue() {
        boat.setMovingLeft(true);
        boat.update(ONE_SECOND);
        assertEquals(START_X - SPEED, boat.getX(), DELTA);
    }

    @Test
    void staysStillWhenBothDirectionsAreAlive() {
        boat.setMovingLeft(true);
        boat.setMovingRight(true);
        boat.update(ONE_SECOND);
        assertEquals(START_X, boat.getX(), DELTA);
    }

    @Test
    void clampsPositionAtMaxBoundary() {
        boat.setMovingRight(true);
        boat.update(LARGE_TIME_STEP);
        assertEquals(MAX_X, boat.getX(), DELTA);
    }

    @Test
    void clampsPositionAtMinBoundary() {
        boat.setMovingLeft(true);
        boat.update(LARGE_TIME_STEP);
        assertEquals(MIN_X, boat.getX(), DELTA);
    }

    @Test
    void yPositionNeverChanges() {
        boat.setMovingRight(true);
        boat.update(FIVE_SECONDS);
        assertEquals(SURFACE_Y, boat.getY(), DELTA);
    }

    @Test
    void stoppingMovementFreezesPosition() {
        boat.setMovingRight(true);
        boat.update(ONE_SECOND);
        final double positionAfterMoving = boat.getX();
        boat.setMovingRight(false);
        boat.update(ONE_SECOND);
        assertEquals(positionAfterMoving, boat.getX(), DELTA);
    }
}
