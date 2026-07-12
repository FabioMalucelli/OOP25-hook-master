package it.unibo.hookmaster.fishdata;

import java.util.Random;

/**
 * Represents a fish instance in the game world.
 */
public class FishSpawner {

    private final int mapWidth;
    private final int mapHeight;
    private final Random random = new Random();

    /**
     * Represents a fish instance in the game world.
     * 
     * @param mapWidth the horizontal size of the map
     * @param mapHeight the vertical size of the map
     */
    public FishSpawner(final int mapWidth, final int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Spawns a fish at a random edge (left or right), at a random height.
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
        return fish;
    }
}
