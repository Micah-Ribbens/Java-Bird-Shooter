package src;

public class VelocityCalculator {
    public static double time;

    public static double getVelocity(double unitOfMeasurement, double howMuch) {
        return (unitOfMeasurement / 1000.0) * howMuch;
    }

    public static double getMeasurement(double unitOfMeasurement, double howMuch) {
        return (unitOfMeasurement / 100.0) * howMuch;
    }

    public static double calculateDistance(double velocity) {
        return velocity * VelocityCalculator.time;

    }
}