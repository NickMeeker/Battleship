package com.teamgroupfourteen.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.teamgroupfourteen.game.Battleship;
import com.teamgroupfourteen.game.GameButton;

/**
 * Created by Jeremy on 4/2/2018.
 */

//State that allows players to place their ships in preparation of the game
public class PlayStateSetup extends State {
    private Texture gameGrid;
    private TextureRegion mainGrid;
    private Texture background;
    private TextureRegion mainBackground;
    private Texture coordinateBackground;
    private TextureRegion coordinateBackgrounRegion;
    private GameButton upBtn;
    private GameButton downBtn;
    private GameButton leftBtn;
    private GameButton rightBtn;
    private GameButton rotateBtn;
    private GameButton confirmBtn;

    public PlayStateSetup(GameStateManager gsm){

        super(gsm);

        cam.setToOrtho(false, Battleship.WIDTH, Battleship.HEIGHT);

        // set the background as a texture region. 178x232 is the resolution of the image
        background = new Texture("backgroundOcean.png");
        gameGrid = new Texture("GameGrid.png");
        coordinateBackground = new Texture("blackSquare.png");

        mainBackground = new TextureRegion(background, 0, 0, 178, 232 );
        mainGrid = new TextureRegion(gameGrid, 0, 0, 324, 324);
        coordinateBackgrounRegion = new TextureRegion(coordinateBackground, 0, 0, 1536, 1478);


        //buttons
        upBtn = new GameButton(190, 220, 100, 100, "upArrow.png");
        downBtn = new GameButton(190, 20, 100, 100, "downArrow.png");
        leftBtn = new GameButton(90, 120, 100, 100, "leftArrow.png");
        rightBtn = new GameButton(290, 120, 100, 100, "rightArrow.png");
        rotateBtn = new GameButton(290, 220, 100, 100, "clockwiseArrow.png");
        //confirmBtn = new GameButton(cam.position.x - 169 / 2, cam.position.y + 50, 169, 42, ".png");

    }

    @Override
    public void handleInput(){
        if(Gdx.input.justTouched()){
            Vector3 touchPosition = super.getInputRegion();


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

        //regions
        sb.draw(mainBackground, 0, 0, Battleship.WIDTH, Battleship.HEIGHT);
        sb.draw(mainGrid, 20, Battleship.HEIGHT/2, Battleship.WIDTH - 40, Battleship.HEIGHT / 2 - 40);
        sb.draw(coordinateBackground, 190, 120,100,100);

        //buttons
        sb.draw(upBtn.getImage(), upBtn.getX(), upBtn.getY(), upBtn.getWidth(), upBtn.getHeight());
        sb.draw(downBtn.getImage(), downBtn.getX(), downBtn.getY(), downBtn.getWidth(), downBtn.getHeight());
        sb.draw(leftBtn.getImage(), leftBtn.getX(), leftBtn.getY(), leftBtn.getWidth(), leftBtn.getHeight());
        sb.draw(rightBtn.getImage(), rightBtn.getX(), rightBtn.getY(), rightBtn.getWidth(), rightBtn.getHeight());
        sb.draw(rotateBtn.getImage(), rotateBtn.getX(),rotateBtn.getY(), rotateBtn.getWidth(), rotateBtn.getHeight());
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        upBtn.disposeAssets();
        downBtn.disposeAssets();
        leftBtn.disposeAssets();
        rightBtn.disposeAssets();
        System.out.println("Play State Setup Disposed");
    }

}
