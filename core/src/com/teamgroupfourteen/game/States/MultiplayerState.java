package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.User.User;

public class MultiplayerState extends State {
        private User user;
        private Texture background;
        private TextureRegion mainBackground;
        private GameButton currentGameBtn;
        private GameButton createGameBtn;
        private GameButton joinGameBtn;

        public MultiplayerState(GameStateManager gsm, User user) {
            super(gsm);
            this.user = user;
            cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );
            background = new Texture("testPic.jpg");
            mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
            currentGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y + 100, 360, 100, "CurrentButton.png");
            createGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y, 360, 100, "CreateButton.png");
            joinGameBtn = new GameButton(Battleship.WIDTH/8, cam.position.y - 100, 360, 100, "JoinButton.png");

        }

        @Override
        public void handleInput() {
            if (Gdx.input.justTouched()) {
                Vector3 touchPosition = super.getInputRegion();
                if (isTouched(touchPosition, currentGameBtn)) {
                    gsm.push(new CurrentGamesState(gsm, user));
                } else if (isTouched(touchPosition, createGameBtn)) {
                    System.out.println("Transition to create game");
                }else if (isTouched(touchPosition, joinGameBtn)) {
                    System.out.println("Transition to join game");
                }

            }
        }

        @Override
        public void update(float dt) {
            handleInput();
        }

        @Override
        public void render(SpriteBatch sb) {
            sb.setProjectionMatrix(cam.combined);
            sb.begin();
            sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
            sb.draw(currentGameBtn.getImage(), currentGameBtn.getX(), currentGameBtn.getY(), currentGameBtn.getWidth(), currentGameBtn.getHeight());
            sb.draw(createGameBtn.getImage(), createGameBtn.getX(), createGameBtn.getY(), createGameBtn.getWidth(), createGameBtn.getHeight());
            sb.draw(joinGameBtn.getImage(), joinGameBtn.getX(), joinGameBtn.getY(), joinGameBtn.getWidth(), joinGameBtn.getHeight());
            sb.end();
        }

        @Override
        public void dispose() {
            background.dispose();
            currentGameBtn.disposeAssets();
            createGameBtn.disposeAssets();
        }
    }
