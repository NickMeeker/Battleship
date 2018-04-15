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
        creator1.setPosition(cam.position.x/2-20, 600);
        creator1.setSize(300, 40);
        creator1.setColor(52,52,51,1);
        stage.addActor(creator1);

        creator2 = new TextField("Derek Workman", uiSkin);
        creator2.setPosition(cam.position.x/2-20, 500);
        creator2.setSize(300, 40);
        creator2.setColor(52,52,51,1);
        stage.addActor(creator2);

        creator3 = new TextField("Jeremy Burns", uiSkin);
        creator3.setPosition(cam.position.x/2-20, 400);
        creator3.setSize(300, 40);
        creator3.setColor(52,52,51,1);
        stage.addActor(creator3);

        creator4 = new TextField("Fazle Akbar", uiSkin);
        creator4.setPosition(cam.position.x/2-20, 300);
        creator4.setSize(300, 40);
        creator4.setColor(52,52,51,1);
        stage.addActor(creator4);

        creator5 = new TextField("Jacqueline van der Meulen", uiSkin);
        creator5.setPosition(cam.position.x/2-20, 200);
        creator5.setSize(300, 40);
        creator5.setColor(52,52,51,1);
        stage.addActor(creator5);

        creator6 = new TextField("Gianni Thomas", uiSkin);
        creator6.setPosition(cam.position.x/2-20, 100);
        creator6.setSize(300, 40);
        creator6.setColor(52,52,51,1);
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
    }
    
    
    
    
}
