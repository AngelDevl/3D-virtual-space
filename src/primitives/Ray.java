package primitives;

import static primitives.Util.isZero;

/**
 * Ray class that's represent a line that has a starting point and direction but without an end point
 */
public class Ray {

    /**
     * Ray constructor to initialize a new Ray object with head and direction
     * (direction should be normalized)
     * @param head a new head point to assign head with
     * @param direction a new direction vector to assign direction with
     */
    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }

        return head.add(direction.scale(t));
    }

    @Override public String toString() {
        return "Ray {" + head + ", " + direction + "}";
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        return (obj instanceof Ray other) && head.equals(other.head) && direction.equals(other.direction);
    }


    /**
     * The start of the line
     */
    public final Point head;

    /**
     * The direction vector that's represent the direction of the line
     */
    public final Vector direction;
}
