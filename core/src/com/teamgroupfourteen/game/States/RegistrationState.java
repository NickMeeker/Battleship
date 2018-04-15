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
 * Created by nick on 4/9/18.
 */

public class RegistrationState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private Stage stage;
    private TextField usernameField;
    private TextField passwordField;
    private TextField confirmPasswordField;
    private GameButton titleBtn;
    private GameButton registerButton;
    private boolean loggedIn;
    Skin uiSkin;

    public RegistrationState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);

        // Setup background
        background = new Texture("MainBackground.png");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );


        // Setup buttons & title
        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        registerButton = new GameButton(Battleship.WIDTH/8, cam.position.y - 200, 360, 100, "ConfirmButton.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields and add them to stage
        usernameField = new TextField("", uiSkin);
        usernameField.setPosition(cam.position.x/2-20, 600);
        usernameField.setSize(300, 40);
        usernameField.setMessageText("Username");
        usernameField.setColor(52,52,51,1);
        stage.addActor(usernameField);

        passwordField = new TextField("", uiSkin);
        passwordField.setPosition(cam.position.x/2-20, 500);
        passwordField.setSize(300, 40);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        passwordField.setMessageText("Password");
        passwordField.setColor(52,52,51,1);
        stage.addActor(passwordField);

        confirmPasswordField = new TextField("", uiSkin);
        confirmPasswordField.setPosition(cam.position.x/2-20, 400);
        confirmPasswordField.setSize(300, 40);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        confirmPasswordField.setMessageText("Confirm Password");
        confirmPasswordField.setColor(52,52,51,1);
        stage.addActor(confirmPasswordField);
    }

    @Override
    protected void handleInput() {

        // Setup touch reactions
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
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
        sb.draw(registerButton.getImage(), registerButton.getX(), registerButton.getY(), registerButton.getWidth(), registerButton.getHeight());
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        usernameField.remove();
        passwordField.remove();
        confirmPasswordField.remove();
        titleBtn.disposeAssets();
        registerButton.disposeAssets();
    }
}
