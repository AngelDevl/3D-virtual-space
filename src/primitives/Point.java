package primitives;

/**
 * Point class represent a point in a 3D coordinate system
 */
public class Point {

    /**
     * Point constructor to initialize a new Point object using 3 doubles
     * @param x double that represent the x value in a point
     * @param y double that represent the y value in a point
     * @param z double that represent the z value in a point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    /**
     * Point constructor to initialize a new Point object with Double3
     * @param xyz Double3 object that represent an x, y, z coordinate
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Add function that takes a vector (uses the point behind the vector class)
     * and add it to a point by adding their corresponding coordinates (using Double3 add function)
     * @param vec the vector that's being added to the point
     * @return A new Point object of the result point
     */
    public Point add(Vector vec) {
        return new Point(xyz.add(vec.xyz));
    }

    /**
     * Subtract function that takes a point and subtract with other point by using their corresponding coordinates
     * (using Double3 subtract function)
     * @param p the point that's being subtracted
     * @return A new Vector object of the result point
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * distanceSquared function that's calculate the distance between two points (using distanceSquared squared root)
     * @param p Point object that its distance has being measured from the current point
     * @return The distance between the two point (type: double)
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    /**
     * distanceSquared function that's calculate the distance between two points
     * @param p Point object that its distance has being measured from the current point
     * @return The squared distance between the two point (type: double)
     */
    public double distanceSquared(Point p) {
        double dx = xyz.d1 - p.xyz.d1;
        double dy = xyz.d2 - p.xyz.d2;
        double dz = xyz.d3 - p.xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    @Override public String toString() { return " " + xyz; }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        return (obj instanceof Point other) && xyz.equals(other.xyz);
    }

    @Override public int hashCode() { return xyz.hashCode(); }

    /**
     * A Point object that represent a point with all zero coordinates
     */
    public static final Point ZERO = new Point(0, 0, 0);

    /**
     * A Double3 object that represent our point has 3 doubles with x, y, z coordinates
     */
    protected final Double3 xyz;

    public double getX() {
        return xyz.d1;
    }
}
