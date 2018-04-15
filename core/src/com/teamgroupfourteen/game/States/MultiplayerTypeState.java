package com.teamgroupfourteen.game.States;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.Board.GameButton;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.User.User;

/**
 * Created by derek on 4/13/18.
 */

public class MultiplayerTypeState extends State {
    private User user;
    private Texture background;
    private TextureRegion mainBackground;
    private GameButton titleBtn;
    private GameButton localPlayBtn;
    private GameButton onlinePlayBtn;
    private GameButton backBtn;

    public MultiplayerTypeState(GameStateManager gsm, User user) {
        super(gsm);
        this.user = user;
        cam.setToOrtho(false, Battleship.WIDTH , Battleship.HEIGHT );

        // Setup background
        background = new Texture("MainBackground.png");
        mainBackground = new TextureRegion(background, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);

        // Setup buttons & title
        titleBtn = new GameButton(Battleship.WIDTH/2 - 200, Battleship.HEIGHT  - 150, 400, 150, "title2.png");
        localPlayBtn = new GameButton(Battleship.WIDTH/8, cam.position.y + 100, 360, 100, "LocalMultiplayer.png");
        onlinePlayBtn = new GameButton(Battleship.WIDTH/8, cam.position.y, 360, 100, "OnlineMultiplayer.png");
        backBtn = new GameButton(0, Battleship.HEIGHT - 64, 64, 64, "Arrow_left.png");
    }

    @Override
    public void handleInput() {

        // Setup touch reactions
        if (Gdx.input.justTouched()) {
            Vector3 touchPosition = super.getInputRegion();
            if (isTouched(touchPosition, localPlayBtn)) {
                Player player1= new Player(null);
                Player player2 = new Player(null);
                gsm.push(new PlayState(gsm, player1, player2));
            } else if (isTouched(touchPosition, onlinePlayBtn)) {
                gsm.push(new MultiplayerState(gsm, user));
            } else if (isTouched(touchPosition, backBtn)) {
                gsm.pop();
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
        sb.draw(titleBtn.getImage(), titleBtn.getX(), titleBtn.getY(), titleBtn.getWidth(), titleBtn.getHeight());
        sb.draw(localPlayBtn.getImage(), localPlayBtn.getX(), localPlayBtn.getY(), localPlayBtn.getWidth(), localPlayBtn.getHeight());
        sb.draw(onlinePlayBtn.getImage(), onlinePlayBtn.getX(), onlinePlayBtn.getY(), onlinePlayBtn.getWidth(), onlinePlayBtn.getHeight());
        sb.draw(backBtn.getImage(), backBtn.getX(), backBtn.getY(), backBtn.getWidth(), backBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        titleBtn.disposeAssets();
        localPlayBtn.disposeAssets();
        onlinePlayBtn.disposeAssets();
        backBtn.disposeAssets();
    }
}
