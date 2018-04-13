package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.User.User;

import org.json.JSONObject;

/**
 * Created by nick on 4/13/18.
 */

public class MultiplayerGameManager {
    /*
    * Game Schema:
    *     move: String,
          setupLog: String,
          gameLog: String,
          turnPlayer: String,
          hostPlayer: String,
          guestPlayer: String,
          timeElapsed: Number,
          active: Boolean
    * */

    private String gameID;
    private String move;
    private String setupLog;
    private String gameLog;
    private String turnPlayer;
    private String hostPlayer;
    private String guestPlayer;
    private int timeElapsed;
    private boolean active;
    private User user;
    JSONObject gameData;


    public MultiplayerGameManager(String gameID){
        user = new User();
        this.gameID = gameID;
        getGameDataByID(this.gameID);
        move = gameData.getString("move");
        setupLog = gameData.getString("setupLog");
        gameLog = gameData.getString("gameLog");
        turnPlayer = gameData.getString("turnPlayer");
        hostPlayer = gameData.getString("hostPlayer");
        guestPlayer = gameData.getString("guestPlayer");
        timeElapsed = gameData.getInt("timeElapsed");
        active = gameData.getBoolean("active");
    }

    private void getGameDataByID(String gameID){
        try{
            HttpResponse<JsonNode> resp = Unirest.get(Battleship.APIPREFIX + "games/" + gameID)
                    .header("Authorization", user.getToken())
                    .asJson();

            gameData = APIParser.getJsonObject(resp);
        } catch(UnirestException e){
            // TODO
            System.out.println("helo");
        }
    }

    private void putUpdatedData(){
        try{
            HttpResponse<JsonNode> resp = Unirest.put(Battleship.APIPREFIX + "games/" + gameID)
                    .header("Authorization", user.getToken())
                    .field("move", move)
                    .field("setupLog", setupLog)
                    .field("gameLog", gameLog)
                    .field("turnPlayer", turnPlayer)
                    .field("hostPlayer", hostPlayer)
                    .field("guestPlayer", guestPlayer)
                    .field("timeElapsed", timeElapsed)
                    .field("active", active)
                    .asJson();


        }catch(UnirestException e){

        }
    }


    public void updateMove(String move){
        this.move = move;
        putUpdatedData();
    }

    public void updateSetupLog(String setupLog){
        this.setupLog = setupLog;
        putUpdatedData();
    }

    public void updateGameLog(String gameLog){
        this.gameLog = gameLog;
        putUpdatedData();
    }

    public void updateTurnPlayer(String turnPlayer){
        this.turnPlayer = turnPlayer;
        putUpdatedData();
    }

    public void updateTimeElapsed(int timeElapsed){
        this.timeElapsed = timeElapsed;
        putUpdatedData();
    }

    public void updateActive(boolean active){
        this.active = active;
        putUpdatedData();
    }


}
