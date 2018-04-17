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
            return true;

        } else {
            System.out.println("Login Failed");
            return false;
        }
    }

    public void writeCredentials(){
        // stores credentials upon successful login:
        // (boolean) logged in on device
        // (string) username
        // (string) user id
        // (string) bearer token
        setIfp(new File("authdata.txt"));
        try {
            if (getIfp().exists())
                getIfp().delete();
            getIfp().createNewFile();
            FileWriter writer = new FileWriter(ifp);
            writer.write("true");
            writer.write("\n");
            writer.write(username);
            writer.write("\n");
            writer.write(getResp().getBody().getObject().getString("id"));
            writer.write("\n");
            writer.write(getResp().getBody().getObject().getString("token"));
            writer.flush();
            writer.close();
        }catch(IOException e){

        }
    }

    // used to check if the user is already logged in on the device
    public boolean attemptInitAuth(){
        setIfp(new File("authdata.txt"));
        try{
            if(!getIfp().exists())
                return false;
            Scanner scan = new Scanner(ifp);
            boolean loggedIn = scan.nextBoolean();
            return loggedIn;
        }catch(IOException e) {
            return false;
        }
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


