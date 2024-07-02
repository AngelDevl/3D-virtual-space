package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * AmbientLight class
 */
public class AmbientLight extends Light {

    /** Default AmbientLight - Black color */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0d);


    /**
     * AmbientLight Constructor to initialize the intensity
     * @param ia Fill color
     * @param ka Fill light factor
     */
    public AmbientLight(Color ia, Double3 ka) {
        super(ia.scale(ka));
    }

    /**
     * AmbientLight Constructor to initialize the intensity
     * @param ia Fill color
     * @param ka Fill light factor
     */
    public AmbientLight(Color ia, double ka) {
        super(ia.scale(ka));
    }
}