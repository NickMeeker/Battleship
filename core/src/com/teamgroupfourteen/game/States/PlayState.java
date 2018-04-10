package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;
import com.teamgroupfourteen.game.Player.Player;

/**
 * Created by nick on 2/28/18.
 */

public class PlayState extends State {

    private Player player1, player2;

    //textures
    private Texture background;
    private TextureRegion mainBackground;


    public PlayState(GameStateManager gsm, Player player1, Player player2){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );

        this.player1 = player1;
        this.player2 = player2;

        gsm.push(new PlayStateSetup(gsm, player1));System.out.println("check");
        gsm.push(new PlayStateSetup(gsm, player2));
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

        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.WIDTH);

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("Play State Disposed");
    }

}