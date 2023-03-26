package com.mochileiros.endlesswar;

import com.badlogic.gdx.Game;
import com.mochileiros.endlesswar.cenas.GameScreen;
import com.mochileiros.endlesswar.cenas.GameService;
import com.mochileiros.endlesswar.cenas.LoadingScreen;
import com.mochileiros.endlesswar.cenas.MenuScreen;

public class GameApplication extends Game {

	// Atributos referente às telas do jogo:
	private LoadingScreen loadingScreen;
	private MenuScreen menuScreen;
	private GameScreen gameScreen;

	private GameService gameService;

	// Índices que utilizamos para transitar entre as telas:
	public final static int MENU = 0;
	public final static int GAME = 1;

	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	// Este método é responsável por alterar a tela do jogo caso a condição para tal seja atendida
	public void changeScreen(int screen){
		switch (screen){
			case MENU:
				if (menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
			break;
			case GAME:
				if (gameService == null) gameService = new GameService(this);
				this.setScreen(gameService);
			break;
		}
	}
}
