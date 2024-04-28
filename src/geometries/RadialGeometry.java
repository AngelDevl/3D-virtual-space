package geometries;

abstract public class RadialGeometry implements Geometry {

    RadialGeometry(double radius) {
        this.radius = radius;
    }

    protected final double radius;
}