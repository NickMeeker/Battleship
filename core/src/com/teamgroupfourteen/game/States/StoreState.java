package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;



public class StoreState extends State{
    private Texture background;
    private TextureRegion mainBackground;
    private User user;
    private GameButton buyShieldBtn;
    private GameButton buyMultishotBtn;
    private GameButton buyDoubleshotBtn;
    private GameButton shieldBtn;
    private GameButton multishotBtn;
    private GameButton doubleshotBtn;
    private GameButton backBtn;


    public StoreState(GameStateManager gsm, User user){


        super(gsm);
        this.user = user;
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        background = new Texture("testPic.jpg");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT );
        buyShieldBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y + 100, 360, 100, "ShieldButton.png");
        buyMultishotBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y, 360, 100, "MultishotButton.png");
        buyDoubleshotBtn = new GameButton(Battleship.WIDTH/8+50, cam.position.y - 100, 360, 100, "DoubleshotButton.png");
        shieldBtn = new GameButton(0, cam.position.y + 100, 100, 100, "shield.png");
        multishotBtn = new GameButton(0, cam.position.y, 100, 100, "multishot.png");
        doubleshotBtn = new GameButton(0, cam.position.y - 100, 100, 100, "doubleshot.png");
        backBtn = new GameButton(0, 700, 100, 100, "Arrow_left.png");

    }


    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, buyShieldBtn)){
                System.out.println("Transition to singleplayer");
                //gsm.push(new PlayState(gsm, player1, player2, true, false));
            } else if(isTouched(touchPosition, buyMultishotBtn)){
                //gsm.push(new MultiplayerTypeState(gsm, user));
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
        sb.draw(buyShieldBtn.getImage(), buyShieldBtn.getX(), buyShieldBtn.getY(), buyShieldBtn.getWidth(), buyShieldBtn.getHeight());
        sb.draw(buyMultishotBtn.getImage(), buyMultishotBtn.getX(), buyMultishotBtn.getY(), buyMultishotBtn.getWidth(), buyMultishotBtn.getHeight());
        sb.draw(buyDoubleshotBtn.getImage(), buyDoubleshotBtn.getX(), buyDoubleshotBtn.getY(), buyDoubleshotBtn.getWidth(), buyDoubleshotBtn.getHeight());
        sb.draw(shieldBtn.getImage(), shieldBtn.getX(), shieldBtn.getY(), shieldBtn.getWidth(), shieldBtn.getHeight());
        sb.draw(multishotBtn.getImage(), multishotBtn.getX(), multishotBtn.getY(), multishotBtn.getWidth(), multishotBtn.getHeight());
        sb.draw(doubleshotBtn.getImage(), doubleshotBtn.getX(), doubleshotBtn.getY(), doubleshotBtn.getWidth(), doubleshotBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        buyShieldBtn.disposeAssets();
        buyMultishotBtn.disposeAssets();

        System.out.println("Menu State Disposed");
    }



}
