package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mochileiros.endlesswar.GameApplication;
import com.mochileiros.endlesswar.entities.Player;
import com.mochileiros.endlesswar.services.MapService;

public class GameService implements Screen {

    private GameApplication parent;
    private MapService mapService;
    public Player player;
    private SpriteBatch spriteBatch;

    public GameService(GameApplication app){
        parent = app;
        mapService = new MapService(this);
        player = new Player(mapService.world);
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {
        System.out.println("GameService...");
    }

    public void update(float delta){
        mapService.update(delta);
        player.update(delta);
        spriteBatch.setProjectionMatrix(mapService.camera.combined);
    }

    @Override
    public void render(float delta) {
        update(delta);
        mapService.render(delta);
        spriteBatch.begin();
        player.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        mapService.resize(width, height);
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
        spriteBatch.dispose();
    }

}
