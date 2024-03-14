package com.turnbased.game;

import static com.badlogic.gdx.graphics.g2d.Animation.*;

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
public class TurnBased extends Game {
	private SpriteBatch batch;
	private Texture player1Image;
	private Texture player2Image;
	private BitmapFont font;
	private TextureAtlas player1Atlas;
	private TextureAtlas player1AtlasX;
	private TextureAtlas player2Atlas;
	private Animation <TextureAtlas.AtlasRegion> player1Animation, player1Punch;
	private Animation player2Animation;
	private float stateTime;
	private float moveTime;
	private Player player1;
	private Player player2;
	private boolean noTurn = true;
	private boolean isPlayer1Turn;
	private boolean showPlayer1Image = true;
	private boolean showPlayer2Image = true;

	private int damageToShow1;
	private int damageToShow2;
	private float damageTimer1;
	private float damageTimer2;

	@Override
	public void create() {
		batch = new SpriteBatch();
		player1Atlas = new TextureAtlas(Gdx.files.internal("kroy/kroy_run.atlas")); // Replace with your atlas file
		player1AtlasX = new TextureAtlas(Gdx.files.internal("kroy/kroy_punch.atlas")); // Replace with your atlas file
		player2Atlas = new TextureAtlas(Gdx.files.internal("yato/yato.atlas")); // Replace with your atlas file

		player1Animation = new Animation<>(1f / 24f, player1Atlas.getRegions()); // Adjust frame duration as needed
		player1Punch = new Animation<>(1f / 24f, player1AtlasX.getRegions()); // Adjust frame duration as needed
		player2Animation = new Animation<>(1f/24f, player2Atlas.getRegions()); // Adjust frame duration as needed

		player1Image = new Texture("kroy/kroy_idle.png"); // Replace with your player image file
		player2Image = new Texture("Yato_0-0.png"); // Replace with your player image file
		font = new BitmapFont(); // Use the default font

		player1 = new Player(100);
		player2 = new Player(100);
		noTurn = true;
		isPlayer1Turn = false;


		damageToShow1 = 0;
		damageToShow2 = 0;
		damageTimer1 = 0;
		damageTimer2 = 0;

		player1.setPosition(50, 50);
		player2.setPosition(Gdx.graphics.getWidth() - 150, 50, -player2Image.getWidth(), player2Image.getHeight());
	}

	@Override
	public void render() {
		stateTime += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.5f, 0, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

//		// Draw player images
//		batch.draw(player1Image, 50, 50);
//		batch.draw(player2Image, Gdx.graphics.getWidth() - 150, 50, -player2Image.getWidth(), player2Image.getHeight());

		// Draw health points
		font.setColor(1, 1, 1, 1); // Set color to white
		font.draw(batch, "Health: " + player1.getHitPoints(), 10, Gdx.graphics.getHeight() - 10);
		font.draw(batch, "Health: " + player2.getHitPoints(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);



		// Reset flags to display player images on the next turn
		showPlayer1Image = true;
		showPlayer2Image = true;

		// Draw damage points
		if (damageTimer1 > 0) {
			drawDamageNumber(damageToShow1, Gdx.graphics.getWidth() - 150 - player2Image.getWidth() / 2f, 75 + player2Image.getHeight());
			damageTimer1 -= Gdx.graphics.getDeltaTime();
		}

		if (damageTimer2 > 0) {
			drawDamageNumber(damageToShow2, 50 + player1Image.getWidth() / 2f, 100 + player1Image.getHeight());
			damageTimer2 -= Gdx.graphics.getDeltaTime();
		}

		if (noTurn && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			noTurn = false;
		}

		if (noTurn){
			batch.draw(player2Image, Gdx.graphics.getWidth() - 150, 50, -player2Image.getWidth(), player2Image.getHeight());
			batch.draw(player1Image, 50, 50);


		} else {


			// Draw player images or running animations based on turn
			if (isPlayer1Turn) {
				if (showPlayer2Image) {
					Sprite frame = new Sprite (player1Animation.getKeyFrame(stateTime, true));
					moveTime += 1;

					batch.draw(player2Image, Gdx.graphics.getWidth() - 150, 50, -player2Image.getWidth(), player2Image.getHeight());
					float x =  50 + (moveTime * 4);
					//boolean flipped = false; // Flag to track if flip has occurred
					if (x >= Gdx.graphics.getWidth() - 200) {

						frame = new Sprite (player1Punch.getKeyFrame(stateTime, true));

						//frame = new Sprite (player1Punch.getKeyFrame(stateTime, false));
						//batch.draw(frame, x, 50);
						moveTime += 2 ;
						//
						frame.setFlip(true, false);
						x =  (Gdx.graphics.getWidth() - 150) - (moveTime );

						//flipped = true;
					}
					if (x <= 50) {


						x = 50;
					}


					batch.draw(frame, x, 50);
					if (x <= 50 ) {
						stateTime = 0;
						//flipped = false;

						batch.draw(frame, 2000, -1000);
						batch.draw(player1Image, 50, 50);

					}

				}
			} else {

				if (showPlayer1Image) {
					moveTime = 0;
					batch.draw(player1Image, 50, 50);

					TextureRegion frame = (TextureRegion) player2Animation.getKeyFrame(stateTime, true);
					batch.draw(frame, Gdx.graphics.getWidth() - 150, 50, -frame.getRegionWidth(), frame.getRegionHeight());
				}

			}
		}


		// Check for a key press
		if (!noTurn && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			// Deal random damage to the opponent
			if (isPlayer1Turn) {
				int damage = generateRandomDamage();
				player1.takeDamage(damage);
				damageToShow2 = damage;
				damageTimer2 = 2f;
				System.out.println("Player 1 dealt " + damage + " damage to Player 2");
			} else {
				int damage = generateRandomDamage();
				player2.takeDamage(damage);
				damageToShow1 = damage;
				damageTimer1 = 2f;
				System.out.println("Player 2 dealt " + damage + " damage to Player 1");
			}

			// Switch turns
			isPlayer1Turn = !isPlayer1Turn;

			// Check for game over
			if (player1.getHitPoints() <= 0 || player2.getHitPoints() <= 0) {
				System.out.println("Game Over!");
				// Add your game over logic here
				Gdx.app.exit(); // Close the application for simplicity
			}
		}



		batch.end();
	}

	private int generateRandomDamage() {
		Random random = new Random();
		return random.nextInt(11) + 10;
	}

	private void drawDamageNumber(int damage, float x, float y) {
		font.setColor(1, 1, 1, 1); // Set color to white
		font.draw(batch, String.valueOf(damage), x, y);
	}

	@Override
	public void dispose() {
		batch.dispose();
		player1Image.dispose();
		player2Image.dispose();
		font.dispose();
		player1Atlas.dispose();
		player2Atlas.dispose();

	}

	private static class Player {
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
}