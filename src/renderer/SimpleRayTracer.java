package renderer;

import primitives.Color;
import primitives.Ray;
import primitives.Point;
import java.util.List;
import scene.Scene;

public class SimpleRayTracer extends RayTracerBase {

    /**
     * SimpleRayTracer constructor to set the scene
     * @param scene scene to set
     */
    SimpleRayTracer(Scene scene) {
        super(scene);
    }


    @Override public Color traceRay(Ray ray) {
        List<Point> intersections = this.scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return this.scene.background;
        }

        Point closestIntersection = ray.findClosestPoint(intersections);

        return calcColor(closestIntersection);
    }

    /**
     * Calculate the color at a given point
     * @param point point to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(Point point) {
        return this.scene.ambientLight.getIntensity();
    }
}