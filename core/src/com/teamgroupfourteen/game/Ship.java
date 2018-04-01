package com.teamgroupfourteen.game;



/**
 * Created by Jeremy on 3/31/2018.
 */

public class Ship {

    //size indicates size of ship
    //orientation indicates the direction of the ship based on the front of the ship
        //d = down, u = up, l = left, r = right
    //damaged[] is an array that denotes which part of the ship is damaged
    private int size;
    private char orientation = 'd';
    private boolean[] damaged;

    //constructor
    public Ship (int size){

        this.size = size;
        damaged = new boolean[size];

    }

    //rotates ship clockwise
    private void changeOrientation(){

        //rotate ship based on current orientation
        if(this.orientation == 'd')
            orientation = 'l';
        if(this.orientation == 'l')
            orientation = 'u';
        if(this.orientation == 'u')
            orientation = 'r';
        if(this.orientation == 'r')
            orientation = 'd';

    }

    private void hitShip(int coordinate){

        this.damaged[coordinate] = true;

    }

    //checks if the ship is destroyed
    private boolean checkDestroyed(){

        //check if there are any undamaged portions of the ship
        for (int i = 0; i < this.size; i++)
            //if undamaged sections detected, ship is not destroyed
            if (damaged[i] = false)
                return false;

        //if all portions are damaged, ship is destroyed
        return true;
    }


}
