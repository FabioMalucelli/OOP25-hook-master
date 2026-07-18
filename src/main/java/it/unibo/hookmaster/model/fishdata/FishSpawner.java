package it.unibo.hookmaster.model.fishdata;

import java.util.Random;

import it.unibo.hookmaster.model.fishdata.movement.DiagonalMovement;
import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.fishdata.movement.MeanderingMovement;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;

/**
 * Represents a fish instance in the game world.
 */
public class FishSpawner {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;
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
        final int y = randomYWithinBand();

        final Position position = new Position(x, y);
        final MovementStrategy strategy = randomMovementStrategy();

        final Fish fish = new Fish(type, position, strategy);
        fish.setDirection(fromLeft ? 1 : -1);
        return fish;
    }

    private int randomYWithinBand() {
        final int minY = (int) Math.round(mapHeight * MIN_Y_RATIO);
        final int maxY = (int) Math.round(mapHeight * MAX_Y_RATIO);
        final int rangeHeight = maxY - minY;
        return minY + random.nextInt(rangeHeight);
    }

    private MovementStrategy randomMovementStrategy() {
        final int choice = random.nextInt(3);
        switch (choice) {
            case 0:
                return new LinearMovement();

            case 1:
                return new DiagonalMovement(TEST_0 + random.nextDouble() * TEST_1);

            default:
                return new MeanderingMovement();
            // in case more movements are added
        }
    }
}
