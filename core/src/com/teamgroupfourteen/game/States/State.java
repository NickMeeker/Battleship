package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.teamgroupfourteen.game.GameButton;

/**
 * Created by nick on 2/28/18.
 */

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt); // dt = delta time, difference between one frame rendered and next frame rendered
    public abstract void render(SpriteBatch sb);
    public abstract void dispose();

    public Vector3 getInputRegion(){
        Vector3 touchPosition = new Vector3();
        touchPosition.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        cam.unproject(touchPosition);
        return touchPosition;
    }

    public boolean isTouched(Vector3 touchPosition, GameButton button){
        return (touchPosition.x >= button.getX() && touchPosition.x <= button.getX() + button.getWidth()
                && touchPosition.y >= button.getY() && touchPosition.y <= button.getY() + button.getHeight());
    }

}
