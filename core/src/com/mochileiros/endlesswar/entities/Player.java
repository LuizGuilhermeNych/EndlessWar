package com.mochileiros.endlesswar.entities;

import com.badlogic.gdx.physics.box2d.*;

public class Player {

    private Body body;

    public Player(World world){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(128, 128);

        CircleShape shape = new CircleShape();
        shape.setRadius(15);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
    }
}
