package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Multiplayer.GameLogParser;
import com.teamgroupfourteen.game.Multiplayer.MultiplayerGameManager;
import com.teamgroupfourteen.game.Multiplayer.SetupParser;
import com.teamgroupfourteen.game.Player.Player;

import java.util.ArrayList;


/**
 * Created by nick on 2/28/18.
 */

public class PlayState extends State {

    //array of 2 players
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
    private Texture crosshair2;
    private TextureRegion crosshairRegion2;
    private Texture crosshair3;
    private TextureRegion crosshairRegion3;
    private Texture crosshair4;
    private TextureRegion crosshairRegion4;
    private Texture miss;
    private TextureRegion missRegion;
    private Texture shipHit;
    private TextureRegion shipHitRegion;
    private Texture hitMarker;
    private TextureRegion hitMarkerRegion;
    private Texture rocket;
    private TextureRegion rocketRegion;

    private Texture[] powerUp;
    private TextureRegion powerUpRegion;

    //buttons
    private GameButton upBtn;
    private GameButton downBtn;
    private GameButton leftBtn;
    private GameButton rightBtn;
    private GameButton greyFireBtn;
    private GameButton fireBtn;
    private GameButton panRight;
    private GameButton panLeft;
    private GameButton rightPowerUp;
    private GameButton leftPowerUp;

    //ship textures
    private Texture minesweeper1, frigate1, submarine1, battleship1, carrier1;
    private Texture minesweeper2, frigate2, submarine2, battleship2, carrier2;
    private TextureRegion minesweeperRegion1, frigateRegion1, submarineRegion1, battleshipRegion1, carrierRegion1;
    private TextureRegion minesweeperRegion2, frigateRegion2, submarineRegion2, battleshipRegion2, carrierRegion2;

    //board sprite vectors
    private Vector3 crosshairVector;
    private Vector3 crosshairVector2;
    private Vector3 crosshairVector3;
    private Vector3 crosshairVector4;
    private Vector3 missVector;
    private Vector3 shipHitVector;
    private Vector3 hitMarkerVector;
    private Vector3 rocketVector;

    private int setupCount = 0;
    private Player currentPlayer;
    private int currentPlayerNum = 0;
    private int currentPowerUpNum;
    private int[] playerPowerUps;
    private boolean seeYourBoard = false;

    //animation flags
    private boolean dropBombs;
    private boolean pressUp;
    private boolean pressDown;
    private boolean pressLeft;
    private boolean pressRight;
    private boolean crosshairMoving;

    //sounds
    private Sound explosion;
    private Sound splash;

    //Flags: type of game
    //  single player: singleplayer = true
    //                 online = false
    //         online: singleplayer = false
    //                 online = true
    //    local multi: singleplayer = false
    //                 online = false
    private boolean singlePlayer;
    private boolean online;
    private boolean popOnlineFlag;

    private String setup;
    private String moveList;
    private StringBuilder sb;

    MultiplayerGameManager mgm;

    //local multiplayer constructor
    public PlayState(GameStateManager gsm, Player player1, Player player2){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);

        //local multiplayer flags
        this.singlePlayer = false;
        this.online = false;

        //assign player one and 2 to the players array
        this.players[0] = player1;
        this.players[1] = player2;

        //make player 1 the current player
        currentPlayer = player1;

        //initialize the background
        background = new Texture("backgroundFinal.png");
        mainBackground = new TextureRegion(background, 0, 0, 480, 800 );

        //initialize ship graphics: vertical
        minesweeper1 = new Texture("up_minesweeper.png");
        frigate1 = new Texture("up_frigate.png");
        submarine1 = new Texture("up_submarine.png");
        battleship1 = new Texture("up_battleship.png");
        carrier1 = new Texture("up_carrier.png");

        //initialize ship graphics: horizontal
        minesweeper2 = new Texture("right_minesweeper.png");
        frigate2 = new Texture("right_frigate.png");
        submarine2 = new Texture("right_submarine.png");
        battleship2 = new Texture("right_battleship.png");
        carrier2 = new Texture("right_carrier.png");

        //set all ship regions
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

        rocket = new Texture("rocket.png");
        rocketRegion = new TextureRegion(rocket, 0, 0, 40, 40);
        rocketVector = new Vector3(800, 800, 0);

        //set animation flags
        dropBombs = false;
        pressDown = false;
        pressUp = false;
        pressRight = false;
        pressLeft = false;
        crosshairMoving = false;

        //sounds
        explosion = Gdx.audio.newSound(Gdx.files.internal("Explosion.mp3"));
        splash = Gdx.audio.newSound(Gdx.files.internal("Splash.mp3"));
    }

    //single player and online constructor
    public PlayState(GameStateManager gsm, Player player1, Player player2, boolean singlePlayer, boolean online, String setup, String moveList){
        super(gsm);
        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);
        mgm = new MultiplayerGameManager("5ad3dbef2211db4208082b2c");
        //set the game type flags
        this.singlePlayer = singlePlayer;
        this.online = online;

        if(online){
            this.setup = setup;
            this.moveList = moveList;
            popOnlineFlag = false;
            sb = new StringBuilder();
        }

        //player one is always player one
        this.players[0] = player1;

        //if the game is online, make player two the player that was pulled from the database
        //else if the game is single player, make the computer player two
        //finally just make make a regular local player two **SHOULD NOT OCCUR**
        if(online)
            this.players[1] = player2;
        else if(singlePlayer){
            this.players[1] = new Player("computer");
        }
        else {
            this.players[1] = player2;
        }

        //set the current player to player 1
        currentPlayer = player1;

        //initialize the background
        background = new Texture("backgroundFinal.png");
        mainBackground = new TextureRegion(background, 0, 0, 480, 800 );

        //initialize the ship textures: vertical
        minesweeper1 = new Texture("up_minesweeper.png");
        frigate1 = new Texture("up_frigate.png");
        submarine1 = new Texture("up_submarine.png");
        battleship1 = new Texture("up_battleship.png");
        carrier1 = new Texture("up_carrier.png");

        //initialize the ship textures: horizontal
        minesweeper2 = new Texture("right_minesweeper.png");
        frigate2 = new Texture("right_frigate.png");
        submarine2 = new Texture("right_submarine.png");
        battleship2 = new Texture("right_battleship.png");
        carrier2 = new Texture("right_carrier.png");

        //initialize the ship regions
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

        //multishot Crosshairs
        if(online) {
            crosshair2 = new Texture("crosshair.png");
            crosshair3 = new Texture("crosshair.png");
            crosshair4 = new Texture("crosshair.png");
            crosshairRegion2 = new TextureRegion(crosshair2, 0, 0, 40, 40);
            crosshairRegion3 = new TextureRegion(crosshair3, 0, 0, 40, 40);
            crosshairRegion4 = new TextureRegion(crosshair4, 0, 0, 40, 40);
            crosshairVector2 = new Vector3(100, 740, 0);
            crosshairVector3 = new Vector3(60, 740, 0);
            crosshairVector4 = new Vector3(100, 740, 0);
        }

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

        rocket = new Texture("rocket.png");
        rocketRegion = new TextureRegion(rocket, 0, 0, 40, 40);
        rocketVector = new Vector3(800, 800, 0);

        //set animation flags
        dropBombs = false;
        pressDown = false;
        pressUp = false;
        pressRight = false;
        pressLeft = false;
        crosshairMoving = false;

        //sounds
        explosion = Gdx.audio.newSound(Gdx.files.internal("Explosion.mp3"));
        splash = Gdx.audio.newSound(Gdx.files.internal("Splash.mp3"));
    }

    @Override
    public void handleInput(){
        //player 1 board setup
        if (setupCount == 0){
            if(online && setup.equals("")) {System.out.println("player1");
                gsm.push(new PlayStateSetup(gsm, players[0], this));
                for(int i = 0; i < 5; i++){
                    sb.append(0);
                    sb.append((char)(9-((players[0].getShipPosition(i).y - 340) / 40) + 65));
                    sb.append((int)((players[0].getShipPosition(i).x - 60) / 40));
                    sb.append(players[0].getShipName(i));
                    sb.append(players[0].getShipOrientation(i));
                    sb.append(',');
                }
                //TODO: Send sb to database

                mgm.updateSetupLog(sb.toString());

                if(popOnlineFlag) {
                    gsm.pop();
                }
            }
            else if(online){
                this.players[0].createShips();
                SetupParser setupParser = new SetupParser(setup);
                setupParser.buildLogEntries();
                for(int i = 0; i < 5; i++){
                    setupParser.parseLogEntry(i);System.out.println("i: " + i + " row "  + setupParser.getRow() + " column " + setupParser.getColumn());
                    players[0].setShipPosition(i, setupParser.getRow(), setupParser.getColumn(), 0);
                    players[0].setShipOrientation(i, setupParser.getOrientaion());
                }
            }
            else if(singlePlayer){
                gsm.push(new PlayStateSetup(gsm, players[0], this));
            }
            //advance to next step of setup
            setupCount++;
        }
        //player 2 board setup
        else if(setupCount == 1){System.out.println("player2");
            if(online && setup.equals("")) {
                gsm.push(new PlayStateSetup(gsm, players[1], this));
                for(int i = 0; i < 5; i++){
                    sb.append(1);
                    sb.append((char)(((players[1].getShipPosition(i).y - 340) / 40) + 65));
                    sb.append(((players[1].getShipPosition(i).x - 60) / 40));
                    sb.append(players[1].getShipName(i));
                    sb.append(players[1].getShipOrientation(i));

                    if(i != 4)
                        sb.append(',');
                }

                //TODO send sb to database
                mgm.updateSetupLog(sb.toString());

                gsm.pop();
            }

            else if(online){
                this.players[1].createShips();
                SetupParser setupParser = new SetupParser(setup);
                setupParser.buildLogEntries();
                for(int i = 5; i < 10; i++){
                    setupParser.parseLogEntry(i);
                    players[1].setShipPosition(i - 5, setupParser.getRow(), setupParser.getColumn(), 0);
                    players[1].setShipOrientation(i - 5, setupParser.getOrientaion());
                }
            }

            //if single player, have the computer build a board
            if(singlePlayer) {
                players[1].makeBoard();
                for(int i = 0; i < 10; i ++){
                    for(int j = 0; j < 10; j++){
                        if(players[1].cellContainsShip(i, j)){
                            System.out.print("1 ");
                        }
                        else{
                            System.out.print("0 ");
                        }
                    }
                    System.out.println();
                }
            }
            //if the game is local multiplayer, make player 2's board
            else {
                gsm.push(new PlayStateSetup(gsm, players[1], this));
            }
            //advance to next step of setup
            setupCount++;
        }
        //Now that the game boards are built, lets build the rest of the game
        else if(setupCount == 2){

            if(online && !moveList.equals("")){
                GameLogParser gameParser = new GameLogParser(moveList);
                gameParser.buildLogEntries();
                for(int i = 0; i < gameParser.getMoveCount() - 1; i++){
                    gameParser.parseLogEntries(i);
                    if(gameParser.getMoveType() == 'n' || gameParser.getMoveType() == 'd'){
                        players[(gameParser.getPlayerNum() + 1) % 2].hitCell(gameParser.getColumn(), gameParser.getRow());
                    }
                    else if(gameParser.getMoveType() == 's'){
                        players[gameParser.getPlayerNum()].placeShield(gameParser.getColumn(), gameParser.getRow());
                    }
                    else if(gameParser.getMoveType() == 'm'){
                        players[(gameParser.getPlayerNum() + 1) % 2].hitCell(gameParser.getColumn(), gameParser.getRow());
                        players[(gameParser.getPlayerNum() + 1) % 2].hitCell(gameParser.getColumn() + 1, gameParser.getRow());
                        players[(gameParser.getPlayerNum() + 1) % 2].hitCell(gameParser.getColumn(), gameParser.getRow() + 1);
                        players[(gameParser.getPlayerNum() + 1) % 2].hitCell(gameParser.getColumn() + 1, gameParser.getRow() + 1);
                    }
                }

                gameParser.parseLogEntries(gameParser.getMoveCount() - 1);

                if(gameParser.getMoveType() != 'd') {
                    currentPlayerNum = (gameParser.getPlayerNum() + 1) % 2;
                }
                else{
                    currentPlayerNum = gameParser.getPlayerNum();
                }

                playerPowerUps[0] = players[currentPlayerNum].getShieldCount();
                playerPowerUps[1] = players[currentPlayerNum].getMultishotCount();
                playerPowerUps[2] = players[currentPlayerNum].getDoubleShotCount();
            }



            //initialize the board textures
            gameGrid  = new Texture("GameGrid.png");
            coordinateBackground  = new Texture("blackSquare.png");

            //initialize board regions
            mainGrid = new TextureRegion(gameGrid, 0, 0, 440, 440);
            coordinateBackgroundRegion = new TextureRegion(coordinateBackground, 0, 0, 1536, 1478);

            if(online) {
                powerUp = new Texture[4];

                for (int i = 0; i < 4; i++) {
                    if (i == 0) {
                        powerUp[i] = new Texture("RedX.png");
                    }
                    if (i == 1) {
                        powerUp[i] = new Texture("Shield.png");
                    }
                    if (i == 2) {
                        powerUp[i] = new Texture("multishot.png");
                    }
                    if (i == 3) {
                        powerUp[i] = new Texture("doubleShot.png");
                    }
                }

                powerUpRegion = new TextureRegion(powerUp[currentPowerUpNum], 0, 0, 40, 40);
            }

            //buttons
            upBtn = new GameButton(190, 220, 100, 100, "Arrow_up.png");
            downBtn = new GameButton(190, 20, 100, 100, "Arrow_down.png");
            leftBtn = new GameButton(90, 120, 100, 100, "Arrow_left.png");
            rightBtn = new GameButton(290, 120, 100, 100, "Arrow_right.png");
            panLeft = new GameButton(20, 145, 50, 50, "Arrow_left.png");
            panRight = new GameButton(410, 145, 50, 50, "Arrow_right.png");
            greyFireBtn = new GameButton(340, 20, 120, 60, "GrayFire.png");
            fireBtn = new GameButton(340, 20, 120, 60, "Fire.png");

            if(online){
                rightPowerUp = new GameButton(20, 20, 50, 50, "Arrow_right.png");
                leftPowerUp = new GameButton(120, 20, 50, 50, "Arrow_left.png");
            }

            //move the setup to the finished stage. Setup is now complete
            setupCount = 3;
        }


        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();

            //move crosshair
            if (isTouched(touchPosition, upBtn) && !crosshairMoving && crosshairVector.y < 700 && !seeYourBoard && !dropBombs) {
                if(online && currentPowerUpNum == 2){
                    if(crosshairVector.y < 660){
                        pressUp = true;
                        crosshairMoving = true;
                    }
                }
                else {
                    pressUp = true;
                    crosshairMoving = true;
                }
            }
            else if (isTouched(touchPosition, downBtn) && !crosshairMoving && crosshairVector.y > 340 && !seeYourBoard && !dropBombs) {
                pressDown = true;
                crosshairMoving = true;
            }
            else if (isTouched(touchPosition, leftBtn) && !crosshairMoving && crosshairVector.x > 60 && !seeYourBoard && !dropBombs) {
                pressLeft = true;
                crosshairMoving = true;
            }
            else if (isTouched(touchPosition, rightBtn) && !crosshairMoving && crosshairVector.x < 420 && !seeYourBoard && !dropBombs) {
                if(online && currentPowerUpNum == 2){
                    if(crosshairVector.x < 380){
                        pressUp = true;
                        crosshairMoving = true;
                    }
                }
                else {
                    pressRight = true;
                    crosshairMoving = true;
                }
            }

            //pan selectors
            else if(isTouched(touchPosition, panLeft)){
                seeYourBoard = true;
            }
            else if(isTouched(touchPosition, panRight)){
                seeYourBoard = false;
            }

            //power up selectors
            else if(online && isTouched(touchPosition, leftPowerUp)){
                for(int i = 0; i < 3; i++){
                    if(currentPowerUpNum == 0){
                        currentPowerUpNum = 3;
                        break;
                    }
                    else if(playerPowerUps[2 - currentPowerUpNum] != 0){
                        currentPowerUpNum--;
                        break;
                    }
                    else{
                        currentPowerUpNum--;
                    }
                }

                powerUpRegion.setTexture(powerUp[currentPowerUpNum]);

                if(currentPowerUpNum == 2 && crosshairVector.y >= 700){
                    crosshairVector.add(0, -40, 0);
                }
                if(currentPowerUpNum == 2 && crosshairVector.x >= 420){
                    crosshairVector.add(-40, 0, 0);
                }
                if(currentPowerUpNum == 2){
                    crosshairVector2.set(crosshairVector.x + 40, crosshairVector.y, 0);
                    crosshairVector3.set(crosshairVector.x, crosshairVector.y + 40, 0);
                    crosshairVector4.set(crosshairVector.x + 40, crosshairVector.y + 40, 0);
                }
            }
            else if(online && isTouched(touchPosition, rightPowerUp)){
                for(int i = 0; i < 3; i++){
                    if(currentPowerUpNum == 3){
                        currentPowerUpNum = 0;
                        break;
                    }
                    else if(playerPowerUps[currentPowerUpNum] != 0){
                        currentPowerUpNum++;
                        break;
                    }
                    else{
                        currentPowerUpNum++;
                    }
                }

                powerUpRegion.setTexture(powerUp[currentPowerUpNum]);
            }

            //fire button: this conditional checks to make sure the fire button was pressed
            //  and that the cell that you want to hit has not been hit already
            else if(isTouched(touchPosition, fireBtn) && !(players[(currentPlayerNum + 1) % 2].cellIsHit(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40))){

                //hit the other player at the position of the crosshair
                players[(currentPlayerNum + 1) % 2].hitCell(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40);

                rocketVector.set(crosshairVector.x, 820, 0);
                dropBombs = true;

                //if it is single player, have the computer take its turn
                if(singlePlayer){

                    if(players[1].allShipsDestroyed()){
                        //TODO player win state

                        gsm.set(new WinState(gsm, "Player 1"));
                    }

                    players[1].makeMove(players[0]);

                    if(players[0].allShipsDestroyed()){System.out.println("check");
                        //TODO computer win state
                        gsm.set(new WinState(gsm, "Computer"));
                    }
                }
                //if the game is online update the database move list with the move that was just made
                else if(online){
                    sb = new StringBuilder();

                    if(currentPowerUpNum == 0){
                        sb.append(currentPlayerNum);
                        sb.append('n');
                        sb.append(9 - (((int)crosshairVector.y - 340) / 40));
                        sb.append(((int)crosshairVector.x - 60) / 40);
                    }
                    else if(currentPowerUpNum == 1){
                        sb.append(currentPlayerNum);
                        sb.append('s');
                        sb.append(9 - (((int)crosshairVector.y - 340) / 40));
                        sb.append(((int)crosshairVector.x - 60) / 40);
                    }
                    else if(currentPowerUpNum == 2){
                        sb.append(currentPlayerNum);
                        sb.append('m');
                        sb.append(9 - (((int)crosshairVector.y - 340) / 40));
                        sb.append(((int)crosshairVector.x - 60) / 40);
                    }
                    else if(currentPowerUpNum == 3){
                        sb.append(currentPlayerNum);
                        sb.append('d');
                        sb.append(9 - (((int)crosshairVector.y - 340) / 40));
                        sb.append(((int)crosshairVector.x - 60) / 40);
                    }

                    //TODO push sb to database

                    if(players[(currentPlayerNum + 1) % 2].allShipsDestroyed()){
                        //TODO end game state

                        //TODO let database know about the win
                        //TODO update winner coin by 1
                        //TODO update exp by 10
                        //TODO update win/loss
                    }

                }
                //if the game is local, change player's turns
                else{

                    if(players[(currentPlayerNum + 1) % 2].allShipsDestroyed()){
                        //TODO end game state

                        gsm.pop();
                    }

                    currentPlayerNum = (currentPlayerNum + 1) % 2;
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

        //always draw the background
        sb.draw(mainBackground, 0, 0, 480, 800);

        //do not draw the rest of the game screen until setup is complete to avoid unwanted
        //crashes due to null pointer exceptions
        if(setupCount > 2) {

            //draw the grid
            sb.draw(mainGrid, 20, 340, 440, 440);
            sb.draw(coordinateBackgroundRegion, 190, 120, 100, 100);


            //draw the buttons
            sb.draw(upBtn.getImage(), upBtn.getX(), upBtn.getY(), upBtn.getWidth(), upBtn.getHeight());
            sb.draw(downBtn.getImage(), downBtn.getX(), downBtn.getY(), downBtn.getWidth(), downBtn.getHeight());
            sb.draw(leftBtn.getImage(), leftBtn.getX(), leftBtn.getY(), leftBtn.getWidth(), leftBtn.getHeight());
            sb.draw(rightBtn.getImage(), rightBtn.getX(), rightBtn.getY(), rightBtn.getWidth(), rightBtn.getHeight());

            //if we wish to view our own board.... (decided by pan buttons in the handleInput method)
            if(seeYourBoard) {

                //draw the proper pan button
                sb.draw(panRight.getImage(), panRight.getX(), panRight.getY(), panRight.getWidth(), panRight.getHeight());

                //ships: Draw the ships based on their orientation
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

                //check each cell to see if it has been hit or not
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        //if it was hit with a ship in it, draw a ship hit marker on the ship(fire ball)
                        if(players[(currentPlayerNum)].cellIsHit(i, j) && players[currentPlayerNum].cellContainsShip(i, j)){
                            shipHitVector.set((i*40) + 70, (j*40) + 350, 0);
                            sb.draw(shipHitRegion, shipHitVector.x, shipHitVector.y, 20, 20);
                        }
                        //if there was a hit but no ship, just draw a miss marker(white x)
                        else if(players[(currentPlayerNum)].cellIsHit(i, j)){
                            missVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(missRegion, missVector.x, missVector.y, 40, 40);
                        }
                    }
                }
            }
            //we are now looking at enemy waters
            else{

                //draw the appropriate pan button
                sb.draw(panLeft.getImage(), panLeft.getX(), panLeft.getY(), panLeft.getWidth(), panLeft.getHeight());

                if(online){
                    sb.draw(leftPowerUp.getImage(), leftPowerUp.getX(), leftPowerUp.getY(), leftPowerUp.getWidth(), leftPowerUp.getHeight());
                    sb.draw(rightPowerUp.getImage(), rightPowerUp.getX(), rightPowerUp.getY(), rightPowerUp.getWidth(), rightPowerUp.getHeight());
                    sb.draw(powerUpRegion, 70, 20, 50, 50);
                }

                //check if all cells to see if there were any hits
                for(int i = 0; i < 10; i++){
                    for(int j = 0; j < 10; j++){
                        //if there was a hit with a ship in it, draw a hit marker(red x)
                        if(players[(currentPlayerNum + 1) % 2].cellIsHit(i, j) && players[(currentPlayerNum + 1) % 2].cellContainsShip(i, j)){
                            hitMarkerVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(hitMarkerRegion, hitMarkerVector.x, hitMarkerVector.y, 40, 40);
                        }
                        //if there was a hit with a miss, draw a miss marker(white x)
                        else if(players[(currentPlayerNum + 1) % 2].cellIsHit(i, j)){
                            missVector.set((i*40) + 60, (j*40) + 340, 0);
                            sb.draw(missRegion, missVector.x, missVector.y, 40, 40);
                        }
                    }
                }

                //draw the crosshair
                sb.draw(crosshairRegion, crosshairVector.x, crosshairVector.y, 40, 40);

                if(online && currentPowerUpNum == 2){
                    sb.draw(crosshairRegion2, crosshairVector2.x, crosshairVector2.y, 40, 40);
                    sb.draw(crosshairRegion3, crosshairVector3.x, crosshairVector3.y, 40, 40);
                    sb.draw(crosshairRegion4, crosshairVector4.x, crosshairVector4.y, 40, 40);
                }

                //animate the crosshair
                if(crosshairMoving){
                    if(pressUp){
                        crosshairVector.add(0, 8, 0);
                        if(online && currentPowerUpNum == 2){
                            crosshairVector2.add(0, 8, 0);
                            crosshairVector3.add(0, 8, 0);
                            crosshairVector4.add(0, 8, 0);
                        }
                        if((crosshairVector.y + 20) % 40 == 0){
                            crosshairMoving = false;
                            pressUp = false;
                        }
                    }
                    else if(pressRight){
                        crosshairVector.add(8, 0, 0);
                        if(online && currentPowerUpNum == 2){
                            crosshairVector2.add(8, 0, 0);
                            crosshairVector3.add(8, 0, 0);
                            crosshairVector4.add(8, 0, 0);
                        }
                        if((crosshairVector.x + 20) % 40 == 0){
                            crosshairMoving = false;
                            pressRight = false;
                        }
                    }
                    else if(pressDown){
                        crosshairVector.add(0, -8, 0);
                        if(online && currentPowerUpNum == 2){
                            crosshairVector2.add(0, -8, 0);
                            crosshairVector3.add(0, -8, 0);
                            crosshairVector4.add(0, -8, 0);
                        }
                        if((crosshairVector.y + 20) % 40 == 0){
                            crosshairMoving = false;
                            pressDown = false;
                        }
                    }
                    else if(pressLeft){
                        crosshairVector.add(-8, 0, 0);
                        if(online && currentPowerUpNum == 2){
                            crosshairVector2.add(-8, 0, 0);
                            crosshairVector3.add(-8, 0, 0);
                            crosshairVector4.add(-8, 0, 0);
                        }
                        if((crosshairVector.x + 20) % 40 == 0){
                            crosshairMoving = false;
                            pressLeft = false;
                        }
                    }
                }

                sb.draw(rocketRegion, rocketVector.x, rocketVector.y, 40, 40);

                if(dropBombs){
                    rocketVector.add(0, -8, 0);
                }

                if(rocketVector.y == crosshairVector.y) {
                    rocketVector.set(800, 800, 0);
                    if(players[(currentPlayerNum + 1) % 2].cellContainsShip(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40)){
                        if(Battleship.soundOn)
                            explosion.play();
                    }
                    else{
                        if(Battleship.soundOn)
                            splash.play();
                    }
                    dropBombs = false;
                }

                //draw a grey fire button if the currently selected cell has already been hit
                if(players[(currentPlayerNum + 1) % 2].cellIsHit(((int)crosshairVector.x - 60) / 40, ((int)crosshairVector.y - 340) / 40)){
                    sb.draw(greyFireBtn.getImage(), greyFireBtn.getX(), greyFireBtn.getY(), greyFireBtn.getWidth(), greyFireBtn.getHeight());
                }
                //else draw an active fire button
                else{
                    sb.draw(fireBtn.getImage(), fireBtn.getX(), fireBtn.getY(), fireBtn.getWidth(), fireBtn.getHeight());
                }
            }
        }
        sb.end();
    }

    //need to do some disposing so nasty memory leaks don't occur
    @Override
    public void dispose() {
        background.dispose();
        gameGrid.dispose();
        coordinateBackground.dispose();
        crosshair.dispose();
        crosshair2.dispose();
        crosshair3.dispose();
        crosshair4.dispose();
        miss.dispose();
        shipHit.dispose();
        hitMarker.dispose();
        rocket.dispose();

        for(int i = 0; i < powerUp.length - 1; i++){
            powerUp[i].dispose();
        }

        upBtn.disposeAssets();
        downBtn.disposeAssets();
        leftBtn.disposeAssets();
        rightBtn.disposeAssets();
        greyFireBtn.disposeAssets();
        fireBtn.disposeAssets();
        panRight.disposeAssets();
        panLeft.disposeAssets();
        rightPowerUp.disposeAssets();
        leftPowerUp.disposeAssets();

        minesweeper1.dispose();
        minesweeper2.dispose();
        frigate1.dispose();
        frigate2.dispose();
        submarine1.dispose();
        submarine2.dispose();
        battleship1.dispose();
        battleship2.dispose();
        carrier1.dispose();
        carrier2.dispose();

        explosion.dispose();
        splash.dispose();

        System.out.println("Play State Disposed");
    }

}