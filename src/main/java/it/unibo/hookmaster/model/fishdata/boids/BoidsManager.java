package it.unibo.hookmaster.model.fishdata.boids;

import java.util.ArrayList;
import java.util.List;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.model.fishdata.Fish;
import it.unibo.hookmaster.model.fishdata.FishManager;
import it.unibo.hookmaster.model.fishdata.FishSpawner;
import it.unibo.hookmaster.model.fishdata.Position;
import it.unibo.hookmaster.model.fishdata.movement.MovementTime;
import it.unibo.hookmaster.model.weather.Weather;

/**
 * Manages the boids in the simulation, including their spawning, movement, and removal.
 */
public final class BoidsManager {
    private static final int NUMBER_OF_BOIDS = 10;
    private static final double CENTERING_FACTOR = 0.0005;
    private static final double AVOID_FACTOR = 0.05;
    private static final double MATCHING_FACTOR = 0.05;

    private final double maxHeight;
    private final FishSpawner spawner;
    private final FishManager fishManager;

    /**
     * Creates a new BoidsManager.
     * 
     * @param maxHeight the maximum height of the simulation area.
     * @param spawner the fish spawner.
     * @param fishManager the fish manager.
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
            justification = "The spawner and fishManager are used to spawn and manage fishes.")
    public BoidsManager(final double maxHeight, final FishSpawner spawner,
            final FishManager fishManager) {
        this.maxHeight = maxHeight;
        this.spawner = spawner;
        this.fishManager = fishManager;
    }

    /**
     * Spawns a number of boids in the simulation.
     */
    public void spawnBoids() {
        for (int i = 0; i < NUMBER_OF_BOIDS; i++) {
            final Fish fish = this.spawner.spawnFish(null, Weather.CLEAR, true);
            final double angle = Math.random() * 2 * Math.PI;
            final double velocityX = Math.cos(angle) * fish.getSpeed();
            final double velocityY = Math.sin(angle) * fish.getSpeed();
            this.fishManager.addFish(new Boid(fish, velocityX, velocityY));
        }
    }

    /**
     * Removes a boid from the simulation.
     * 
     * @param fish the boid to remove.
     */
    public void removeBoid(final Fish fish) {
        this.fishManager.removeFish(fish);
    }

    /**
     * Returns a list of all boids in the simulation.
     * 
     * @return a list of all boids in the simulation.
     */
    public List<Boid> getBoids() {
        return (ArrayList<Boid>) this.fishManager.getFishes().stream()
                .filter(f -> f instanceof Boid).map(f -> (Boid) f)
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * Updates the position and velocity of all boids in the simulation.
     * 
     * @param deltaTime the time elapsed since the last update.
     */
    public void update(final long deltaTime) {
        move(deltaTime);
    }

    /**
     * Moves the boids based on their current velocity and the positions of nearby boids.
     * 
     * @param deltaTime the time elapsed since the last update.
     */
    private void move(final long deltaTime) {
        for (final Boid boid : this.getBoids()) {
            double closeDx = 0;
            double closeDy = 0;
            double xPosAvg = 0;
            double yPosAvg = 0;
            double xVelAvg = 0;
            double yVelAvg = 0;
            int neighbourCount = 0;
            final double visualRange =
                    boid.getCollisionArea().getWidth() + boid.getCollisionArea().getHeight();
            final double protectedRange = boid.getCollisionArea().getWidth() / 2.0
                    + boid.getCollisionArea().getHeight() / 2.0;

            for (final Boid otherBoid : this.getBoids()) {
                if (otherBoid.equals(boid)) {
                    continue;
                }

                final double dx = boid.getX() - otherBoid.getX();
                final double dy = boid.getY() - otherBoid.getY();

                if (Math.abs(dx) < visualRange && Math.abs(dy) < visualRange) {
                    final double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < protectedRange) {
                        closeDx += boid.getX() - otherBoid.getX();
                        closeDy += boid.getY() - otherBoid.getY();
                    } else if (distance < visualRange) {
                        xPosAvg += otherBoid.getX();
                        yPosAvg += otherBoid.getY();
                        xVelAvg += otherBoid.getVelocityX();
                        yVelAvg += otherBoid.getVelocityY();
                        neighbourCount++;
                    }
                }
            }

            if (neighbourCount > 0) {
                xPosAvg /= neighbourCount;
                yPosAvg /= neighbourCount;
                xVelAvg /= neighbourCount;
                yVelAvg /= neighbourCount;

                boid.setVelocityX(boid.getVelocityX() + (xPosAvg - boid.getX()) * CENTERING_FACTOR
                        + (xVelAvg - boid.getVelocityX()) * MATCHING_FACTOR);
                boid.setVelocityY(boid.getVelocityY() + (yPosAvg - boid.getY()) * CENTERING_FACTOR
                        + (yVelAvg - boid.getVelocityY()) * MATCHING_FACTOR);
            }

            boid.setVelocityX(boid.getVelocityX() + closeDx * AVOID_FACTOR);
            boid.setVelocityY(boid.getVelocityY() + closeDy * AVOID_FACTOR);

            final double speed = Math.sqrt((boid.getVelocityX() * boid.getVelocityX())
                    + (boid.getVelocityY() * boid.getVelocityY()));
            if (speed > boid.getSpeed()) {
                boid.setVelocityX(boid.getVelocityX() / speed * boid.getSpeed());
                boid.setVelocityY(boid.getVelocityY() / speed * boid.getSpeed());
            }

            final double frameScale = MovementTime.frameScale(deltaTime);
            final double halfHeight = boid.getCollisionArea().getHeight() / 2.0;
            final double minY = halfHeight;
            final double maxY = this.maxHeight - halfHeight;

            final double newX = boid.getX() + (boid.getVelocityX() * frameScale);
            double newY = boid.getY() + (boid.getVelocityY() * frameScale);

            if (newY < minY) {
                newY = minY;
                boid.setVelocityY(Math.abs(boid.getVelocityY()));
            } else if (newY > maxY) {
                newY = maxY;
                boid.setVelocityY(-Math.abs(boid.getVelocityY()));
            }

            boid.setPosition(new Position(newX, newY));
            boid.setDirection((int) Math.signum(boid.getVelocityX()));
        }
    }
}
