package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;
import java.util.List;
import java.util.LinkedList;

/**
 * PointLight class - to represent a point light
 */
public class PointLight extends Light implements LightSource {

    /** position of the light */
    private Point position;

    /** radius of light to create the soft shadow */
    private double radius;

    /** representing attenuation attributes */
    private double kC = 1, kL = 0, kQ = 0;

    /**
     * Constructor to initialize PointLight members
     * @param intensity new intensity
     * @param position position of the light
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }


    @Override
    public Color getIntensity(Point p) {
        // I0/(kC + kL) * d + kQ * d^2
        return intensity.scale(1 / (kC + kL * position.distance(p) + kQ * position.distanceSquared(p)));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    @Override
    public double getDistance(Point p) { return position.distance(p); }


    /**
     * Setter for kQ
     * @param kC new double value
     * @return this - current object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Setter for kL
     * @param kL new double value
     * @return this - current object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Setter for kQ
     * @param kQ new double value
     * @return this - current object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Setter for the radius of soft-shadows
     * @param radius new radius value
     * @return this - current object
     */
    public PointLight setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * @param p point on the geometry that the light is on
     * @return list of vectors from the point to the light
     */
    @Override
    public List<Vector> getListL(Point p) {
        List<Vector> vectors = new LinkedList<>();

        // grid of vectors around the light
        for (double i = -radius; i < radius; i += radius / 10) {
            for (double j = -radius; j < radius; j += radius / 10) {
                if (i != 0 && j != 0) {
                    // create a point on the grid
                    Point point = position.add(new Vector(i, 0.1d, j));
                    if (point.equals(position)) {
                        // if the point is the same as the light position,
                        // add the vector from the point to the light
                        vectors.add(p.subtract(point).normalize());
                    } else {
                        try {
                            if (point.subtract(position).dotProduct(point.subtract(position))
                                    <= radius * radius) {
                                // if the point is in the radius of the light, add the vector from the point to the light
                                vectors.add(p.subtract(point).normalize());
                            }
                        } catch (Exception e) {
                            // if the point is in the radius of the light, add the vector from the point to the light
                            vectors.add(p.subtract(point).normalize());
                        }

                    }
                }

            }
        }

        vectors.add(getL(p));
        return vectors;
    }
}
