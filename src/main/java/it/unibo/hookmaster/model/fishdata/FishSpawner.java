package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.weather.Weather;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates new fish at the map edges, choosing species eligible under
 * the current weather condition.
 */
public class FishSpawner {

    private static final double MIN_Y_RATIO = 0.30;
    private static final double MAX_Y_RATIO = 0.95;

    private final int mapWidth;
    private final int mapHeight;
    private final Random random = new Random();

    /**
     * Creates a new fish spawner.
     *
     * @param mapWidth  the horizontal size of the map
     * @param mapHeight the vertical size of the map
     */
    public FishSpawner(final int mapWidth, final int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Spawns a fish at a random edge (left or right), at a random height
     * within the allowed vertical band, choosing only among species
     * eligible under the given weather condition.
     *
     * @param currentWeather the current weather, used to filter eligible species
     * @return new fish
     */
    public Fish spawnFish(final Weather currentWeather) {
        final List<FishType> eligibleTypes = eligibleTypes(currentWeather);
        final FishType type = eligibleTypes.get(random.nextInt(eligibleTypes.size()));

        final boolean fromLeft = random.nextBoolean();
        final int x = fromLeft ? -50 : mapWidth + 50;
        final int y = randomYWithinBand();

        final Fish fish = new Fish(type, new Position(x, y), type.createDefaultMovementStrategy());
        fish.setDirection(fromLeft ? 1 : -1);
        return fish;
    }

    private List<FishType> eligibleTypes(final Weather currentWeather) {
        final List<FishType> eligible = new ArrayList<>();
        for (final FishType type : FishType.values()) {
            if (!type.isStormOnly() || currentWeather == Weather.STORMY) {
                eligible.add(type);
            }
        }
        return eligible;
    }

    private int randomYWithinBand() {
        final int minY = (int) Math.round(mapHeight * MIN_Y_RATIO);
        final int maxY = (int) Math.round(mapHeight * MAX_Y_RATIO);
        return minY + random.nextInt(maxY - minY);
    }
}
