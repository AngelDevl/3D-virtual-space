package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * SpotLight class - to represent a spotlight light
 */
public class SpotLight extends PointLight {

    /** narrowBeam allows to narrow the light from the spotlight */
    private double narrowBeam = 1;

    /** direction on the spotlight */
    private Vector direction;


    /**
     * Constructor to initialize SpotLight class members
     * @param intensity new intensity
     * @param position position of the light
     * @param direction direction of the light
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }


    @Override
    public Color getIntensity(Point p){
        double projection = direction.normalize().dotProduct(getL(p));
        double factor = Math.max(0, projection);

        // Here we apply the narrowBeam factor to get a more narrow light
        // if the narrowBeam is 1 it would take not affect since it will be factor^1
        factor = Math.pow(factor, narrowBeam);

        // I0 * max(0, dir*l) / (kC + kL) * d + kQ * d^2
        return super.getIntensity(p).scale(factor);
    }

    @Override
    public SpotLight setKc(double kC) {
        super.setKc(kC);
        return this;
    }

    @Override
    public SpotLight setKl(double kL) {
        super.setKl(kL);
        return this;
    }

    @Override
    public SpotLight setKq(double kQ) {
        super.setKq(kQ);
        return this;
    }

    /**
     * Setter for narrowBeam
     * @param narrowBeam new double value
     * @return this - current object
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }
}
