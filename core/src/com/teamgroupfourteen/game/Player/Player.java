package com.teamgroupfourteen.game.Player;

import com.teamgroupfourteen.game.GameBoard;

/**
 * Created by Jeremy on 3/31/2018.
 */

//A player with all relevant information
public class Player {

    //playerName is based on username

    private String playerName;
    private GameBoard board;
    private String exp;
    private String coins;
    private String numPowerup1;
    private String numPowerup2;
    private String numPowerup3;

    public Player (String name){

        //if name is null, no online account is active
        if(name == null) {
            playerName = "Player1";
            exp = "--";
            coins = "--";
            numPowerup1 = "--";
            numPowerup2 = "--";
            numPowerup3 = "--";
        }
        else {
            playerName = name;
            // TODO: Query Database For Player Information
        }

        board = new GameBoard();

    }
}
