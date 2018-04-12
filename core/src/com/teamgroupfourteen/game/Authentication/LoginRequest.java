package com.teamgroupfourteen.game.Authentication;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Battleship;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by nick on 4/8/18.
 */

public class LoginRequest {
    private String username;
    private String password;
    private HttpResponse<JsonNode> resp;

    private File ifp;

    private CredentialsManager cm;

    public LoginRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);

    }

    public boolean newLoginAttempt() {
        HttpResponse<JsonNode> resp;
        try {
            resp = Unirest.post(Battleship.APIPREFIX + "authenticate")
                    .field("username", username)
                    .field("password", password)
                    .asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            System.out.println(String.valueOf(HttpStatus.SC_INTERNAL_SERVER_ERROR));
            return false;
        }

        if (resp.getStatus() == 201) {
            System.out.println("Login Successful");
            this.setResp(resp);
            cm = new CredentialsManager(username, resp);
            return true;

        } else if(resp.getStatus() == 404){
            // TODO: Invalid username/password error message
            System.out.println("Invalid Username/password");
            return false;
        }else {
            System.out.println("Login Failed");
            return false;
        }
    }

    public void writeCredentials(){
        cm.writeCredentials();
    }

    // used to check if the user is already logged in on the device
    public boolean attemptInitAuth(){
        cm = new CredentialsManager();
        return cm.getLoggedIn();
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HttpResponse<JsonNode> getResp() {
        return resp;
    }

    public void setResp(HttpResponse<JsonNode> resp) {
        this.resp = resp;
    }

    public File getIfp() {
        return ifp;
    }

    public void setIfp(File ifp) {
        this.ifp = ifp;
    }
}


