package it.unibo.hookmaster.model.fishdata;

import it.unibo.hookmaster.model.fishdata.movement.DiagonalMovement;
import it.unibo.hookmaster.model.fishdata.movement.LinearMovement;
import it.unibo.hookmaster.model.fishdata.movement.MeanderingMovement;
import it.unibo.hookmaster.model.fishdata.movement.MovementStrategy;
import it.unibo.hookmaster.model.weather.Weather;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates new eligible fish at the edges of the map.
 */
public class FishSpawner {

    private static final double MIN_DIAGONAL_RATIO = 0.2;
    private static final double MAX_DIAGONAL_RATIO = 0.5;
    private static final int MOVEMENT_STRATEGY_COUNT = 3;

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
    public Fish spawnFish(final FishManager fishManager, final Weather currentWeather) {
        final List<FishType> eligibleTypes = eligibleTypes(currentWeather);
        final FishType type = eligibleTypes.get(random.nextInt(eligibleTypes.size()));

        final boolean fromLeft = random.nextBoolean();
        final double x = fromLeft ? -50 : mapWidth + 50;
        final double y = randomYWithinBand();

        Fish fish = new FishImpl(type, new Position(x, y), randomMovementStrategy());
        if (type.isPredator()) {
            fish = new PredatorFishImpl(fishManager, fish);
        }
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

    private MovementStrategy randomMovementStrategy() {
        final int choice = random.nextInt(MOVEMENT_STRATEGY_COUNT);
        switch (choice) {
            case 0:
                return new LinearMovement();
            case 1:
                final double ratio = MIN_DIAGONAL_RATIO
                        + random.nextDouble() * (MAX_DIAGONAL_RATIO - MIN_DIAGONAL_RATIO);
                return new DiagonalMovement(ratio);
            default:
                return new MeanderingMovement();
        }
    }

    private double randomYWithinBand() {
        return random.nextDouble(mapHeight);
    }
}
