package it.unibo.hookmaster.controller.phase.shop;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.hookmaster.controller.phase.AbstractPhaseController;
import it.unibo.hookmaster.controller.phase.Phase;
import it.unibo.hookmaster.model.GameWorld;
import it.unibo.hookmaster.model.upgrade.UpgradeType;
import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.ShopSnapshot;
import javafx.application.Platform;

/**
 * Controller for the game phase of the game.
 * It handles the user input and updates the view for the game phase.
 */
public class ShopPhaseController extends AbstractPhaseController {
    private final View<ShopSnapshot, ShopInputHandler> shopView;
    private final GameWorld gameWorld;
    private boolean needsRefresh = true;

    /**
     * Creates a new MinigamePhaseController tied to the given minigame view.
     * 
     * @param minigameView the minigame view to tie the controller to
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "The view does not contain any of the controller state, so it is safe to expose it."
    )
    public ShopPhaseController(final GameWorld gameworld, final View<ShopSnapshot, ShopInputHandler> shopView) {
        this.gameWorld = gameworld;
        this.shopView = shopView;
        this.shopView.setInputHandler(new InputHandlerImpl());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void select() {
        this.shopView.select();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void tick(final long deltaTime) {
        if (needsRefresh) {
            this.shopView.update(buildSnapshot());
            this.needsRefresh = false;
        }
    }

    /**
     * Builds a snapshot of the current game state
     * for the view to render.
     * 
     * @return a snapshot of the current game state
     */
    private ShopSnapshot buildSnapshot() {
        return new ShopSnapshot(gameWorld.getShop().getUpgrades(), gameWorld.getPlayerWallet().getCoins());
    }

    /**
     * Implementation of the ShopInputHandler interface.
     */
    private final class InputHandlerImpl implements ShopInputHandler {
        /**
         * {@inheritDoc}
         */
        @Override
        public void pressBuyBtn(UpgradeType upgradeType) {
            gameWorld.getShop().buy(upgradeType, gameWorld.getPlayerWallet());
            needsRefresh = true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void pressBackBtn() {
            getGraph().selectPhase(Phase.GAME);
        }
    }
}
