package com.teamgroupfourteen.game.Board;


import com.teamgroupfourteen.game.Ship;

/**
 * Created by Jeremy on 3/31/2018.
 */

//Holds all of the information for each position on the board
public class BoardCell {
    private boolean containsShip;
    private boolean shipFront;
    private boolean hit;
    private Ship ship;
    private boolean hasShield;

    public BoardCell(){
        this.containsShip = false;
        this.shipFront = false;
        this.hit = false;
        this.ship = null;
        this.hasShield = false;
    }

    public void fill(){
        this.containsShip = true;
    }

    public boolean containsShip(){
        return containsShip;
    }

    public void hit(){
        this.hit = true;
    }

    public boolean isHit(){
        return hit;
    }

    public void placeShield(){
        this.hasShield = true;
    }
}
