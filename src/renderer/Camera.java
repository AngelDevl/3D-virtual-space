package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * Class for camera - used for rendering the image
 */
public class Camera implements Cloneable {

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

    // ImageWriter object
    ImageWriter imageWriter;

    // RayTracerBase object
    RayTracerBase rayTracer;


    private Camera() {

    }

    @Override
    public Camera clone() {
        try {
            return (Camera) super.clone();

        } catch (CloneNotSupportedException exp) {
            throw new AssertionError();
        }
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

    /**
     * Looping over all the pixels and using castRay function on every pixel to create a ray to the pixel to find the color
     * and write the color to the pixel
     */
    public void renderImage() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                castRay(nX, nY, i, j);
            }
        }
    }

    /**
     * construct a ray in the given row and column and find the color of the pixel using traceRay.
     * after we find the color we write the color to the right pixel
     * @param Nx width
     * @param Ny height
     * @param i row of the pixel
     * @param j column of the pixel
     */
    private void castRay(int Nx, int Ny, int i, int j) {
        Ray ray = constructRay(Nx, Ny, i, j);
        imageWriter.writePixel(i, j, rayTracer.traceRay(ray));
    }


    /**
     * * This is much faster than the imageWriterTest since we are not running on all the pixels
     * printGrid function to print grid around the image objects using interval
     * @param interval interval for when to draw the grid color
     * @param color the color that we would draw the grid pixels
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null) throw new MissingResourceException("Missing image writer.", "", "");

        for (int i = 0; i < imageWriter.getNx(); i += interval) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(i, j, color);
            }
        }

        for (int j = 0; j < imageWriter.getNy(); j += interval) {
            for (int i = 0; i < imageWriter.getNx(); i++) {
                imageWriter.writePixel(i, j, color);
            }
        }
    }

    /**
     * Calling the imageWriter writeToImage function
     */
    public void writeToImage() {
        this.imageWriter.writeToImage();
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

    /**
     * Getter for Builder object
     * @return A new Builder object
     */
    public static Builder getBuilder() { return new Builder(); }

    /**
     * nested builder class to build the camera
     */
    public static class Builder {

        /**
         * This function set new camera position with rotation (transformation)
         *
         * @param location - the new position
         * @param target      - the target point
         * @param angle       - the angle of rotation
         * @return the camera after set the new position
         */
        public Builder transform(Point location, Point target, double angle) {
            camera.location = location;
            camera.vecTo = target.subtract(location).normalize();

            try {
                camera.vecRight = camera.vecTo.crossProduct(Vector.y).normalize();
                camera.vecUp = camera.vecTo.crossProduct(camera.vecRight).normalize();

            } catch (IllegalArgumentException e) {
                camera.vecUp = Vector.z;
                camera.vecRight = camera.vecTo.crossProduct(camera.vecUp).normalize();
            }

            return angle == 0 ? this : rotateCamera(angle);
        }

        /**
         * This function rotate the camera by a given angle by updating the vecTo
         *
         * @param angle - the angle of rotation
         * @return the camera after set the new position
         */
        public Builder rotateCamera(double angle) {
            camera.vecUp = camera.vecUp.vectorRotate(camera.vecTo, Math.toRadians(angle));
            camera.vecRight = camera.vecUp.crossProduct(camera.vecTo).normalize();
            return this;
        }


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
         * Sets the ImageWriter for the camera
         * @param imageWriter ImageWriter object
         * @return the updated this object
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Sets the ray tracer
         * @param rayTracer RayTracerBase object
         * @return the updated this object
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
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
            if (camera.imageWriter == null) throw new MissingResourceException(errorPrefix, className, "imageWriter" + errorSuffix);
            if (camera.rayTracer == null) throw new MissingResourceException(errorPrefix, className, "rayTracer" + errorSuffix);

            camera.vecRight = (camera.vecTo.crossProduct(camera.vecUp)).normalize();
            camera.viewPlaneCenter = camera.location.add(camera.vecTo.scale(camera.distance));

            return (Camera) camera.clone();
        }

        final private Camera camera = new Camera();
    }
}