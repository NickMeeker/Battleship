package com.teamgroupfourteen.game.States;
/*
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.User.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.Font;

public class CurrentGamesState extends State {

    private Texture background;
    private TextureRegion mainBackground;
    private GameButton selectGameBtn;
    private GameButton cancelGameBtn;
    private User user;
    private Texture barBlue;
    private BitmapFont font;
    private Stage stage;
    private Table table;
    private int i;


    public CurrentGamesState(GameStateManager gsm, User user) {
        super(gsm);
        this.user = user;
        cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        selectGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 200, 360, 100, "SelectButton.png");
        cancelGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 300, 360, 100, "CancelButton.png");

        //Image bgImage = new Image();
        //bgImage.setDrawable(new TextureRegionDrawable(new TextureRegion(background)));

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        barBlue = new Texture("barBlue.png");

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        Table container = new Table();
        table = new Table();
        table.setSkin(skin);
        ScrollPane pane = new ScrollPane(table, skin);
        pane.layout();
        //table.setBackground(bgImage.getDrawable());
        container.add(pane).width(Battleship.WIDTH).height(350);
        container.row();
        container.setBounds(0, 15, Battleship.WIDTH, 1000);
        stage.addActor(container);

        JSONArray gamesArray = user.getUserHostGames();
        System.out.println(gamesArray.getJSONObject(0));

        this is where the games will actually go
        for(i = 0; i < gamesArray.length(); i++){
            TextButton tmp = new TextButton("", skin);
            Image matchBar = new Image(barBlue);
            tmp.setText(i + "");
            Stack stack = new Stack();
            stack.add(matchBar);
            // TODO PLEASE SOMEONE MAKE THIS BETTER
            stack.add(new Label("   Host: ", skin));
            stack.add(new Label("             " + gamesArray.getJSONObject(i).getString("hostPlayer"), skin));
            stack.add(new Label("                                                           Guest: ", skin));
            stack.add(new Label("                                                                        " + gamesArray.getJSONObject(i).getString("guestPlayer"), skin));

            stack.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    System.out.println("game selected");
                }
            });
//            table.add(matchBar).width(454).height(40).padTop(10).padBottom(5);
//            table.add("Host:").;
            table.add(stack).width(454).height(40).padTop(10).padBottom(5);

            table.row();

        }
        table.add();

    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, selectGameBtn)) {
                System.out.println("Transition to select game");
            } else if (isTouched(touchPosition, cancelGameBtn)) {
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
        sb.draw(selectGameBtn.getImage(), selectGameBtn.getX(), selectGameBtn.getY(), selectGameBtn.getWidth(), selectGameBtn.getHeight());
        sb.draw(cancelGameBtn.getImage(), cancelGameBtn.getX(), cancelGameBtn.getY(), cancelGameBtn.getWidth(), cancelGameBtn.getHeight());



        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        selectGameBtn.disposeAssets();
        cancelGameBtn.disposeAssets();
    }
}
*/