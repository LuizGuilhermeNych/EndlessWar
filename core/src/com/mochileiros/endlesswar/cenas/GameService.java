package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mochileiros.endlesswar.GameApplication;
import com.mochileiros.endlesswar.services.MapService;

public class GameService implements Screen {

    private GameApplication parent;
    private MapService mapService;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;

    public GameService(GameApplication app){
        parent = app;

        camera = new OrthographicCamera();
//        this.mapService = new MapService(this);
        this.renderer = mapService.setupMap();
    }

    @Override
    public void show() {
        System.out.println("GameService...");
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
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
