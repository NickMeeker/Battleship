package com.teamgroupfourteen.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.teamgroupfourteen.game.Battleship;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// Just sets the window height/width for the desktop launcher
		// We won't need this for the android app, just for the testing
		// environment.
		config.width = Battleship.WIDTH;
		config.height = Battleship.HEIGHT;
		config.title = Battleship.TITLE;
		new LwjglApplication(new Battleship(), config);
	}
}
