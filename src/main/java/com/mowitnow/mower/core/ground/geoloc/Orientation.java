package com.mowitnow.mower.core.ground.geoloc;

/**
 * Didn't implement an enum to enable any angle possibility without implementing new values.<br>
 * Remarkable values are required only to read / write an Orientation instance.
 * <p/>
 * Created by ffradet on 18/10/2014.
 */
public class Orientation {

    public static final Orientation DEG_0 = new Orientation(Math.toRadians(0));
    public static final Orientation DEG_90 = new Orientation(Math.toRadians(90));
    public static final Orientation DEG_180 = new Orientation(Math.toRadians(180));
    public static final Orientation DEG_270 = new Orientation(Math.toRadians(270));

    private static final Orientation[] ALL_VALUES = {DEG_0, DEG_90, DEG_180, DEG_270};

    private static final double HALF_TURN = Math.PI;
    private static final double ONE_TURN = Math.PI * 2;

    private double angleRad;

    /**
     * @param angleRad
     */
    public Orientation(double angleRad) {
        this.angleRad = normalize(angleRad);
    }

    /**
     * @param other
     */
    public Orientation(Orientation other) {
        this(other.getAngleRad());
    }

    /**
     * Keep angleRad in range [0 ; ONE_TURN[
     *
     * @param angleRad
     * @return
     */
    private static double normalize(double angleRad) {
        if (angleRad < 0) {
            int nbTurns = (int) (Math.abs(angleRad) / ONE_TURN);
            angleRad += ONE_TURN * (nbTurns + 1);
        }

        if (angleRad >= ONE_TURN) {
            angleRad %= ONE_TURN;
        }

        return angleRad;
    }

    /**
     * @return
     */
    public double getAngleRad() {
        return angleRad;
    }

    /**
     * @param angleRelative
     * @return a new instance of 'this' rotated by angleRelative radians
     */
    public Orientation rotate(Orientation angleRelative) {
        return new Orientation(angleRad + angleRelative.getAngleRad());
    }

    /**
     * @return the nearest predefined constant value (ie. DEG_xxx)<br>
     * null if angleRad is NaN.
     */
    public Orientation getNearest() {
        double minDistance = Double.MAX_VALUE;
        Orientation minDistanceInstance = null;

        for (Orientation o : ALL_VALUES) {
            double distance = getAngleRad() - o.getAngleRad();
            distance = normalize(distance);

            // NEGATIVE_INFINITY
            if (distance < 0) {
                continue;
            }

            if (distance > HALF_TURN) {
                // Angle distance must be [0 ; HALF_TURN]
                distance = ONE_TURN - distance;
            }

            if (distance < minDistance) {
                minDistance = distance;
                minDistanceInstance = o;
            }
        }

        return minDistanceInstance;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Orientation{");
        sb.append("angleRad=").append(angleRad);
        sb.append('}');
        return sb.toString();
    }
}
