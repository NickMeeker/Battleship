package com.teamgroupfourteen.game.Multiplayer;

/**
 * Created by nick on 4/12/18.
 */

public class SetupParser {
    private String setupLog;
    private String[] logEntries;

    public SetupParser(String setupLog){
        this.setSetupLog(setupLog);
    }

    public void buildLogEntries(){
        setLogEntries(getSetupLog().split(","));
    }

    public void parseLogEntry(String logEntry){
        if(logEntry.charAt(0) == '0'){
            // THIS IS HOST PLAYER
        } else if(logEntry.charAt(1) == '1'){
            // THIS IS GUEST PLAYER
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

    }

    public String getSetupLog() {
        return setupLog;
    }

    public void setSetupLog(String setupLog) {
        this.setupLog = setupLog;
    }

    public String[] getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(String[] logEntries) {
        this.logEntries = logEntries;
    }
}
