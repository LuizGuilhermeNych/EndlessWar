package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mochileiros.endlesswar.GameApplication;
import com.mochileiros.endlesswar.entities.Player;

import static com.mochileiros.endlesswar.utils.Constants.PPM;

public class GameScreen implements Screen {

    // Atributos usados para carregar a UI da tela de jogo
    private GameApplication parent;
    private Stage stage;
    private Skin skin;
    private Label gameLabel;

    // Atributos usados para carregar o mapa na tela
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Atributos para configurar a câmera no jogo
    private OrthographicCamera camera;
    private Viewport viewport;
    private float mapWidth, mapHeight;
    private float screenWidth, screenHeight;
    
    // Atributos para configuração da entidade do jogador
    private World world;
    private Box2DDebugRenderer b2dr;
    private Player player;
    private SpriteBatch spriteBatch;
    private float stateTime;

    public GameScreen(GameApplication app){
        parent = app;

        // Cria o estágio
        stage = new Stage(new ScreenViewport());

        // Configura o skin
        skin = new Skin(Gdx.files.internal("skin/arcade-ui.json"));

        // Cria um ator
        gameLabel = new Label("Game Started!", skin);

        // Carrega o mapa
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("stage_zero.tmx");

        // Configurando o ator no estágio
        gameLabel.setPosition(100, 100);
        gameLabel.setSize(200, 50);
        stage.addActor(gameLabel);
        
        // Cria e configura mundo e jogador
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        player = new Player(world);
        spriteBatch = new SpriteBatch();
    }
    
    public void update(float delta) {
    	world.step(1 / 60f, 6, 2);
        stateTime += delta;
        player.update(delta);
    }

	@Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
        mapWidth = mapLayer.getWidth() * mapLayer.getTileWidth();
        mapHeight = mapLayer.getHeight() * mapLayer.getTileWidth();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(screenWidth, screenHeight);
        viewport = new ExtendViewport(screenWidth, screenHeight, camera);

        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(160, 128,0);

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void render(float delta) {
    	update(delta);

        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float cameraLeft = camera.position.x - screenWidth / 2f;
        float cameraRight = camera.position.x + screenWidth / 2f;

        if (cameraLeft < 0) {
            camera.position.x = screenWidth / 2f;
        } else if (cameraRight > mapWidth) {
            camera.position.x = mapWidth - screenWidth / 2f;
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
        	camera.translate(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
        	camera.translate(1, 0);
        }

//        camera.zoom = 0.39f;

        // Configura a câmera
        camera.update();

        // Renderiza o mapa
        renderer.setView(camera);
        renderer.render();

        //Renderiza os elementos da UI
        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            parent.changeScreen(GameApplication.MENU);
        }

        spriteBatch.begin();
        player.draw(spriteBatch);
        spriteBatch.end();

        b2dr.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
    }
}