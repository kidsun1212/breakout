/**
 * Created by Max on 2016-01-18.
 */

import javax.swing.*;
import java.awt.*;

// top-level container class
class Breakout {
    // constructor for the game
    // instantiates all of the top-level classes (model, view)
    // and tells the model to start the game
    Breakout(int framepersec, int gamespeed) {
        JFrame f = new JFrame("Breakout!!");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setBackground(Color.white);
        Model model = new Model(framepersec, gamespeed);
        View view = new View(model);
        view.setMinimumSize(new Dimension(640, 480));
        model.view = view;
        f.add(view);
        f.pack();
        f.getPreferredSize();
        f.setMinimumSize(f.getSize());
        f.setResizable(true);
        f.setVisible(true);
    }

    // entry point for the application
    public static void main(String[] args) {
        if (args.length == 0) {
            Breakout game = new Breakout(60, 3);
        } else if (args.length == 2) {
            boolean ex = false;
            int arg1 = 0;
            int arg2 = 0;
            try {
                arg1 = Integer.parseInt(args[0]);
                arg2 = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                System.err.println("arg1 and arg2 must be integers");
                System.exit(1);
            }

            if (arg1 < 25 || arg1 > 60) {
                System.err.println("arg1=frame-rate must be between 25 to 60");
                ex = true;
            }
            if (arg2 < 1 || arg2 > 4) {
                System.err.println("arg2=speed must be between 1 to 4");
                ex = true;
            }
            if (ex) System.exit(1);
            Breakout game = new Breakout(arg1, arg2);
        } else {
            System.err.println("Can accept 2 Arguments only. arg1=frame-rate arg2=speed");
        }
    }
}