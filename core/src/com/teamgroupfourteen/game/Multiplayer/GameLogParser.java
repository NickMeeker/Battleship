package com.teamgroupfourteen.game.Multiplayer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 4/12/18.
 */

public class GameLogParser {
    private String gameLog;
    private int playerNum;
    private int row;
    private int column;
    private int shipNum;
    private int shotResult;
    private int rowShot1;
    private int columnShot1;
    private int multiShotResult1;
    private int rowShot2;

    public int getPlayerNum() {
        return playerNum;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getShipNum() {
        return shipNum;
    }

    public int getShotResult() {
        return shotResult;
    }

    public int getRowShot1() {
        return rowShot1;
    }

    public int getColumnShot1() {
        return columnShot1;
    }

    public int getMultiShotResult1() {
        return multiShotResult1;
    }

    public int getRowShot2() {
        return rowShot2;
    }

    public int getColumnShot2() {
        return columnShot2;
    }

    public int getMultiShotResult2() {
        return multiShotResult2;
    }

    public char getOrientation() {
        return orientation;
    }

    public char getMoveType() {
        return moveType;
    }

    private int columnShot2;
    private int multiShotResult2;
    private char orientation;
    private char moveType;



    public ArrayList<String> getLogEntries() {
        return logEntries;
    }

    private ArrayList<String> logEntries;


    public GameLogParser(String gameLog){
        this.gameLog = gameLog;
    }

    public void buildLogEntries(){
        String[] strArr = gameLog.split(",");
        logEntries = new ArrayList<String>(Arrays.asList(strArr));
    }

    public void parseLogEntries(String logEntry){
        if(logEntry.charAt(1) == '0')
            playerNum = 0;
        else if(logEntry.charAt(1) == '1')
            playerNum = 1;


        if(logEntry.charAt(0) == 'n'){
            // we're dealing with a normal move of firing one shot
            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);

            // result with either be h or o
            // h represents a hit and o represents a miss
            // we can change it to x and o if it's easier to read
            shotResult = logEntry.charAt(4);

        } else if(logEntry.charAt(0) == 's'){
            // we're dealing with a shield placement
            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);

            // yields the row and column of the shield

        } else if(logEntry.charAt(0) == 'e'){
            // we're dealing with an evade

            // get the type of ship evading
            switch(logEntry.charAt(2)){
                case 'm':
                    // We are dealing with a minesweeper
                    shipNum = 0;
                    break;
                case 'f':
                    // We are dealing with a frigate
                    shipNum = 1;
                    break;
                case 's':
                    // submarine
                    shipNum = 2;
                    break;
                case 'b':
                    // battleship
                    shipNum = 3;
                    break;
                case 'c':
                    // carrier
                    shipNum = 4;
                    break;
            }

            row = logEntry.charAt(3);
            row -= 65;

            column = logEntry.charAt(4);

            // orientation, d for down and r for right
            orientation = logEntry.charAt(5);

        } else if(logEntry.charAt(0) == 'm'){
            // we're dealing with a multishot
            rowShot1 = logEntry.charAt(2);
            rowShot1 -= 65;

            columnShot1 = logEntry.charAt(3);
            multiShotResult1 = logEntry.charAt(4);

            // and again for the next shot

            rowShot2 = logEntry.charAt(5);
            rowShot2 -= 65;

            columnShot2 = logEntry.charAt(6);
            multiShotResult2 = logEntry.charAt(7);
        } else{
            // bad text, this should never happen
            System.out.println("Error: game log incorrect");
        }
    }

}
