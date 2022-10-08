package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

    public static double length = VelocityCalculator.getMeasurement(screenLength, 10);
    public static double height = VelocityCalculator.getMeasurement(screenHeight, 20);
    public static double turretLength = VelocityCalculator.getMeasurement(3, screenLength);
    public double turretHeight = VelocityCalculator.getMeasurement(3.5, screenHeight);
    public double playerVelocity = VelocityCalculator.getVelocity(screenLength, 700);
    public double turretVelocity = VelocityCalculator.getVelocity(screenLength, 250);

    public int rightKey = 0;
    public int leftKey = 0;
    public int upKey = 0;
    public int downKey = 0;
    public int shootKey = 0;
    public int moveTurretKey = 0;

    public TimedEvent stunEvent;
    public int playerNumber = 1;
    public static ArrayList<Bullet> newBullets;
    public static double capExtension = Math.ceil(21/86 * length);

    // Booleans
    boolean isMovingRight;
    boolean isMovingLeft;
    boolean isMovingDown;
    boolean isMovingUp;
    boolean isMovingTurretUp;
    boolean isMovingTurretDown;


    public Player(Integer[] playerKeys, int playerNumber, Double[] boundaries, boolean isFacingRight) {
        super("images/player" + playerNumber + "_right.png");
        pathToBulletImage = "images/player" + playerNumber + "_bullet.png";

        leftKey = playerKeys[0];
        rightKey = playerKeys[1];
        upKey = playerKeys[2];
        downKey = playerKeys[3];


        setDimensions(screenLength / 2, screenHeight - height, length, height);

        newBullets = new ArrayList<>();
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
        newBullets = new ArrayList<>();
        waitToShootEvent.run(waitToShootEvent.currentTime >= waitToShootEvent.timeNeeded, false);
        stunEvent.run(stunEvent.currentTime >= stunEvent.timeNeeded, false);

        turret.leftEdge = isFacingRight ? getRightEdge() - capExtension : leftEdge + capExtension - turret.width;
        turret.topEdge = topEdge + verticalDelta;

        double playerDistance = VelocityCalculator.calculateDistance(playerVelocity);
        double turretDistance = VelocityCalculator.calculateDistance(turretVelocity);

        leftEdge += isMovingRight ? playerDistance : 0;
        leftEdge -= isMovingLeft ? playerDistance : 0;

        topEdge -= isMovingUp ? playerDistance : 0;
        topEdge += isMovingDown ? playerDistance : 0;

        leftEdge = getNewCoordinates(minLeftEdge, maxLeftEdge, leftEdge);
        topEdge = getNewCoordinates(minTopEdge, maxTopEdge, topEdge);

        verticalDelta -= isMovingUp ? turretDistance : 0;
        verticalDelta += isMovingDown ? turretDistance : 0;
        verticalDelta = getNewCoordinates(30 / 122 * height, height - turret.height, verticalDelta);
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (stunEvent.hasFinished()) {

            isMovingRight = keyEvent.getKeyCode() == rightKey;
            isMovingLeft = keyEvent.getKeyCode() == leftKey;

            isMovingTurretDown = keyEvent.getKeyCode() == downKey && keyEvent.getKeyCode() == moveTurretKey;
            isMovingTurretUp = keyEvent.getKeyCode() == upKey && keyEvent.getKeyCode() == moveTurretKey;

            isMovingDown = keyEvent.getKeyCode() == downKey && keyEvent.getKeyCode() != moveTurretKey;
            isMovingUp = keyEvent.getKeyCode() == upKey && keyEvent.getKeyCode() != moveTurretKey;

            isFacingRight = keyEvent.getKeyCode() == rightKey ? true : isFacingRight;
            isFacingRight = keyEvent.getKeyCode() == leftKey ? false : isFacingRight;
        }

        boolean canShootBullet = waitToShootEvent.hasFinished() && stunEvent.hasFinished();
        if (canShootBullet && keyEvent.getKeyCode() == shootKey) {
            shootLaser();
            waitToShootEvent.start();
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        isMovingRight = false;
        isMovingLeft = false;

        isMovingUp = false;
        isMovingDown = false;

        isMovingTurretUp = false;
        isMovingTurretDown = false;

    }

    public void shootLaser() {
        double turretLeftEdge = isFacingRight ? turret.getRightEdge() : turret.leftEdge - Bullet.getWidth();

        newBullets.add(new Bullet(pathToBulletImage, turretLeftEdge, turret.getVerticalMidpoint() - Bullet.getHeight() / 2, isFacingRight));
    }

    public void reset() {
        newBullets = new ArrayList<>();
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
        String turretType = !stunEvent.hasFinished() ? "Stunned.png" : ".png";
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

