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

    private int playerNum;
    private int row;
    private int column;
    private char result;
    private int shipNum;
    private char orientation;
    private int rowShot1;
    private int rowShot2;
    private int columnShot1;
    private int columnShot2;
    private char result1;
    private char result2;
    private char moveType;

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

            moveType = logEntry.charAt(0);
            // we're dealing with a normal move of firing one shot
            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);

        } else if(logEntry.charAt(0) == 's'){
            // we're dealing with a shield placement
            moveType = logEntry.charAt(0);
            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);


        } else if(logEntry.charAt(0) == 'd'){
            // we're dealing with a double shot

            moveType = logEntry.charAt(0);

            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);


        } else if(logEntry.charAt(0) == 'm'){
            // we're dealing with a multishot
            moveType = logEntry.charAt(0);

            row = logEntry.charAt(2);
            row -= 65;

            column = logEntry.charAt(3);
        } else{
            // bad text, this should never happen
            System.out.println("Error: game log incorrect");
        }
    }

    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public char getOrientation(){
        return this.orientation;
    }

    public int getPlayerNum(){
        return this.playerNum;
    }

    public char getMoveType(){
        return this.moveType;
    }
}
