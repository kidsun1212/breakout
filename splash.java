
import java.awt.*;

/**
 * Created by Max on 2016-01-28.
 */
public class splash {
    Model m;

    splash(Model mode_) {
        m = mode_;
    }

    public void paint(Graphics g) {
        Graphics2D g1 = (Graphics2D) g; // cast to get 2D drawing methods
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int sposx = m.width / 12 + m.width / 24;
        int sposy = m.height / 6 + m.height / 24;
        if (m.life == 3 && !m.ball.started) {
            g1.setColor(Color.white);
            g1.fillRoundRect(m.width / 12, m.height / 12, m.width - m.width / 6, m.height - m.height / 4, m.width / 32, m.height / 24);
            g1.setColor(Color.darkGray);
            g1.drawRoundRect(m.width / 12, m.height / 12, m.width - m.width / 6, m.height - m.height / 4, m.width / 32, m.height / 24);
            String welcome = "Welcome to Breakout!!";
            String name = "Myungsun Jung";
            String userid = "20511678";
            String control = "Control :";
            String mouse = ": mouse-cursor";
            String mousec = ": mouse-click";
            String inst1 = "Break all blocks to WIN!";
            String inst2 = "You are given 4 balls and 10 bullets!";
            String inst3 = "Press 'SPACE' to START!";
            g1.setColor(Color.BLACK);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g1.drawString(welcome, sposx, sposy);
            g1.drawString(mouse, m.width/3, m.height/3+m.height/48);
            g1.drawString(mousec, m.width/3, m.height/3+m.height/48+30);
            g1.drawString(inst1, m.width/6, m.height/3+100);
            g1.drawString(inst2, m.width/6, m.height/3+140);
            g1.drawString(inst3, m.width/6, m.height/3+190);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g1.drawString(name, sposx * 5, sposy);
            g1.drawString(userid, sposx * 5, sposy + 15);
            g1.drawString(control, sposx, sposy + 40);

            g1.setStroke(new BasicStroke(5));
            g1.fillRoundRect(m.width/6, m.height/3, m.width/8, m.height/40, m.width/40, m.height/40);
            g1.setStroke(new BasicStroke(1));
            g1.setColor(Color.CYAN);
            g1.fillRect(m.width/6+m.width/18, m.height/3 + 30, m.width/160, m.height/24);
            g1.setColor(Color.BLACK);
            g1.drawRect(m.width/6+m.width/18, m.height/3 + 30, m.width/160, m.height/24);
        }

        // game over
        if (m.gamestate == 2) {
            g1.setColor(Color.white);
            g1.fillRoundRect(m.width / 4, m.height / 4, m.width - m.width / 2, m.height - m.height / 2, m.width / 32, m.height / 24);
            g1.setColor(Color.darkGray);
            g1.drawRoundRect(m.width / 4, m.height / 4, m.width - m.width / 2, m.height - m.height / 2, m.width / 32, m.height / 24);
            String gameover = "GAME OVER";
            String score = "Your Score: " + m.score;
            String restart = "Press 'Enter' to RESTART";
            g1.setColor(Color.BLACK);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g1.drawString(gameover, m.width/2 - m.width/10, m.height/3);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g1.drawString(score, m.width/3, m.height/2);
            g1.drawString(restart, m.width/3, m.height/2 + 20);
        }

        // win
        if (m.gamestate == 3) {
            g1.setColor(Color.white);
            g1.fillRoundRect(m.width / 4, m.height / 4, m.width - m.width / 2, m.height - m.height / 2, m.width / 32, m.height / 24);
            g1.setColor(Color.darkGray);
            g1.drawRoundRect(m.width / 4, m.height / 4, m.width - m.width / 2, m.height - m.height / 2, m.width / 32, m.height / 24);
            String gameover = "YOU WIN!!";
            String score = "Your Score: " + m.score;
            String restart = "Press 'Enter' to RESTART";
            String exit = "Press 'ESC' to EXIT";
            g1.setColor(Color.BLACK);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 20));
            g1.drawString(gameover, m.width/2 - m.width/10, m.height/3);
            g1.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g1.drawString(score, m.width/3, m.height/2);
            g1.drawString(restart, m.width/3, m.height/2 + 20);
            g1.drawString(exit, m.width/3, m.height/2 + 40);
        }
    }
}
