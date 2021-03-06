package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Authentication.CredentialsManager;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;

/**
 * Created by derek on 4/13/18.
 */

public class OptionsState extends State{

    private Texture background;
    private TextureRegion mainBackground;
    private GameButton titleBtn;
    private GameButton soundFXBtn;
    private GameButton soundOnBtn;
    private GameButton soundOffBtn;
    private GameButton logoutBtn;
    private GameButton backBtn;

    public OptionsState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );

        // Setup background
        background = new Texture("MainBackground.png");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);

        // Setup buttons & title
        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        soundFXBtn = new GameButton(Battleship.WIDTH/8, cam.position.y + 100, 360, 100, "SoundFXButton.png");
        soundOnBtn = new GameButton(Battleship.WIDTH/2 - 100, 300, 200, 200, "soundOn.png");
        soundOffBtn = new GameButton(Battleship.WIDTH/2 -100, 300, 200, 200, "soundOff.png");
        logoutBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 200, 360, 100, "LogoutButton.png");
        backBtn = new GameButton(0, Battleship.HEIGHT-64, 64, 64, "Arrow_left.png");
    }

    @Override
    public void handleInput() {

        // Setup touch reactions
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, soundFXBtn)) {
                Battleship.soundOn = !(Battleship.soundOn);
            } else if (isTouched(touchPosition, soundOnBtn)) {
                    Battleship.soundOn = !(Battleship.soundOn);
            } else if (isTouched(touchPosition, soundOffBtn)) {
                Battleship.soundOn = !(Battleship.soundOn);
            } else if (isTouched(touchPosition, backBtn)) {
                gsm.pop();
            } else if (isTouched(touchPosition, logoutBtn)) {
                CredentialsManager cm = new CredentialsManager();
                //cm.deleteAuthData();
                gsm.set(new LoginState(gsm));
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
        sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
        sb.draw(soundFXBtn.getImage(), soundFXBtn.getX(), soundFXBtn.getY(), soundFXBtn.getWidth(), soundFXBtn.getHeight());
        sb.draw(logoutBtn.getImage(), logoutBtn.getX(), logoutBtn.getY(), logoutBtn.getWidth(), logoutBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

        // display correct icon per sound options selected.
        if(Battleship.soundOn == true){
            sb.draw(soundOnBtn.getImage(), soundOnBtn.getX(), soundOnBtn.getY(), soundOnBtn.getWidth(), soundOnBtn.getHeight());
        } else if(Battleship.soundOn == false){
            sb.draw(soundOffBtn.getImage(), soundOffBtn.getX(), soundOffBtn.getY(), soundOffBtn.getWidth(), soundOffBtn.getHeight());
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        titleBtn.disposeAssets();
        soundFXBtn.disposeAssets();
        soundOnBtn.disposeAssets();
        soundOffBtn.disposeAssets();
        backBtn.disposeAssets();
    }

}
