package src;

import javax.swing.*;
import java.awt.*;

import static src.Constants.screenHeight;
import static src.Constants.screenWidth;

public class GameFrame  extends JFrame {
    BirdShooter birdShooter;

    public GameFrame() {
        birdShooter = new BirdShooter();

        this.add(birdShooter);

        this.setTitle("Pong Game");

        this.setResizable(false);

        this.setBackground(Color.blue);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        this.setVisible(true);

        this.setLocationRelativeTo(null);
    }
}
