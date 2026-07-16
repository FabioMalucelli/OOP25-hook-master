package it.unibo.hookmaster.controller;

import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.animation.AnimationTimer;

/**
 * Implementation of the GameController.
 */
public class GameControllerImpl implements GameController {
    private final LoopTimer loopTimer = new LoopTimer();
    private final View<MenuSnapshot> menuView;

    public GameControllerImpl(final View<MenuSnapshot> menuView) {
        this.menuView = menuView;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        loopTimer.start();
    }

    /**
     * Runs an iteration of the game loop, updating the game world and rendering the view.
     * 
     * @param deltaTime the amount of milliseconds elapsed since last tick.
     */
    private void tick(final long deltaTime) {
        menuView.select();
        menuView.update(new MenuSnapshot(false));
    }

    private class LoopTimer extends AnimationTimer {
        private long lastTime = -1;

        @Override
        public void handle(long now) {
            if (lastTime < 0) {
                lastTime = now;
                return;
            }
            final long deltaTime = now - lastTime;
            lastTime = now;
            tick(deltaTime);
        }

    }
}
