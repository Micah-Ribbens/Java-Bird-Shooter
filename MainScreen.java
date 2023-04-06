package src;

import java.awt.*;
import static src.Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** # Player Position and Keys */
public class MainScreen extends JPanel implements Runnable {
    Thread gameThread;
    public MainScreen() {
        KeyBoardListener.initialize();
        this.setFocusable(true);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));


        gameThread = new Thread(this);

        gameThread.start();


    }
    
    

    public void run() {
        long startTime = System.nanoTime();

        while (true) {
            runGameCode();
            this.repaint();

            long endTime = System.nanoTime();
            VelocityCalculator.time = (endTime - startTime) / Math.pow(10, 9);
//            System.out.println(VelocityCalculator.time + " " + (endTime - startTime));
            startTime = endTime;


        }
    }


    public void paint(Graphics graphics) {
        paintBackground(graphics);
        // TODO call all the component draw() methods here
        Toolkit.getDefaultToolkit().sync();
    }

    public void paintBackground(Graphics graphics) {
        try {
            BufferedImage image = ImageIO.read(new File("images/background.png"));
            graphics.drawImage(image, 0, 0, screenWidth, screenHeight, null);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void runGameCode() {
        // TODO put the game code that should be run every cycle in here
        // TODO also call all the component run methods
    }
}


