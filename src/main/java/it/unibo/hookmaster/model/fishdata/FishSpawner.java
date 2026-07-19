package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.weather.Weather;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates new eligible fish at the edges of the map.
 */
public class FishSpawner {

    private final double mapWidth;
    private final double mapHeight;
    private final Random random = new Random();

    /**
     * Creates a new fish spawner.
     *
     * @param mapWidth  the horizontal size of the map
     * @param mapHeight the vertical size of the map
     */
    public FishSpawner(final double mapWidth, final double mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    /**
     * Spawns an eligible species at a random edge and height within bounds.
     *
     * @param currentWeather the current weather, used to filter eligible species
     * @return new fish
     */
    public Fish spawnFish(final Weather currentWeather) {
        final List<FishType> eligibleTypes = eligibleTypes(currentWeather);
        final FishType type = eligibleTypes.get(random.nextInt(eligibleTypes.size()));

        final boolean fromLeft = random.nextBoolean();
        final double x = fromLeft ? -50 : mapWidth + 50;
        final double y = randomYWithinBand();

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

    private double randomYWithinBand() {
        return random.nextDouble(mapHeight);
    }
}
