package it.unibo.hookmaster.model.collision;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CollisionManagerTest {
    @Test
    void testPrefilledCollisionManagerCreation() {
        assertDoesNotThrow(() -> {
            final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();
            assertNotNull(collisionManager);
        });
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 10, 10, 5, 5, 10, 10, true",
        "0, 0, 10, 10, 9, 9, 10, 10, true",
        "0, 0, 10, 10, 20, 20, 10, 10, false"
    })
    void testRectangleCollisions(
        final double x1,
        final double y1,
        final double width1,
        final double height1,
        final double x2,
        final double y2,
        final double width2,
        final double height2,
        final boolean expectedCollision
    ) {
        final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();

        final RectangleCollidable rect1 = new RectangleCollidable(x1, y1, width1, height1);
        final RectangleCollidable rect2 = new RectangleCollidable(x2, y2, width2, height2);

        collisionManager.checkCollisions(List.of(rect1, rect2));

        assertCollisionState(expectedCollision, rect1, rect2);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 5, 3, 4, 5, true",
        "0, 0, 5, 20, 20, 5, false"
    })
    void testCircleCollisions(
        final double centerX1,
        final double centerY1,
        final double radius1,
        final double centerX2,
        final double centerY2,
        final double radius2,
        final boolean expectedCollision
    ) {
        final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();

        final CircleCollidable circle1 = new CircleCollidable(centerX1, centerY1, radius1);
        final CircleCollidable circle2 = new CircleCollidable(centerX2, centerY2, radius2);

        collisionManager.checkCollisions(List.of(circle1, circle2));

        assertCollisionState(expectedCollision, circle1, circle2);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 10, 10, 5, 5, 5, true",
        "0, 0, 10, 10, 20, 20, 5, false"
    })
    void testRectangleCircleCollisions(
        final double rectX,
        final double rectY,
        final double rectWidth,
        final double rectHeight,
        final double circleCenterX,
        final double circleCenterY,
        final double circleRadius,
        final boolean expectedCollision
    ) {
        final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();
        final RectangleCollidable rect = new RectangleCollidable(rectX, rectY, rectWidth, rectHeight);
        final CircleCollidable circle = new CircleCollidable(circleCenterX, circleCenterY, circleRadius);

        collisionManager.checkCollisions(List.of(rect, circle));

        assertCollisionState(expectedCollision, rect, circle);
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 10, 10",
        "5, 5, 8, 8"
    })
    void testRemovedCollidable(
        final double x,
        final double y,
        final double width,
        final double height
    ) {
        final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();
        final RectangleCollidable remover = new RectangleCollidable(x, y, width, height, true);
        final RectangleCollidable removed = new RectangleCollidable(x, y, width, height);
        final RectangleCollidable other = new RectangleCollidable(x, y, width, height);

        collisionManager.checkCollisions(List.of(remover, removed, other));

        assertEquals(List.of(removed, other), remover.getCollidedWith());
        assertEquals(List.of(remover), removed.getCollidedWith());
        assertEquals(List.of(remover), other.getCollidedWith());
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 10, 10",
        "5, 5, 8, 8"
    })
    void testRemoveObjectsFromOriginalCollection(
        final double x,
        final double y,
        final double width,
        final double height
    ) {
        final CollisionManager collisionManager = CollisionPredicates.prefilledCollisionManager();
        final List<Collidable> collidables = new ArrayList<>();
        final RectangleCollidable remover = new RectangleCollidable(
            x,
            y,
            width,
            height,
            true,
            collidables::remove
        );
        final RectangleCollidable firstRemoved = new RectangleCollidable(x, y, width, height);
        final RectangleCollidable secondRemoved = new RectangleCollidable(x, y, width, height);
        collidables.addAll(List.of(remover, firstRemoved, secondRemoved));

        assertDoesNotThrow(() -> collisionManager.checkCollisions(collidables));

        assertEquals(List.of(remover), collidables);
        assertEquals(List.of(firstRemoved, secondRemoved), remover.getCollidedWith());
    }

    private void assertCollisionState(
        final boolean expectedCollision,
        final TestCollidable first,
        final TestCollidable second
    ) {
        assertEquals(expectedCollision, first.hasCollided());
        assertEquals(expectedCollision, second.hasCollided());
    }

    private interface TestCollidable extends Collidable {
        boolean hasCollided();

        List<Collidable> getCollidedWith();
    }

    private static final class RectangleCollidable implements TestCollidable {
        private final CollisionAreaRectangle area;
        private final boolean removesOtherOnCollision;
        private final Consumer<Collidable> collisionAction;
        private final List<Collidable> collidedWith = new ArrayList<>();
        private boolean collided;

        RectangleCollidable(final double x, final double y, final double width, final double height) {
            this(x, y, width, height, false);
        }

        RectangleCollidable(
            final double x,
            final double y,
            final double width,
            final double height,
            final boolean removesOtherOnCollision
        ) {
            this(x, y, width, height, removesOtherOnCollision, ignored -> {
            });
        }

        RectangleCollidable(
            final double x,
            final double y,
            final double width,
            final double height,
            final boolean removesOtherOnCollision,
            final Consumer<Collidable> collisionAction
        ) {
            this.area = new CollisionAreaRectangle(x, y, width, height);
            this.removesOtherOnCollision = removesOtherOnCollision;
            this.collisionAction = collisionAction;
        }

        @Override
        public CollisionArea getCollisionArea() {
            return this.area;
        }

        @Override
        public boolean onCollision(final Collidable other) {
            this.collided = true;
            this.collidedWith.add(other);
            this.collisionAction.accept(other);
            return this.removesOtherOnCollision;
        }

        @Override
        public boolean hasCollided() {
            return this.collided;
        }

        @Override
        public List<Collidable> getCollidedWith() {
            return List.copyOf(this.collidedWith);
        }
    }

    private static final class CircleCollidable implements TestCollidable {
        private final CollisionAreaCircle area;
        private final List<Collidable> collidedWith = new ArrayList<>();
        private boolean collided;

        CircleCollidable(final double centerX, final double centerY, final double radius) {
            this.area = new CollisionAreaCircle(centerX, centerY, radius);
        }

        @Override
        public CollisionArea getCollisionArea() {
            return this.area;
        }

        @Override
        public boolean onCollision(final Collidable other) {
            this.collided = true;
            this.collidedWith.add(other);
            return false;
        }

        @Override
        public boolean hasCollided() {
            return this.collided;
        }

        @Override
        public List<Collidable> getCollidedWith() {
            return List.copyOf(this.collidedWith);
        }
    }
}
