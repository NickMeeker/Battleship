package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;


/**
 * Created by nick on 2/28/18.
 */

public class MainMenuState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private GameButton singlePlayerBtn;
    private GameButton multiPlayerBtn;
    private GameButton spectatorBtn;
    private GameButton storeBtn;
    private GameButton friendsBtn;
    private GameButton creditsBtn;




    public MainMenuState(GameStateManager gsm){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );
        singlePlayerBtn = new GameButton(cam.position.x - 169 / 2, cam.position.y + 50, 169, 42, "button_single-player.png");
        multiPlayerBtn = new GameButton(cam.position.x - 158 / 2, cam.position.y, 158, 42, "button_multiplayer.png");
        creditsBtn = new GameButton(cam.position.x - 114 / 2, cam.position.y - 50, 114, 42, "button_credits.png");
        spectatorBtn = new GameButton(cam.position.x - 138 / 2, cam.position.y - 100, 138, 42, "button_spectator.png");

    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, singlePlayerBtn)){
                System.out.println("Transition to singleplayer");
                Player player1 = new Player(null);
                Player player2 = new Player(null);
                gsm.push(new PlayState(gsm, player1, player2, true, false));
            } else if(isTouched(touchPosition, multiPlayerBtn)){
                System.out.println("Transition to multiplayer");
                //gsm.push(new MultiplayerState(gsm));
            } else if(isTouched(touchPosition, spectatorBtn)) {
                System.out.println("Transition to spectator button");
            } else if(isTouched(touchPosition, creditsBtn)){
                System.out.println("Transition to credits button");
            }

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
        sb.draw(singlePlayerBtn.getImage(), singlePlayerBtn.getX(), singlePlayerBtn.getY(), singlePlayerBtn.getWidth(), singlePlayerBtn.getHeight());
        sb.draw(multiPlayerBtn.getImage(), multiPlayerBtn.getX(), multiPlayerBtn.getY(), multiPlayerBtn.getWidth(), multiPlayerBtn.getHeight());
        sb.draw(spectatorBtn.getImage(), spectatorBtn.getX(), spectatorBtn.getY(), spectatorBtn.getWidth(), spectatorBtn.getHeight());
        sb.draw(creditsBtn.getImage(), creditsBtn.getX(), creditsBtn.getY(), creditsBtn.getWidth(), creditsBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        singlePlayerBtn.disposeAssets();
        multiPlayerBtn.disposeAssets();
        spectatorBtn.disposeAssets();
        creditsBtn.disposeAssets();
        System.out.println("Menu State Disposed");
    }

}
