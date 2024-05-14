package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public Geometries() {

    }

    public void add(Intersectable... geometries) {

    }

    public List<Point> findIntersections(Ray ray) {
        return null;
    }

    List<Intersectable> shapes = new LinkedList<>();
}