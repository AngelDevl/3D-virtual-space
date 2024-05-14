package geometries;

import primitives.Ray;
import java.util.List;

public interface Intersectable {
    List<Intersectable> findIntersections(Ray ray);
}
