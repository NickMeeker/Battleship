package com.teamgroupfourteen.game.Multiplayer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 4/12/18.
 */

public class SetupParser {
    private String setupLog;
    private ArrayList<String> logEntries;
    int playerNum;
    int row;
    int column;
    int shipNum;
    char orientation;

    public SetupParser(String setupLog){
        this.setupLog = setupLog;
    }

    public void buildLogEntries(){
        String[] strArr = setupLog.split(",");
        logEntries = new ArrayList<String>(Arrays.asList(strArr));
    }

    public void parseLogEntry(int entryNum){

        String logEntry = this.logEntries.get(entryNum);

        if(logEntry.charAt(0) == '0'){
            // THIS IS HOST PLAYER
            playerNum = 0;
        } else if(logEntry.charAt(0) == '1'){
            // THIS IS GUEST PLAYER
            playerNum = 1;
        }

        // Row will be uppercase A-J
        row = logEntry.charAt(1);
        row -= 65;

        // Column will be 0-9
        column = logEntry.charAt(2);
        column -= 48;

        switch(logEntry.charAt(3)){
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

        orientation = logEntry.charAt(4);

        // Now you have player number, row, column, shipNum, and orientation to work with

    }

    public String getSetupLog() {
        return setupLog;
    }

    public void setSetupLog(String setupLog) {
        this.setupLog = setupLog;
    }

    public ArrayList<String> getLogEntries() {
        return logEntries;
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public int getShipNum(){
        return this.shipNum;
    }

    public char getOrientaion(){
        return this.orientation;
    }
}
