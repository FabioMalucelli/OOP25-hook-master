package it.unibo.hookmaster.controller;

/**
 * Implementation of the GameController.
 */
public class GameControllerImpl implements GameController {
    private static final long FRAME_TIME = 16; // 60 FPS

    /**
     * Constructs the game controller.
     */
    public GameControllerImpl() {
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        long lastTime = System.currentTimeMillis();
        while (true) {
            final long currentTime = System.currentTimeMillis();
            final long deltaTime = currentTime - lastTime;
            if (deltaTime >= FRAME_TIME) {
                lastTime = currentTime;
                tick(deltaTime);
            }
        }
    }

    /**
     * Runs an iteration of the game loop, updating the game world and rendering the view.
     * 
     * @param deltaTime the amount of milliseconds elapsed since last tick.
     */
    private void tick(final long deltaTime) { }

}
