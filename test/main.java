package src.test;

public class main {
    public static class Enemy {
        public boolean hasReachedPlatformsEnd = true;
        public void run() {
            if (hasReachedPlatformsEnd) {
                runPlatformEnd();
            }
        }
        public void runPlatformEnd() {

        }

    }
    public static class SightsEnemy extends Enemy {
        @Override
        public void runPlatformEnd() {
            System.out.println("I don't need to do anything");

        }
    }
    public static class LaserEnemy extends Enemy {
        @Override
        public void runPlatformEnd() {
            System.out.println("Turn around and shoot laser");

        }
    }
    public static void main(String[] args) {
        SightsEnemy sightsEnemy = new SightsEnemy();
        LaserEnemy laserEnemy = new LaserEnemy();

        sightsEnemy.run();
        laserEnemy.run();

    }
}
