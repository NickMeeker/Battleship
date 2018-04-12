package com.teamgroupfourteen.game.Player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameBoard;

import org.json.*;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Jeremy on 3/31/2018.
 */

//A player with all relevant information
public class Player {
    private GameBoard board;

    private String playerName;
    private String userID;
    private String token;
    private int exp;
    private int numCoins;
    private int numPowerUp1;
    private int numPowerUp2;
    private int numPowerUp3;

    private boolean isComputer;

    //computer setup
    private int currentShipNum;
    private int shipSideWays;
    private int setX;
    private int setY;
    private boolean collision;
    private boolean hit;
    private int prevHitX;
    private int prevHitY;
    private char predict;

    private HttpResponse<JsonNode> resp;


    public Player (String name){
        // if name is null, no online account is active
        if(name == "computer"){
            playerName = name;
            exp = 0;
            numCoins = 0;
            numPowerUp1 = 0;
            numPowerUp2 = 0;
            numPowerUp3 = 0;
            isComputer = true;
            hit = false;
            predict = 'u';
        }
        else if(name == null) {
            playerName = "Player1";
            exp = 0;
            numCoins = 0;
            numPowerUp1 = 0;
            numPowerUp2 = 0;
            numPowerUp3 = 0;
            isComputer = false;
        }
        else {
            getAuthDataFromFile();
            if(userID.equals("ERROR")){
                // TODO: implement error
                System.out.println("Something went wrong");
            }

            try {
                resp = Unirest.get(Battleship.APIPREFIX + "users/{id}")
                        .header("Authorization", token)
                        .routeParam("id", userID)
                        .asJson();

                JSONObject respAsJSON = (JSONObject)resp.getBody().getArray().get(0);

                exp = respAsJSON.getInt("exp");
                numCoins = respAsJSON.getInt("coins");
                numPowerUp1 = respAsJSON.getInt("powerUp1");
                numPowerUp2 = respAsJSON.getInt("powerUp2");
                numPowerUp3 = respAsJSON.getInt("powerUp3");
                isComputer = false;

                System.out.println(exp + " " + numCoins + " " + numPowerUp1 + " " + numPowerUp2 + " " + numPowerUp3);

            } catch(UnirestException e){
                // TODO: implement error

            }
            playerName = name;
            // TODO: Query Database For Player Information
        }

        board = new GameBoard();

    }

    public void createShips(){

        this.board.makeShip(2);
        this.board.makeShip(3);
        this.board.makeShip(3);
        this.board.makeShip(4);
        this.board.makeShip(5);

    }

    public void makeBoard(){
        currentShipNum = 0;
        Random rand = new Random();

        this.createShips();

        for(int i = 0; i < 5; i++){
            shipSideWays = rand.nextInt(2);
            setX = rand.nextInt(10);
            setY = rand.nextInt(10);
            collision = true;

            //check for valid placement
            while(collision){System.out.println("check1");
                collision = false;
                for(int j = 0; j < this.getShipSize(i); j++){
                    if(shipSideWays == 0){
                        while (setY > 10 - this.getShipSize(i)){System.out.println("check2 setY = " + setY + " i = " + i + " j = "+ j);
                            setY = rand.nextInt(10);
                        }
                        if(this.cellContainsShip(setX, setY + j)){
                            collision = true;
                            setX = rand.nextInt(10);
                            setY = rand.nextInt(10);
                            j = -1;
                            System.out.println("check3 setY = " + setY + " i = " + i + " j = "+ j);
                        }
                    }
                    else{
                        while (setX > 10 - this.getShipSize(i)){
                            setX = rand.nextInt(10);System.out.println("check4 setX = " + setX + " i = " + i + " j = "+ j);
                        }
                        if(this.cellContainsShip(setX + j, setY)){
                            collision = true;
                            setX = rand.nextInt(10);
                            setY = rand.nextInt(10);
                            j = -1;
                            System.out.println("check5 setX = " + setX + " i = " + i + " j = "+ j);
                        }
                    }
                }
            }

            //place ship
            for(int j = 0; j < this.getShipSize(i); j++){
                if(shipSideWays == 0){System.out.println();
                    this.fillCell(setX, setY + j, i);
                }
                else{
                    this.fillCell(setX + j, setY, i);
                }
            }
        }
    }

    public void makeMove(Player otherPlayer){
        Random rand = new Random();

        if(hit){
            if(predict == 'u'){
                if(prevHitY != 9 && !otherPlayer.cellIsHit(prevHitX, prevHitY)){

                }
            }
            else if(predict == 'r'){

            }
            else if(predict == 'd'){

            }
            else if(predict == 'l'){

            }
        }
        else {
            setX = rand.nextInt(10);
            setY = rand.nextInt(10);

            if (otherPlayer.cellIsHit(setX, setY)) {
                setX = rand.nextInt(10);
                setY = rand.nextInt(10);
            }

            otherPlayer.hitCell(setX, setY);

            if(otherPlayer.cellContainsShip(setX, setY)){
                this.hit = true;
                this.prevHitX = setX;
                this.prevHitY = setY;
                this.predict = 'u';
            }
        }
    }

    public Texture getShipTexture(int shipNum){

        return this.board.getShipTexture(shipNum);

    }

    public void updateShipPosition(int shipNum, int x, int y, int z){
        this.board.updateShipPosition(shipNum, x, y, z);
    }

    public Vector3 getShipPosition(int shipNum){
        return this.board.getShipPosition(shipNum);
    }

    public int getShipSize (int shipNum){
        return this.board.getShipSize(shipNum);
    }

    public void rotateShip(int shipNum){
        this.board.rotateShip(shipNum);
    }

    public char getShipOrientation(int shipNum){
        return this.board.getShipOrientation(shipNum);
    }

    public void setShipPosition(int shipNum, int x, int y, int z){
        this.board.setShipPosition(shipNum, x, y, z);
    }

    public void fillCell(int x, int y, int shipNum){
        this.board.fillCell(x, y, shipNum);
    }

    public boolean cellContainsShip(int x, int y){
        return this.board.cellContainsShip(x, y);
    }

    public void hitCell(int x, int y){
        this.board.hitCell(x, y);
    }

    public boolean cellIsHit(int x, int y){
        return this.board.isCellHit(x, y);
    }

    public boolean allShipsDestroyed(){
        return this.board.allShipsDestroyed();
    }


    private void getAuthDataFromFile(){
        File ifp = new File("authdata.txt");
        try{
            // TODO: Implement error handling here
            if(!ifp.exists())
                return;
            Scanner scan = new Scanner(ifp);
            scan.nextLine();
            this.playerName = scan.nextLine();
            this.userID = scan.nextLine();
            this.token = scan.nextLine();
            return;
        }catch(IOException e) {
            return;
        }
    }
}
