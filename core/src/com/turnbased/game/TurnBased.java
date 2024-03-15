package com.turnbased.game;

import static com.badlogic.gdx.graphics.g2d.Animation.*;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
public class TurnBased extends Game implements Screen {

	private GameRenderer renderer;
	private Player player1;
	private Player player2;
	//private PlayMode normal = PlayMode.NORMAL;


	@Override
	public void show() {
		renderer = new GameRenderer(GameRenderer.batch, GameRenderer.font);
		Gdx.input.setInputProcessor((InputProcessor) this); // Set input processor to handle input
	}


	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.5f, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render(delta);
	}

	@Override
	public void create() {
		player1 = new Player(100);
		player2 = new Player(100);
		GameRenderer.noTurn = true;
		GameRenderer.isPlayer1Turn = false;


		GameRenderer.damageToShow1 = 0;
		GameRenderer.damageToShow2 = 0;
		GameRenderer.damageTimer1 = 0;
		GameRenderer.damageTimer2 = 0;

		player1.setPosition(50, 50);
		player2.setPosition(Gdx.graphics.getWidth() - 150, 50, -Player.player2Image.getWidth(), Player.player2Image.getHeight());

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
		Player.player1Image.dispose();
		Player.player2Image.dispose();
		renderer.dispose();
		super.dispose();


	}
}

