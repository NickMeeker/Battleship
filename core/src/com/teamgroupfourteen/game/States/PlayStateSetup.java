package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;

/**
 * Created by Jeremy on 4/2/2018.
 */

//State that allows players to place their ships in preparation of the game
public class PlayStateSetup extends State {
    private Texture background;
    private TextureRegion mainBackground;


    public PlayStateSetup(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );

    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();


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
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("Play State Setup Disposed");
    }

}
