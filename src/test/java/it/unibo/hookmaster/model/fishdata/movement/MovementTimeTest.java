package it.unibo.hookmaster.model.fishdata.movement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MovementTimeTest {

    private static final double DELTA = 1e-9;
    private static final double[] EXPECTED_RESULTS = {1, 2, 0.5, 0};
    private static final long[] DELTA_TIMES = {16, 32, 8, 0}; 

    @Test
    void referenceFrameProducesScaleOfOne() {
        assertEquals(EXPECTED_RESULTS[0], MovementTime.frameScale(DELTA_TIMES[0]), DELTA);
    }

    @Test
    void doubleDeltaProducesDoubleScale() {
        assertEquals(EXPECTED_RESULTS[1], MovementTime.frameScale(DELTA_TIMES[1]), DELTA);
    }

    @Test
    void halfDeltaProducesHalfScale() {
        assertEquals(EXPECTED_RESULTS[2], MovementTime.frameScale(DELTA_TIMES[2]), DELTA);
    }

    @Test
    void zeroDeltaProducesZeroScale() {
        assertEquals(EXPECTED_RESULTS[3], MovementTime.frameScale(DELTA_TIMES[3]), DELTA);
    }
}
