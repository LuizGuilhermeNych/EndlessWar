package com.mochileiros.endlesswar.entities;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.mochileiros.endlesswar.utils.Constants.PPM;

public class Player {

    public Body body;
    private BodyDef def;
    private PolygonShape shape;

    private SpriteBatch batch;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> walkAnimation;
    private String state;
    private World world;
    private float stateTime;


    public Player(World world){
        this.world = world;
        stateTime = 0;
        batch = new SpriteBatch();
        state = "parado";

        createPlayer();
        loadAnimations();
    }

    private Body createPlayer(){
        def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(0 / PPM, 0 / PPM);
        def.fixedRotation = true;

        body = world.createBody(def);

        shape = new PolygonShape();
        shape.setAsBox(64 / 2 / PPM, 64 / 2 / PPM);

        body.createFixture(shape, 1.0f);
        shape.dispose();
        return body;
    }

    private void loadAnimations(){
        TextureAtlas idleAtlas = new TextureAtlas(Gdx.files.internal("idle.atlas"));
        idleAnimation = new Animation<TextureRegion>(0.2f, idleAtlas.findRegions("idle"), Animation.PlayMode.LOOP);

        TextureAtlas walkAtlas = new TextureAtlas(Gdx.files.internal("walk.atlas"));
        walkAnimation = new Animation<TextureRegion>(0.1f, walkAtlas.findRegions("walk"), Animation.PlayMode.LOOP);
    }

    public void stateMachine(){

        if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0){
            state = "movendo";
            System.out.println(state);
        } else {
            state = "parado";
            System.out.println(state);
        }

    }

    public TextureRegion getAnimation(){
        switch (state){
            case "movendo":
                currentFrame = walkAnimation.getKeyFrame(stateTime, true);
                break;
            default:
                currentFrame = idleAnimation.getKeyFrame(stateTime, true);
                break;
        }
        return currentFrame;
    }

    public void inputListener(float delta){
        int horizontalForce = 0;
        int verticalForce = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//            System.out.println("Apertando pra esquerda" + " | " + "velH: " + body.getLinearVelocity().x);
            horizontalForce -= 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            verticalForce += 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            verticalForce -= 2;
        }
        body.setLinearVelocity(horizontalForce * 2, verticalForce);
    }

    public void update(float delta){
        stateTime += delta;
        inputListener(delta);
        stateMachine();
        getAnimation();
    }

    public void draw(SpriteBatch batch){
        batch.draw(currentFrame,
                body.getPosition().x * PPM - (currentFrame.getRegionWidth() / 2),
                body.getPosition().y * PPM - (currentFrame.getRegionHeight() / 2),
                currentFrame.getRegionWidth(),
                currentFrame.getRegionHeight());
    }

}