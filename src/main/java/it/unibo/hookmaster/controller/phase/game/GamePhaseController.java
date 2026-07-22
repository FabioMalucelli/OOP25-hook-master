package it.unibo.hookmaster.controller.phase.game;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.model.fishing.hook.Hook;
import it.unibo.hookmaster.model.fishing.hook.HookState;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.GameSnapshot;

/**
 * Controller for the game phase of the game. It handles the user input and updates the view for the
 * game phase.
 */
public class GamePhaseController extends AbstractPhaseController {
    private final View<GameSnapshot, GameInputHandler> gameView;
    private final GameWorld gameWorld;

    /**
     * Creates a new GamePhaseController tied to the given game view.
     * 
     * @param gameworld the game world
     * @param gameView the game view to tie the controller to
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
            justification = "The view does not contain any of the controller state, so it is safe to expose it.")
    public GamePhaseController(final GameWorld gameworld,
            final View<GameSnapshot, GameInputHandler> gameView) {
        this.gameWorld = gameworld;
        this.gameView = gameView;
        this.gameView.setInputHandler(new InputHandlerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void select() {
        // Reset the hook state and clear any scheduled actions when the game phase is selected.
        // This is to avoid any unexpected behavior when switching between phases in which
        // the hook could keep moving in a direction even if the user is not pressing any key.
        final Hook hook = gameWorld.getHook();
        hook.setMovingDown(false);
        hook.setMovingUp(false);
        hook.setMovingLeft(false);
        hook.setMovingRight(false);
        this.gameView.select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tick(final long deltaTime) {
        if (this.gameWorld.getHook().getCurrentState() == HookState.MINIGAME) {
            getGraph().selectPhase(Phase.MINIGAME);
            return;
        }
        this.gameWorld.update(deltaTime);
        this.gameView.update(buildSnapshot());
    }

    /**
     * Builds a snapshot of the current game state for the view to render.
     * 
     * @return a snapshot of the current game state
     */
    private GameSnapshot buildSnapshot() {
        return new GameSnapshot(gameWorld.getFishes(), gameWorld.consumeDeadFishes(),
                gameWorld.getHook(), gameWorld.getPlayerWallet().getCoins(),
                gameWorld.getWeather());
    }

    /**
     * Implementation of the GameInputHandler interface. All the operations are performed directly
     * on the game world, because, since the game loop is running in the same thread as the view, we
     * are sure that the input events are not processed in the middle of a tick.
     */
    private final class InputHandlerImpl implements GameInputHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void pressW() {
            gameWorld.getHook().setMovingUp(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseW() {
            gameWorld.getHook().setMovingUp(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressA() {
            gameWorld.getHook().setMovingLeft(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseA() {
            gameWorld.getHook().setMovingLeft(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressS() {
            gameWorld.getHook().setMovingDown(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseS() {
            gameWorld.getHook().setMovingDown(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressD() {
            gameWorld.getHook().setMovingRight(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseD() {
            gameWorld.getHook().setMovingRight(false);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressShopBtn() {
            getGraph().selectPhase(Phase.SHOP);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressEsc() {
            getGraph().selectPhase(Phase.MENU);
        }

    }
}
