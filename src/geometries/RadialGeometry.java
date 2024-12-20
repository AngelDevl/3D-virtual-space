package geometries;


/**
 * RadialGeometry class that's serve as a parent class to all geometrical shapes that are circular and need a radius
 */
abstract public class RadialGeometry extends Geometry {

    /**
     * The radius that represent the radius for some classes
     */
    protected final double radius;


    /**
     * RadialGeometry Constructor to initialize a radius
     * @param radius value to initialize radius with
     */
    RadialGeometry(double radius) {
        this.radius = radius;
    }
}