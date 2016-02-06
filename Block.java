import java.awt.*;

/**
 * Created by Max on 2016-01-26.
 */
public class Block {
    Model m;
    int posX, posY, defaultX, defaultY;
    int width, height, defaultW, defaultH;
    int downY, rightX, defaultDY, defaultRX;
    int hp;
    int balldirection;

    Block(Model model_, int x, int y, int healthpoint) {
        m = model_;
        defaultX = x;
        defaultY = y;
        defaultW = m.defaultW/10;
        defaultH = m.defaultH/20;
        defaultDY = defaultY + defaultH;
        defaultRX = defaultX + defaultW;
        posX = m.width * defaultX / m.defaultW;
        posY = m.height * defaultY / m.defaultH;
        width = m.width * defaultW / m.defaultW;
        height = m.height * defaultH / m.defaultH;
        downY = m.height * defaultDY / m.defaultH;
        rightX = m.width * defaultRX / m.defaultW;
        hp = healthpoint;
    }

    public void paint(Graphics g) {
        if (hp > 0) {
            Graphics2D g4 = (Graphics2D) g; // cast to get 2D drawing methods
            g4.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g4.setStroke(new BasicStroke(5));
            if (hp == 1) { g4.setColor(Color.WHITE); }
            if (hp == 2) { g4.setColor(Color.LIGHT_GRAY); }
            if (hp == 3) { g4.setColor(Color.GRAY); }
            if (hp == 4) { g4.setColor(Color.DARK_GRAY); }
            if (hp == 5) { g4.setColor(Color.BLACK); }
            g4.fillRoundRect(posX, posY, width, height, m.width/40, m.height/40);
            g4.setStroke(new BasicStroke(1));
            g4.setColor(Color.BLACK);
            g4.drawRoundRect(posX, posY, width, height, m.width/40, m.height/40);
        }
    }
}
