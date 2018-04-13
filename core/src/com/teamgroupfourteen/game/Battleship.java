package com.teamgroupfourteen.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.teamgroupfourteen.game.Player.Player;
import com.teamgroupfourteen.game.States.*;

public class Battleship extends ApplicationAdapter {
	// KEEP RELEVANT CONSTANTS HERE
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;
    public static final String TITLE = "BATTLESHIP";
    public static final String APIPREFIX = "http://localhost:3000/api/";

	private SpriteBatch batch;
	private GameStateManager gsm;

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Player player1= new Player(null);
		Player player2 = new Player(null);
		gsm.push(new MainMenuState(gsm));
		//gsm.push(new CurrentGamesState(gsm));
		//gsm.push(new LoginState(gsm));
		//gsm.push(new RegistrationState(gsm));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
