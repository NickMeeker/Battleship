package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;
import com.teamgroupfourteen.game.Player.Player;

import java.util.ArrayList;

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
    private GameButton greyFinishBtn;
    private GameButton finishBtn;
    private GameButton leftShipBtn;
    private GameButton rightShipBtn;
    private Texture minesweeper, frigate, submarine, battleship, carrier;
    private TextureRegion minesweeperRegion, frigateRegion, submarineRegion, battleshipRegion, carrierRegion;
    private Texture currentShipTexture;
    private TextureRegion currentShipTextureRegion;


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
        rotateBtn = new GameButton(335, 220, 100, 100, "clockwiseArrow.png");
        greyFinishBtn = new GameButton(340, 20, 120, 60, "GrayFinish.png");
        finishBtn = new GameButton(340, 20, 120, 60, "Finish.png");
        leftShipBtn = new GameButton(20, 245, 50, 50, "Arrow_left.png");
        rightShipBtn = new GameButton(110, 245, 50, 50, "Arrow_right.png");

        //place the ships in their initial location
        this.player.createShips();
        minesweeper = player.getShipTexture(0);
        frigate = player.getShipTexture(1);
        submarine = player.getShipTexture(2);
        battleship = player.getShipTexture(3);
        carrier = player.getShipTexture(4);

        minesweeperRegion = new TextureRegion(minesweeper, 0, 0, 40, 80 );
        frigateRegion = new TextureRegion(frigate, 0, 0, 40, 120 );
        submarineRegion = new TextureRegion(submarine, 0, 0, 40, 120 );
        battleshipRegion = new TextureRegion(battleship, 0, 0, 40, 160 );
        carrierRegion = new TextureRegion(carrier, 0, 0, 40, 200 );

        //current ship starts with the minesweeper
        currentShipTexture = player.getShipTexture(currentShipNumber);
        currentShipTextureRegion = new TextureRegion(currentShipTexture, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();

            //move ships
            if (isTouched(touchPosition, upBtn)) {
                if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                    if (player.getShipSize(currentShipNumber) * 40 + player.getShipPosition(currentShipNumber).y < 740) {
                        player.updateShipPosition(currentShipNumber, 0, 40, 0);
                    }
                }
                else{
                    if(player.getShipPosition(currentShipNumber).y + 40 < 740)
                        player.updateShipPosition(currentShipNumber, 0, 40, 0);
                }
            }
            else if (isTouched(touchPosition, downBtn)) {
                if (player.getShipPosition(currentShipNumber).y > 340) {
                    player.updateShipPosition(currentShipNumber, 0, -40, 0);
                }
            }
            else if (isTouched(touchPosition, leftBtn)) {
                if(player.getShipPosition(currentShipNumber).x > 60)
                    player.updateShipPosition(currentShipNumber, -40, 0, 0);
            }
            else if (isTouched(touchPosition, rightBtn)) {
                if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u'){
                    if(player.getShipPosition(currentShipNumber).x + 40 < 460){
                        player.updateShipPosition(currentShipNumber, 40, 0, 0);
                    }
                }
                else{
                    if(player.getShipSize(currentShipNumber) * 40 + player.getShipPosition(currentShipNumber).x < 460) {
                        player.updateShipPosition(currentShipNumber, 40, 0, 0);
                    }
                }

            }

            //ship selectors
            else if(isTouched(touchPosition, leftShipBtn)){
                if(currentShipNumber == 0)
                    currentShipNumber = 5;
                currentShipNumber = (currentShipNumber - 1);
                currentShipTexture = player.getShipTexture(currentShipNumber);
                currentShipTextureRegion = new TextureRegion(currentShipTexture, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);

            }
            else if(isTouched(touchPosition, rightShipBtn)){
                currentShipNumber = (currentShipNumber + 1) % 5;
                currentShipTexture = player.getShipTexture(currentShipNumber);
                currentShipTextureRegion = new TextureRegion(currentShipTexture, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
            }
            else if(isTouched(touchPosition, rotateBtn)) {
                player.rotateShip(currentShipNumber);

                if((player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') && player.getShipSize(currentShipNumber) * 40 + player.getShipPosition(currentShipNumber).y > 740)
                    player.setShipPosition(currentShipNumber, (int)player.getShipPosition(currentShipNumber).x, 740 - (player.getShipSize(currentShipNumber) * 40), 0);

                if((player.getShipOrientation(currentShipNumber) == 'r' || player.getShipOrientation(currentShipNumber) == 'l') && player.getShipSize(currentShipNumber) * 40 + player.getShipPosition(currentShipNumber).x > 460)
                    player.setShipPosition(currentShipNumber, 460 - (player.getShipSize(currentShipNumber) * 40), (int)player.getShipPosition(currentShipNumber).y, 0);

                //minesweeper
                if (currentShipNumber == 0){
                    if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                        minesweeper = player.getShipTexture(currentShipNumber);
                        minesweeperRegion = new TextureRegion(minesweeper, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
                    } else {
                        minesweeper = player.getShipTexture(currentShipNumber);
                        minesweeperRegion = new TextureRegion(minesweeper, 0, 0, player.getShipSize(currentShipNumber) * 40, 40);
                    }
                }
                //frigate
                else if (currentShipNumber == 1){
                    if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                        frigate = player.getShipTexture(currentShipNumber);
                        frigateRegion = new TextureRegion(frigate, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
                    } else {
                        frigate = player.getShipTexture(currentShipNumber);
                        frigateRegion = new TextureRegion(frigate, 0, 0, player.getShipSize(currentShipNumber) * 40, 40);
                    }
                }
                //submarine
                else if (currentShipNumber == 2){
                    if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                        submarine = player.getShipTexture(currentShipNumber);
                        submarineRegion = new TextureRegion(submarine, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
                    } else {
                        submarine = player.getShipTexture(currentShipNumber);
                        submarineRegion = new TextureRegion(submarine, 0, 0, player.getShipSize(currentShipNumber) * 40, 40);
                    }
                }
                //battleship
                else if (currentShipNumber == 3){
                    if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                        battleship = player.getShipTexture(currentShipNumber);System.out.println("check");
                        battleshipRegion = new TextureRegion(battleship, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
                    } else {
                        battleship = player.getShipTexture(currentShipNumber);
                        battleshipRegion = new TextureRegion(battleship, 0, 0, player.getShipSize(currentShipNumber) * 40, 40);
                    }
                }
                //aircraft carrier
                else if (currentShipNumber == 4){
                    if (player.getShipOrientation(currentShipNumber) == 'd' || player.getShipOrientation(currentShipNumber) == 'u') {
                        carrier = player.getShipTexture(currentShipNumber);
                        carrierRegion = new TextureRegion(carrier, 0, 0, 40, player.getShipSize(currentShipNumber) * 40);
                    } else {
                        carrier = player.getShipTexture(currentShipNumber);
                        carrierRegion = new TextureRegion(carrier, 0, 0, player.getShipSize(currentShipNumber) * 40, 40);
                    }
                }
            }
            else if(isTouched(touchPosition, finishBtn) && !collision()) {
                for(int i = 0; i < 5; i++){
                    for(int j = 0; j < player.getShipSize(i); j++) {
                        if (player.getShipOrientation(i) == 'd' || player.getShipOrientation(i) == 'u')
                            player.fillCell(((int) player.getShipPosition(i).x - 60) / 40, (((int) player.getShipPosition(i).y + (40 * j)) - 340) / 40, i);
                        else
                            player.fillCell((((int)player.getShipPosition(i).x + (40 * j)) - 60) / 40, ((int)player.getShipPosition(i).y - 340) / 40, i);
                    }
                }

                //dispose();
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

        //regions
        sb.draw(mainBackground, 0, 0, 480, 800);
        sb.draw(mainGrid, 20, 340, 440, 440);
        sb.draw(coordinateBackground, 190, 120,100,100);

        //ships
        //minesweeper
        if(player.getShipOrientation(0) == 'd' || player.getShipOrientation(0) == 'u'){
            sb.draw(minesweeperRegion, player.getShipPosition(0).x, player.getShipPosition(0).y,40,80);
        }
        else
            sb.draw(minesweeperRegion, player.getShipPosition(0).x, player.getShipPosition(0).y,80,40);

        //frigate
        if(player.getShipOrientation(1) == 'd' || player.getShipOrientation(1) == 'u'){
            sb.draw(frigateRegion, player.getShipPosition(1).x, player.getShipPosition(1).y,40,120);
        }
        else
            sb.draw(frigateRegion, player.getShipPosition(1).x, player.getShipPosition(1).y,120,40);

        //submarine
        if(player.getShipOrientation(2) == 'd' || player.getShipOrientation(2) == 'u'){
            sb.draw(submarineRegion, player.getShipPosition(2).x, player.getShipPosition(2).y,40,120);
        }
        else
            sb.draw(submarineRegion, player.getShipPosition(2).x, player.getShipPosition(2).y,120,40);

        //battleship
        if(player.getShipOrientation(3) == 'd' || player.getShipOrientation(3) == 'u'){
            sb.draw(battleshipRegion, player.getShipPosition(3).x, player.getShipPosition(3).y,40,160);
        }
        else
            sb.draw(battleshipRegion, player.getShipPosition(3).x, player.getShipPosition(3).y,160,40);

        //aircraft carrier
        if(player.getShipOrientation(4) == 'd' || player.getShipOrientation(4) == 'u'){
            sb.draw(carrierRegion, player.getShipPosition(4).x, player.getShipPosition(4).y,40,200);
        }
        else
            sb.draw(carrierRegion, player.getShipPosition(4).x, player.getShipPosition(4).y,200,40);

            sb.draw(currentShipTextureRegion, 80, 270 - (player.getShipSize(currentShipNumber) * 10),20,player.getShipSize(currentShipNumber) * 20);

        //buttons
        sb.draw(upBtn.getImage(), upBtn.getX(), upBtn.getY(), upBtn.getWidth(), upBtn.getHeight());
        sb.draw(downBtn.getImage(), downBtn.getX(), downBtn.getY(), downBtn.getWidth(), downBtn.getHeight());
        sb.draw(leftBtn.getImage(), leftBtn.getX(), leftBtn.getY(), leftBtn.getWidth(), leftBtn.getHeight());
        sb.draw(rightBtn.getImage(), rightBtn.getX(), rightBtn.getY(), rightBtn.getWidth(), rightBtn.getHeight());
        sb.draw(rotateBtn.getImage(), rotateBtn.getX(),rotateBtn.getY(), rotateBtn.getWidth(), rotateBtn.getHeight());
        sb.draw(leftShipBtn.getImage(), leftShipBtn.getX(),leftShipBtn.getY(), leftShipBtn.getWidth(), leftShipBtn.getHeight());
        sb.draw(rightShipBtn.getImage(), rightShipBtn.getX(),rightShipBtn.getY(), rightShipBtn.getWidth(), rightShipBtn.getHeight());

        if(!collision()) {
            sb.draw(finishBtn.getImage(), finishBtn.getX(), finishBtn.getY(), finishBtn.getWidth(), finishBtn.getHeight());
        }
        else{
            sb.draw(greyFinishBtn.getImage(), greyFinishBtn.getX(), greyFinishBtn.getY(), greyFinishBtn.getWidth(), greyFinishBtn.getHeight());
        }

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        gameGrid.dispose();
        background.dispose();
        coordinateBackground.dispose();
        minesweeper.dispose();
        frigate.dispose();
        submarine.dispose();
        battleship.dispose();
        carrier.dispose();
        currentShipTexture.dispose();
        upBtn.disposeAssets();
        downBtn.disposeAssets();
        leftBtn.disposeAssets();
        rightBtn.disposeAssets();
        rotateBtn.disposeAssets();
        greyFinishBtn.disposeAssets();
        finishBtn.disposeAssets();
        leftShipBtn.disposeAssets();
        rightShipBtn.disposeAssets();
        System.out.println("Play State Setup Disposed");

    }

    private boolean collision(){

        ArrayList<Vector3> coordinates = new ArrayList<Vector3>();
        Vector3 tempVector = new Vector3();



        //check if any ships collide
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < player.getShipSize(i); j++){
                tempVector = player.getShipPosition(i).cpy();
                if(player.getShipOrientation(i) == 'd' || player.getShipOrientation(i) == 'u'){
                    if(coordinates.contains(tempVector.add(0, 40 * j, 0))){
                        return true;
                    }
                    else{
                        tempVector.add(0, -40 * j, 0);
                        coordinates.add(tempVector.add(0, 40 * j, 0));
                    }
                }
                else{
                    if(coordinates.contains(tempVector.add(40 * j, 0, 0))){
                        return true;
                    }
                    else{
                        tempVector.add(-40 * j, 0, 0);
                        coordinates.add(tempVector.add(40 * j, 0, 0));
                    }
                }
            }
        }

        return false;
    }
}
