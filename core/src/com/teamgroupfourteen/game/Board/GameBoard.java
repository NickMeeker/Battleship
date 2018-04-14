package com.teamgroupfourteen.game.Board;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Ship;

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

        char name;

        if(size == 2){
            name = 'm';
            this.Ships[0] = new Ship(size, name, 0);
        }
        else if(size == 3){

            if(Ships[1] == null){
                name = 'f';
                this.Ships[1] = new Ship(size, name, 0);
            }
            else{
                name = 's';
                this.Ships[2] = new Ship(size, name, 1);
            }

        }
        else if(size == 4){
            name = 'b';
            this.Ships[3] = new Ship(size, name, 0);
        }
        else if(size == 5){
            name = 'c';
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

    public char getShipName(int shipNum){
        return this.Ships[shipNum].getName();
    }

    public void  setShipOrientation(int shipNum, char orientation){
        this.Ships[shipNum].setOrientation(orientation);
    }

    public char getShipOrientation(int shipNum){
        return this.Ships[shipNum].getOrientation();
    }

    public void fillCell(int x, int y, int shipNum){
        this.gameBoard[x][y].fill();
    }

    public boolean cellContainsShip(int x, int y){
        return this.gameBoard[x][y].containsShip();
    }

    public void hitCell(int x, int y){
        this.gameBoard[x][y].hit();
    }

    public boolean isCellHit(int x, int y){
        return this.gameBoard[x][y].isHit();
    }

    public void placeShield(int x, int y){
        this.gameBoard[x][y].placeShield();
    }

    public boolean allShipsDestroyed(){

        int counter = 0;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                if(this.gameBoard[i][j].isHit() && this.gameBoard[i][j].containsShip()){
                    counter++;
                    if(counter == 17)
                        return true;
                }
            }
        }
        return false;
    }
}

