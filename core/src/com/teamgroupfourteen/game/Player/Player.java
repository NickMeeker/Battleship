package com.teamgroupfourteen.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.GameBoard;

/**
 * Created by Jeremy on 3/31/2018.
 */

//A player with all relevant information
public class Player {

    //playerName is based on username

    private String playerName;
    private GameBoard board;
    private String exp;
    private String coins;
    private String numPowerup1;
    private String numPowerup2;
    private String numPowerup3;

    public Player (String name){

        //if name is null, no online account is active
        if(name == null) {
            playerName = "Player1";
            exp = "--";
            coins = "--";
            numPowerup1 = "--";
            numPowerup2 = "--";
            numPowerup3 = "--";
        }
        else {
            playerName = name;
            // TODO: Query Database For Player Information
        }

        board = new GameBoard();

    }

    public void createShips(){

        this.board.makeShip(2);
        this.board.makeShip(3);
        this.board.makeShip(3);
        this.board.makeShip(4);
        this.board.makeShip(5);

    }

    public Texture getShipTexture(int shipNum){

        return this.board.getShipTexture(shipNum);

    }

    public void updateShipPosition(int shipNum, int x, int y, int z){
        this.board.updateShipPosition(shipNum, x, y, z);
    }

    public Vector3 getShipPosition(int shipNum){
        return this.board.getShipPosition(shipNum);
    }

    public int getShipSize (int shipNum){
        return this.board.getShipSize(shipNum);
    }

    public void rotateShip(int shipNum){
        this.board.rotateShip(shipNum);
    }

    public char getShipOrientation(int shipNum){
        return this.board.getShipOrientation(shipNum);
    }

    public void setShipPosition(int shipNum, int x, int y, int z){
        this.board.setShipPosition(shipNum, x, y, z);
    }

    public void fillCell(int x, int y, int shipNum){
        this.board.fillCell(x, y, shipNum);
    }
}
