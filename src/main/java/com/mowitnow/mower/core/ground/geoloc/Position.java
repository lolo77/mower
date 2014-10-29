package com.mowitnow.mower.core.ground.geoloc;

import java.util.Comparator;

/**
 * Created by ffradet on 17/10/2014.
 */
public class Position {

    public static final Position ORIGIN = new Position();

    // Axis coordinates
    private double x;
    private double y;
    // ...z ? Could mow in three dimensions, i.e. on veg walls too ;-)


    public Position() {
        this(0, 0);
    }

    /**
     * @param other
     */
    public Position(Position other) {
        this(other.getX(), other.getY());
    }

    /**
     * @param x
     * @param y
     */
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /*
     * Creation
     */

    /**
     * @param angleRadians the theta
     * @param radius       the rho
     * @return
     */
    public static Position fromPolar(double angleRadians, double radius) {
        double newX = Math.cos(angleRadians) * radius;
        double newY = Math.sin(angleRadians) * radius;
        return new Position(newX, newY);
    }

    /*
     *  Accessors
     */

    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }


    /*
     * Arithmetic operators implementation
     */

    /**
     * Adds this to other.
     *
     * @param other
     * @return the addition resultant vector
     */
    public Position add(Position other) {
        return new Position(getX() + other.getX(), getY() + other.getY());
    }

    /**
     * @param other
     * @return true if at least one coordinate of 'this' is lower than its respective 'other' one.
     */
    public boolean isLowerThan(Position other) {
        return (getX() < other.getX()) || (getY() < other.getY());
    }

    /**
     * @param other
     * @return true if at least one coordinate of 'this' is greater than its respective 'other' one.
     */
    public boolean isGreaterThan(Position other) {
        return (getX() > other.getX()) || (getY() > other.getY());
    }

    /**
     * @param pos
     * @return
     */
    public Position min(Position pos) {
        return new Position(Math.min(getX(), pos.getX()), Math.min(getY(), pos.getY()));
    }

    /**
     * @param pos
     * @return
     */
    public Position max(Position pos) {
        return new Position(Math.max(getX(), pos.getX()), Math.max(getY(), pos.getY()));
    }

    /**
     * Be careful with this function<br>
     * since 1.0 is not equal to (0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1 + 0.1)
     *
     * @param other
     * @return true if all the coordinates are the same between 'this' and 'other'.
     */
    public boolean isEqualsTo(Position other) {
        return equals(other);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (Double.compare(position.x, x) != 0) return false;
        if (Double.compare(position.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Position{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append('}');
        return sb.toString();
    }

    /**
     *
     */
    public static class PositionComparator implements Comparator<Position> {

        @Override
        public int compare(Position o1, Position o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            }

            if (o1 == null) {
                return -1;
            }

            if (o2 == null) {
                return 1;
            }

            if (o1.getX() < o2.getX()) {
                return -1;
            }

            if (o1.getX() > o2.getX()) {
                return 1;
            }

            if (o1.getY() < o2.getY()) {
                return -1;
            }

            if (o1.getY() > o2.getY()) {
                return 1;
            }

            return 0;
        }

    }
}
