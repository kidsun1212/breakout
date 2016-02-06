import java.awt.*;

/**
 * Created by Max on 2016-01-26.
 */
public class Paddle {
    Model m;
    int pointUL, point1, point2, point3, point4, pointUR;
    int pointY;

    Paddle(Model model_) {
        m = model_;
        pointUL = m.mouseX-m.width/16;
        pointUR = m.mouseX-m.width/16 + m.width/8;
        point1 = pointUL + (pointUR-pointUL)/8;
        point2 = point1 + (pointUR-pointUL)/4;
        point3 = point2 + (pointUR-pointUL)/4;
        point4 = point3 + (pointUR-pointUL)/4;
    }

    public void paint(Graphics g) {
        pointY = m.height - m.height/16;
        Graphics2D g2 = (Graphics2D) g; // cast to get 2D drawing methods
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.BLACK);
        if (pointUL < 0) {
            pointUL = 0;
            pointUR = pointUL + m.width/8;
            point1 = pointUL + (pointUR-pointUL)/8;
            point2 = point1 + (pointUR-pointUL)/4;
            point3 = point2 + (pointUR-pointUL)/4;
            point4 = point3 + (pointUR-pointUL)/4;
        } else if (pointUR > m.width) {
            pointUL = m.width - m.width/8;
            pointUR = m.width;
            point1 = pointUL + (pointUR-pointUL)/8;
            point2 = point1 + (pointUR-pointUL)/4;
            point3 = point2 + (pointUR-pointUL)/4;
            point4 = point3 + (pointUR-pointUL)/4;
        }
        g2.fillRoundRect(pointUL, pointY, m.width/8, m.height/40, m.width/40, m.height/40);

        /*String label = "Mouse at (" + m.mouseX + ")";
        String label2 = "pointUL,UR (" + pointUL + ", " + pointUR + ")";
        String position = "Window resolution (" + m.width + ", " + m.height + ")";
        g2.setColor(Color.BLACK);
        g2.drawString(label, 130, 40);
        g2.drawString(label2, 130, 10);
        g2.drawString(position, 130, 80);*/
    }
}
