package com.teamgroupfourteen.game.Authentication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import org.json.JSONObject;

/**
 * Created by nick on 4/11/18.
 */

public class APIParser {
    public static JSONObject getJsonObject(HttpResponse<JsonNode> resp){
        return (JSONObject)resp.getBody().getArray().get(0);
    }
}
