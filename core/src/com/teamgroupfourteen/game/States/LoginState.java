package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.teamgroupfourteen.game.Authentication.LoginRequest;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;


import java.io.File;

/**
 * Created by nick on 3/9/18.
 */

public class LoginState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private Stage stage;
    private TextField usernameField;
    private TextField passwordField;
    private GameButton titleBtn;
    private GameButton loginButton;
    private File ifp;
    private LoginRequest resp;
    private boolean loggedIn;
    Skin uiSkin;



    public LoginState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );

        // attempts a login check using stored authentication data if applicable
        resp = new LoginRequest("", "");
        loggedIn = resp.attemptInitAuth();

        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        loginButton = new GameButton(Battleship.WIDTH/8, cam.position.y - 200, 360, 100, "ConfirmButton.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields and add them to stage
        usernameField = new TextField("", uiSkin);
        usernameField.setPosition(cam.position.x/2-20, 500);
        usernameField.setSize(300, 40);
        usernameField.setMessageText("Username");
        stage.addActor(usernameField);

        passwordField = new TextField("", uiSkin);
        passwordField.setPosition(cam.position.x/2-20, 400);
        passwordField.setSize(300, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");
        stage.addActor(passwordField);
        //stage.setKeyboardFocus(usernameField);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, loginButton)){
                resp.setUsername(usernameField.getText());
                resp.setPassword(passwordField.getText());
                boolean attempt = resp.newLoginAttempt();
                if(attempt){
                    resp.writeCredentials();
                    Player player = new Player(usernameField.getText());
                    Player player2 = new Player(usernameField.getText());
                    User user = new User();
                    gsm.set(new MainMenuState(gsm, user));
                }

            }

        }

    }

    @Override
    public void update(float dt) {

        // Setup touch reactions
        if(loggedIn){
            System.out.println("The user is already logged in.");
            Player player = new Player(resp.getUsername());
            User user = new User();
            gsm.set(new MainMenuState(gsm, user));
        }
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
        sb.draw(loginButton.getImage(), loginButton.getX(), loginButton.getY(), loginButton.getWidth(), loginButton.getHeight());
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }

}
