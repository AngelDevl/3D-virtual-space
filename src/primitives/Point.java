package primitives;

import java.util.Objects;

public class Point {

    public Point(double x, double y, double z) {
        xyz = new Double3(x,y,z);
    }

    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Point add(Vector vec) {
        return new Point(xyz.add(vec.xyz));
    }

    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

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

    public static final Point ZERO = new Point(0, 0, 0);
    protected final Double3 xyz;
}
