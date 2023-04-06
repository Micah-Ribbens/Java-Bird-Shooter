package src;

import javax.swing.*;
import java.awt.*;

import static src.Constants.screenHeight;
import static src.Constants.screenWidth;

public class GameFrame  extends JFrame {
    MainScreen mainScreen;

    public GameFrame() {
        mainScreen = new MainScreen();

        this.add(mainScreen);

        this.setTitle("Pong Game");

        this.setResizable(false);

        this.setBackground(Color.blue);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.pack();

        this.setVisible(true);

        this.setLocationRelativeTo(null);
    }
}
