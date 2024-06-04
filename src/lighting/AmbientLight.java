package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {

    /**
     * AmbientLight Constructor to initialize the intensity
     * @param ia Fill color
     * @param ka Fill light factor
     */
    public AmbientLight(Color ia, Double3 ka) {
        intensity = ia.scale(ka);
    }

    /**
     * AmbientLight Constructor to initialize the intensity
     * @param ia Fill color
     * @param ka Fill light factor
     */
    AmbientLight(Color ia, double ka) {
        intensity = ia.scale(ka);
    }


    /**
     * Getter for intensity
     * @return intensity
     */
    public Color getIntensity() { return intensity; }

    /** Intensity of the light source */
    private final Color intensity;

    /** Default AmbientLight - Black color */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0d);
}