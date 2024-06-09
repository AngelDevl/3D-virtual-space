package renderer;

import lighting.LightSource;
import primitives.*;

import java.util.List;

import scene.Scene;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

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
        GeoPoint closestIntersection = findClosestIntersection(ray);
        if (closestIntersection == null) { return scene.background; }

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
        if (gp.geometry.getMaterial().kT == Double3.ZERO) {
            return true;
        }

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
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }


    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp.point, ray.direction, gp.geometry.getNormal(gp.point)), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp.point, ray.direction, gp.geometry.getNormal(gp.point)), material.kR, level, k));
    }


    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }

        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * find the closest intersection to the ray out of all the geometries
     * @param ray ray to calculate for
     * @return closest point
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return null;
        }
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * construct the refracted ray but first make sure nv is zero
     * @param point point to caclulate for
     * @param direction direction vector of ray
     * @param normal normal vector
     * @return secondary ray
     */
    private Ray constructRefractedRay(Point point, Vector direction, Vector normal) {
        double vn = direction.dotProduct(normal);

        if (isZero(alignZero(vn))) {
            return null;
        }
        return new Ray(point, normal, direction);
    }

    /**
     * construct reflected ray using equation in presentation according to: 𝒓 = 𝒗 − 𝟐 ∙ (𝒗 ∙ 𝒏) ∙ 𝒏
     * @param point point to calculate for
     * @param direction direction vector
     * @param normal normal vector
     * @return secondary ray
     */
    private Ray constructReflectedRay(Point point, Vector direction, Vector normal) {
        double vn = direction.dotProduct(normal);
        if (isZero(alignZero(vn))) {
            return null;
        }

        Vector r = direction.subtract(normal.scale(2 * vn));
        return new Ray(point, normal, r);
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

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = Double3.ONE;
}