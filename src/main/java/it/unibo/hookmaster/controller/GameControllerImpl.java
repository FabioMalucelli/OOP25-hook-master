package it.unibo.hookmaster.controller;

import it.unibo.hookmaster.controller.phase.MenuController;
import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.controller.phase.PhaseGraph;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.animation.AnimationTimer;

/**
 * Implementation of the GameController.
 */
public class GameControllerImpl implements GameController {
    private final LoopTimer loopTimer = new LoopTimer();
    private final PhaseGraph phaseGraph;

    public GameControllerImpl(final View<MenuSnapshot> menuView) {
        this.phaseGraph = new PhaseGraph();
        this.phaseGraph.registerPhase(Phase.MENU, new MenuController(menuView));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        loopTimer.start();
    }

    private class LoopTimer extends AnimationTimer {
        private long lastTime = -1;

        @Override
        public void handle(long now) {
            if (lastTime < 0) {
                lastTime = now;
                phaseGraph.selectPhase(Phase.MENU);
                return;
            }
            final long deltaTime = now - lastTime;
            lastTime = now;
            phaseGraph.tick(deltaTime);
        }

    }
}
