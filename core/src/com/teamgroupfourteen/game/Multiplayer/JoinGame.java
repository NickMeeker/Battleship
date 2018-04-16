package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.User.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nick on 4/15/18.
 */

public class JoinGame {
    private HttpResponse<JsonNode> resp;
    private User user;
    private String guestName;
    private ArrayList<JSONObject> gamesList;
    public JoinGame(String guestName, User user){
        this.user = user;
        this.guestName = guestName;
    }

    public void pairGames(){
        JSONArray gamesArray = getGames();
        gamesList = new ArrayList<JSONObject>();
        for(int i = 0; i < gamesArray.length(); i++){
            gamesList.add((JSONObject)gamesArray.get(i));
        }
        while(!gamesList.isEmpty()){
            Random rand = new Random();
            int tryIndex = rand.nextInt(gamesList.size());
            String hostPlayer = gamesList.get(tryIndex).getString("hostPlayer");
            if(hostPlayer.equals(guestName) || gamesList.get(tryIndex).getBoolean("Active")){
                gamesList.remove(tryIndex);
                continue;
            } else{
                // WE FOUND A GAME
                String gameID = gamesList.get(tryIndex).getString("gameID");
                MultiplayerGameManager mgm = new MultiplayerGameManager(gameID);
                mgm.updateGuestPlayer(guestName);
                //GameLoader gameLoader = new GameLoader(gameID);
            }
        }
    }

    public JSONArray getGames(){
        try{
            resp = Unirest.get(Battleship.APIPREFIX + "games")
                    .header("Authorization", user.getToken())
                    .asJson();

            return resp.getBody().getArray();

        }catch(UnirestException e){
            // TODO error etc
        }

        return null;
    }

    //public JSONObject get
}
