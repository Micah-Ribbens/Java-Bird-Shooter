package src;

public class CollisionsEngine {
    public static boolean isHorizontalCollision(Component object1, Component object2) {
        return object1.leftEdge <= object2.getRightEdge() && object1.getRightEdge() >= object2.leftEdge;
    }

    public static boolean isVerticalCollision(Component object1, Component object2) {
        return object1.topEdge <= object2.getBottomEdge() && object1.getBottomEdge() >= object2.topEdge;
    }

    public static boolean isCollision(Component object1, Component object2) {
        return CollisionsEngine.isHorizontalCollision(object1, object2) && CollisionsEngine.isVerticalCollision(object1, object2);
    }
}


