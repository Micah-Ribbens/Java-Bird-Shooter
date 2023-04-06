package src;

public class TimedEvent {
    public double currentTime = 0;
    public boolean isStarted = false;
    public double timeNeeded = 0;

    public TimedEvent(double timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    public void run(boolean shouldReset, boolean shouldStart) {
        if (shouldReset) {
            reset();
        }

        if (shouldStart && !isStarted) {
            start();
        }

        if (isStarted) {
            currentTime += VelocityCalculator.time;
        }
    }

    public void start() {
        currentTime = 0;
        isStarted = true;
    }

    public void reset() {
        currentTime = 0;
        isStarted = false;
    }

    public boolean isDone() {
        return isStarted && currentTime >= timeNeeded;
    }

    public boolean hasFinished() {
        return !isStarted || isDone();
    }
}