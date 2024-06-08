package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * abstract class Ray Tracer Base - used to trace the ray and decide the pixel color pixel and ray intersection
 */
public abstract class RayTracerBase {

    /**
     * RayTracerBase constructor to set a scene
     * @param scene the scene to set
     */
    RayTracerBase(Scene scene) {
        this.scene = scene;
    }


    /**
     * Traces a given ray and get the color the ray hits
     * @param ray ray to trace
     * @return the color that the ray hits
     */
    public abstract Color traceRay(Ray ray);


    /**
     *The scene to trace ray in
     */
    protected Scene scene;
}
