package com.teamgroupfourteen.game;



/**
 * Created by Jeremy on 3/31/2018.
 */

//Holds all of the information for each position on the board
public class BoardCell {
    private boolean containsShip;
    private boolean shipFront;
    private boolean hit;
    private Ship ship;

    public BoardCell(){
        containsShip = false;
        shipFront = false;
        hit = false;
        ship = null;
    }

    public void fill(){
        this.containsShip = true;
    }
}
