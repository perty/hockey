package hockey;

import javafx.animation.PathTransition;
import javafx.animation.PathTransitionBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
    private BooleanProperty selectedState = new SimpleBooleanProperty(false);
    private PathTransition transition;

    public HockeyPlayer(ArithmeticPoint startPoint, Color teamColor) {
        getChildren().add(createBody(teamColor));
        getChildren().add(createNose());
        getChildren().add(createHelmet());
        getChildren().add(createSelectionCircle());
        this.setTranslateX(startPoint.getX());
        this.setTranslateY(startPoint.getY());
        setOnMouseClicked(beSelected());
    }

    private Node createSelectionCircle() {
        Circle circle = CircleBuilder.create()
                .radius(width * 1.2)
                .fill(Color.TRANSPARENT)
                .stroke(Color.YELLOWGREEN)
                .build();
        circle.visibleProperty().bind(selectedState);
        return circle;

    }

    private EventHandler<? super MouseEvent> beSelected() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Hockey.select(HockeyPlayer.this);
                mouseEvent.consume();
            }
        };
    }

    private Circle createHelmet() {
        return CircleBuilder.create()
                .fill(Color.WHITESMOKE)
                .radius(depth)
                .build();
    }

    private Circle createNose() {
        return CircleBuilder.create()
                .fill(Color.BROWN)
                .radius(depth * 0.5)
                .centerX(depth)
                .build();
    }

    private Ellipse createBody(Color teamColor) {
        return EllipseBuilder.create()
                .fill(teamColor)
                .radiusX(depth)
                .radiusY(width)
                .build();
    }

    public void queueSkateToCommand(ArithmeticPoint goalPoint) {
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
        transition = PathTransitionBuilder.create()
                .path(path)
                .node(this)
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .duration(duration)
                .onFinished(finishedAction())
                .build();
    }

    private EventHandler<ActionEvent> finishedAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                transition = null;
            }
        };
    }

    public void act() {
        if(transition != null) {
            transition.play();
        }
    }

    public void faceTowardsPoint(ArithmeticPoint focusPoint) {
        double x0 = getTranslateX();
        double y0 = getTranslateY();
        this.setRotate(new ArithmeticPoint(x0, y0).angleTo(focusPoint));
    }

    private Duration durationFromSpeedAndDistance(double distance) {
        double time = distance / speed;
        return Duration.seconds(time);
    }

    public void setSelected(boolean selected) {
        this.selectedState.setValue(selected);
    }
}
