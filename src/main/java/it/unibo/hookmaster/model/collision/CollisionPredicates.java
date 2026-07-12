package it.unibo.hookmaster.model.collision;

/**
 * Collision checks for the supported collision areas.
 */
public final class CollisionPredicates {
    /**
     * Private constructor to prevent instantiation.
     */
    private CollisionPredicates() { }

    /**
     * Utility function that creates a collision manager with the default
     * predicates already registered.
     * 
     * @return the prefilled collision manager
     */
    public static CollisionManager prefilledCollisionManager() {
        final CollisionManagerImpl collisionManager = new CollisionManagerImpl();
        collisionManager.registerCollisionPredicate(
            CollisionAreaRectangle.class,
            CollisionAreaRectangle.class,
            new RectangleRectangleCollisionPredicate()
        );
        collisionManager.registerCollisionPredicate(
            CollisionAreaCircle.class,
            CollisionAreaCircle.class,
            new CircleCircleCollisionPredicate()
        );
        collisionManager.registerCollisionPredicate(
            CollisionAreaRectangle.class,
            CollisionAreaCircle.class,
            new RectangleCircleCollisionPredicate()
        );
        return collisionManager;
    }

    /**
     * Collision predicate for rectangle-rectangle collision areas.
     */
    public static final class RectangleRectangleCollisionPredicate implements CollisionPredicate {
        @Override
        public boolean test(final CollisionArea first, final CollisionArea second) {
            if (!(first instanceof CollisionAreaRectangle) || !(second instanceof CollisionAreaRectangle)) {
                throw new IllegalArgumentException("Both collision areas must be rectangles.");
            }
            final CollisionAreaRectangle r1 = (CollisionAreaRectangle) first;
            final CollisionAreaRectangle r2 = (CollisionAreaRectangle) second;
            return r1.getX() < r2.getX() + r2.getWidth() 
                   && r1.getX() + r1.getWidth() > r2.getX()
                   && r1.getY() < r2.getY() + r2.getHeight()
                   && r1.getY() + r1.getHeight() > r2.getY();
        }
    }

    /**
     * Collision predicate for circle-circle collision areas.
     */
    public static final class CircleCircleCollisionPredicate implements CollisionPredicate {
        @Override
        public boolean test(final CollisionArea first, final CollisionArea second) {
            if (!(first instanceof CollisionAreaCircle) || !(second instanceof CollisionAreaCircle)) {
                throw new IllegalArgumentException("Both collision areas must be circles.");
            }
            final CollisionAreaCircle c1 = (CollisionAreaCircle) first;
            final CollisionAreaCircle c2 = (CollisionAreaCircle) second;
            final double dx = c1.getCenterX() - c2.getCenterX();
            final double dy = c1.getCenterY() - c2.getCenterY();
            final double sum = c1.getRadius() + c2.getRadius();
            return dx * dx + dy * dy < sum * sum;
        }
    }

    /**
     * Collision predicate for rectangle-circle collision areas.
     */
    public static final class RectangleCircleCollisionPredicate implements CollisionPredicate {
        @Override
        public boolean test(final CollisionArea first, final CollisionArea second) {
            if (!(first instanceof CollisionAreaRectangle) || !(second instanceof CollisionAreaCircle)) {
                throw new IllegalArgumentException("First collision area must be a rectangle and second must be a circle.");
            }
            final CollisionAreaRectangle rect = (CollisionAreaRectangle) first;
            final CollisionAreaCircle circle = (CollisionAreaCircle) second;
            final double closestX = Math.clamp(circle.getCenterX(), rect.getX(), rect.getX() + rect.getWidth());
            final double closestY = Math.clamp(circle.getCenterY(), rect.getY(), rect.getY() + rect.getHeight());
            final double dx = circle.getCenterX() - closestX;
            final double dy = circle.getCenterY() - closestY;
            return dx * dx + dy * dy < circle.getRadius() * circle.getRadius();
        }
    }
}
