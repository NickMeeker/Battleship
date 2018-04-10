package com.teamgroupfourteen.game.Authentication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Battleship;

/**
 * Created by nick on 4/9/18.
 */

public class RegistrationRequest {
    private String username;
    private String password;
    private String confirmPassword;

    private HttpResponse<JsonNode> resp;

    public RegistrationRequest(String username, String password, String confirmPassword){
        this.username = username;
        this.password = password;
        this.confirmPassword = password;
    }

    public boolean newRegistrationAttempt(){
        HttpResponse<JsonNode> resp;
        try{
            resp = Unirest.post(Battleship.APIPREFIX + "users")
                    .field("username", username)
                    .field("password", password)
                    .asJson();

            if(resp.getStatus() == 201){
                System.out.println("Registration Successful");
                return true;
            }else{
                return false;
            }
        } catch(UnirestException e){
            return false;
        }
    }
}

