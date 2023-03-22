package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mochileiros.endlesswar.services.MapService;

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

    private World world;
    private MapService mapService;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private SpriteBatch batch;

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
        map = mapLoader.load("test_map.tmx");

        this.batch = new SpriteBatch();
        this.mapService = new MapService(this);
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        // Configurando o ator no estágio
        gameLabel.setPosition(100, 100);
        gameLabel.setSize(200, 50);
        stage.addActor(gameLabel);
    }

    public void update(){
        world.step(1 / 60f, 6, 2);

        batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // Retorna as dimensões do mapa
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) map.getLayers().get(0);
        mapWidth = mapLayer.getWidth() * mapLayer.getTileWidth();
        mapHeight = mapLayer.getHeight() * mapLayer.getTileWidth();

        // Retorna as dimensões da tela
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Inicializa câmera e viewport
        camera = new OrthographicCamera(screenWidth, screenHeight);
        viewport = new ExtendViewport(screenWidth, screenHeight, camera);

        // Configura câmera e viewport
        viewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(160, 128,0);

        // Inicializa renderizador do mapa
        renderer = mapService.setupMap();
    }

    @Override
    public void render(float delta) {
        this.update();
        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Atributos para verificar os extremos da tela
        float cameraLeft = camera.position.x - screenWidth / 2f;
        float cameraRight = camera.position.x + screenWidth / 2f;

        // Condições para ajustar a câmera caso tenha chegado nos extremos do mapa
        if (cameraLeft < 0) {
            camera.position.x = screenWidth / 2f;
        } else if (cameraRight > mapWidth) {
            camera.position.x = mapWidth - screenWidth / 2f;
        }

        // Testar movimentação da câmera
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            camera.translate(1, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            camera.translate(-1, 0);
        }

        // Zoom no mapa
        camera.zoom = 0.39f;

        // Configura a câmera
        camera.update();

        // Renderiza o mapa
        renderer.setView(camera);
        renderer.render();

        //Renderiza os elementos da UI
        stage.act();
        stage.draw();

        batch.begin();
        batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(32.0f));

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            parent.changeScreen(GameApplication.MENU);
        }
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
        batch.dispose();
    }
}