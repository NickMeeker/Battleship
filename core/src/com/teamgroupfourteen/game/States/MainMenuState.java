package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.teamgroupfourteen.game.Battleship;

import java.awt.Button;

/**
 * Created by nick on 2/28/18.
 */

public class MainMenuState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private Texture playBtn;
    private Texture singlePlayerBtn;
    private Texture multiPlayerBtn;
    private Texture spectatorBtn;
    private Texture storeBtn;
    private Texture friendsBtn;
    private Texture creditsBtn;




    public MainMenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );
        singlePlayerBtn = new Texture("button_single-player.png");
        multiPlayerBtn = new Texture("button_multiplayer.png");
        friendsBtn = new Texture("button_friends.png");
        spectatorBtn = new Texture("button_spectator.png");
        creditsBtn = new Texture("button_credits.png");


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
        // TODO: Figure out why this works
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.WIDTH);
        sb.draw(singlePlayerBtn, cam.position.x - singlePlayerBtn.getWidth() / 2, cam.position.y + 50, singlePlayerBtn.getWidth(), singlePlayerBtn.getHeight());
        sb.draw(multiPlayerBtn, cam.position.x - multiPlayerBtn.getWidth() / 2, cam.position.y, multiPlayerBtn.getWidth(), multiPlayerBtn.getHeight());
        sb.draw(spectatorBtn,cam.position.x - spectatorBtn.getWidth() / 2, cam.position.y - 50, spectatorBtn.getWidth(), spectatorBtn.getHeight());
        sb.draw(creditsBtn, cam.position.x - creditsBtn.getWidth() / 2, cam.position.y - 100, creditsBtn.getWidth(), creditsBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu State Disposed");
    }

}
