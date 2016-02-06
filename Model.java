
import java.util.*;

/**
 * Created by Max on 2016-01-26.
 */

// model keeps track of game state (objects in the game)
// contains a Timer that ticks periodically to advance the game
// AND calls an update() method in the View to tell it to redraw
public class Model {
    int fps, speed;
    int score,life, gamestate, nbullets;

    View view;
    Paddle paddle;
    Ball ball;
    ArrayList<Block> block;
    ArrayList<Bullet> bullet;
    splash s;

    int mouseX;
    int width, height, defaultW, defaultH;

    Model(int framepersec, int gamespeed) {
        fps = framepersec;
        speed = gamespeed+1;
        defaultW = 640;
        defaultH = 480;
        score = 0;
        life = 3;
        gamestate = 1;
        nbullets = 10;

        paddle = new Paddle(this);
        ball = new Ball(this);
        block = new ArrayList<Block>();
        bullet = new ArrayList<Bullet>();
        s = new splash(this);
        for (int i=0; i<9; ++i) {
            for (int j=0; j<11; ++j) {
                if (i>0 && j>1) {
                    if (j == 2 || j == 10) { block.add(new Block(this, i*64, j*24, 1)); }
                    if (j == 3 || j == 9) { block.add(new Block(this, i*64, j*24, 2)); }
                    if (j == 4 || j == 8) { block.add(new Block(this, i*64, j*24, 3)); }
                    if (j == 5 || j == 7) { block.add(new Block(this, i*64, j*24, 4)); }
                    if (j == 6) { block.add(new Block(this, i*64, j*24, 5)); }
                }
            }
        }

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                view.repaint();
            }
        }, 1000/fps, 1000/fps);

        java.util.Timer timer2 = new java.util.Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                view.update();
            }
        }, 1000/60, 1000/60);
    }
}
