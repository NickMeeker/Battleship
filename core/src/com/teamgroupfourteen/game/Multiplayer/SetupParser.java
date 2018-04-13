package com.teamgroupfourteen.game.Multiplayer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 4/12/18.
 */

public class SetupParser {
    private String setupLog;
    private ArrayList<String> logEntries;

    public SetupParser(String setupLog){
        this.setupLog = setupLog;
    }

    public void buildLogEntries(){
        String[] strArr = setupLog.split(",");
        logEntries = new ArrayList<String>(Arrays.asList(strArr));
    }

    public void parseLogEntry(String logEntry){
        int playerNum;
        if(logEntry.charAt(0) == '0'){
            // THIS IS HOST PLAYER
            playerNum = 0;
        } else if(logEntry.charAt(1) == '1'){
            // THIS IS GUEST PLAYER
            playerNum = 1;
        }

        // Row will be uppercase A-J
        char row = logEntry.charAt(1);
        row -= 65;

        // Column will be 0-9
        char column = logEntry.charAt(2);

        int shipNum;
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

        char orientation = logEntry.charAt(4);

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
}
