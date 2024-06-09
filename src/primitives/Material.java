package primitives;

/**
 * class representing a Material
 */
public class Material {

    /**
     * Setter for kD using double type
     * @param kD new double value
     * @return this - current object
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Setter for kS using double type
     * @param kS new double value
     * @return this - current object
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Setter for kT using double type
     * @param kT new double value
     * @return this - current object
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Setter for kR using double type
     * @param kR new double value
     * @return this - current object
     */
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Setter for kD
     * @param kD new Double3 value
     * @return this - current object
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * Setter for kS
     * @param kS new Double3 value
     * @return this - current object
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Setter for kT
     * @param kT new Double3 value
     * @return this - current object
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Setter for kR
     * @param kR new Double3 value
     * @return this - current object
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Setter for Shininess
     * @param nShininess level of shininess
     * @return this - current object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /** Diffusive coefficient */
    public Double3 kD = Double3.ZERO;

    /** Specular coefficient */
    public Double3 kS = Double3.ZERO;

    /** transparency coefficient */
    public Double3 kT = Double3.ZERO;

    /** reflection coefficient */
    public Double3 kR = Double3.ZERO;

    //hw page says init at 0, presentation says 1
    /** represents how concentrated the shininess is*/
    public int nShininess = 1;
}
