package it.unibo.hookmaster.controller;

import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.controller.phase.PhaseGraph;
import it.unibo.hookmaster.controller.phase.game.GameInputHandler;
import it.unibo.hookmaster.controller.phase.game.GamePhaseController;
import it.unibo.hookmaster.controller.phase.menu.MenuPhaseController;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.model.GameWorldImpl;
import it.unibo.hookmaster.controller.phase.menu.MenuInputHandler;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.GameSnapshot;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.animation.AnimationTimer;

/**
 * Implementation of the GameController.
 */
public class GameControllerImpl implements GameController {
    private static final long MIN_TICK_INTERVAL = 16_000_000; // 16 ms in nanoseconds, ~60 tick/s

    private final LoopTimer loopTimer = new LoopTimer();
    private final PhaseGraph phaseGraph;
    private final GameWorld gameWorld;

    /**
     * Creates the default controller.
     * 
     * @param menuView the view of the menu phase
     * @param gameView the view of the game phase
     */
    public GameControllerImpl(
        final View<MenuSnapshot, MenuInputHandler> menuView,
        final View<GameSnapshot, GameInputHandler> gameView
    ) {
        this.gameWorld = new GameWorldImpl(800, 600);
        this.phaseGraph = new PhaseGraph();
        this.phaseGraph.registerPhase(Phase.MENU, new MenuPhaseController(menuView));
        this.phaseGraph.registerPhase(Phase.GAME, new GamePhaseController(gameWorld, gameView));
    }

    /**
     * @inheritDoc
     */
    @Override
    public void run() {
        loopTimer.start();
    }

    /**
     * The actual game loop, which exploits the JavaFX event loop
     * to have the game loop running in the JavaFX thread, to avoid
     * concurrency issues with the view.
     * Running the controller in a separate thread would not gain
     * any real benefits, since the model is not thread-safe and
     * therefore the game loop and the view would have to be
     * synchronized anyway.
     */
    private final class LoopTimer extends AnimationTimer {
        private long lastTime = -1;

        @Override
        public void handle(final long now) {
            if (lastTime < 0) {
                lastTime = now;
                phaseGraph.selectPhase(Phase.MENU);
                return;
            }
            if (now - lastTime < MIN_TICK_INTERVAL) {
                return;
            }
            final long deltaTime = (now - lastTime) / 1_000_000;
            lastTime = now;
            phaseGraph.tick(deltaTime);
        }

    }
}
