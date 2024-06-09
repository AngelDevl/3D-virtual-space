package geometries;

import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Geometries class that's represent all geometries objects that Intersectable in a 3D Cartesian coordinate system
 */
public class Geometries extends Intersectable {

    /**
     * Geometries Constructor to initialize the geoObjects - using add to add all the Intersectable objects
     * @param geometries all the geometries objects that implements Intersectable interface
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    // Default constructor for Geometries
    public Geometries() {

    }

    /**
     * add function adds all the shapes that given to a List in the class
     * @param geometries all the geometries objects that implements Intersectable interface
     */
    public void add(Intersectable... geometries) {
        if (geometries.length != 0)
            geoObjects.addAll(List.of(geometries));
    }


    @Override public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable object : geoObjects) {
            List<GeoPoint> currentIntersections = object.findGeoIntersections(ray, maxDistance);
            if (currentIntersections == null) {
                continue;
            }

            // Allocate memory if not initialized yet
            if (intersections == null)
                intersections = new LinkedList<>();

            // Merge the lists
            intersections.addAll(currentIntersections);
        }

        return intersections;
    }


    private final List<Intersectable> geoObjects = new LinkedList<>();
}