package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.teamgroupfourteen.game.Authentication.LoginRequest;
import com.teamgroupfourteen.game.Authentication.RegistrationRequest;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;
import com.teamgroupfourteen.game.Player.Player;

import java.io.File;

/**
 * Created by nick on 4/9/18.
 */

public class RegistrationState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private Stage stage;
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private GameButton registerButton;
    private boolean loggedIn;
    Skin uiSkin;

    public RegistrationState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );

        registerButton = new GameButton(cam.position.x/2, 300, 120, 60, "Confirm.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields and add them to stage
        usernameField = new TextField("", uiSkin);
        usernameField.setPosition(cam.position.x/2, 350);
        usernameField.setSize(300, 40);
        stage.addActor(usernameField);

        passwordField = new TextField("", uiSkin);
        passwordField.setPosition(cam.position.x/2, 250);
        passwordField.setSize(300, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        stage.addActor(passwordField);

        confirmPasswordField = new TextField("", uiSkin);
        confirmPasswordField.setPosition(cam.position.x/2, 150);
        confirmPasswordField.setSize(300, 40);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        stage.addActor(confirmPasswordField);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, registerButton)){
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = passwordField.getText();
                if(!password.equals(confirmPassword)){
                    // password doesn't match confirm password
                    return;
                }
                RegistrationRequest resp = new RegistrationRequest(username, password, confirmPassword);
                boolean registered = resp.newRegistrationAttempt();
                if(registered){
                    gsm.set(new LoginState(gsm));
                }

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
        sb.draw(registerButton.getImage(), registerButton.getX(), registerButton.getY(), registerButton.getWidth(), registerButton.getHeight());
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        registerButton.disposeAssets();
    }
}
