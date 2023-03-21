package com.mochileiros.endlesswar.cenas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mochileiros.endlesswar.GameApplication;

public class MenuScreen implements Screen {

    private GameApplication parent;
    private Stage stage;
    private Skin skin;
    private Label startLabel;

    public MenuScreen(GameApplication app){
        parent = app;

        // Cria um noto estágio
        stage = new Stage(new ScreenViewport());

        // Carrega a skin do estágio
        skin = new Skin(Gdx.files.internal("skin/arcade-ui.json"));

        // Cria um ator para o estágio
        startLabel = new Label("Press Space to start Game...", skin);

        // Configura o ator
        startLabel.setPosition(100, 100);
        startLabel.setSize(200, 50);
        stage.addActor(startLabel);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        // Limpa a tela
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicializa e desenha o ator
        stage.act();
        stage.draw();

        // Caso a tecla espaço seja pressionada, muda a tela para o jogo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            parent.changeScreen(GameApplication.GAME);
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
