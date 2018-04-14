package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;


/**
 * Created by nick on 2/28/18.
 */

public class MainMenuState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private User user;
    private GameButton singlePlayerBtn;
    private GameButton multiPlayerBtn;
    private GameButton spectatorBtn;
    private GameButton storeBtn;
    private GameButton friendsBtn;
    private GameButton creditsBtn;
    private GameButton optionsBtn;


    public MainMenuState(GameStateManager gsm, User user){
        super(gsm);
        this.user = user;
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );
        singlePlayerBtn = new GameButton(Battleship.WIDTH/8, cam.position.y + 100, 360, 100, "SinglePlayerButton.png");
        multiPlayerBtn = new GameButton(Battleship.WIDTH/8, cam.position.y, 360, 100, "MultiPlayerButton.png");
        creditsBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 100, 360, 100, "CreditsButton.png");
        spectatorBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 200, 360, 100, "SpectatorButton.png");
        optionsBtn = new GameButton(Battleship.WIDTH-64, Battleship.HEIGHT - 64, 64, 64, "Options.png");

    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, singlePlayerBtn)){
                System.out.println("Transition to singleplayer");
                Player player1 = new Player(null);
                Player player2 = new Player(null);
                //gsm.push(new PlayState(gsm, player1, player2, true, false));
            } else if(isTouched(touchPosition, multiPlayerBtn)){
                gsm.push(new MultiplayerTypeState(gsm, user));
            } else if(isTouched(touchPosition, spectatorBtn)) {
                System.out.println("Transition to spectator button");
            } else if(isTouched(touchPosition, optionsBtn)){
                gsm.push(new OptionsState(gsm));
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
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        sb.draw(singlePlayerBtn.getImage(), singlePlayerBtn.getX(), singlePlayerBtn.getY(), singlePlayerBtn.getWidth(), singlePlayerBtn.getHeight());
        sb.draw(multiPlayerBtn.getImage(), multiPlayerBtn.getX(), multiPlayerBtn.getY(), multiPlayerBtn.getWidth(), multiPlayerBtn.getHeight());
        sb.draw(spectatorBtn.getImage(), spectatorBtn.getX(), spectatorBtn.getY(), spectatorBtn.getWidth(), spectatorBtn.getHeight());
        sb.draw(creditsBtn.getImage(), creditsBtn.getX(), creditsBtn.getY(), creditsBtn.getWidth(), creditsBtn.getHeight());
        sb.draw(optionsBtn.getImage(), optionsBtn.getX(), optionsBtn.getY(), optionsBtn.getWidth(), optionsBtn.getHeight());
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
