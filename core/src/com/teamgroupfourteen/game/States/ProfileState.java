package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.GetRequest;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.User.User;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by gianni on 4/14/18.
 */

public class ProfileState extends State{
    private Texture background;
    private TextureRegion mainBackground;
    private User user;
    private GameButton backBtn;
    private GameButton coinBtn;
    private GameButton shieldBtn;
    private GameButton multishotBtn;
    private GameButton doubleshotBtn;
    private GameButton countShieldBtn;
    private GameButton countMultishotBtn;
    private GameButton countDoubleshotBtn;
    private Stage stage;
    private TextField shieldAmount;
    private TextField multishotAmount;
    private TextField doubleshotAmount;
    private Label displayName;
    private TextField coinAmount;
    private Label winLbl;
    private Label lossLbl;
    private Label levelLbl;
    private Table recordTbl;
    private Table lvlTbl;
    private Image rank;
    private Image border;

    HttpResponse<JsonNode> resp;
    JSONObject test;
    GetRequest getReq;

    Skin uiSkin;

    public ProfileState(GameStateManager gsm, User user){
        super(gsm);
        this.user = user;

        // Setup background
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("MainBackground.png");

        // Setup buttons & power-up buying options
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        countShieldBtn = new GameButton(0, Battleship.HEIGHT-200,50, 50, "shield.png");
        countMultishotBtn = new GameButton(0, Battleship.HEIGHT-260, 50, 50, "multishot.png");
        countDoubleshotBtn = new GameButton(0, Battleship.HEIGHT-320, 50, 50, "doubleshot.png");
        coinBtn = new GameButton(Battleship.WIDTH-50, Battleship.HEIGHT - 50, 50, 50, "coin.png");

        border = new Image(new Texture("border.png"));
        border.setPosition(3*Battleship.WIDTH/4, 3*Battleship.HEIGHT/4);
        border.setSize(120, 90);

        if((int) Math.floor(user.getExp()/100) == 0)
            rank = new Image(new Texture("rank1.png"));
        else if ((int) (Math.floor(user.getExp()/100)) > 5)
            rank = new Image(new Texture("rank5.png"));
        else
            rank = new Image(new Texture("rank" + Integer.toString((int) Math.floor(user.getExp()/100)) + ".png"));
        //TODO FIX FOR RANK >10
        rank.setPosition(Integer.valueOf(3*Battleship.WIDTH/4)+20,Integer.valueOf(3*Battleship.HEIGHT/4)+15);
        rank.setSize(80,60);

        backBtn = new GameButton(0, Battleship.HEIGHT-75, 64, 64, "Arrow_left.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields for power-up amounts
        shieldAmount = new TextField(Integer.toString(user.getNumPowerUp1()), uiSkin);
        shieldAmount.setPosition(50, Battleship.HEIGHT-200);
        shieldAmount.setSize(50, 50);
        shieldAmount.setDisabled(true);
        stage.addActor(shieldAmount);

        multishotAmount = new TextField(Integer.toString(user.getNumPowerUp2()), uiSkin);
        multishotAmount.setPosition(50, Battleship.HEIGHT-260);
        multishotAmount.setSize(50, 50);
        multishotAmount.setDisabled(true);
        stage.addActor(multishotAmount);

        doubleshotAmount = new TextField(Integer.toString(user.getNumPowerUp3()), uiSkin);
        doubleshotAmount.setPosition(50, Battleship.HEIGHT-320);
        doubleshotAmount.setSize(50, 50);
        doubleshotAmount.setDisabled(true);
        stage.addActor(doubleshotAmount);

        coinAmount = new TextField(Integer.toString(user.getCoins()), uiSkin);
        coinAmount.setPosition(Battleship.WIDTH-100, Battleship.HEIGHT-50);
        coinAmount.setSize(50, 50);
        coinAmount.setDisabled(true);
        coinAmount.setColor(52,52,51,1);
        stage.addActor(coinAmount);

        displayName = new Label(("<username>").toUpperCase() /*user.getUsername()*/, uiSkin);
        //displayName.setColor(Color.BLUE);
        displayName.setFontScale(2);
        displayName.setPosition(Battleship.WIDTH/6, Integer.valueOf(7*Battleship.HEIGHT/8));
        //displayName.setSize(300, 300);
        stage.addActor(displayName);

        winLbl = new Label("Wins", uiSkin);
        lossLbl = new Label("Loss", uiSkin);
        winLbl = new Label("Wins", uiSkin);
        winLbl.setColor(Color.BLACK);
        //winLbl.setPosition(Battleship.WIDTH/2, Integer.valueOf(3*Battleship.HEIGHT/4));
        //stage.addActor(winLbl);

        recordTbl = new Table(uiSkin);
        recordTbl.add("Wins  ");
        recordTbl.add(Integer.toString(user.getWins()));
        recordTbl.row();
        recordTbl.add(lossLbl.getText() + "  ");
        recordTbl.add(Integer.toString(user.getLosses()));
        recordTbl.row();
        recordTbl.add("Record  ");
        if(user.getLosses()==0)
            recordTbl.add(Integer.toString(user.getWins()));
        else
            recordTbl.add(Double.toString(user.getWins()/user.getLosses())); //wins/loss
        recordTbl.row();
        recordTbl.add("Level");
        recordTbl.add(Integer.toString((int) Math.floor(user.getExp()/100))); //(int) Math.floor(user.getExp/1000)
        recordTbl.setPosition(200, Integer.valueOf(3*Battleship.HEIGHT/4));
        recordTbl.padRight(20);
        recordTbl.padLeft(20);
        recordTbl.pad(20);
        recordTbl.scaleBy(2);
        stage.addActor(recordTbl);

        lvlTbl = new Table(uiSkin);
        //lvlTbl.add(rank.getDrawable());
        lvlTbl.row();
        lvlTbl.add("Rank: 3");
        lvlTbl.setPosition(390, Integer.valueOf(3*Battleship.HEIGHT/4)-30);

        stage.addActor(lvlTbl);

        JSONArray gamesList;


        try {
            resp = Unirest.get(Battleship.APIPREFIX + "games/")
                    .header("Authorization", user.getToken())
                    .asJson();
            gamesList = resp.getBody().getArray();

            System.out.println(gamesList.get(0).toString());

            getReq = Unirest.get(Battleship.APIPREFIX + "games/");
            //System.out.println(getReq);

            //test = resp.getBody().getString("username");

            //System.out.println(test.toString());
        }
        catch(UnirestException e)
        {
            System.out.println(e);
            System.out.println("Did not get anything");
        }
    }


    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, backBtn)){
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
        sb.draw(countShieldBtn.getImage(), countShieldBtn.getX(), countShieldBtn.getY(), countShieldBtn.getWidth(), countShieldBtn.getHeight());
        sb.draw(countMultishotBtn.getImage(), countMultishotBtn.getX(), countMultishotBtn.getY(), countMultishotBtn.getWidth(), countMultishotBtn.getHeight());
        sb.draw(countDoubleshotBtn.getImage(), countDoubleshotBtn.getX(), countDoubleshotBtn.getY(), countDoubleshotBtn.getWidth(), countDoubleshotBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        sb.draw(coinBtn.getImage(), coinBtn.getX(), coinBtn.getY(), coinBtn.getWidth(), coinBtn.getHeight());

        border.draw(sb, 1);
        rank.draw(sb, 1);
        displayName.draw(sb, 0);
        winLbl.draw(sb, 0);
        recordTbl.draw(sb, 0);
        lvlTbl.draw(sb, 0);
        sb.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();

        System.out.println("Menu State Disposed");
    }



}
