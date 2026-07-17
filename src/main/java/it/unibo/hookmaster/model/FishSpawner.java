package it.unibo.hookmaster.model;

import java.util.Random;

import it.unibo.hookmaster.model.movement.DiagonalMovement;
import it.unibo.hookmaster.model.movement.LinearMovement;
import it.unibo.hookmaster.model.movement.MovementStrategy;

/**
 * Represents a fish instance in the game world.
 */
public class FishSpawner {

    private static final double TEST_0 = 0.1;
    private static final double TEST_1 = 0.3;

    private final int mapWidth;
    private final int mapHeight;
    private final Random random = new Random();

    /**
     * Represents a fish instance in the game world.
     * 
     * @param mapWidth  the X size of the map
     * @param mapHeight the Y size of the map
     */

    public FishSpawner(final int mapWidth, final int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Spawns a fish at a random edge and random height.
     *
     * @return new fish
     */
    public Fish spawnFish() {
        final FishType[] types = FishType.values();
        final FishType type = types[random.nextInt(types.length)];

        final boolean fromLeft = random.nextBoolean();
        final int x = fromLeft ? -50 : mapWidth + 50;
        final int y = random.nextInt(mapHeight);

        final Fish fish = new Fish(type, x, y);
        fish.setDirection(fromLeft ? 1 : -1);
        fish.setMovementStrategy(randomMovementStrategy());
        return fish;
    }

    private MovementStrategy randomMovementStrategy() {
        final int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return new LinearMovement();
            default:
                return new DiagonalMovement(TEST_0 + random.nextDouble() * TEST_1); 
            // in case more movement speeds are added
        }
    }
}
