package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;

/**
 * Created by derek on 4/14/18.
 */

public class TransitionState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private GameButton titleBtn;
    private GameButton confirmBtn;
    private Stage stage;
    private TextField userInfo;
    Skin uiSkin;


    public TransitionState(GameStateManager gsm, String user){
        super(gsm);

        // Setup background
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );

        // Setup buttons & title
        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        confirmBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 100, 360, 100, "ConfirmButton.png");


        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields for power-up amounts
        userInfo = new TextField(user + ", it's your turn!!", uiSkin);
        userInfo.setPosition(Battleship.WIDTH/2-200, cam.position.y);
        userInfo.setSize(400, 50);
        userInfo.setDisabled(true);
        userInfo.setColor(52,52,51,1);
        stage.addActor(userInfo);

    }

    public void handleInput(){

        // Setup touch reactions
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, confirmBtn)) {
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
        sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
        sb.draw(confirmBtn.getImage(), confirmBtn.getX(), confirmBtn.getY(), confirmBtn.getWidth(), confirmBtn.getHeight());
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
