package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Authentication.CredentialsManager;
import com.teamgroupfourteen.game.Battleship;

import org.json.JSONObject;

/**
 * Created by nick on 4/12/18.
 */

public class GameLoader  {
    private String gameID;
    private String hostPlayer;
    private String guestPlayer;
    private String turnPlayer;
    String setupLog;
    String gameLog;
    private JSONObject gameData;
    CredentialsManager cm;

    public GameLoader(String gameID){
        this.gameID = gameID;
        this.cm = new CredentialsManager();
        gameData = getGameById();
        assignDataFields();
    }

    public void assignDataFields(){
        hostPlayer = gameData.getString("hostPlayer");
        guestPlayer = gameData.getString("guestPlayer");
        setupLog = gameData.getString("setupLog");
        gameLog = gameData.getString("gameLog");
        turnPlayer = gameData.getString("turnPlayer");
    }

    public void buildGame(){
        buildSetup();
    }

    private void buildSetup(){
        SetupParser sp = new SetupParser(setupLog);
        sp.buildLogEntries();
        int l = sp.getLogEntries().size();
        for(int i = 0; i < l; i++){
            sp.parseLogEntry(sp.getLogEntries().get(i));
        }

        GameLogParser glp = new GameLogParser(gameLog);
        glp.buildLogEntries();
        l = glp.getLogEntries().size();
        for(int i = 0; i <l; i++){
            glp.parseLogEntries(glp.getLogEntries().get(i));
        }

    }

    private JSONObject getGameById(){
        try{
            HttpResponse<JsonNode> resp = Unirest.get(Battleship.APIPREFIX + "games/" + gameID)
                    .header("Authorization", cm.getToken())
                    .asJson();

            JSONObject respAsJson = APIParser.getJsonObject(resp);
            return respAsJson;
        } catch(UnirestException e){
            return null;
        }
    }
}