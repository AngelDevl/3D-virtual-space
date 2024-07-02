package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class - to represent a point light
 */
public class PointLight extends Light implements LightSource {

    /** position of the light */
    private Point position;

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
}
