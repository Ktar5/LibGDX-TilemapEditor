package com.ktar5.tileeditor.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ktar5.tileeditor.Main;
import com.ktar5.tileeditor.scene.utils.ZoomablePannableWidget;

public class Input implements InputProcessor {

    public Input() {

    }

    public boolean isKeyPressed(int key) {
        return Gdx.input.isKeyPressed(key);
    }

    @Override
    public boolean keyDown(int keycode) {
        if(UndoRedoInput.keyPressed(keycode)){
            return true;
        }else if((isKeyPressed(Keys.CONTROL_LEFT) || isKeyPressed(Keys.CONTROL_RIGHT)) && keycode == Keys.S){
            if(Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab() == null){
                return false;
            }
            Main.getInstance().getRoot().getTabHoldingPane().getCurrentTab().save();
        }
        return UndoRedoInput.keyPressed(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        Actor hit = Main.getInstance().getRoot().hit(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), false);
        if (hit instanceof ZoomablePannableWidget) {
            ((ZoomablePannableWidget) hit).scale(amount);
        }
        return true;
    }
}
