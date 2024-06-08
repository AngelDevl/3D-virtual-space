package primitives;

import geometries.Intersectable.GeoPoint;

import java.util.List;

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

    /**
     * According to the formula of point on ray (p = P0 + V * t)
     * @param t t for calculating the point
     * @return A point on a eay
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return head;
        }

        return head.add(direction.scale(t));
    }

    /**
     * findClosestPoint function to find the closest point on a ray to ray head point
     * @param points list of points that on the ray
     * @return the closest point to the head point
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * findClosestGeoPoint function find the closest point intersection with ray head
     * from a list of GeoPoints
     * @param points list of GeoPoints that each one hold a point
     * @return the closest intersection GeoPoint
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points == null || points.isEmpty()) { return null; }

        GeoPoint closestPoint = points.getFirst();
        double closestPointDistance = closestPoint.point.distance(head);

        for (int i = 1; i < points.size(); i++) {
            double nextPointDistance = points.get(i).point.distance(head);

            if (closestPointDistance > nextPointDistance) {
                closestPointDistance = nextPointDistance;
                closestPoint = points.get(i);
            }
        }

        return closestPoint;
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
