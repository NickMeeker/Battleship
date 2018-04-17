package com.teamgroupfourteen.game.Authentication;


import com.badlogic.gdx.Net;
import com.mashape.unirest.http.HttpResponse;

import org.json.JSONObject;



public class APIParser {

    JSONObject object;

    public static JSONObject getJsonObject(HttpResponse resp){

        return new JSONObject(resp.getBody());
        //The way this is being called in the player class, returns a JSON object. which I can't seem to extract the value from
        //But I could write a parser than takes in the HTTPResponse and a 'key', to return the value requested
        //Let me know, I'm probably missing something.
    }
}