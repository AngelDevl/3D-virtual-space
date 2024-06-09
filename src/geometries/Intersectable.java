package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

/**
 * Intersectable interface that's represent an intersections between two geometries objects in a 3D Cartesian coordinate system
 */
public abstract class Intersectable {


    /**
     * findIntersections Function finds the intersection points between a ray and other geometries objects
     * uses findGeoIntersections and since it returns GeoPoint we need to separate the points from the GeoPoint objects
     * we use .map() function to loop over the points and separate the points from the GeoPoint objects
     * @param ray the ray object that intersect with other object
     * @return a list of points that represent the intersections points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * findGeoIntersections function to find GeoIntersections with a ray - uses the helper function
     * @param ray ray to look for intersections on
     * @return list of GeoPoint intersections which the ray intersects with
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * findGeoIntersections function to find GeoIntersections with a ray in range of a given max distance - uses the helper function
     * @param ray ray to look for intersections on
     * @param maxDistance the max distance between ray head and the intersection point
     * @return list of GeoPoint intersections which the ray intersects with
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * helper function to find GeoPoint intersections in max distance
     * @param ray ray to look for intersections on
     * @param maxDistance the max distance between ray head and the intersection point
     * @return list of GeoPoint intersections which the ray intersects with
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * GeoPoint class - hold a point and geometry object
     */
    public static class GeoPoint {

        /**
         * GeoPoint Constructor to initialize geometry and point
         * @param geometry new Geometry object
         * @param point new Point object
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override public String toString() {
            return "Point: " + point.toString() + " Geometry: "  + geometry.toString();
        }

        @Override public boolean equals(Object obj) {
            if (obj == null) return false;

            if (this == obj) return true;

            return (obj instanceof GeoPoint other) && geometry == other.geometry && point.equals(other.point);
        }

        // Geometry object
        public Geometry geometry;

        // Point object
        public Point point;
    }
}