package renderer;

import lighting.LightSource;
import primitives.*;

import java.util.List;

import scene.Scene;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;

/**
 * SimpleRayTracer class - inheriting class
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * SimpleRayTracer constructor to set the scene
     * @param scene scene to set
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }


    @Override public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return this.scene.background;
        }

        GeoPoint closestIntersection = ray.findClosestGeoPoint(intersections);

        return calcColor(closestIntersection, ray);
    }


    /**
     * Checking for shading between a point and the light source
     * @param gp the GeoPoint to check if shaded
     * @param l direction from light to point
     * @param n normal from the object at the point
     * @param nl dot product of n & l params
     * @param lightSource the light source
     * @return if GeoPoint is unshaded return true else false
     */
    private boolean unshaded(GeoPoint gp, Vector l, Vector n, double nl, LightSource lightSource) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -1 * DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);

        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, lightSource.getDistance(gp.point));

        return intersections == null;
    }

    /**
     * Calculate the color at a given point
     * @param geoPoint GeoPoint to calculate the color
     * @return the color at the given point
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(geoPoint, ray));
    }


    /**
     * calculate the local affect using phong (iE + sum((kD*abs(lI*n)+kS*(max(0, -v*r))^nSh)*iLi))
     * @param geoPoint geo point at which to calculate light
     * @param ray ray from camera
     * @return Local Effect
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray) {
        Vector v = ray.direction.normalize();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);

        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return Color.BLACK;

        int nShininess = geoPoint.geometry.getMaterial().nShininess;
        Double3 kd = geoPoint.geometry.getMaterial().kD;
        Double3 ks = geoPoint.geometry.getMaterial().kS;

        Color color = geoPoint.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));

            if ((nl * nv > 0) && unshaded(geoPoint, l, n, nl, lightSource)) {
                Color lightIntensity = lightSource.getIntensity(geoPoint.point);
                color = color.add(calcDiffusive(kd, nl, lightIntensity), calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
            }
        }

        return color;
    }

    /**
     * Calculate the diffusive component of the light at this point
     *
     * @param kd             diffusive component
     * @param nl             dot-product n*l
     * @param lightIntensity light intensity
     * @return the diffusive component at the point
     */
    private Color calcDiffusive(Double3 kd, double nl, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(nl)));
    }


    /**
     * Calculate the Specular component of the light at this point
     *
     * @param ks             specular component
     * @param l              direction from light to point
     * @param n              normal from the object at the point
     * @param nl             dot-product n*l
     * @param v              direction from the camera to the point
     * @param nShininess     shininess level
     * @param lightIntensity light intensity
     * @return the Specular component at the point
     */
    private Color calcSpecular(Double3 ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color lightIntensity) {
        double vr = alignZero(v.dotProduct(l.add(n.scale(-2 * nl))));
        return lightIntensity.scale(ks.scale(Math.pow(Math.max(0, -1 * vr), nShininess)));
    }

    // size for moving the main axis for shading rays
    private static final double DELTA = 0.1;
}