package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FishImplTest {

    private static final double MAP_WIDTH = 800;
    private static final double MAP_HEIGHT = 600;
    private static final long DELTA_TIME = 16;
    private static final int[] X_POSITIONS = {0, 5, 3};
    private static final int[] Y_POSITIONS = {0, 5, 4};
    private static final double SPEED_MULTIPLIER = 2;

    @Test
    void nameAndTypeMatchTheConstructorType() {
        final Fish fish = new FishImpl(FishType.SAWSHARK,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertEquals(FishType.SAWSHARK, fish.getType());
        assertEquals(FishType.SAWSHARK.getName(), fish.getName());
    }

    @Test
    void weightIsWithinTheExpectedRange() {
        final Fish fish = new FishImpl(FishType.TUNA, new Position(X_POSITIONS[0], Y_POSITIONS[0]),
                new LinearMovement());
        final double base = FishType.TUNA.getBaseWeight();
        assertTrue(fish.getWeight() >= base && fish.getWeight() <= base * 2);
    }

    @Test
    void economicValueIsConsistentWithWeightRatio() {
        final Fish fish = new FishImpl(FishType.TUNA, new Position(X_POSITIONS[0], Y_POSITIONS[0]),
                new LinearMovement());
        final double ratio = fish.getWeight() / FishType.TUNA.getBaseWeight();
        final int expected = (int) Math.round(FishType.TUNA.getBaseEconomicValue() * ratio);
        assertEquals(expected, fish.getEconomicValue());
    }

    @Test
    void catchDifficultyIsWithinRange() {
        final Fish fish = new FishImpl(FishType.CLOWNFISH,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertEquals(1.0, fish.getCatchDifficulty());
    }

    @Test
    void catchDifficultyIsNeverNegative() {
        final Fish fish = new FishImpl(FishType.GREATWHITE,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertTrue(fish.getCatchDifficulty() >= 0.0);
    }

    @Test
    void speedMatchesTheBaseSpeedByDefault() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertEquals(Math.round(FishType.ANCHOVY.getSpeed()), fish.getSpeed());
    }

    @Test
    void speedMultiplierScalesTheBaseSpeed() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        fish.setSpeedMultiplier(SPEED_MULTIPLIER);
        assertEquals(Math.round(FishType.ANCHOVY.getSpeed() * SPEED_MULTIPLIER), fish.getSpeed());
    }

    @Test
    void positionCanBeUpdated() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        fish.setPosition(new Position(X_POSITIONS[1], Y_POSITIONS[1]));
        assertEquals(X_POSITIONS[1], fish.getX());
        assertEquals(Y_POSITIONS[1], fish.getY());
    }

    @Test
    void settingANullPositionThrows() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertThrows(NullPointerException.class, () -> fish.setPosition(null));
    }

    @Test
    void directionCanBeUpdated() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertEquals(1, fish.getDirection());
        fish.setDirection(-1);
        assertEquals(-1, fish.getDirection());
    }

    @Test
    void settingANullMovementStrategyThrows() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertThrows(NullPointerException.class, () -> fish.setMovementStrategy(null));
    }

    @Test
    void collisionAreaHasPositiveDimensionsAndMatchesThePosition() {
        final Fish fish = new FishImpl(FishType.MARLIN,
                new Position(X_POSITIONS[2], Y_POSITIONS[2]), new LinearMovement());
        final CollisionAreaRectangle area = fish.getCollisionArea();
        assertTrue(area.getWidth() > 0);
        assertTrue(area.getHeight() > 0);
        assertEquals(X_POSITIONS[2], area.getX());
        assertEquals(Y_POSITIONS[2], area.getY());
    }

    @Test
    void updateDelegatesToTheMovementStrategy() {
        final RecordingMovementStrategy strategy = new RecordingMovementStrategy();
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), strategy);

        fish.update(MAP_WIDTH, MAP_HEIGHT, DELTA_TIME);

        assertTrue(strategy.called);
        assertEquals(MAP_WIDTH, strategy.lastMapWidth);
        assertEquals(MAP_HEIGHT, strategy.lastMapHeight);
        assertEquals(DELTA_TIME, strategy.lastDeltaTime);
    }

    @Test
    void onCollisionReturnsFalseByDefault() {
        final Fish fish = new FishImpl(FishType.ANCHOVY,
                new Position(X_POSITIONS[0], Y_POSITIONS[0]), new LinearMovement());
        assertFalse(fish.onCollision(fish));
    }

    private static final class RecordingMovementStrategy implements MovementStrategy {
        private boolean called;
        private double lastMapWidth;
        private double lastMapHeight;
        private long lastDeltaTime;

        @Override
        public void move(final Fish fish, final double mapWidth, final double mapHeight,
                final long deltaTime) {
            this.called = true;
            this.lastMapWidth = mapWidth;
            this.lastMapHeight = mapHeight;
            this.lastDeltaTime = deltaTime;
        }
    }
}
