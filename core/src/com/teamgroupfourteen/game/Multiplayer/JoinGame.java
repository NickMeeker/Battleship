package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.States.GameStateManager;
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
    GameStateManager gsm;
    public JoinGame(String guestName, User user, GameStateManager gsm){
        this.user = user;
        this.guestName = guestName;
        this.gsm = gsm;
    }

    public String pairGames(){
        JSONArray gamesArray = getGames();
        gamesList = new ArrayList<JSONObject>();
        for(int i = 0; i < gamesArray.length(); i++){
            gamesList.add((JSONObject)gamesArray.get(i));
        }
        while(!gamesList.isEmpty()){
            Random rand = new Random();
            int tryIndex = rand.nextInt(gamesList.size());
            String hostPlayer = gamesList.get(tryIndex).getString("hostPlayer");
            if(hostPlayer.equals(guestName) || gamesList.get(tryIndex).getBoolean("active")){
                gamesList.remove(tryIndex);
            } else{
                // WE FOUND A GAME
                String gameID = gamesList.get(tryIndex).getString("_id");
                return gameID;

            }
        }

        // if we made it here, we were unable to join a game so there needs to be some functionality for that
        return "";
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
