package com.turnbased.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

import java.util.Random;

public class GameRenderer implements Disposable {



    public SpriteBatch getBatch() {
        return batch;
    }

    public static SpriteBatch batch;

    public static BitmapFont font = new BitmapFont();;
    public static float stateTime;
    public static float moveTime;
    public static boolean showPlayer1Image = true;
    public static boolean showPlayer2Image = true;
    public static boolean  noTurn = true;
    public static boolean isPlayer1Turn;
    public static int damageToShow1;
    public static int damageToShow2;
    public static float damageTimer1;
    public static float damageTimer2;
    private Player player1;
    private Player player2;



    public GameRenderer(SpriteBatch batch, BitmapFont font) {
        GameRenderer.batch = batch;
        GameRenderer.font = font;
    }

    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        clearScreen();


        batch.begin();

        drawHealthPoints();

        resetFlags();

        drawDamagePoints();

        handleNoTurn();


        drawPlayerImages();


        checkKeyPress();


        batch.end();
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(0.5f, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawHealthPoints() {
        font.setColor(1, 1, 1, 1); // Set color to white
        font.draw(batch, "Health: " + player1.getHitPoints(), 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Health: " + player2.getHitPoints(), Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 10);
    }

    private void resetFlags() {
        showPlayer1Image = true;
        showPlayer2Image = true;
    }

    private void drawDamagePoints() {
        if (damageTimer1 > 0) {
            drawDamageNumber(damageToShow1, Gdx.graphics.getWidth() - 150 - Player.player2Image.getWidth() / 2f, 75 + Player.player2Image.getHeight());
            damageTimer1 -= Gdx.graphics.getDeltaTime();
        }

        if (damageTimer2 > 0) {
            drawDamageNumber(damageToShow2, 50 + Player.player1Image.getWidth() / 2f, 100 + Player.player1Image.getHeight());
            damageTimer2 -= Gdx.graphics.getDeltaTime();
        }
    }

    private void handleNoTurn() {
        if (noTurn && Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            noTurn = false;
        }
    }

    private void checkKeyPress(){
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
    }

    private void drawPlayerImages() {

        if (noTurn){
            batch.draw(Player.player2Image, Gdx.graphics.getWidth() - 150, 50, -Player.player2Image.getWidth(), Player.player2Image.getHeight());
            batch.draw(Player.player1Image, 50, 50);


        } else {


            // Draw player images or running animations based on turn
            if (isPlayer1Turn) {
                if (showPlayer2Image) {
                    Sprite frame = new Sprite (player1.player1Animation.getKeyFrame(stateTime, true));
                    moveTime += 1;

                    batch.draw(Player.player2Image, Gdx.graphics.getWidth() - 150, 50, -Player.player2Image.getWidth(), Player.player2Image.getHeight());
                    float x =  50 + (moveTime * 4);
                    //boolean flipped = false; // Flag to track if flip has occurred
                    if (x >= Gdx.graphics.getWidth() - 200) {

                        //frame = new Sprite (player1Punch.getKeyFrame(stateTime, true));
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
                        batch.draw(Player.player1Image, 50, 50);

                    }

                }
            } else {

                if (showPlayer1Image) {
                    moveTime = 0;
                    batch.draw(Player.player1Image, 50, 50);

                    TextureRegion frame = (TextureRegion) player2.player2Animation.getKeyFrame(stateTime, true);
                    batch.draw(frame, Gdx.graphics.getWidth() - 150, 50, -frame.getRegionWidth(), frame.getRegionHeight());
                }

            }
        }

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

    }
}
