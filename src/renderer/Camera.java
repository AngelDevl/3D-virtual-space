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

        /**
         * Sets the location of the camera
         * @param location the new location of the camera
         * @return the updated this object
         */
        public Builder setLocation(Point location) {
            camera.location = location;
            return this;
        }

        /**
         * Setting the direction of vecTo and vecRight and ensure that they are orthogonal and normalized
         * @param vecTo a vector that points in the direction the camera looking
         * @param vecUp upward direction vector
         * @return the updated this object
         */
        public Builder setDirection(Vector vecTo, Vector vecUp) {
            if (!isZero(alignZero(vecUp.dotProduct(vecTo)))) {
                throw new IllegalArgumentException("Vectors mush be orthogonal");
            }

            camera.vecTo = vecTo.normalize();
            camera.vecUp = vecUp.normalize();

            return this;
        }

        /**
         * Sets the view plane size
         * @param width width of the view plane
         * @param height height of the view plane
         * @return the updated this object
         */
        public Builder setVpSize(double width, double height) {
            camera.width = width;
            camera.height = height;

            return this;
        }

        /**
         * Sets the view plane distance from the camera
         * @param distance the distance between the view plane and the camera
         * @return the updated this object
         */
        public Builder setVpDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        /**
         * build function initializing camera properties and making sure all the needed properties are initialized
         * @return A processed camera object
         */
        public Camera build() {
            final String errorPrefix = "Render data in missing";
            final String errorSuffix = " is not initialized.";
            final String className = "Camera";

            if (isZero(camera.height)) throw new MissingResourceException(errorPrefix, className, "height" + errorSuffix);
            if (isZero(camera.width)) throw new MissingResourceException(errorPrefix, className, "width" + errorSuffix);
            if (isZero(camera.distance)) throw new MissingResourceException(errorPrefix, className, "distance" + errorSuffix);

            camera.vecRight = (camera.vecTo.crossProduct(camera.vecUp)).normalize();
            camera.viewPlaneCenter = camera.location.add(camera.vecTo.scale(camera.distance));

            return (Camera) camera.clone();
        }

        final private Camera camera = new Camera();
    }


    /**
     * Getter for Builder object
     * @return A new Builder object
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * constructRay function construct a ray from the camera into the middle of a pixel
     * @param nX row length - define how much pixels are horizontally
     * @param nY column height - define how much pixels are vertically
     * @param j pixel index - column (x)
     * @param i pixel index - row (y)
     * @return A ray pointing to the middle of the pixel from the camera
     */
    public Ray constructRay(int nX, int nY, int j, int i) {

        double Rx = width / nX; // The width of a single pixel
        double Ry = height / nY; // The height of a single pixel

        double xj = (j - (nX - 1) / 2d) * Rx;
        double yi = - (i - (nY - 1) / 2d) * Ry;
        Point pij = viewPlaneCenter;

        if(!isZero(xj))
            pij = pij.add(vecRight.scale(xj));

        if(!isZero(yi))
            pij = pij.add(vecUp.scale(yi));

        return new Ray (location, pij.subtract(location));
    }

    /** @return current camera location*/
    public Point getLocation() { return location; }

    /** @return camera up direction vector*/
    public Vector getVecUp() { return vecUp; }

    /** @return camera right direction vector*/
    public Vector getVecRight() { return vecRight; }

    /** @return camera to direction vector*/
    public Vector getVecTo() { return vecTo; }

    /** @return camera height*/
    public double getHeight() { return height; }

    /** @return camera distance from the view plane*/
    public double getDistance() { return distance; }

    /** @return camera width*/
    public double getWidth() { return width; }


    /** Camera location */
    private Point location;

    /**
     * vecTo - points in the direction the camera looking
     * vecUp - defines which direction is "up" from the camera's perspective
     * vecRight - points to the right of the camera's view (to calculate - crossProduct of vecTo and vecUp)
     */
    private Vector vecUp, vecRight, vecTo;

    /** height of view plane*/
    private double height = 0;
    /** distance of camera from view plane*/
    private double distance = 0;
    /** width of view plane*/
    private double width = 0;

    /** Plane center point */
    private Point viewPlaneCenter;
}