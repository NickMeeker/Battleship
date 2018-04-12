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
    private String username;
    private String userId;
    private String token;

    private HttpResponse<JsonNode> resp;

    private File ifp;

    // the default constructor is for when you only need to read data
    public CredentialsManager(){
        ifp = new File("authdata.txt");
        try{
            if(ifp.exists())
                return;
            Scanner scan = new Scanner(ifp);
            boolean loggedIn = scan.nextBoolean();
            this.username = scan.nextLine();
            this.userId = scan.nextLine();
            this.token = scan.nextLine();
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
        try{
            if(ifp.exists())
                return false;
            Scanner scan = new Scanner(ifp);
            boolean loggedIn = scan.nextBoolean();
            return loggedIn;
        }catch(IOException e) {
            return false;
        }
    }

}
