package src;

import static src.Constants.*;

class Bullet extends Component {
    double velocity = VelocityCalculator.getVelocity(screenHeight, 700);
    double hitsLeftToDestroy = 1;
    double damage = 1;
    boolean isMovingRight = true;

    public Bullet(String pathToImage, double leftEdge, double topEdge, boolean isMovingRight) {
        super(pathToImage);
        this.isMovingRight = isMovingRight;

        setDimensions(leftEdge, topEdge, Bullet.getWidth(), Bullet.getHeight());

    }

    public void run() {
        double distance = VelocityCalculator.calculateDistance(velocity);
        leftEdge += isMovingRight ? distance : -distance;
    }

    public static double getWidth() {
        return VelocityCalculator.getMeasurement(screenLength, 2);
    }

    public static double getHeight() {
        return VelocityCalculator.getMeasurement(screenHeight, 4);
    }
}
