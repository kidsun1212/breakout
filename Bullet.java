import java.awt.*;

/**
 * Created by Max on 2016-01-28.
 */
public class Bullet {
    Model m;
    int x,y;

    Bullet(Model model_) {
        m = model_;
        x = m.mouseX;
        y = m.height - m.height/16;
    }

    public void move() {
        y -= m.height/48;
    }

    public void paint(Graphics g) {
        Graphics2D g4 = (Graphics2D) g; // cast to get 2D drawing methods
        g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g4.setStroke(new BasicStroke(1));
        g4.setColor(Color.CYAN);
        g4.fillRect(x, y, m.width/320, m.height/48);
        g4.setColor(Color.BLACK);
        g4.drawRect(x, y, m.width/320, m.height/48);
    }
}
