import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Max on 2016-01-26.
 */

// game window
// draws everything based on the game state
// receives notification from the model when something changes, and
// draws components based on the model.
public class View extends JComponent {
    Model m;
    Ball ball;
    Paddle paddle;
    ArrayList<Block> block;
    ArrayList<Bullet> bullet;
    splash s;

    public Dimension getPreferredSize() {
        return new Dimension(640, 480);
    }

    View(Model model_) {
        m = model_;
        ball = m.ball;
        paddle = m.paddle;
        block = m.block;
        bullet = m.bullet;
        s = m.s;
        setFocusable(true);
        this.addMouseMotionListener( new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                m.mouseX = e.getX();
                if (paddle.pointUL < 0) {
                    paddle.pointUL = 0;
                    paddle.pointUR = paddle.pointUL + m.width/8;
                } else if (paddle.pointUR > m.width) {
                    paddle.pointUL = m.width - m.width/8;
                    paddle.pointUR = m.width;
                } else {
                    paddle.pointUL = m.mouseX-m.width/16;
                    paddle.pointUR = m.mouseX-m.width/16 + m.width/8;
                }
                paddle.point1 = paddle.pointUL + (paddle.pointUR-paddle.pointUL)/8;
                paddle.point2 = paddle.point1 + (paddle.pointUR-paddle.pointUL)/4;
                paddle.point3 = paddle.point2 + (paddle.pointUR-paddle.pointUL)/4;
                paddle.point4 = paddle.point3 + (paddle.pointUR-paddle.pointUL)/4;
            }
            @Override
            public void mouseDragged(MouseEvent e) {
                m.mouseX = e.getX();
                if (paddle.pointUL < 0) {
                    paddle.pointUL = 0;
                    paddle.pointUR = paddle.pointUL + m.width / 8;
                } else if (paddle.pointUR > m.width) {
                    paddle.pointUL = m.width - m.width / 8;
                    paddle.pointUR = m.width;
                } else {
                    paddle.pointUL = m.mouseX - m.width / 16;
                    paddle.pointUR = m.mouseX - m.width / 16 + m.width / 8;
                }
                paddle.point1 = paddle.pointUL + (paddle.pointUR - paddle.pointUL) / 8;
                paddle.point2 = paddle.point1 + (paddle.pointUR - paddle.pointUL) / 4;
                paddle.point3 = paddle.point2 + (paddle.pointUR - paddle.pointUL) / 4;
                paddle.point4 = paddle.point3 + (paddle.pointUR - paddle.pointUL) / 4;
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (ball.started && m.nbullets > 0) {
                    --m.nbullets;
                    m.bullet.add(new Bullet(m));
                }
            }
        });
        this.addKeyListener( new KeyAdapter() {
            @Override
            public void keyPressed (KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !ball.started && m.gamestate == 1) {
                    ball.pointLx = m.mouseX - m.width /128;
                    ball.pointRx = m.mouseX + m.width /128;
                    ball.pointUy = m.height - m.height/16 - m.height/80 - m.height /96;
                    ball.pointDy = m.height - m.height/16 - m.height/80 + m.height /96;
                    ball.oriVelX = m.speed;
                    ball.oriVelY = -m.speed;
                    ball.velX = m.width * ball.oriVelX / m.defaultW;
                    ball.velY = m.height * ball.oriVelY / m.defaultH;
                    ball.started = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER && (m.gamestate == 2 || m.gamestate == 3)) {
                    m.life = 3;
                    m.score = 0;
                    m.gamestate = 1;
                    m.nbullets = 5;
                    ball.gameEnd = false;
                    block.clear();
                    for (int i=0; i<9; ++i) {
                        for (int j=0; j<11; ++j) {
                            if (i>0 && j>1) {
                                if (j == 2 || j == 10) { block.add(new Block(m, i*64, j*24, 1)); }
                                if (j == 3 || j == 9) { block.add(new Block(m, i*64, j*24, 2)); }
                                if (j == 4 || j == 8) { block.add(new Block(m, i*64, j*24, 3)); }
                                if (j == 5 || j == 7) { block.add(new Block(m, i*64, j*24, 4)); }
                                if (j == 6) { block.add(new Block(m, i*64, j*24, 5)); }
                            }
                        }
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        });
    }

    public void update() {
        int width = m.width;
        int height = m.height;
        m.width = getWidth();
        m.height = getHeight();
        if (width != m.width || height != m.height) {
            if (width == 0 && height == 0) { reposition(1, 1); }
            else if (width == 0) { reposition(1, height); }
            else if (height == 0) {reposition(width, 1); }
            else { reposition(width, height); }
        }
        if (ball.started) { ball.move(); }
        for (int i=0; i<m.bullet.size(); ++i) {
            m.bullet.get(i).move();
        }
        checkCollision();
        winningCondition();
    }

    public void reposition(int width, int height) {
        m.mouseX = m.width * m.mouseX / width;
        paddle.pointUL = m.width * paddle.pointUL / width;
        paddle.pointUR = m.width * paddle.pointUR / width;
        paddle.point1 = paddle.pointUL + (paddle.pointUR-paddle.pointUL)/10;
        paddle.point2 = paddle.point1 + (paddle.pointUR-paddle.pointUL)/5;
        paddle.point3 = paddle.point2 + (paddle.pointUR-paddle.pointUL)/5;
        paddle.point4 = paddle.point3 + (paddle.pointUR-paddle.pointUL)/5;
        paddle.pointY = m.height * paddle.pointY / height;
        ball.pointLx = m.width * ball.pointLx / width;
        ball.pointRx = m.width * ball.pointRx / width;
        ball.pointUy = m.height * ball.pointUy / height;
        ball.pointDy = m.height * ball.pointDy / height;
        if (ball.started) {
            ball.velX = m.width * ball.oriVelX / m.defaultW;
            ball.velY = m.height * ball.oriVelY / m.defaultH;
        }
        for (int i=0; i<m.block.size(); ++i) {
            Block b = block.get(i);
            b.posX = m.width * b.defaultX / m.defaultW;
            b.posY = m.height * b.defaultY / m.defaultH;
            b.width = m.width * b.defaultW / m.defaultW;
            b.height = m.height * b.defaultH / m.defaultH;
            b.downY = m.height * b.defaultDY / m.defaultH;
            b.rightX = m.width * b.defaultRX / m.defaultW;
        }
    }

    private void checkCollision() {
        int centreX = ball.pointLx + (ball.pointRx-ball.pointLx)/2;
        int centreY = ball.pointUy + (ball.pointDy-ball.pointUy)/2;
        BallDirectionCheck(centreY);

        // window collision
        if (ball.pointLx < 0) {
            if (ball.velX < 0) {
                ball.velX = -ball.velX;
                ball.oriVelX = -ball.oriVelX;
                if (ball.started) { m.score += 10; }
            }
        } else if (ball.pointUy < 0) {
            if (ball.velY < 0) {
                ball.velY = -ball.velY;
                ball.oriVelY = -ball.oriVelY;
                if (ball.started) { m.score += 10; }
            }
        } else if (ball.pointRx > m.width) {
            if (ball.velX > 0) {
                ball.velX = -ball.velX;
                ball.oriVelX = -ball.oriVelX;
                if (ball.started) { m.score += 10; }
            }
        } else if (ball.pointDy > m.height) {
            if (ball.started) {
                ball.started = false;
                if (m.life == 0) {
                    m.gamestate = 2;
                    ball.gameEnd = true;
                } else {
                    m.life--;
                }
            }
        }

        // block collision
        for (int i=0; i<block.size(); ++i) {
            Block b = block.get(i);
            if (b.hp > 0) {
                if (ball.velY < 0) {
                    if (b.balldirection == 3) {
                        if (ball.pointUy <= b.downY && centreX < b.rightX && centreX > b.posX) {
                            ball.velY = -ball.velY;
                            ball.oriVelY = -ball.oriVelY;
                            b.hp--;
                            m.score += 100;
                        }
                    }
                } else {
                    if (b.balldirection == 4) {
                        if (ball.pointDy >= b.posY && centreX < b.rightX && centreX > b.posX) {
                            ball.velY = -ball.velY;
                            ball.oriVelY = -ball.oriVelY;
                            b.hp--;
                            m.score += 100;
                        }
                    }
                }
                if (ball.velX < 0) {
                    if (b.balldirection == 1) {
                        if (ball.pointLx <= b.rightX) {
                            ball.velX = -ball.velX;
                            ball.oriVelX = -ball.oriVelX;
                            b.hp--;
                            m.score += 100;
                        }
                    }
                } else {
                    if (b.balldirection == 2) {
                        if (ball.pointRx >= b.posX) {
                            ball.velX = -ball.velX;
                            ball.oriVelX = -ball.oriVelX;
                            b.hp--;
                            m.score += 100;
                        }
                    }
                }
            }
        }

        // paddle collision
        if (ball.pointDy >= paddle.pointY && ball.pointDy < paddle.pointY + m.height/24) {
            if (centreX >= paddle.pointUL && centreX <= paddle.pointUR) {
                if (ball.velY > 0) {
                    ball.velY = -ball.velY;
                    ball.oriVelY = -ball.oriVelY;
                }
                if (centreX <= paddle.point1) {
                    if (ball.oriVelX - m.speed > -m.speed*3) {
                        ball.oriVelX = ball.oriVelX - m.speed;
                        ball.velX = m.width * ball.oriVelX / m.defaultW;
                    }
                } else if (centreX > paddle.point1 && centreX < paddle.point2) {
                    if (ball.oriVelX - m.speed/2 > -m.speed*3) {
                        ball.oriVelX = ball.oriVelX - m.speed/2;
                        ball.velX = m.width * ball.oriVelX / m.defaultW;
                        if (ball.oriVelX == 0) {
                            ball.oriVelX = 1;
                            ball.velX = m.width * ball.oriVelX / m.defaultW;
                        }
                    }
                } else if (centreX > paddle.point3 && centreX < paddle.point4) {
                    if (ball.oriVelX + m.speed/2 < m.speed*3) {
                        ball.oriVelX = ball.oriVelX + m.speed/2;
                        ball.velX = m.width * ball.oriVelX / m.defaultW;
                        if (ball.oriVelX == 0) {
                            ball.oriVelX = -1;
                            ball.velX = m.width * ball.oriVelX / m.defaultW;
                        }
                    }
                } else if (centreX >= paddle.point4 && centreX <= paddle.pointUR) {
                    if (ball.oriVelX + m.speed < m.speed*3) {
                        ball.oriVelX = ball.oriVelX + m.speed;
                        ball.velX = m.width * ball.oriVelX / m.defaultW;
                    }
                }
                if (ball.started) { m.score += 10; }
            }
        }

        // bullet collision
        for (int i=0; i<bullet.size(); ++i) {
            Bullet bull = bullet.get(i);
            for (int j=0; j<block.size(); ++j) {
                Block b = block.get(j);
                if (b.hp > 0) {
                    if (bull.y <= b.downY && bull.x < b.rightX && bull.x > b.posX) {
                        bullet.remove(i);
                        --i;
                        b.hp--;
                        m.score += 100;
                    }
                }
            }
        }
    }

    private void BallDirectionCheck(int cY) {
        for (int i=0; i < block.size(); ++i) {
            Block b = block.get(i);
            if (b.hp > 0) {
                if (cY > b.downY) {
                    b.balldirection = 3;
                } else if (cY < b.posY) {
                    b.balldirection = 4;
                } else if (ball.pointLx < b.posX) {
                    b.balldirection = 2;
                } else if (ball.pointRx > b.rightX) {
                    b.balldirection = 1;
                }
            }
        }
    }

    public void winningCondition() {
        boolean win = true;
        for (int i=0; i<block.size(); ++i) {
            if (win) {
                if (block.get(i).hp > 0) win = false;
            }
        }

        if (win) {
            ball.started = false;
            m.gamestate = 3;
            ball.gameEnd = true;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;
        g1.setPaintMode();

        paddle.paint(g1);
        ball.paint(g1);
        for (int i = 0; i < block.size(); ++i) {
            block.get(i).paint(g1);
        }
        for (int i = 0; i<bullet.size(); ++i) {
            bullet.get(i).paint(g1);
        }

        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        String score = "Score: " + m.score;
        String fps = "FPS=" + m.fps;
        String nbull = ": " + m.nbullets;
        g1.setColor(Color.DARK_GRAY);
        g1.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g1.drawString(score, 10, 20);
        g1.drawString(fps, 10, 40);

        g1.setStroke(new BasicStroke(1));
        g1.setColor(Color.CYAN);
        g1.fillRect(m.width-m.width/16-m.width/128, m.height - m.height/96 - m.height/48, m.width/320, m.height/48);
        g1.setColor(Color.BLACK);
        g1.drawRect(m.width-m.width/16-m.width/128, m.height - m.height/96 - m.height/48, m.width/320, m.height/48);
        g1.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g1.drawString(nbull, m.width-m.width/16, m.height - m.height/96);

        s.paint(g1);
    }
}
