package src;

import java.awt.*;

import static src.Constants.*;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;


/** # Movement and Direction */
class Enemy extends Component {
    double baseVelocity = VelocityCalculator.getVelocity(screenWidth, 70);
    double velocity = baseVelocity;
    double velocityIncrease = velocity * .2;
    double totalHitsToChangeDirection = 5;
    double hitsLeftToChangeDirection = totalHitsToChangeDirection;
    boolean isMovingRight = false;
    boolean isMovingDown = true;;
    // Size and Eyes;
    double width = VelocityCalculator.getMeasurement(screenWidth, 11);
    double height = width;
    Component leftEye = new Component("images/enemy_eye.png");
    Component rightEye = new Component("images/enemy_eye.png");

    public Enemy() {
        super("images/enemy.png");

        center();

        Double[] eyeSize = getEyeSize();

        leftEye.width = eyeSize[0];
        leftEye.height = eyeSize[1];

        rightEye.width = eyeSize[0];
        rightEye.height = eyeSize[1];

        setDimensions(leftEdge, topEdge, width, height);
    }

    public void run() {
        if (hitsLeftToChangeDirection <= 0) {
            hitsLeftToChangeDirection = totalHitsToChangeDirection;
            isMovingRight = !isMovingRight;
            velocity += velocityIncrease;
        }

        double distance = VelocityCalculator.calculateDistance(velocity);

        topEdge += isMovingDown ? distance : -distance;
        leftEdge += isMovingRight ? distance : -distance;

        if (topEdge <= 0) {
            topEdge = 0;
            isMovingDown = true;
        }

        if (getBottomEdge() >= screenHeight) {
            topEdge = screenHeight - height;
            isMovingDown = false;
        }

        Double[] horizontalEyePositions = isMovingRight ? getEyeRightPositions() : getEyeLeftPositions();
        Double[] topEyePositions = isMovingDown ? getEyeBottomPositions() : getEyeTopPositions();

        leftEye.leftEdge = horizontalEyePositions[0];
        rightEye.leftEdge = horizontalEyePositions[1];

        leftEye.topEdge = topEyePositions[0];
        rightEye.topEdge = topEyePositions[1];
    }

    public void center() {
        leftEdge = (screenWidth / 2.0) - (width / 2.0);
        topEdge = (screenHeight / 2.0) - (height / 2.0);
    }

    public void draw(Graphics graphics) {
        super.draw(graphics);
        leftEye.draw(graphics);
        rightEye.draw(graphics);
    }

    public void reset() {
        center();
        velocity = baseVelocity;
    }

    public Double[] getEyeSize() {
        return new Double[] {4.0/47.0 * width, 4.0/47.0 * height};
    }

    public Double[] getEyeLeftPositions() {
        return new Double[] {floor(leftEdge + 12.0 / 47.0 * width) - 1, floor(leftEdge + 30.0 / 47.0 * width) - 1};
    }

    public Double[] getEyeRightPositions() {
        return new Double[] {ceil(leftEdge + 14.0/47.0 * width) + 1, ceil(leftEdge + 32.0/47.0 * width) + 1};
    }

    public Double[] getEyeTopPositions() {
        return new Double[] {floor(topEdge + 18.0 / 47.0 * height) - 1, floor(topEdge + 18.0 / 47.0 * height) - 1};
    }

    public Double[] getEyeBottomPositions() {
        return new Double[]{ceil(topEdge + 20.0 / 47.0 * height) + 1, ceil(topEdge + 20.0 / 47.0 * height) + 1};
    }
}



