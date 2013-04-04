package hockey;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

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

    public HockeyPlayer(double startX, double startY) {
        Ellipse body = EllipseBuilder.create()
                .fill(Color.GREEN)
                .radiusX(width)
                .radiusY(depth)
                .build();
        getChildren().add(body);
        Circle nose = CircleBuilder.create()
                .fill(Color.BROWN)
                .radius(depth * 0.5)
                .centerY(depth)
                .build();
        getChildren().add(nose);
        Circle helmet = CircleBuilder.create()
                .fill(Color.WHITESMOKE)
                .radius(depth)
                .build();
        getChildren().add(helmet);
        this.setTranslateX(startX);
        this.setTranslateY(startY);
    }

    public void skateTo(double x, double y) {
        Path path = PathBuilder.create()
                .elements(new MoveTo(getTranslateX(),getTranslateY()), new LineTo(x,y))
                .build();
        PathTransition pathTransition = PathTransitionBuilder.create()
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .duration(Duration.millis(10000))
                .build();
        pathTransition.play();
        /*double angle = angleToGoal(x, y);
        this.setRotate(angle);
        TranslateTransition transition = TranslateTransitionBuilder.create()
                .node(this)
                .toX(x)
                .toY(y)
                .duration(Duration.millis(10000))
                .build();
        transition.play();*/
    }

    private double angleToGoal(double x, double y) {
        double x1 = getTranslateX();
        double y1 = getTranslateY();

        return (Math.atan2(y1, x1) - Math.atan2(y, x)) * 180 / Math.PI;
    }
}
