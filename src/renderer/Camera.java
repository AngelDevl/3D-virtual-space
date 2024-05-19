package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingFormatArgumentException;
import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Camera implements Cloneable {

    private Camera() {

    }

    @Override public Camera clone() {
        try {
            return (Camera) super.clone();

        } catch (CloneNotSupportedException exp) {
            throw new AssertionError();
        }
    }


    public static class Builder {

        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        public Builder setDirection(Vector vecTo, Vector vecUp) {
            if (isZero(alignZero(vecUp.dotProduct(vecTo)))) {
                throw new IllegalArgumentException("Vectors mush be orthogonal");
            }

            camera.vecTo = vecTo.normalize();
            camera.vecUp = vecUp.normalize();

            return this;
        }

        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;

            return this;
        }

        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        public Camera build() {
            final String errorPrefix = "Render data in missing";
            final String errorSuffix = " is not initialized.";
            final String className = "Camera";

            if (isZero(camera.height)) throw new MissingResourceException(errorPrefix, className, "height" + errorSuffix);
            if (isZero(camera.width)) throw new MissingResourceException(errorPrefix, className, "width" + errorSuffix);
            if (isZero(camera.distance)) throw new MissingResourceException(errorPrefix, className, "distance" + errorSuffix);

            camera.vecRight = (camera.vecTo.crossProduct(camera.vecUp)).normalize();

            return (Camera) camera.clone();
        }

        final private Camera camera = new Camera();
    }


    public static Builder getBuilder() {
        return null;
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }


    public Point getLocation() { return location; }

    public Vector getVecUp() { return vecUp; }
    public Vector getVecRight() { return vecRight; }
    public Vector getVecTo() { return vecTo; }

    public double getHeight() { return height; }
    public double getDistance() { return distance; }
    public double getWidth() { return width; }


    private Point location;

    private Vector vecUp, vecRight, vecTo;

    /** height of view plane*/
    private double height = 0;
    /** distance of camera from view plane*/
    private double distance = 0;
    /** width of view plane*/
    private double width = 0;
}
