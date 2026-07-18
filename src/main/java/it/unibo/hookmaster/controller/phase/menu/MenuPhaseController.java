package it.unibo.hookmaster.controller.phase.menu;

import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.controller.phase.PhaseController;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;
import javafx.application.Platform;

public class MenuPhaseController extends PhaseController {
    private final View<MenuSnapshot, MenuInputHandler> menuView;

    public MenuPhaseController(final View<MenuSnapshot, MenuInputHandler> menuView) {
        this.menuView = menuView;
        menuView.setInputHandler(new InputHandlerImpl());
    }

    @Override
    protected void select() {
        this.menuView.select();
    }

    @Override
    protected void tick(final long deltaTime) {
        this.menuView.update(new MenuSnapshot(false));
    }

    private class InputHandlerImpl implements MenuInputHandler {
        @Override
        public void pressPlayButton() {
            getGraph().selectPhase(Phase.GAME);
        }

        @Override
        public void pressLoadButton() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'pressLoadButton'");
        }

        @Override
        public void pressExitButton() {
            Platform.exit();
        }
        
    }
}
