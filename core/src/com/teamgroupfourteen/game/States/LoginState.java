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
    private GameButton loginButton;
    private File ifp;
    private LoginRequest resp;
    private boolean loggedIn;
    Skin uiSkin;



    public LoginState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );

        // attempts a login check using stored authentication data if applicable
        resp = new LoginRequest("", "");
        loggedIn = resp.attemptInitAuth();

        loginButton = new GameButton(cam.position.x/2, 300, 120, 60, "Confirm.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields and add them to stage
        usernameField = new TextField("", uiSkin);
        usernameField.setPosition(cam.position.x/2, 200);
        usernameField.setSize(300, 40);
        stage.addActor(usernameField);

        passwordField = new TextField("", uiSkin);
        passwordField.setPosition(cam.position.x/2, 100);
        passwordField.setSize(300, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
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
                    gsm.set(new MainMenuState(gsm));
                }

            }

        }

    }

    @Override
    public void update(float dt) {
        if(loggedIn){
            System.out.println("The user is already logged in.");
            Player player = new Player(resp.getUsername());
            gsm.set(new MainMenuState(gsm));
        }
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // TODO: Figure out why this works
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.WIDTH);
        sb.draw(loginButton.getImage(), loginButton.getX(), loginButton.getY(), loginButton.getWidth(), loginButton.getHeight());
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }

}
