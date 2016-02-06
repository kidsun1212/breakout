import java.awt.*;

/**
 * Created by Max on 2016-01-26.
 */
public class Ball {
    Model m;
    int pointLx, pointRx, pointUy, pointDy;
    int velX, velY, oriVelX, oriVelY;
    boolean started, gameEnd;

    Ball(Model model_) {
        m = model_;
        started = false;
        gameEnd = false;
    }

    public void paint(Graphics g) {
        Graphics2D g3 = (Graphics2D) g;
        g3.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (started) {
            g3.setColor(Color.BLACK);
            g3.fillRoundRect(pointLx, pointUy, m.width/64, m.height/48, m.width/10, m.height/10);
            /*String label = "ball(" + pointLx + ", " + pointRx + ", " + pointUy + ", " + pointDy + ")";
            String label2 = "ballV(" + velX + ", " + velY + ")";
            g3.setColor(Color.BLACK);
            g3.drawString(label, 230, 40);
            g3.drawString(label2, 230, 60);*/
        } else if (!gameEnd) {
            g3.setColor(Color.BLACK);
            g3.fillRoundRect(m.mouseX - m.width /128, m.height - m.height/16 - m.height/80 - m.height/96, m.width/64, m.height/48, m.width/10, m.height/10);
        }

        // life
        for (int i=0; i<m.life; ++i) {
            g3.setColor(Color.BLACK);
            g3.fillRoundRect(m.width/64 + i*(m.width/64+m.width/128), m.height - m.height/48 - m.height/96, m.width/64, m.height/48, m.width/10, m.height/10);
        }
    }

    public void move() {
        pointLx = pointLx + velX;
        pointRx = pointRx + velX;
        pointUy = pointUy + velY;
        pointDy = pointDy + velY;
    }
}
