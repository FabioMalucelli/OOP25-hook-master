package it.unibo.hookmaster.controller.phase.minigame;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.model.fishing.minigame.FishingMinigame;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MinigameSnapshot;

/**
 * Controller for the game phase of the game.
 * It handles the user input and updates the view for the game phase.
 */
public class MinigamePhaseController extends AbstractPhaseController {
    private final View<MinigameSnapshot, MinigameInputHandler> minigameView;
    private final GameWorld gameWorld;

    /**
     * Creates a new MinigamePhaseController tied to the given minigame view.
     * 
     * @param gameworld the game world
     * @param minigameView the minigame view to tie the controller to
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The view does not contain any of the controller state, so it is safe to expose it."
    )
    public MinigamePhaseController(final GameWorld gameworld, final View<MinigameSnapshot, MinigameInputHandler> minigameView) {
        this.gameWorld = gameworld;
        this.minigameView = minigameView;
        this.minigameView.setInputHandler(new InputHandlerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void select() {
        this.minigameView.select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tick(final long deltaTime) {
        if (this.gameWorld.getHook().getCurrentState() != HookState.MINIGAME) {
            getGraph().selectPhase(Phase.GAME);
            return;
        }
        this.gameWorld.update(deltaTime);
        this.minigameView.update(buildSnapshot());
    }

    /**
     * Builds a snapshot of the current game state
     * for the view to render.
     * 
     * @return a snapshot of the current game state
     */
    private MinigameSnapshot buildSnapshot() {
        final FishingMinigame minigame = gameWorld.getHook().getCurrentMinigame();
        return new MinigameSnapshot(minigame.getIndicatorPosition(), minigame.getTargetStart(), minigame.getTargetEnd());
    }

    /**
     * Implementation of the MinigameInputHandler interface.
     */
    private final class InputHandlerImpl implements MinigameInputHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void pressEsc() {
            getGraph().selectPhase(Phase.MENU);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressSpace() {
            gameWorld.getHook().attemptCatch();
        }
    }
}
