package com.teamgroupfourteen.game.Multiplayer;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Authentication.APIParser;
import com.teamgroupfourteen.game.Authentication.CredentialsManager;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Player.Player;

/**
 * Created by nick on 4/12/18.
 */

public class HostGame {
    private String host;
    private HttpResponse<JsonNode> resp;
    private CredentialsManager cm;
    public String gameID;
    public HostGame(String host){
        this.host = host;
        this.cm = new CredentialsManager();
        try{
            this.resp = Unirest.post(Battleship.APIPREFIX + "games")
                .header("Authorization", cm.getToken())
                .field("move", "")
                .field("setupLog", "")
                .field("gameLog", "")
                .field("turnPlayer", "")
                .field("hostPlayer", this.host)
                .field("guestPlayer", "")
                .field("timeElapsed", 0)
                .field("active", false)
                .asJson();

            gameID = this.resp.getBody().getObject().getJSONObject("data").getString("_id");
        }catch(UnirestException e){
            // TODO: Implement error handling
            System.out.println(e);
        }
    }
}
