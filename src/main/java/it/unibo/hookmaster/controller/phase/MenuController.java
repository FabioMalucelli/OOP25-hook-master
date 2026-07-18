package it.unibo.hookmaster.controller.phase;

import it.unibo.hookmaster.view.View;
import it.unibo.hookmaster.view.snapshot.MenuSnapshot;

public class MenuController extends PhaseController {
    private final View<MenuSnapshot> menuView;

    public MenuController(final View<MenuSnapshot> menuView) {
        this.menuView = menuView;
    }

    @Override
    public void select() {
        this.menuView.select();
    }

    @Override
    public void tick(final long deltaTime) {
        this.menuView.update(new MenuSnapshot(false));
    }
}
