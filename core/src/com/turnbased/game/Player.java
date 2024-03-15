package com.turnbased.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Player {

    public static final Texture player1Image = new Texture("kroy/kroy_idle.png");
    public static final Texture player2Image= new Texture("Yato_0-0.png"); // Replace with your player image file

    private TextureAtlas player1Atlas = new TextureAtlas(Gdx.files.internal("kroy/kroy_run.atlas")); // Replace with your atlas file;
    private TextureAtlas player2Atlas = new TextureAtlas(Gdx.files.internal("yato/yato.atlas")); // Replace with your atlas file;

    public Animation<TextureAtlas.AtlasRegion> player1Animation = new Animation<>(1f / 24f, player1Atlas.getRegions()); // Adjust frame duration as needed;
    private Animation<TextureAtlas.AtlasRegion> player1Punch ;
    public Animation player2Animation = new Animation<>(1f/24f, player2Atlas.getRegions()); // Adjust frame duration as needed;


    public Player() {
    }

    private int hitPoints;
    private Vector2 position; // Add this line
    public Player(int initialHitPoints) {
        this.hitPoints = initialHitPoints;
        this.position = new Vector2(); // Add this line
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public void setPosition(float x, float y, float offsetX, float offsetY) {
        position.set(x + offsetX, y + offsetY);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void takeDamage(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0) {
            hitPoints = 0;
        }
        System.out.println("Remaining Hit Points: " + hitPoints);
    }

    public int getHitPoints() {
        return hitPoints;
    }
}

