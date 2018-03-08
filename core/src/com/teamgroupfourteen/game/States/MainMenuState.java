package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamgroupfourteen.game.Battleship;

/**
 * Created by nick on 2/28/18.
 */

public class MainMenuState extends State {
    private Texture background;
    private Texture playBtn;
    public MainMenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        background = new Texture("bg.png");
        playBtn = new Texture("playbtn.png");
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            System.out.println("Transition");
            // gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }


    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }

}
