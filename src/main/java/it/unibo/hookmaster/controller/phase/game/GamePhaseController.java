package it.unibo.hookmaster.controller.phase.game;

import it.unibo.hookmaster.controller.phase.PhaseController;
import it.unibo.hookmaster.view.View;

public class GamePhaseController extends PhaseController {
    private final View<?, GameInputHandler> gameView;

    public GamePhaseController(final View<?, GameInputHandler> gameView) {
        this.gameView = gameView;
        this.gameView.setInputHandler(new InputHandlerImpl());
    }

    @Override
    protected void select() {
        this.gameView.select();
    }

    @Override
    protected void tick(final long deltaTime) {
        
    }

    private class InputHandlerImpl implements GameInputHandler { }
}
