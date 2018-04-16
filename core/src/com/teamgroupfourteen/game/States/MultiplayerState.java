package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Multiplayer.GameLoader;
import com.teamgroupfourteen.game.Multiplayer.HostGame;
import com.teamgroupfourteen.game.Multiplayer.JoinGame;
import com.teamgroupfourteen.game.Multiplayer.MultiplayerGameManager;
import com.teamgroupfourteen.game.User.User;

/**
 * Created by derek on 4/11/18.
 */

public class MultiplayerState extends State {
    private User user;
    private Texture background;
    private TextureRegion mainBackground;
    private GameButton titleBtn;
    private GameButton currentGameBtn;
    private GameButton createGameBtn;
    private GameButton joinGameBtn;
    private boolean hosting;
    private String gameID;
    MultiplayerGameManager mgm;
    private GameButton backBtn;

        public MultiplayerState(GameStateManager gsm, User user) {
            super(gsm);
            this.user = user;
            cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );

            // Setup background
            background = new Texture("MainBackground.png");
            mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);

            // Setup buttons & title
            titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
            currentGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y+100, 360, 100, "CurrentButton.png");
            createGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y, 360, 100, "CreateButton.png");
            joinGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y-100, 360, 100, "JoinButton.png");
            backBtn = new GameButton(0, Battleship.HEIGHT-64, 64, 64, "Arrow_left.png");

        }

        @Override
        public void handleInput() {

            // Setup touch reactions
            if (Gdx.input.justTouched()) {
                Vector3 touchPosition = super.getInputRegion();
                if (isTouched(touchPosition, currentGameBtn)) {
                    gsm.push(new CurrentGamesState(gsm, user));
                } else if (isTouched(touchPosition, createGameBtn)) {
                    System.out.println("Transition to create game");
                    GameLoader gameLoader = new GameLoader("5ad3f5e771a0dc467698dc0a", gsm);
                    //gsm.pop();System.out.println("what the hell?!?!?!");
                }else if (isTouched(touchPosition, joinGameBtn)) {
                    System.out.println("Transition to join game");
                    JoinGame joinGame = new JoinGame(user.getUsername(), user);
                    joinGame.pairGames();
                }else if (isTouched(touchPosition, backBtn)) {
                    gsm.pop();
                }
            }
        }

        @Override
        public void update(float dt) {
            handleInput();
            if(hosting){
                MultiplayerGameManager mgm = new MultiplayerGameManager(gameID);
                if(!mgm.getGuestPlayer().equals(""));
                mgm.updateActive(true);
                GameLoader gameLoader = new GameLoader(gameID, gsm);
            }
        }

        @Override
        public void render(SpriteBatch sb) {
            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
            sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
            sb.draw(currentGameBtn.getImage(), currentGameBtn.getX(), currentGameBtn.getY(), currentGameBtn.getWidth(), currentGameBtn.getHeight());
            sb.draw(createGameBtn.getImage(), createGameBtn.getX(), createGameBtn.getY(), createGameBtn.getWidth(), createGameBtn.getHeight());
            sb.draw(joinGameBtn.getImage(), joinGameBtn.getX(), joinGameBtn.getY(), joinGameBtn.getWidth(), joinGameBtn.getHeight());
            sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
            sb.end();
        }

        @Override
        public void dispose() {
            background.dispose();
            titleBtn.disposeAssets();
            currentGameBtn.disposeAssets();
            createGameBtn.disposeAssets();
            joinGameBtn.disposeAssets();
            backBtn.disposeAssets();
        }
    }
