package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Screen;
import com.mochileiros.endlesswar.GameApplication;

public class LoadingScreen implements Screen {

    private GameApplication parent;

    public LoadingScreen(GameApplication app){
        parent = app;
    }

    @Override
    public void show() {
        System.out.println("Loading...");
    }

    @Override
    public void render(float delta) {
        parent.changeScreen(GameApplication.MENU);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
