package lighting;

import primitives.Color;

/**
 * Light class - to represent light
 */
abstract class Light {

    /**
     * Constructor to initialize the intensity
     * @param intensity new intensity
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter for intensity
     * @return intensity
     */
    public Color getIntensity() { return intensity; }

    /** Intensity of the light source */
    protected Color intensity;
}
