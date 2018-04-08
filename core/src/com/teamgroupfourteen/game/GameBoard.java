package com.teamgroupfourteen.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Jeremy on 3/31/2018.
 */


//An individual player's game board
public class GameBoard {

    private int numShips;
    private Ship[] Ships;
    private BoardCell[][] gameBoard;

    public GameBoard(){

        Ships = new Ship[5];
        gameBoard = new BoardCell[10][10];

        for (int i = 0; i < 5; i++)
            Ships[i] = null;

        for(int i = 0; i < 10; i++)
        {
            for(int j = 0; j < 10; j++)
            {
                gameBoard[i][j] = new BoardCell();
            }
        }

    }

    public void makeShip(int size){

        String name;

        if(size == 2){
            name = "minesweeper";
            this.Ships[0] = new Ship(size, name, 0);
        }
        else if(size == 3){

            if(Ships[1] == null){
                name = "frigate";
                this.Ships[1] = new Ship(size, name, 0);
            }
            else{
                name = "submarine";
                this.Ships[2] = new Ship(size, name, 1);
            }

        }
        else if(size == 4){
            name = "battleship";
            this.Ships[3] = new Ship(size, name, 0);
        }
        else if(size == 5){
            name = "carrier";
            this.Ships[4] = new Ship(size, name, 0);
        }

    }

    public Texture getShipTexture(int shipNum){
        return Ships[shipNum].getTexture();
    }

    public void setShipPosition(int shipNum, int x, int y, int z){
        Ships[shipNum].setPosition(x, y, z);
    }

    public void updateShipPosition(int shipNum, int x, int y, int z){
        Ships[shipNum].changePosition(x, y, z);
    }

    public Vector3 getShipPosition(int shipNum){
        return Ships[shipNum].getPosition();
    }

    public int getShipSize(int shipNum){
        return Ships[shipNum].getSize();
    }

    public void rotateShip(int shipNum){
        this.Ships[shipNum].changeOrientation();
    }

    public char getShipOrientation(int shipNum){
        return this.Ships[shipNum].getOrientation();
    }
}

