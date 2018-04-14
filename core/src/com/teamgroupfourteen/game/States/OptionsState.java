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
 * Created by derek on 4/13/18.
 */

public class OptionsState extends State{

    private Texture background;
    private TextureRegion mainBackground;
    private GameButton soundFXBtn;
    private GameButton soundOnBtn;
    private GameButton soundOffBtn;
    private GameButton backBtn;


    public OptionsState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        soundFXBtn = new GameButton(Battleship.WIDTH/8, cam.position.y + 100, 360, 100, "SoundFXButton.png");
        soundOnBtn = new GameButton(Battleship.WIDTH/2 - 150, cam.position.y - 300, 300, 300, "soundOn.png");
        soundOffBtn = new GameButton(Battleship.WIDTH/2 -150, cam.position.y - 300, 300, 300, "soundOff.png");
        backBtn = new GameButton(0, 700, 100, 100, "Arrow_left.png");
    }

    @Override
    public void handleInput() {

        // Setup touch reactions
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, soundFXBtn)) {

            } else if (isTouched(touchPosition, soundOnBtn)) {
                Battleship.soundOn = !(Battleship.soundOn);
            } else if (isTouched(touchPosition, backBtn)) {
                gsm.pop();
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
        sb.draw(soundFXBtn.getImage(), soundFXBtn.getX(), soundFXBtn.getY(), soundFXBtn.getWidth(), soundFXBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());

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
        soundFXBtn.disposeAssets();
        soundOnBtn.disposeAssets();
    }



}
