package com.teamgroupfourteen.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jeremy on 3/31/2018.
 */

public class Ship {

    //size indicates size of ship
    //orientation indicates the direction of the ship based on the front of the ship
        //d = down, u = up, l = left, r = right
    //damaged[] is an array that denotes which part of the ship is damaged

    private Texture picture;
    private String name;
    private int size;
    private char orientation = 'd';
    private boolean[] damaged;
    private Vector3 position;

    //constructor
    public Ship (int size, String name, int identifier){

        this.name = name;
        this.size = size;
        damaged = new boolean[size];

        if(size == 2){
            this.picture = new Texture("minesweeper.png");
            this.position = new Vector3(220, 660, 0);
        }
        else if(size == 3){

            if(identifier == 1){
                this.picture = new Texture("submarine.png");
                this.position = new Vector3(140, 620, 0);
            }
            else{
                this.picture = new Texture("frigate.png");
                this.position = new Vector3(180, 620, 0);
            }

        }
        else if(size == 4){
            this.picture = new Texture("battleship.png");
            this.position = new Vector3(100, 580, 0);
        }
        else if(size == 5){
            this.picture = new Texture("carrier.png");
            this.position = new Vector3(60, 540,0);
        }

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

    public Texture getTexture(){
        return this.picture;
    }

    public void changePosition(int x, int y, int z){
        this.position.add(x, y, z);
    }

    public Vector3 getPosition(){
        return position;
    }
}
