package com.ktar5.tileeditor.scene.menu;


import com.kotcrab.vis.ui.widget.Menu;
import com.kotcrab.vis.ui.widget.MenuItem;
import com.ktar5.tileeditor.Main;
import com.ktar5.tileeditor.util.KChangeListener;

class EditMenu extends Menu {

    EditMenu() {
        super("Edit");
        MenuItem undo = new MenuItem("Undo");
        undo.addListener(new KChangeListener((changeEvent, actor) -> {
            if (Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab() != null) {
                Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab().getTabbable().undo();
            }
        }));
        this.addItem(undo);
        MenuItem redo = new MenuItem("Redo");
        redo.addListener(new KChangeListener((changeEvent, actor) -> {
            if (Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab() != null) {
                Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab().getTabbable().redo();
            }
        }));
        this.addItem(redo);
    }

}
