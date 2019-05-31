package com.ktar5.tileeditor.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ktar5.tileeditor.Main;

class UndoRedoInput {
    private static boolean isKeyPressed(int key){
        return Gdx.input.isKeyPressed(key);
    }


    static boolean keyPressed(int keycode){
        if (keycode != Input.Keys.Z){
            return false;
        }
        if(Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab() == null){
            return false;
        }
        if(isKeyPressed(Input.Keys.CONTROL_LEFT) || isKeyPressed(Input.Keys.CONTROL_RIGHT)){
            if(isKeyPressed(Input.Keys.SHIFT_LEFT) || isKeyPressed(Input.Keys.SHIFT_RIGHT)){
                //REDO
//                System.out.println("Redo");
                Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab().getTabbable().redo();
            }else {
                //UNDO
//                System.out.println("Undo");
                Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab().getTabbable().undo();
            }
        }else {
            return false;
        }
        return true;
    }

}
