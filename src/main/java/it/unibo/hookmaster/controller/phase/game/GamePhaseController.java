package it.unibo.hookmaster.controller.phase.game;

import java.util.ArrayDeque;
import java.util.Queue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.GameSnapshot;

/**
 * Controller for the game phase of the game.
 * It handles the user input and updates the view for the game phase.
 */
public class GamePhaseController extends AbstractPhaseController {
    private final View<GameSnapshot, GameInputHandler> gameView;
    private final Queue<Runnable> scheduledActions = new ArrayDeque<>();
    private final GameWorld gameWorld;

    /**
     * Creates a new GamePhaseController tied to the given game view.
     * 
     * @param gameView the game view to tie the controller to
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The view does not contain any of the controller state, so it is safe to expose it."
    )
    public GamePhaseController(final GameWorld gameworld, final View<GameSnapshot, GameInputHandler> gameView) {
        this.gameWorld = gameworld;
        this.gameView = gameView;
        //this.gameView.setInputHandler(new InputHandlerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void select() {
        this.gameView.select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tick(final long deltaTime) {
        while (!scheduledActions.isEmpty()) {
            final Runnable action = scheduledActions.remove();
            action.run();
        }
        this.gameWorld.update(deltaTime);
        this.gameView.update(buildSnapshot());
    }

    /**
     * Builds a snapshot of the current game state
     * for the view to render.
     * 
     * @return a snapshot of the current game state
     */
    private GameSnapshot buildSnapshot() {
        return new GameSnapshot(gameWorld.getFishes(), gameWorld.getHook(), gameWorld.getPlayerWallet().getCoins());
    }

    /**
     * Implementation of the GameInputHandler interface.
     */
    private final class InputHandlerImpl implements GameInputHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void pressW() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressW'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseW() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'releaseW'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressA() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressA'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseA() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'releaseA'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressS() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressS'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseS() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'releaseS'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressD() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressD'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void releaseD() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'releaseD'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressShopBtn() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressShopBtn'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressEsc() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressEsc'");
        }

    }
}
