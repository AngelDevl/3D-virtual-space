package primitives;

/**
 * Vector class that's represent a vector in 3D coordinate system using a Point object that has 3 coordinates
 */
public class Vector extends Point {

    /**
     * For camera rotation bonus - (for rotation by given vector that represent the axis)
     */
    public static final Vector x = new Vector(1, 0, 0);
    public static final Vector y = new Vector(0, 1, 0);
    public static final Vector z = new Vector(0, 0, 1);

    /**
     * Constructor to initialize a new Vector with 3 doubles
     * Throws an IllegalArgumentException if vector zero is passed
     * @param x double type that becomes the x coordinate
     * @param y double type that becomes the y coordinate
     * @param z double type that becomes the z coordinate
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);

        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Cannot accept a zero vector");
    }


    /**
     * Constructor to initialize a new Vector using Double3 object that represents 3 coordinates
     * Throws an IllegalArgumentException if vector zero is passed
     * @param xyz Double3 type object that represent 3 coordinates
     */
    public Vector(Double3 xyz) {
        super(xyz);

        if (xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("Cannot accept a zero vector");
    }

    /**
     * length function calculates the length of the current vector using squared root of lengthSquared function
     * @return The length of the current vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * lengthSquared function calculates the length squared of the current vector using dotProduct function
     * @return The length squared of the current vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Add function adding a vector to the current vector by adding their Point object with the corresponding coordinates (using Double3 add function)
     * @param vec the vector that's being added to the vector
     * @return A new Vector object of the result
     */
    public Vector add(Vector vec) {
        return new Vector(xyz.add(vec.xyz));
    }

    /**
     * scale function multiply each Vector point coordinate by a scalar
     * @param scalar of type double that we use to multiply the vector with
     * @return A new vector of the scale result
     */
    public Vector scale(double scalar) {
        return new Vector(xyz.scale(scalar));
    }

    /**
     * dotProduct function preform a dot product between two vectors (current vector + given vector)
     * @param vec the vector that we preform dot product with the current vector
     * @return A double of the dot product result
     */
    public double dotProduct(Vector vec) {
        return xyz.d1 * vec.xyz.d1 + xyz.d2 * vec.xyz.d2 + xyz.d3 * vec.xyz.d3;
    }

    /**
     * crossProduct function preform a cross product between two vectors (current vector + given vector)
     * @param vec the vector that we preform cross product with the current vector
     * @return A new Vector of the cross product result
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(
                (xyz.d2 * vec.xyz.d3) - (xyz.d3 * vec.xyz.d2),
                (xyz.d3 * vec.xyz.d1) - (xyz.d1 * vec.xyz.d3),
                (xyz.d1 * vec.xyz.d2) - (xyz.d2 * vec.xyz.d1)
        );
    }

    /**
     * normalize function normalize (make the vector length to 1) the current vector
     * @return A new normalize Vector object
     */
    public Vector normalize() {
        double vectorLength = length();
        return new Vector(xyz.d1 / vectorLength, xyz.d2 / vectorLength, xyz.d3 / vectorLength);
    }


    /**
     * The function construct new vector after rotation calculating
     * with consideration of axis vector and rotation angle.
     * using this formula: <a href="https://en.wikipedia.org/wiki/Rodrigues%27_rotation_formula">...</a>
     * @param axis     of rotation
     * @param thetaRad of rotation in radians
     * @return new rotated vector
     */
    public Vector vectorRotate(Vector axis, double thetaRad) {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;

        double u = axis.xyz.d1;
        double v = axis.xyz.d2;
        double w = axis.xyz.d3;

        double v1 = u * x + v * y + w * z;

        double xPrime = u * v1 * (1d - Math.cos(thetaRad))
                + x * Math.cos(thetaRad)
                + (-w * y + v * z) * Math.sin(thetaRad);

        double yPrime = v * v1 * (1d - Math.cos(thetaRad))
                + y * Math.cos(thetaRad)
                + (w * x - u * z) * Math.sin(thetaRad);

        double zPrime = w * v1 * (1d - Math.cos(thetaRad))
                + z * Math.cos(thetaRad)
                + (-v * x + u * y) * Math.sin(thetaRad);

        return new Vector(xPrime, yPrime, zPrime);
    }

    @Override
    public String toString() { return " " + super.toString(); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null) return false;

        return (obj instanceof Vector other) && super.equals(other);
    }

    @Override
    public int hashCode() { return super.hashCode(); }
}
