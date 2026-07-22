package it.unibo.hookmaster.model.fishdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import it.unibo.hookmaster.model.collision.Collidable;
import it.unibo.hookmaster.model.collision.CollisionArea;
import it.unibo.hookmaster.model.collision.CollisionAreaRectangle;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.weather.Weather;
import it.unibo.hookmaster.model.weather.WeatherObserver;
import it.unibo.hookmaster.model.weather.WeatherSystem;

class PredatorFishImplTest {
    private static final double DEFAULT_MAP_WIDTH = 100.0;
    private static final double DEFAULT_MAP_HEIGHT = 100.0;
    private static final double DEFAULT_FISH_WEIGHT = 5.0;
    private static final double COLLISION_AREA_X = 0.0;
    private static final double COLLISION_AREA_Y = 0.0;
    private static final double COLLISION_AREA_SIZE = 1.0;
    private static final double DEFAULT_CATCH_DIFFICULTY = 0.0;
    private static final int DEFAULT_ECONOMIC_VALUE = 0;
    private static final int DEFAULT_DIRECTION = 1;

    @ParameterizedTest
    @CsvSource({
        "10.0, 5.0, true",
        "10.0, 10.0, false",
        "10.0, 20.0, false"
    })
    void testRemovesOnlyLighterFish(
        final double predatorWeight,
        final double otherWeight,
        final boolean expectedRemoval
    ) {
        final RecordingFishManager fishManager = new RecordingFishManager();
        final TestFish wrappedFish = new TestFish(FishType.GREATWHITE, predatorWeight);
        final PredatorFishImpl predatorFish = new PredatorFishImpl(fishManager, wrappedFish);
        final TestFish otherFish = new TestFish(FishType.ANCHOVY, otherWeight);
        final List<Fish> expectedRemovedFishes = new ArrayList<>();
        wrappedFish.setDirection(1);
        otherFish.setDirection(-1);
        if (expectedRemoval) {
            expectedRemovedFishes.add(otherFish);
        }

        final boolean removeOtherFish = predatorFish.onCollision(otherFish);

        assertEquals(expectedRemoval, removeOtherFish);
        assertEquals(expectedRemovedFishes, fishManager.getRemovedFishes());
        assertEquals(List.of(otherFish), wrappedFish.getCollidedWith());
    }

    @ParameterizedTest
    @CsvSource("10.0")
    void testNonFishCollision(final double predatorWeight) {
        final RecordingFishManager fishManager = new RecordingFishManager();
        final PredatorFishImpl predatorFish = new PredatorFishImpl(
            fishManager,
            new TestFish(FishType.GREATWHITE, predatorWeight)
        );

        final boolean removeOther = predatorFish.onCollision(new TestCollidable());

        assertFalse(removeOther);
        assertTrue(fishManager.getRemovedFishes().isEmpty());
    }

    @ParameterizedTest
    @CsvSource("10.0, 20.0")
    void testPredatorFishDelegatesToWrappedFish(final double predatorWeight, final double otherWeight) {
        final RecordingFishManager fishManager = new RecordingFishManager();
        final TestFish wrappedFish = new TestFish(FishType.GREATWHITE, predatorWeight);
        wrappedFish.setRemoveOtherOnCollision(true);
        final PredatorFishImpl predatorFish = new PredatorFishImpl(fishManager, wrappedFish);
        final TestFish otherFish = new TestFish(FishType.TUNA, otherWeight);

        final boolean removeOtherFish = predatorFish.onCollision(otherFish);

        assertTrue(removeOtherFish);
        // Assert that it was not the predator logic to remove the other fish,
        // but the wrapped fish logic
        assertTrue(fishManager.getRemovedFishes().isEmpty());
    }

    private static final class RecordingFishManager extends FishManager {
        private final List<Fish> removedFishes = new ArrayList<>();

        RecordingFishManager() {
            super(new TestFishSpawner(), new TestWeatherSystem(), DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        }

        @Override
        public void removeFish(final Fish fish) {
            this.removedFishes.add(fish);
        }

        @Override
        public void removeDeadFish(final Fish fish) {
            removeFish(fish);
        }

        List<Fish> getRemovedFishes() {
            return List.copyOf(this.removedFishes);
        }
    }

    private static final class TestFishSpawner extends FishSpawner {
        TestFishSpawner() {
            super(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        }

        @Override
        public Fish spawnFish(final FishManager fishManager, final Weather currentWeather, final boolean isBoid) {
            return new TestFish(FishType.ANCHOVY, DEFAULT_FISH_WEIGHT);
        }
    }

    private static final class TestWeatherSystem implements WeatherSystem {
        @Override
        public void update(final long deltaTime) {
        }

        @Override
        public Weather getCurrentWeather() {
            return Weather.CLEAR;
        }

        @Override
        public void addObserver(final WeatherObserver observer) {
        }

        @Override
        public void removeObserver(final WeatherObserver observer) {
        }
    }

    private static final class TestCollidable implements Collidable {
        @Override
        public CollisionArea getCollisionArea() {
            return new CollisionAreaRectangle(
                COLLISION_AREA_X,
                COLLISION_AREA_Y,
                COLLISION_AREA_SIZE,
                COLLISION_AREA_SIZE
            );
        }

        @Override
        public boolean onCollision(final Collidable other) {
            return false;
        }
    }

    private static final class TestFish implements Fish {
        private final FishType type;
        private final double weight;
        private final List<Collidable> collidedWith = new ArrayList<>();
        private final Position position = new Position(COLLISION_AREA_X, COLLISION_AREA_Y);
        private boolean removeOtherOnCollision;
        private int direction = DEFAULT_DIRECTION;

        TestFish(final FishType type, final double weight) {
            this.type = type;
            this.weight = weight;
        }

        @Override
        public CollisionAreaRectangle getCollisionArea() {
            return new CollisionAreaRectangle(
                this.position.getX(),
                this.position.getY(),
                COLLISION_AREA_SIZE,
                COLLISION_AREA_SIZE
            );
        }

        @Override
        public FishType getType() {
            return this.type;
        }

        @Override
        public double getSpeed() {
            return this.type.getSpeed();
        }

        @Override
        public void setSpeedMultiplier(final double speedMultiplier) {
        }

        @Override
        public Position getPosition() {
            return this.position;
        }

        @Override
        public double getX() {
            return this.position.getX();
        }

        @Override
        public double getY() {
            return this.position.getY();
        }

        @Override
        public void setPosition(final Position newPosition) { }

        @Override
        public int getDirection() {
            return direction;
        }

        @Override
        public void setDirection(final int direction) {
            this.direction = direction;
        }

        @Override
        public int getEconomicValue() {
            return DEFAULT_ECONOMIC_VALUE;
        }

        @Override
        public double getCatchDifficulty() {
            return DEFAULT_CATCH_DIFFICULTY;
        }

        @Override
        public String getName() {
            return this.type.getName();
        }

        @Override
        public double getWeight() {
            return this.weight;
        }

        @Override
        public boolean onCollision(final Collidable other) {
            this.collidedWith.add(other);
            return this.removeOtherOnCollision;
        }

        @Override
        public void setMovementStrategy(final MovementStrategy movementStrategy) { }

        @Override
        public void update(final double mapWidth, final double mapHeight, final long deltaTime) { }

        void setRemoveOtherOnCollision(final boolean removeOtherOnCollision) {
            this.removeOtherOnCollision = removeOtherOnCollision;
        }

        List<Collidable> getCollidedWith() {
            return List.copyOf(this.collidedWith);
        }
    }
}
