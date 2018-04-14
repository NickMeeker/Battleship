package com.teamgroupfourteen.game.Multiplayer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 4/12/18.
 */

public class GameLogParser {
    private String gameLog;

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
        int playerNum;
        if(logEntry.charAt(1) == '0')
            playerNum = 0;
        else if(logEntry.charAt(1) == '1')
            playerNum = 1;


        if(logEntry.charAt(0) == 'n'){
            // we're dealing with a normal move of firing one shot
            int row = logEntry.charAt(2);
            row -= 65;

            int column = logEntry.charAt(3);

            // result with either be h or o
            // h represents a hit and o represents a miss
            // we can change it to x and o if it's easier to read
            char result = logEntry.charAt(4);

        } else if(logEntry.charAt(0) == 's'){
            // we're dealing with a shield placement
            int row = logEntry.charAt(2);
            row -= 65;

            int column = logEntry.charAt(3);

            // yields the row and column of the shield

        } else if(logEntry.charAt(0) == 'e'){
            // we're dealing with an evade

            // get the type of ship evading
            int shipNum;
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

            int row = logEntry.charAt(3);
            row -= 65;

            int column = logEntry.charAt(4);

            // orientation, d for down and r for right
            char orientation = logEntry.charAt(5);

        } else if(logEntry.charAt(0) == 'm'){
            // we're dealing with a multishot
            int rowShot1 = logEntry.charAt(2);
            rowShot1 -= 65;

            int columnShot1 = logEntry.charAt(3);
            char result1 = logEntry.charAt(4);

            // and again for the next shot

            int rowShot2 = logEntry.charAt(5);
            rowShot2 -= 65;

            int columnShot2 = logEntry.charAt(6);
            char result2 = logEntry.charAt(7);
        } else{
            // bad text, this should never happen
            System.out.println("Error: game log incorrect");
        }
    }

}
