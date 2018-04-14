package com.teamgroupfourteen.game.User;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Authentication.CredentialsManager;
import com.teamgroupfourteen.game.Battleship;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nick on 4/12/18.
 */

public class User {
    private String username;
    private String userID;
    private String token;
    private String friendsString;
    private String[] friendsByID;
    private String[] friendsByName;
    private int coins;
    private int exp;
    private int wins;
    private int losses;
    private int numPowerUp1;
    private int numPowerUp2;
    private int numPowerUp3;

    CredentialsManager cm;
    HttpResponse<JsonNode> resp;
    JSONObject userJSON;

    public User(){
        cm = new CredentialsManager();
        username = cm.getUsername();
        userID = cm.getUserId();
        token = cm.getToken();
        getUserResp();
/*        coins = userJSON.getInt("coins");
        exp = userJSON.getInt("exp");
        wins = userJSON.getInt("wins");
        losses = userJSON.getInt("losses");
        numPowerUp1 = userJSON.getInt("powerUp1");
        numPowerUp2 = userJSON.getInt("powerUp2");
        numPowerUp3 = userJSON.getInt("powerUp3");
        friendsString = userJSON.getString("friends");
        friendsByName = getFriendsString().split(",");
*/
    }

    private void getUserResp(){
        try{
            resp = Unirest.get(Battleship.APIPREFIX + "users/" + getUserID())
                    .header("Authorization", getToken())
                    .asJson();

            userJSON = APIParser.getJsonObject(resp);
        }catch(UnirestException e){

        }
    }

    public void putUser(){
        if(getFriendsString().length() > 0 && getFriendsString().charAt(0) == ',')
            getFriendsString().replaceFirst(",", "");
        try{
            resp = Unirest.put(Battleship.APIPREFIX + "users/" + getUserID())
                    .header("Authorization", getToken())
                    .field("exp", getExp())
                    .field("wins", getWins())
                    .field("losses", getLosses())
                    .field("coins", getCoins())
                    .field("powerUp1", getNumPowerUp1())
                    .field("powerUp2", getNumPowerUp2())
                    .field("powerUp3", getNumPowerUp3())
                    .field("friends", getFriendsString())
                    .asJson();
        }catch(UnirestException e){
            System.out.println("Failed to put");
            System.out.println(e);
        }
    }

    public void addFriend(String newFriend){
        HttpResponse<JsonNode> searchUserResponse;
        int status = -1;
        try {
            searchUserResponse = Unirest.get(Battleship.APIPREFIX + "users/username/" + newFriend)
                    .header("Authorization", getToken())
                    .asJson();

            status = searchUserResponse.getStatus();
        } catch(UnirestException e){
            // TODO Error etc
        }


        if(!getFriendsString().contains(newFriend) && status == 201) {
            friendsString = getFriendsString() + ("," + newFriend);
            friendsByName = getFriendsString().split(",");
        }
        // implement friendsByID
        if(!getFriendsString().equals("")){
            friendsString = getFriendsString().substring(1, getFriendsString().length());
        }
        putUser();
    }

    public void deleteFriend(String deleteFriend){
        friendsByName = getFriendsString().split(",");
        ArrayList<String> strArr = new ArrayList<String>(Arrays.asList(getFriendsByName()));
        strArr.remove(deleteFriend);
        friendsString = "";
        for(int i = 0; i < strArr.size(); i++){
            friendsString = getFriendsString() + ("," + strArr.get(i));
        }

        if(!getFriendsString().equals("")){
            friendsString = getFriendsString().substring(1, getFriendsString().length());
        }
        putUser();
    }

    public JSONArray getUserHostGames(){
        HttpResponse<JsonNode> searchGames;
        try{
            searchGames = Unirest.get(Battleship.APIPREFIX + "games/hostPlayer/" + getUsername())
                    .header("Authorization", getToken())
                    .asJson();

            return searchGames.getBody().getArray();
        }catch(UnirestException e){

        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getUserID() {
        return userID;
    }

    public String getToken() {
        return token;
    }

    public String getFriendsString() {
        return friendsString;
    }

    public String[] getFriendsByID() {
        return friendsByID;
    }

    public String[] getFriendsByName() {
        return friendsByName;
    }

    public int getCoins() {
        return coins;
    }

    public int getExp() {
        return exp;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getNumPowerUp1() {
        return numPowerUp1;
    }

    public int getNumPowerUp2() {
        return numPowerUp2;
    }

    public int getNumPowerUp3() {
        return numPowerUp3;
    }

}
