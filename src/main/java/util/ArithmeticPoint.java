package util;

import javafx.geometry.Point2D;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-06
 * Time: 16:04
 */
public class ArithmeticPoint extends Point2D {
    public ArithmeticPoint(double x, double y) {
        super(x, y);
    }

    public ArithmeticPoint add(ArithmeticPoint other) {
        return new ArithmeticPoint(this.getX() + other.getX(), this.getY() + other.getY());
    }

    public double angleTo(ArithmeticPoint focusPoint) {
        return ((Math.atan2(getY() - focusPoint.getY(), getX() - focusPoint.getX()) * 180 / Math.PI) + 180) % 360;
    }
}
