package it.unibo.hookmaster.model.collision;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.unibo.hookmaster.util.Pair;

/**
 * Naive implementation of a collision manager that
 * checks for collisions between each pair of
 * Collidables.
 */
public final class CollisionManagerImpl implements CollisionManager {
    private final Map<
        Pair<Class<? extends CollisionArea>, Class<? extends CollisionArea>>,
        CollisionPredicate
    > collisionPredicates = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCollisionPredicate(
        final Class<? extends CollisionArea> firstArea,
        final Class<? extends CollisionArea> secondArea,
        final CollisionPredicate predicate
    ) {
        if (collisionPredicates.containsKey(Pair.of(firstArea, secondArea))) {
            throw new IllegalArgumentException("Collision predicate for these two areas is already registered.");
        }
        // Register the predicate for both orderings of the area types,
        // as we don't know in which order the collidables will be checked for collisions.
        collisionPredicates.put(Pair.of(firstArea, secondArea), predicate);
        collisionPredicates.put(Pair.of(secondArea, firstArea), (second, first) -> predicate.test(first, second));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkCollisions(final List<? extends Collidable> collidablesParam) {
        // Make a copy to avoid concurrent modification issues
        final List<? extends Collidable> collidables = List.copyOf(collidablesParam);
        final boolean[] toRemove = new boolean[collidables.size()];
        for (int i = 0; i < collidables.size(); i++) {
            final Collidable first = collidables.get(i);
            final CollisionArea firstArea = first.getCollisionArea();
            for (int j = i + 1; j < collidables.size(); j++) {
                if (toRemove[i] || toRemove[j]) {
                    continue; // Skip if either collidable is already marked for removal
                }
                final Collidable second = collidables.get(j);
                final CollisionArea secondArea = second.getCollisionArea();
                final CollisionPredicate predicate = collisionPredicates.get(
                    Pair.of(firstArea.getClass(), secondArea.getClass())
                );
                if (predicate != null && predicate.test(firstArea, secondArea)) {
                    final boolean removeSecond = first.onCollision(second);
                    final boolean removeFirst = second.onCollision(first);
                    toRemove[i] = removeFirst;
                    toRemove[j] = removeSecond;
                }
            }
        }
    }
}
