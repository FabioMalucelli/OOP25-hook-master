package it.unibo.hookmaster.model;

/**
 * This interface represents the game world, which is the main model of the game.
 * It contains all the game entities and their states, and it is responsible for updating them.
 * The GameWorld is the main entry point for the game logic, and it is used by the GameController to run the game loop.
 */
public interface GameWorld {
    /**
     * Advances the game state by the given delta time.
     * This method is called by the game loop to update the game world.
     * It updates all the game entities and their states.
     * 
     * @param deltaTime the amount of milliseconds of which the game world should advance.
     *     This is the time elapsed since the last frame.
     */
    void update(long deltaTime);
}
