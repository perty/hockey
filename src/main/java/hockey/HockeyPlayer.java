package hockey;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.EllipseBuilder;

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

    public HockeyPlayer() {
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
    }

    public void skateTo(double x, double y) {
        this.setTranslateX(x);
        this.setTranslateY(y);
    }
}
