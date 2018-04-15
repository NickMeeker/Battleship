package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.teamgroupfourteen.game.Authentication.RegistrationRequest;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;

/**
 * Created by derek on 4/14/18.
 */

public class CreditsState extends State{

    private Texture background;
    private TextureRegion mainBackground;
    private Stage stage;
    private TextField creator1;
    private TextField creator2;
    private TextField creator3;
    private TextField creator4;
    private TextField creator5;
    private TextField creator6;
    private GameButton titleBtn;
    private GameButton backBtn;
    Skin uiSkin;

    public CreditsState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);

        // Setup background
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );

        // Setup buttons & title
        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        backBtn = new GameButton(0, Battleship.HEIGHT - 64, 64, 64, "Arrow_left.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields and add them to stage
        creator1 = new TextField("Nick Meeker", uiSkin);
        creator1.setPosition(Battleship.WIDTH/2-100, 500);
        creator1.setSize(200, 40);
        creator1.setColor(52,52,51,1);
        creator1.setDisabled(true);
        stage.addActor(creator1);

        creator2 = new TextField("Derek Workman", uiSkin);
        creator2.setPosition(Battleship.WIDTH/2-100, 450);
        creator2.setSize(200, 40);
        creator2.setColor(52,52,51,1);
        creator2.setDisabled(true);
        stage.addActor(creator2);

        creator3 = new TextField("Jeremy Burns", uiSkin);
        creator3.setPosition(Battleship.WIDTH/2-100, 400);
        creator3.setSize(200, 40);
        creator3.setColor(52,52,51,1);
        creator3.setDisabled(true);
        stage.addActor(creator3);

        creator4 = new TextField("Fazle Akbar", uiSkin);
        creator4.setPosition(Battleship.WIDTH/2-100, 350);
        creator4.setSize(200, 40);
        creator4.setColor(52,52,51,1);
        creator4.setDisabled(true);
        stage.addActor(creator4);

        creator5 = new TextField("Jacqueline van der Meulen", uiSkin);
        creator5.setPosition(Battleship.WIDTH/2-100, 300);
        creator5.setSize(200, 40);
        creator5.setColor(52,52,51,1);
        creator5.setDisabled(true);
        stage.addActor(creator5);

        creator6 = new TextField("Gianni Thomas", uiSkin);
        creator6.setPosition(Battleship.WIDTH/2-100, 250);
        creator6.setSize(200, 40);
        creator6.setColor(52,52,51,1);
        creator6.setDisabled(true);
        stage.addActor(creator6);
    }

    @Override
    protected void handleInput() {

        // Setup touch reactions
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, backBtn)) {
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
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        creator1.remove();
        creator2.remove();
        creator3.remove();
        creator4.remove();
        creator5.remove();
        creator6.remove();
        titleBtn.disposeAssets();
        backBtn.disposeAssets();
    }
}
