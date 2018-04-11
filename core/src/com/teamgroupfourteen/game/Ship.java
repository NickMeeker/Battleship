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

    private Texture[] pictures = new Texture[4];
    private Texture currentPicture;
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

        //fill the pictures array with the 4 orientations

        //minesweeper
        if(size == 2){

            for(int i = 0; i < 4; i++) {
                if(i == 0)
                    this.pictures[i] = new Texture("down_minesweeper.png");
                if(i == 1)
                    this.pictures[i] = new Texture("up_minesweeper.png");
                if(i == 2)
                    this.pictures[i] = new Texture("left_minesweeper.png");
                if(i == 3)
                    this.pictures[i] = new Texture("right_minesweeper.png");
            }
            this.position = new Vector3(220, 660, 0);
        }
        //3-size ships
        else if(size == 3){

            //submarine
            if(identifier == 1){
                for(int i = 0; i < 4; i++) {
                    if(i == 0)
                        this.pictures[i] = new Texture("down_submarine.png");
                    if(i == 1)
                        this.pictures[i] = new Texture("up_submarine.png");
                    if(i == 2)
                        this.pictures[i] = new Texture("left_submarine.png");
                    if(i == 3)
                        this.pictures[i] = new Texture("right_submarine.png");
                }
                this.position = new Vector3(140, 620, 0);
            }
            //frigate
            else{
                for(int i = 0; i < 4; i++) {
                    if(i == 0)
                        this.pictures[i] = new Texture("down_frigate.png");
                    if(i == 1)
                        this.pictures[i] = new Texture("up_frigate.png");
                    if(i == 2)
                        this.pictures[i] = new Texture("left_frigate.png");
                    if(i == 3)
                        this.pictures[i] = new Texture("right_frigate.png");
                }
                this.position = new Vector3(180, 620, 0);
            }

        }
        //battleship
        else if(size == 4){
            for(int i = 0; i < 4; i++) {
                if(i == 0)
                    this.pictures[i] = new Texture("down_battleship.png");
                if(i == 1)
                    this.pictures[i] = new Texture("up_battleship.png");
                if(i == 2)
                    this.pictures[i] = new Texture("left_battleship.png");
                if(i == 3)
                    this.pictures[i] = new Texture("right_battleship.png");
            }
            this.position = new Vector3(100, 580, 0);
        }
        //aircraft carrier
        else if(size == 5){
            for(int i = 0; i < 4; i++) {
                if(i == 0)
                    this.pictures[i] = new Texture("down_carrier.png");
                if(i == 1)
                    this.pictures[i] = new Texture("up_carrier.png");
                if(i == 2)
                    this.pictures[i] = new Texture("left_carrier.png");
                if(i == 3)
                    this.pictures[i] = new Texture("right_carrier.png");
            }
            this.position = new Vector3(60, 540,0);
        }

        //set the current picture
        this.currentPicture = this.pictures[0];

    }

    //rotates ship clockwise
    public void changeOrientation(){

        //rotate ship based on current orientation
        if(this.orientation == 'd') {
            this.orientation = 'l';
            this.currentPicture = this.pictures[2];
        }
        else if(this.orientation == 'l') {
            this.orientation = 'd';
            this.currentPicture = this.pictures[0];
        }
        else if(this.orientation == 'u') {
            this.orientation = 'r';
            this.currentPicture = this.pictures[3];
        }
        else if(this.orientation == 'r') {
            this.orientation = 'd';
            this.currentPicture = this.pictures[0];
        }
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
        return this.currentPicture;
    }

    public void setPosition(int x, int y, int z){
        this.position.set(x, y, z);
    }

    public void changePosition(int x, int y, int z){
        this.position.add(x, y, z);
    }

    public Vector3 getPosition(){
        return this.position;
    }

    public int getSize(){
        return this.size;
    }

    public char getOrientation(){
        return this.orientation;
    }
}
