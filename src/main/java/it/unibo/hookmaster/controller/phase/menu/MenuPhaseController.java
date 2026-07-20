package it.unibo.hookmaster.controller.phase.menu;

import it.unibo.hookmaster.controller.phase.Phase;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.application.Platform;

/**
 * Controller for the menu phase of the game.
 * It handles the user input and updates the view for the menu phase.
 */
public class MenuPhaseController extends AbstractPhaseController {
    private final View<MenuSnapshot, MenuInputHandler> menuView;
    private boolean isGameStarted = false;

    /**
     * Creates a new MenuPhaseController tied to the given menu view.
     * 
     * @param menuView the menu view
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The view does not contain any of the controller state, so it is safe to expose it."
    )
    public MenuPhaseController(final View<MenuSnapshot, MenuInputHandler> menuView) {
        this.menuView = menuView;
        menuView.setInputHandler(new InputHandlerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void select() {
        this.menuView.select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tick(final long deltaTime) {
        this.menuView.update(new MenuSnapshot(isGameStarted));
    }

    /**
     * Implementation of the menu input handler
     * as an inner class, so that it can access
     * data from the controller.
     */
    private final class InputHandlerImpl implements MenuInputHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void pressPlayButton() {
            isGameStarted = true;
            getGraph().selectPhase(Phase.GAME);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressLoadButton() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressLoadButton'");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressExitButton() {
            Platform.exit();
        }
    }
}
