package it.unibo.hookmaster.controller.phase.menu;

import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.model.GameWorld;

import java.io.File;
import java.io.IOException;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.application.Platform;

/**
 * Controller for the menu phase of the game. It handles the user input and updates the view for the
 * menu phase.
 */
public class MenuPhaseController extends AbstractPhaseController {
    private final View<MenuSnapshot, MenuInputHandler> menuView;
    private boolean isGameStarted;
    private final GameWorld gameWorld;

    /**
     * Creates a new MenuPhaseController tied to the given menu view.
     * 
     * @param gameWorld the game world
     * @param menuView the menu view
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2",
            justification = "The view does not contain any of the controller state, so it is safe to expose it.")
    public MenuPhaseController(final GameWorld gameWorld,
            final View<MenuSnapshot, MenuInputHandler> menuView) {
        this.gameWorld = gameWorld;
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
     * Implementation of the menu input handler as an inner class, so that it can access data from
     * the controller.
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
        public void pressLoadButton(final File file) throws IllegalArgumentException {
            try {
                final GameWorld.Memento memento = (GameWorld.Memento) SaveManager.load(file);
                gameWorld.restoreFromMemento(memento);
            } catch (final IOException | ClassNotFoundException e) {
                throw new IllegalArgumentException(
                        "Failed to load the game state from the file: " + file.getAbsolutePath());
            }
            pressPlayButton();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressExitButton() {
            Platform.exit();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressSaveButton(final File file) {
            try {
                SaveManager.save(gameWorld.createMemento(), file);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}
