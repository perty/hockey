package hockey;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;
import util.ArithmeticPoint;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-04
 * Time: 19:53
 */
public class HockeyPlayer extends Group {
    static final double meter = Hockey.meter;
    static final double depth = 0.3 * meter;
    static final double width = 1.0 * meter;
    static final double speed = 10 * meter;

    public HockeyPlayer(ArithmeticPoint startPoint, Color teamColor) {
        Ellipse body = EllipseBuilder.create()
                .fill(teamColor)
                .radiusX(depth)
                .radiusY(width)
                .build();
        getChildren().add(body);
        Circle nose = CircleBuilder.create()
                .fill(Color.BROWN)
                .radius(depth * 0.5)
                .centerX(depth)
                .build();
        getChildren().add(nose);
        Circle helmet = CircleBuilder.create()
                .fill(Color.WHITESMOKE)
                .radius(depth)
                .build();
        getChildren().add(helmet);
        this.setTranslateX(startPoint.getX());
        this.setTranslateY(startPoint.getY());
    }

    public void skateTo(ArithmeticPoint goalPoint) {
        double x0 = getTranslateX();
        double y0 = getTranslateY();
        double x1 = goalPoint.getX();
        double y1 = goalPoint.getY();
        Path path = PathBuilder.create()
                .elements(
                        new MoveTo(x0, y0),
                        new LineTo(x1, y1)
                )
                .build();

        Duration duration = durationFromSpeedAndDistance(Point.distance(x0, y0, x1, y1));
        PathTransition pathTransition = PathTransitionBuilder.create()
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .duration(duration)
                .build();
        pathTransition.play();
    }

    private Duration durationFromSpeedAndDistance(double distance) {
        double time = distance / this.speed;
        return Duration.seconds(time);
    }

}
