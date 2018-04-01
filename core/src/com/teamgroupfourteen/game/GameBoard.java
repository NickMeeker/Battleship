package com.teamgroupfourteen.game;



/**
 * Created by Jeremy on 3/31/2018.
 */


//An individual player's game board
public class GameBoard {

    private int numShips;
    private BoardCell[][] gameBoard;

    public GameBoard(){

        gameBoard = new BoardCell[10][10];

    }
}
