package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;
import com.teamgroupfourteen.game.Player.Player;

/**
 * Created by Jeremy on 4/2/2018.
 */

//State that allows players to place their ships in preparation of the game
public class PlayStateSetup extends State {

    Player player;

    //graphics variables
    private Texture gameGrid;
    private TextureRegion mainGrid;
    private Texture background;
    private TextureRegion mainBackground;
    private Texture coordinateBackground;
    private TextureRegion coordinateBackgrounRegion;
    private GameButton upBtn;
    private GameButton downBtn;
    private GameButton leftBtn;
    private GameButton rightBtn;
    private GameButton rotateBtn;
    private GameButton confirmBtn;
    private Texture minesweeper, frigate, submarine, battleship, carrier;
    private TextureRegion minesweeperRegion, frigateRegion, submarineRegion, battleshipRegion, carrierRegion;

    //data variables
    int currentShipNumber;

    public PlayStateSetup(GameStateManager gsm, Player player){

        super(gsm);

        this.player = player;

        currentShipNumber = 0;

        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);

        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        gameGrid = new Texture("GameGrid.png");
        coordinateBackground = new Texture("blackSquare.png");

        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );
        mainGrid = new TextureRegion(gameGrid, 0, 0, 440, 440);
        coordinateBackgrounRegion = new TextureRegion(coordinateBackground, 0, 0, 1536, 1478);


        //buttons
        upBtn = new GameButton(190, 220, 100, 100, "Arrow_up.png");
        downBtn = new GameButton(190, 20, 100, 100, "Arrow_down.png");
        leftBtn = new GameButton(90, 120, 100, 100, "Arrow_left.png");
        rightBtn = new GameButton(290, 120, 100, 100, "Arrow_right.png");
        rotateBtn = new GameButton(290, 220, 100, 100, "clockwiseArrow.png");
        confirmBtn = new GameButton(340, 20, 120, 60, "blackSquare.png");

        //place the ships in their initial location
        this.player.createShips();
        minesweeper = player.getShipTexture(1);
        frigate = player.getShipTexture(2);
        submarine = player.getShipTexture(3);
        battleship = player.getShipTexture(4);
        carrier = player.getShipTexture(5);

        minesweeperRegion = new TextureRegion(minesweeper, 0, 0, 806, 285 );
        frigateRegion = new TextureRegion(frigate, 0, 0, 1024, 330 );
        submarineRegion = new TextureRegion(submarine, 0, 0, 656, 390 );
        battleshipRegion = new TextureRegion(battleship, 0, 0, 2041, 736 );
        carrierRegion = new TextureRegion(carrier, 0, 0, 720, 405 );
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();
            if(isTouched(touchPosition, upBtn)){
                player.updateShipPosition(currentShipNumber, 0, 40, 0);
            }
            else if(isTouched(touchPosition, downBtn)){
                player.updateShipPosition(currentShipNumber, 0, -40, 0);
            }
            else if(isTouched(touchPosition, leftBtn)){
                player.updateShipPosition(currentShipNumber, -40, 0, 0);
            }
            else if(isTouched(touchPosition, rightBtn)){
                player.updateShipPosition(currentShipNumber, 40, 0, 0);
            }
            else if(isTouched(touchPosition, rotateBtn)){

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

        //regions
        sb.draw(mainBackground, 0, 0, 480, 800);
        sb.draw(mainGrid, 20, 340, 440, 440);
        sb.draw(coordinateBackground, 190, 120,100,100);

        sb.draw(minesweeperRegion, player.getShipPosition(0).x, player.getShipPosition(0).y,40,80);
        sb.draw(frigateRegion, player.getShipPosition(1).x, player.getShipPosition(1).y,40,120);
        sb.draw(submarineRegion, player.getShipPosition(2).x, player.getShipPosition(2).y,40,120);
        sb.draw(battleshipRegion, player.getShipPosition(3).x, player.getShipPosition(3).y,40,160);
        sb.draw(carrierRegion, player.getShipPosition(4).x, player.getShipPosition(4).y,40,200);

        //buttons
        sb.draw(upBtn.getImage(), upBtn.getX(), upBtn.getY(), upBtn.getWidth(), upBtn.getHeight());
        sb.draw(downBtn.getImage(), downBtn.getX(), downBtn.getY(), downBtn.getWidth(), downBtn.getHeight());
        sb.draw(leftBtn.getImage(), leftBtn.getX(), leftBtn.getY(), leftBtn.getWidth(), leftBtn.getHeight());
        sb.draw(rightBtn.getImage(), rightBtn.getX(), rightBtn.getY(), rightBtn.getWidth(), rightBtn.getHeight());
        sb.draw(rotateBtn.getImage(), rotateBtn.getX(),rotateBtn.getY(), rotateBtn.getWidth(), rotateBtn.getHeight());
        sb.draw(confirmBtn.getImage(), confirmBtn.getX(),confirmBtn.getY(), confirmBtn.getWidth(), confirmBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        upBtn.disposeAssets();
        downBtn.disposeAssets();
        leftBtn.disposeAssets();
        rightBtn.disposeAssets();
        System.out.println("Play State Setup Disposed");
    }

}
