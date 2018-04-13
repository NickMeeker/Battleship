package com.teamgroupfourteen.game.Authentication;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by nick on 4/12/18.
 */

public class CredentialsManager {
    private boolean loggedIn;
    private String username;
    private String userId;
    private String token;


    public boolean getLoggedIn(){
        return this.loggedIn;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private HttpResponse<JsonNode> resp;

    private File ifp;

    // the default constructor is for when you only need to read data
    public CredentialsManager(){
        System.out.println("Attempting to read file");
        ifp = new File("authdata.txt");
        try{
            if(!ifp.exists())
                return;
            Scanner scan = new Scanner(ifp);
            this.loggedIn = scan.nextBoolean();
            scan.nextLine();
            this.username = scan.nextLine();
            this.userId = scan.nextLine();
            this.token = scan.nextLine();
            System.out.println(this.token);
        }catch(IOException e) {
            return;
        }
    }
    public CredentialsManager(String username, HttpResponse<JsonNode> resp){
        this.username = username;
        this.resp = resp;

        this.userId = resp.getBody().getObject().getString("id");
        this.token = resp.getBody().getObject().getString("token");

        ifp = new File("authdata.txt");
    }

    public void writeCredentials(){
        // stores credentials upon successful login:
        // (boolean) logged in on device
        // (string) username
        // (string) user id
        // (string) bearer token
        try {
            if (ifp.exists())
                ifp.delete();
            ifp.createNewFile();
            FileWriter writer = new FileWriter(ifp);
            writer.write("true");
            writer.write("\n");
            writer.write(username);
            writer.write("\n");
            writer.write(userId);
            writer.write("\n");
            writer.write(token);
            writer.flush();
            writer.close();
        }catch(IOException e){

        }
    }

    public boolean attemptInitAuth(){
        return loggedIn;
    }

}
