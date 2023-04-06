package src;

import java.awt.*;
import static src.Constants.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/** # Player Position and Keys */
public class BirdShooter extends JPanel implements Runnable {
    public double middleOfScreen = screenWidth / 2;
    public double totalBuffer = VelocityCalculator.getMeasurement(screenWidth, 2);

    public double individualBuffer = totalBuffer /2;

    public Integer[][] playersKeys = {{KEY_A,KEY_D,KEY_W,KEY_S,KEY_F},
                             {KEY_LEFT,KEY_RIGHT,KEY_UP,KEY_DOWN,KEY_QUESTION_MARK}};

    public Double[][] playerBoundaries = {{0.0, middleOfScreen -Player.width -individualBuffer, 0.0, screenHeight -Player.height},
                      {middleOfScreen +individualBuffer,screenWidth -Player.width, 0.0, screenHeight -Player.height}};

    public Player player1 = new Player(playersKeys[0], 1, playerBoundaries[0], true);
    public Player player2 = new Player(playersKeys[1], 2, playerBoundaries[1], false);

    public int player1Score = 0;
    public int player2Score = 0;
    
    // Miscellaneous Stuff;
    public ArrayList<Bullet> bullets = new ArrayList<>();
    public Enemy enemy = new Enemy();

    public int highScore = 0;
    Thread gameThread;

    public TextField player1ScoreField = new TextField("Player 1 Score: 0");
    public TextField player2ScoreField = new TextField("Player 2 Score: 0");
    public int scoreFieldsWidth = screenWidth / 2;
    public int scoreFieldHeight = (int) VelocityCalculator.getMeasurement(screenWidth, 5);

    public BirdShooter() {
        this.setFocusable(true);

        this.addKeyListener(new BirdShooter.Keys());

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));


        gameThread = new Thread(this);

        gameThread.start();

        centerPlayers();
        
        player1ScoreField.setBounds(0, 0, scoreFieldsWidth, scoreFieldHeight);
        player2ScoreField.setBounds(scoreFieldsWidth, 0, scoreFieldsWidth, scoreFieldHeight);
//        this.setVisible(true);
//        this.setLayout(null);
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
        player1.draw(graphics);
        player2.draw(graphics);
        enemy.draw(graphics);
        player1ScoreField.paintAll(graphics);
        player2ScoreField.paintAll(graphics);

        for (var bullet : bullets) {
            try {
                bullet.draw(graphics);
            } catch (Exception e) {
                System.out.println("ERROR");
            }
        }
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
        runCollisions();
        player1.run();
        player2.run();
        enemy.run();

        for (var bullet : bullets) {
            bullet.run();
        }
    }

    public void runCollisions() {
        if (player1.hasShootBullet()) {
            bullets.add(player1.getBullet());
        }

        if (player2.hasShootBullet()) {
            bullets.add(player2.getBullet());
        }

        runBulletCollisions();
        boolean enemyHasHitPlayer1 = CollisionsEngine.isCollision(enemy, player1);
        boolean enemyHasHitPlayer2 = CollisionsEngine.isCollision(enemy, player2);

        if (enemyHasHitPlayer1 || enemy.getRightEdge() < 0){
            runPlayerScoring(false);
        }

        if (enemyHasHitPlayer2 || enemy.leftEdge > screenWidth){
            runPlayerScoring(true);
        }
    }

    public void runBulletCollisions() {
        for (int i = 0; i < bullets.size(); i++) {
            double stunTime = .4;
            Bullet bullet1 = bullets.get(i);

            boolean bulletHasHitPlayer1 = CollisionsEngine.isCollision(player1, bullet1);
            boolean bulletHasHitPlayer2 = CollisionsEngine.isCollision(player2, bullet1);
            boolean bulletHasHitEnemy = CollisionsEngine.isCollision(bullet1, enemy);

            if (bulletHasHitPlayer1) {
                player1.stun(stunTime);
            }

            else if (bulletHasHitPlayer2) {
                player2.stun(stunTime);
            }

            if (bulletHasHitEnemy && enemy.isMovingRight != bullet1.isMovingRight){
                enemy.hitsLeftToChangeDirection -= 1;
            }

            if (bulletHasHitPlayer1 || bulletHasHitPlayer2 || bulletHasHitEnemy) {
                bullet1.hitsLeftToDestroy = 0;
                continue;
            }

            for (int j = i; j < bullets.size() - i; j++){
                Bullet bullet2 = bullets.get(j);

                if (bullet1 != bullet2 && CollisionsEngine.isCollision(bullet1, bullet2)){
                    bullet1.hitsLeftToDestroy = 0;
                    bullet2.hitsLeftToDestroy = 0;
                }
            }
        }
        ArrayList<Bullet> aliveBullets = new ArrayList<>();

        for (var bullet : bullets) {
            if (bullet.hitsLeftToDestroy > 0) {
                aliveBullets.add(bullet);
            }
        }
        bullets = aliveBullets;
    }

    public void runPlayerScoring(boolean player1HasScored) {
        player1.reset();
        player2.reset();
        enemy.reset();

        player1Score += player1HasScored ? 1 : 0;
        player2Score += !player1HasScored ? 1 : 0;

        centerPlayers();
    }

    public void centerPlayers() {
        player1.leftEdge = 0;
        player2.leftEdge = screenWidth - player2.width;
    }

    public class Keys extends KeyAdapter {
        public void keyPressed(KeyEvent keyEvent) {
            player1.keyPressed(keyEvent);
            player2.keyPressed(keyEvent);

        }
        public void keyReleased(KeyEvent keyEvent) {
            player1.keyReleased(keyEvent);
            player2.keyReleased(keyEvent);
        }

    }
}


