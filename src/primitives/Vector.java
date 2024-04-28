package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);

        if (x == 0 && y == 0 && z == 0) throw new IllegalArgumentException("Cannot accept a zero vector");
    }

    public Vector(Double3 coordinates) {
        super(coordinates);

        if (coordinates.equals(Double3.ZERO)) throw new IllegalArgumentException("Cannot accept a zero vector");
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return dotProduct(this);
    }

    public Vector add(Vector vec) {
        return new Vector(xyz.add(vec.xyz));
    }

    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    public double dotProduct(Vector vec) {
        return xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3;
    }

    public Vector crossProduct(Vector v) {
        return new Vector(
                (xyz.d2 * v.xyz.d3) - (xyz.d3 * v.xyz.d2),
                (xyz.d3 * v.xyz.d1) - (xyz.d1 * v.xyz.d3),
                (xyz.d1 * v.xyz.d2) - (xyz.d2 * v.xyz.d1)
        );
    }

    public Vector normalize() {
        double vectorLength = length();
        if (vectorLength == 0) {
            // Would never happen cause constructor would not accept a zero vector
            throw new IllegalArgumentException("Cannot accept a zero length vector");
        }

        return new Vector(xyz.d1 / vectorLength, xyz.d2 / vectorLength, xyz.d3 / vectorLength);
    }

    @Override public String toString() { return " " + super.toString(); }

    @Override public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override public int hashCode() { return super.hashCode(); }
}
