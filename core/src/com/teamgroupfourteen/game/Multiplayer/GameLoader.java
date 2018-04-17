package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Authentication.CredentialsManager;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.States.GameStateManager;
import com.teamgroupfourteen.game.States.PlayState;

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

    public GameLoader(String gameID, GameStateManager gsm, String playerName){
        this.gameID = gameID;
        System.out.println(gameID);
        this.cm = new CredentialsManager();
        gameData = getGameById();
        assignDataFields();

        Player player1 = new Player(hostPlayer);
        Player player2 = new Player(guestPlayer);
        Player clientPlayer;

        if(playerName.equals(player1.getPlayerName())){
            clientPlayer = player1;
        } else
            clientPlayer = player2;
        gsm.set(new PlayState(gsm, player1, player2, false, true, setupLog, gameLog, gameID, clientPlayer));
    }

    public void assignDataFields(){
        hostPlayer = gameData.getString("hostPlayer");
        guestPlayer = gameData.getString("guestPlayer");
        setupLog = gameData.getString("setupLog");
        gameLog = gameData.getString("gameLog");
        turnPlayer = gameData.getString("turnPlayer");
    }

    private JSONObject getGameById(){
        try{
            HttpResponse<JsonNode> resp = Unirest.get(Battleship.APIPREFIX + "games/" + gameID)
                    .header("Authorization", cm.getToken())
                    .asJson();

            System.out.println(resp.getBody().getObject());
            JSONObject respAsJson = APIParser.getJsonObject(resp);
            System.out.println(respAsJson);
            return respAsJson;
        } catch(UnirestException e){
            return null;
        }
    }
}
