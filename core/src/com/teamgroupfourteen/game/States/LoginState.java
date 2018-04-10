package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.teamgroupfourteen.game.Battleship;



/**
 * Created by nick on 3/9/18.
 */

public class LoginState extends State {
    private Texture background;
    private TextureRegion mainBackground;
    private Stage stage;
    private TextField usernameField;
    Skin uiSkin;



    public LoginState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, Battleship.WIDTH/2, Battleship.HEIGHT/2);
        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );

        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        this.stage = new Stage();
        this.usernameField = new TextField("", uiSkin);
        usernameField.setPosition(100, 100);
        usernameField.setSize(88, 14);
        stage.addActor(usernameField);
        Gdx.input.setInputProcessor(this.stage);


    }

    @Override
    protected void handleInput() {


    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        // TODO: Figure out why this worksa
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.WIDTH);
        sb.end();

        stage.draw();
        stage.act();


    }

    @Override
    public void dispose() {

    }

}
