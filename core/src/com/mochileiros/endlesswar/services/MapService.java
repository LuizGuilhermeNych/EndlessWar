package com.mochileiros.endlesswar.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.mochileiros.endlesswar.cenas.GameService;
import com.mochileiros.endlesswar.entities.Player;
import com.mochileiros.endlesswar.utils.OrthogonalTiledMapRendererBleeding;

import static com.mochileiros.endlesswar.utils.Constants.PPM;
import static com.mochileiros.endlesswar.utils.Constants.SCALE;

public class MapService implements Screen {

    private GameService gameService;
    public OrthographicCamera camera;
    public World world;
    private Box2DDebugRenderer b2dr;

    private OrthogonalTiledMapRendererBleeding renderer;
    private TiledMap map;

    public MapService(GameService app) {
        this.gameService = app;
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / SCALE, h / SCALE);

        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();

        map = new TmxMapLoader().load("stage_zero.tmx");
        renderer = new OrthogonalTiledMapRendererBleeding(map, 1 / 1f);
    }


    @Override
    public void show() {

    }

    public void update(float delta){
        world.step(1 / 60f, 6, 2);
        cameraUpdate(delta, gameService.player);
        renderer.setView(camera);
    }

    public void cameraUpdate(float delta, Player player){
        Vector3 position = camera.position;

        position.x = gameService.player.body.getPosition().x * PPM;
        position.y = gameService.player.body.getPosition().y * PPM;

        camera.position.set(position);
        camera.update();
    }

    @Override
    public void render(float delta) {
        update(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, camera.combined.scl(PPM));
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / SCALE, height / SCALE);
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
        world.dispose();
        b2dr.dispose();
        renderer.dispose();
        map.dispose();
    }
}