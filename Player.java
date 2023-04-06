package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.ceil;
import static src.Constants.*;


/** # Player Position */
public class Player extends Component {
    public double minLeftEdge = 0;
    public double maxLeftEdge = 0;
    public double minTopEdge = 0;
    public double maxTopEdge = 0;
    public boolean isFacingRight = false;
    public boolean isFacingUp = false;
    public double verticalDelta = 0;
    public String pathToBulletImage = "";
    public String pathToPlayerImage = "";
    public Component turret;
    public TimedEvent waitToShootEvent;

    public static double width = VelocityCalculator.getMeasurement(screenWidth, 10);
    public static double height = VelocityCalculator.getMeasurement(screenHeight, 20);
    public static double turretLength = VelocityCalculator.getMeasurement(3, screenWidth);
    public double turretHeight = VelocityCalculator.getMeasurement(3.5, screenHeight);
    public double playerVelocity = VelocityCalculator.getVelocity(screenWidth, 700);
    public double turretVelocity = VelocityCalculator.getVelocity(screenWidth, 250);
    public double capExtension;

    public int rightKey = 0;
    public int leftKey = 0;
    public int upKey = 0;
    public int downKey = 0;
    public int shootKey = 0;

    public TimedEvent stunEvent;
    public int playerNumber = 1;

    public Player(Integer[] playerKeys, int playerNumber, Double[] boundaries, boolean isFacingRight) {
        super("images/player" + playerNumber + "_right.png");
        pathToBulletImage = "images/player" + playerNumber + "_bullet.png";

        leftKey = playerKeys[0];
        rightKey = playerKeys[1];
        upKey = playerKeys[2];
        downKey = playerKeys[3];
        shootKey = playerKeys[4];


        capExtension = ceil(21.0 / 86.0 * width);
        System.out.println(capExtension);
        setDimensions(screenWidth / 2, screenHeight - height, width, height);

        waitToShootEvent = new TimedEvent(.2);
        stunEvent = new TimedEvent(.2);

        minLeftEdge = boundaries[0];
        maxLeftEdge = boundaries[1];
        minTopEdge = boundaries[2];
        maxTopEdge = boundaries[3];

        turret = new Component("images/beak.png");

        turret.setDimensions(getRightEdge(), getVerticalMidpoint(), turretLength, turretHeight);

        this.playerNumber = playerNumber;
        isFacingRight = playerNumber == 0;

        String[] imagePaths = {"images/beak.png", "images/beak_stunned.png", "images/enemy_eye.png"};

        // Images that have a left and right version
        String[] transformedImagePaths = {"images/player" + playerNumber, "images/stunned"};

        for (var imagePath : imagePaths) {
            Constants.loadImage(imagePath);
        }

        for (var imagePath : transformedImagePaths) {
            Constants.loadImage(imagePath + "_left.png");
            Constants.loadImage(imagePath + "_right.png");
        }
    }

    public void run() {
        waitToShootEvent.run(waitToShootEvent.currentTime >= waitToShootEvent.timeNeeded, false);
        stunEvent.run(stunEvent.currentTime >= stunEvent.timeNeeded, false);

        double playerDistance = VelocityCalculator.calculateDistance(playerVelocity);

        leftEdge += KeyBoardListener.get(rightKey) ? playerDistance : 0;
        leftEdge -= KeyBoardListener.get(leftKey) ? playerDistance : 0;

        topEdge -= KeyBoardListener.get(upKey) ? playerDistance : 0;
        topEdge += KeyBoardListener.get(downKey) ? playerDistance : 0;

        leftEdge = getNewCoordinates(minLeftEdge, maxLeftEdge, leftEdge);
        topEdge = getNewCoordinates(minTopEdge, maxTopEdge, topEdge);

        turret.topEdge = getVerticalMidpoint();
        turret.leftEdge = isFacingRight ? getRightEdge() - capExtension : leftEdge - turret.width + capExtension;

        isFacingRight = KeyBoardListener.get(rightKey) ? true : isFacingRight;
        isFacingRight = KeyBoardListener.get(leftKey) ? false : isFacingRight;

    }

    public boolean hasShootBullet() {
        boolean canShootBullet = waitToShootEvent.hasFinished() && stunEvent.hasFinished();

        return canShootBullet && KeyBoardListener.get(shootKey);
    }

    public Bullet getBullet() {
        waitToShootEvent.start();

        double bulletLeftEdge = isFacingRight ? turret.getRightEdge() : turret.leftEdge - Bullet.getWidth();
        return new Bullet(pathToBulletImage, bulletLeftEdge, turret.getVerticalMidpoint() - Bullet.getHeight() / 2, isFacingRight);
    }

    public void reset() {
        waitToShootEvent.reset();
    }

    public double getNewCoordinates(double minCoordinate, double maxCoordinate, double currentCoordinate) {
        currentCoordinate = currentCoordinate < minCoordinate ? minCoordinate : currentCoordinate;
        currentCoordinate = currentCoordinate > maxCoordinate ? maxCoordinate : currentCoordinate;

        return currentCoordinate;
    }

    public void draw(Graphics graphics) {
        String direction = isFacingRight ? "_right.png" : "_left.png";
        String originalPath = stunEvent.hasFinished() ? "images/player" + playerNumber : "images/stunned";
        pathToImage = originalPath + direction;

        String originalTurretPath = "images/beak";
        String turretType = !stunEvent.hasFinished() ? "_stunned.png" : ".png";
        turret.pathToImage = originalTurretPath + turretType;

        super.draw(graphics);
        turret.draw(graphics);
    }

    public void stun(double stunTime) {
        if (stunEvent.hasFinished()) {
            stunEvent.start();
            stunEvent.timeNeeded = stunTime;
        }
    }
}

