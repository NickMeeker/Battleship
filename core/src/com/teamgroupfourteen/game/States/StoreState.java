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
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;

/**
 * Created by derek on 4/13/18.
 */

public class StoreState extends State{
    private Texture background;
    private TextureRegion mainBackground;
    private User user;
    private GameButton coinBtn;
    private GameButton buyShieldBtn;
    private GameButton buyMultishotBtn;
    private GameButton buyDoubleshotBtn;
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
    private TextField coinAmount;
    private GameButton backBtn;
    Skin uiSkin;

    public StoreState(GameStateManager gsm, User user){
        super(gsm);
        this.user = user;

        // Setup background
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("MainBackground.png");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );

        // Setup buttons & power-up buying options
        coinBtn = new GameButton(Battleship.WIDTH-50, Battleship.HEIGHT - 50, 50, 50, "coin.png");
        buyShieldBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y + 100, 360, 100, "ShieldButton.png");
        buyMultishotBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y, 360, 100, "MultishotButton.png");
        buyDoubleshotBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y - 100, 360, 100, "DoubleshotButton.png");
        shieldBtn = new GameButton(0, cam.position.y+100, 100, 100, "shield.png");
        multishotBtn = new GameButton(0, cam.position.y, 100, 100, "multishot.png");
        doubleshotBtn = new GameButton(0, cam.position.y-100, 100, 100, "doubleshot.png");
        countShieldBtn = new GameButton(Battleship.WIDTH/4-50, 0, 50, 50, "shield.png");
        countMultishotBtn = new GameButton(Battleship.WIDTH/2-25, 0, 50, 50, "multishot.png");
        countDoubleshotBtn = new GameButton(Battleship.WIDTH*3/4, 0, 50, 50, "doubleshot.png");
        backBtn = new GameButton(0, Battleship.HEIGHT-64, 64, 64, "Arrow_left.png");

        // Initialize the stage for text fields
        stage = new Stage();
        stage.getViewport().update(Battleship.WIDTH, Battleship.HEIGHT);
        Gdx.input.setInputProcessor(stage);
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        // initialize text fields for power-up amounts
        coinAmount = new TextField(Integer.toString(user.getCoins()), uiSkin);
        coinAmount.setPosition(Battleship.WIDTH-100, Battleship.HEIGHT-50);
        coinAmount.setSize(50, 50);
        coinAmount.setDisabled(true);
        coinAmount.setColor(52,52,51,1);
        stage.addActor(coinAmount);

        shieldAmount = new TextField(Integer.toString(user.getNumPowerUp1()), uiSkin);
        shieldAmount.setPosition(Battleship.WIDTH/4-50, 50);
        shieldAmount.setSize(50, 50);
        shieldAmount.setDisabled(true);
        shieldAmount.setColor(52,52,51,1);
        stage.addActor(shieldAmount);

        multishotAmount = new TextField(Integer.toString(user.getNumPowerUp2()), uiSkin);
        multishotAmount.setPosition(Battleship.WIDTH/2-25, 50);
        multishotAmount.setSize(50, 50);
        multishotAmount.setDisabled(true);
        multishotAmount.setColor(52,52,51,1);
        stage.addActor(multishotAmount);

        doubleshotAmount = new TextField(Integer.toString(user.getNumPowerUp3()), uiSkin);
        doubleshotAmount.setPosition(Battleship.WIDTH*3/4, 50);
        doubleshotAmount.setSize(50, 50);
        doubleshotAmount.setDisabled(true);
        doubleshotAmount.setColor(52,52,51,1);
        stage.addActor(doubleshotAmount);
    }

    public void handleInput(){

        // Setup touch reactions
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, buyShieldBtn)){
                if(user.getCoins() >= 3) {
                    // TODO: update coin count & power-up count
                }
            } else if(isTouched(touchPosition, buyMultishotBtn)){
                if(user.getCoins() >= 5) {
                    // TODO: update coin count & power-up count
                }
            } else if(isTouched(touchPosition, buyDoubleshotBtn)){
                if(user.getCoins() >= 10) {
                    // TODO: update coin count & power-up count
                }
            } else if (isTouched(touchPosition, backBtn)) {
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
        sb.draw(coinBtn.getImage(), coinBtn.getX(), coinBtn.getY(), coinBtn.getWidth(), coinBtn.getHeight());
        sb.draw(buyShieldBtn.getImage(), buyShieldBtn.getX(), buyShieldBtn.getY(), buyShieldBtn.getWidth(), buyShieldBtn.getHeight());
        sb.draw(buyMultishotBtn.getImage(), buyMultishotBtn.getX(), buyMultishotBtn.getY(), buyMultishotBtn.getWidth(), buyMultishotBtn.getHeight());
        sb.draw(buyDoubleshotBtn.getImage(), buyDoubleshotBtn.getX(), buyDoubleshotBtn.getY(), buyDoubleshotBtn.getWidth(), buyDoubleshotBtn.getHeight());
        sb.draw(shieldBtn.getImage(), shieldBtn.getX(), shieldBtn.getY(), shieldBtn.getWidth(), shieldBtn.getHeight());
        sb.draw(multishotBtn.getImage(), multishotBtn.getX(), multishotBtn.getY(), multishotBtn.getWidth(), multishotBtn.getHeight());
        sb.draw(doubleshotBtn.getImage(), doubleshotBtn.getX(), doubleshotBtn.getY(), doubleshotBtn.getWidth(), doubleshotBtn.getHeight());
        sb.draw(countShieldBtn.getImage(), countShieldBtn.getX(), countShieldBtn.getY(), countShieldBtn.getWidth(), countShieldBtn.getHeight());
        sb.draw(countMultishotBtn.getImage(), countMultishotBtn.getX(), countMultishotBtn.getY(), countMultishotBtn.getWidth(), countMultishotBtn.getHeight());
        sb.draw(countDoubleshotBtn.getImage(), countDoubleshotBtn.getX(), countDoubleshotBtn.getY(), countDoubleshotBtn.getWidth(), countDoubleshotBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.disposeAssets();
        coinBtn.disposeAssets();
        buyShieldBtn.disposeAssets();
        buyMultishotBtn.disposeAssets();
        buyDoubleshotBtn.disposeAssets();
        shieldBtn.disposeAssets();
        multishotBtn.disposeAssets();
        doubleshotBtn.disposeAssets();
        countShieldBtn.disposeAssets();
        countMultishotBtn.disposeAssets();
        countDoubleshotBtn.disposeAssets();
        shieldAmount.remove();
        multishotAmount.remove();
        doubleshotAmount.remove();
        coinAmount.remove();
    }
}
