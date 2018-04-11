package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;

/**
 * Created by nick on 2/28/18.
 */

public class PlayState extends State {

    private Player[] players= new Player[2];

    //textures
    private Texture gameGrid;
    private TextureRegion mainGrid;
    private Texture coordinateBackground;
    private TextureRegion coordinateBackgroundRegion;
    private Texture background;
    private TextureRegion mainBackground;
    private Texture crosshair;
    private TextureRegion crosshairRegion;
    private Texture miss;
    private TextureRegion missRegion;
    private Texture shipHit;
    private TextureRegion shipHitRegion;
    private Texture hitMarker;
    private TextureRegion hitMarkerRegion;

    private GameButton upBtn;
    private GameButton downBtn;
    private GameButton leftBtn;
    private GameButton rightBtn;
    private GameButton greyFireBtn;
    private GameButton fireBtn;
    private GameButton panRight;
    private GameButton panLeft;

    private Texture minesweeper1, frigate1, submarine1, battleship1, carrier1;
    private Texture minesweeper2, frigate2, submarine2, battleship2, carrier2;
    private TextureRegion minesweeperRegion1, frigateRegion1, submarineRegion1, battleshipRegion1, carrierRegion1;
    private TextureRegion minesweeperRegion2, frigateRegion2, submarineRegion2, battleshipRegion2, carrierRegion2;

    private Vector3 crosshairVector;
    private Vector3 missVector;
    private Vector3 shipHitVector;
    private Vector3 hitMarkerVector;

    private int setupCount = 0;
    private Player currentPlayer;
    private int currentPlayerNum = 0;
    private boolean seeYourBoard = false;

    public PlayState(GameStateManager gsm, Player player1, Player player2){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        // set the background as a texture region. 178x232 is the resolution of the image

        this.players[0] = player1;
        this.players[1] = player2;

        currentPlayer = player1;

        background = new Texture("backgroundFinal.png");
        mainBackground = new TextureRegion(background, 0, 0, 480, 800 );

        minesweeper1 = new Texture("up_minesweeper.png");
        frigate1 = new Texture("up_frigate.png");
        submarine1 = new Texture("up_submarine.png");
        battleship1 = new Texture("up_battleship.png");
        carrier1 = new Texture("up_carrier.png");

        minesweeper2 = new Texture("right_minesweeper.png");
        frigate2 = new Texture("right_frigate.png");
        submarine2 = new Texture("right_submarine.png");
        battleship2 = new Texture("right_battleship.png");
        carrier2 = new Texture("right_carrier.png");

        minesweeperRegion1 = new TextureRegion(minesweeper1, 0, 0, 40, 80);
        minesweeperRegion2 = new TextureRegion(minesweeper2, 0, 0, 80, 40);
        frigateRegion1 = new TextureRegion(frigate1, 0, 0, 40, 120);
        frigateRegion2 = new TextureRegion(frigate2, 0, 0, 120, 40);
        submarineRegion1 = new TextureRegion(submarine1, 0, 0, 40, 120);
        submarineRegion2 = new TextureRegion(submarine2, 0, 0, 120, 40);
        battleshipRegion1 = new TextureRegion(battleship1, 0, 0, 40, 160);
        battleshipRegion2 = new TextureRegion(battleship2, 0, 0, 160, 40);
        carrierRegion1 = new TextureRegion(carrier1, 0, 0, 40, 200);
        carrierRegion2 = new TextureRegion(carrier2, 0, 0, 200, 40);

        //crosshair
        crosshair = new Texture("crosshair.png");
        crosshairRegion = new TextureRegion(crosshair, 0, 0, 40, 40);
        crosshairVector = new Vector3(60, 700, 0);

        //board sprites
        miss = new Texture("miss.png");
        missRegion = new TextureRegion(miss, 0, 0, 40, 40);
        missVector = new Vector3(0, 0, 0);

        shipHit = new Texture("hitmarker.png");
        shipHitRegion = new TextureRegion(shipHit, 0, 0, 20, 20);
        shipHitVector = new Vector3(0, 0, 0);

        hitMarker = new Texture("RedX.png");
        hitMarkerRegion = new TextureRegion(hitMarker, 0, 0, 40, 40);
        hitMarkerVector = new Vector3(0, 0, 0);
    }

    @Override
    public void handleInput(){
        if (setupCount == 0){
            gsm.push(new PlayStateSetup(gsm, players[0], this));
            setupCount++;
        }
        else if(setupCount == 1){
            gsm.push(new PlayStateSetup(gsm, players[1], this));
            setupCount++;
        }
        else if(setupCount == 2){

            gameGrid  = new Texture("GameGrid.png");
            coordinateBackground  = new Texture("blackSquare.png");


            mainGrid = new TextureRegion(gameGrid, 0, 0, 440, 440);
            coordinateBackgroundRegion = new TextureRegion(coordinateBackground, 0, 0, 1536, 1478);

            //buttons
            upBtn = new GameButton(190, 220, 100, 100, "Arrow_up.png");
            downBtn = new GameButton(190, 20, 100, 100, "Arrow_down.png");
            leftBtn = new GameButton(90, 120, 100, 100, "Arrow_left.png");
            rightBtn = new GameButton(290, 120, 100, 100, "Arrow_right.png");
            panLeft = new GameButton(20, 145, 50, 50, "Arrow_left.png");
            panRight = new GameButton(410, 145, 50, 50, "Arrow_right.png");
            greyFireBtn = new GameButton(340, 20, 120, 60, "GrayFire.png");
            fireBtn = new GameButton(340, 20, 120, 60, "Fire.png");

            setupCount = 3;
        }


        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();

            //move crosshair
            if (isTouched(touchPosition, upBtn) && crosshairVector.y < 700 && !seeYourBoard) {
                crosshairVector.add(0, 40, 0);
            }
            else if (isTouched(touchPosition, downBtn) && crosshairVector.y > 340 && !seeYourBoard) {
                crosshairVector.add(0, -40, 0);
            }
            else if (isTouched(touchPosition, leftBtn) && crosshairVector.x > 60 && !seeYourBoard) {
                crosshairVector.add(-40, 0 , 0);
            }
            else if (isTouched(touchPosition, rightBtn) && crosshairVector.x < 420 && !seeYourBoard) {
                crosshairVector.add(40, 0, 0);
            }

            //pan selectors
            else if(isTouched(touchPosition, panLeft)){
                seeYourBoard = true;
            }
            else if(isTouched(touchPosition, panRight)){
                seeYourBoard = false;
            }

            else if(isTouched(touchPosition, fireBtn) && !(players[(currentPlayerNum + 1) % 2].cellIsHit(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40))){
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        if(players[(currentPlayerNum +1) % 2].cellContainsShip(i, j))
                            System.out.print("1 ");
                        else
                            System.out.print("0 ");
                    }
                    System.out.println();
                }
                System.out.println();

                players[(currentPlayerNum + 1) % 2].hitCell(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40);

                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        if(players[(currentPlayerNum +1) % 2].cellContainsShip(i, j))
                            System.out.print("1 ");
                        else
                            System.out.print("0 ");
                    }
                    System.out.println();
                }

                if(players[(currentPlayerNum + 1) % 2].allShipsDestroyed()) {
                    System.out.println("Player " + currentPlayerNum + 1 + ", you win!");
                    //gsm.pop();
                }

                currentPlayerNum = (currentPlayerNum + 1) % 2;
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

        sb.draw(mainBackground, 0, 0, 480, 800);

        if(setupCount > 2) {



            sb.draw(mainGrid, 20, 340, 440, 440);
            sb.draw(coordinateBackgroundRegion, 190, 120, 100, 100);

            //buttons
            sb.draw(upBtn.getImage(), upBtn.getX(), upBtn.getY(), upBtn.getWidth(), upBtn.getHeight());
            sb.draw(downBtn.getImage(), downBtn.getX(), downBtn.getY(), downBtn.getWidth(), downBtn.getHeight());
            sb.draw(leftBtn.getImage(), leftBtn.getX(), leftBtn.getY(), leftBtn.getWidth(), leftBtn.getHeight());
            sb.draw(rightBtn.getImage(), rightBtn.getX(), rightBtn.getY(), rightBtn.getWidth(), rightBtn.getHeight());



            if(seeYourBoard) {


                sb.draw(panRight.getImage(), panRight.getX(), panRight.getY(), panRight.getWidth(), panRight.getHeight());
                //ships
                //minesweeper
                if (players[currentPlayerNum % 2].getShipOrientation(0) == 'd' || players[currentPlayerNum % 2].getShipOrientation(0) == 'u') {
                    sb.draw(minesweeperRegion1, players[currentPlayerNum % 2].getShipPosition(0).x, players[currentPlayerNum % 2].getShipPosition(0).y, 40, 80);
                } else
                    sb.draw(minesweeperRegion2, players[currentPlayerNum % 2].getShipPosition(0).x, players[currentPlayerNum % 2].getShipPosition(0).y, 80, 40);

                //frigate
                if (players[currentPlayerNum % 2].getShipOrientation(1) == 'd' || players[currentPlayerNum % 2].getShipOrientation(1) == 'u') {
                    sb.draw(frigateRegion1, players[currentPlayerNum % 2].getShipPosition(1).x, players[currentPlayerNum % 2].getShipPosition(1).y, 40, 120);
                } else
                    sb.draw(frigateRegion2, players[currentPlayerNum % 2].getShipPosition(1).x, players[currentPlayerNum % 2].getShipPosition(1).y, 120, 40);

                //submarine
                if (players[currentPlayerNum % 2].getShipOrientation(2) == 'd' || players[currentPlayerNum % 2].getShipOrientation(2) == 'u') {
                    sb.draw(submarineRegion1, players[currentPlayerNum % 2].getShipPosition(2).x, players[currentPlayerNum % 2].getShipPosition(2).y, 40, 120);
                } else
                    sb.draw(submarineRegion2, players[currentPlayerNum % 2].getShipPosition(2).x, players[currentPlayerNum % 2].getShipPosition(2).y, 120, 40);

                //battleship
                if (players[currentPlayerNum % 2].getShipOrientation(3) == 'd' || players[currentPlayerNum % 2].getShipOrientation(3) == 'u') {
                    sb.draw(battleshipRegion1, players[currentPlayerNum % 2].getShipPosition(3).x, players[currentPlayerNum % 2].getShipPosition(3).y, 40, 160);
                } else
                    sb.draw(battleshipRegion2, players[currentPlayerNum % 2].getShipPosition(3).x, players[currentPlayerNum % 2].getShipPosition(3).y, 160, 40);

                //aircraft carrier
                if (players[currentPlayerNum % 2].getShipOrientation(4) == 'd' || players[currentPlayerNum % 2].getShipOrientation(4) == 'u') {
                    sb.draw(carrierRegion1, players[currentPlayerNum % 2].getShipPosition(4).x, players[currentPlayerNum % 2].getShipPosition(4).y, 40, 200);
                } else
                    sb.draw(carrierRegion2, players[currentPlayerNum % 2].getShipPosition(4).x, players[currentPlayerNum % 2].getShipPosition(4).y, 200, 40);

                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        if(players[(currentPlayerNum)].cellIsHit(i, j) && players[(currentPlayerNum + 1) % 2].cellContainsShip(i, j)){
                            shipHitVector.set((i*40) + 70, (j*40) + 350, 0);
                            sb.draw(shipHitRegion, shipHitVector.x, shipHitVector.y, 20, 20);
                        }
                        else if(players[(currentPlayerNum)].cellIsHit(i, j)){
                            missVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(missRegion, missVector.x, missVector.y, 40, 40);
                        }
                    }
                }
            }
            else{

                sb.draw(panLeft.getImage(), panLeft.getX(), panLeft.getY(), panLeft.getWidth(), panLeft.getHeight());

                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        if(players[(currentPlayerNum + 1) % 2].cellIsHit(i, j) && players[(currentPlayerNum + 1) % 2].cellContainsShip(i, j)){
                            hitMarkerVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(hitMarkerRegion, hitMarkerVector.x, hitMarkerVector.y, 40, 40);
                        }
                        else if(players[(currentPlayerNum + 1) % 2].cellIsHit(i, j)){
                            missVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(missRegion, missVector.x, missVector.y, 40, 40);
                        }
                    }
                }

                sb.draw(crosshairRegion, crosshairVector.x, crosshairVector.y, 40, 40);

                if(players[(currentPlayerNum + 1) % 2].cellIsHit(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40)){
                    sb.draw(greyFireBtn.getImage(), greyFireBtn.getX(), greyFireBtn.getY(), greyFireBtn.getWidth(), greyFireBtn.getHeight());
                }
                else{
                    sb.draw(fireBtn.getImage(), fireBtn.getX(), fireBtn.getY(), fireBtn.getWidth(), fireBtn.getHeight());
                }
            }
        }
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        System.out.println("Play State Disposed");
    }

}