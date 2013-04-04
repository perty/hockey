package hockey;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Hockey extends Application {

    private static final double meter = 15;
    static final double centerSpotRadius = 0.3 * meter;
    static final double faceOffSpotRadius = 0.3 * meter;
    static final double centerCircleRadius = 4.5 * meter;
    static final double mainLineWidth = 0.3 * meter;
    static final double rinkWidth = 30 * meter;
    static final double rinkLength = 60 * meter;
    static final double goalLineInset = 4 * meter;
    static final double goalLineDistance = rinkLength - 2 * goalLineInset;
    static final double circleStrokeWidth = 0.05 * meter;
    static final double goalLineWidth = circleStrokeWidth;
    static final double goalCreaseRadius = 1.8 * meter;
    static final double zoneLength = goalLineDistance / 3;

    DoubleProperty scale = new SimpleDoubleProperty(1.0);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hockey!");
        BorderPane root = new BorderPane();
        root.setCenter(createRink());
        root.setRight(createZoomControl());
        primaryStage.setScene(new Scene(root, 100 * meter, 50 * meter));
        primaryStage.show();
    }

    private Node createZoomControl() {
        Slider slider = SliderBuilder.create()
                .orientation(Orientation.VERTICAL)
                .min(1.0)
                .max(10.0)
                .build();
        scale.bind(slider.valueProperty());
        return slider;

    }

    private Node createRink() {
        Rectangle ice = RectangleBuilder.create()
                .width(rinkLength)
                .height(rinkWidth)
                .arcHeight(8.5 * meter)
                .arcWidth(8.5 * meter)
                .stroke(Color.BLACK)
                .strokeWidth(0.1 * meter)
                .strokeType(StrokeType.OUTSIDE)
                .fill(Color.WHITESMOKE)
                .build();
        Line centerLine = createLineAcross(rinkLength / 2, Color.RED, mainLineWidth);
        Circle centerCircle = CircleBuilder.create()
                .centerX(rinkLength / 2)
                .centerY(rinkWidth / 2)
                .radius(centerCircleRadius)
                .fill(Color.TRANSPARENT)
                .stroke(Color.BLUE)
                .strokeWidth(circleStrokeWidth)
                .build();
        Circle centerSpot = CircleBuilder.create()
                .centerX(rinkLength / 2)
                .centerY(rinkWidth / 2)
                .radius(centerSpotRadius)
                .fill(Color.BLUE)
                .build();
        Line westBlueLine = createLineAcross(goalLineInset + zoneLength, Color.BLUE, mainLineWidth);
        Line eastBlueLine = createLineAcross(goalLineInset + zoneLength * 2, Color.BLUE, mainLineWidth);
        Line westGoalLine = createLineAcross(goalLineInset, Color.RED, goalLineWidth);
        Line eastGoalLine = createLineAcross(rinkLength - goalLineInset, Color.RED, goalLineWidth);

        Group nwFaceOff = createFaceOff(10 * meter, 8 * meter);
        Group neFaceOff = createFaceOff(50 * meter, 8 * meter);
        Group swFaceOff = createFaceOff(10 * meter, 22 * meter);
        Group seFaceOff = createFaceOff(50 * meter, 22 * meter);

        Node faceOffSpotNW = createFaceOffSpot(goalLineInset + zoneLength + 1.5 * meter, 8 * meter);
        Node faceOffSpotNE = createFaceOffSpot(goalLineInset + zoneLength * 2 - 1.5 * meter, 8 * meter);
        Node faceOffSpotSW = createFaceOffSpot(goalLineInset + zoneLength + 1.5 * meter, 22 * meter);
        Node faceOffSpotSE = createFaceOffSpot(goalLineInset + zoneLength * 2 - 1.5 * meter, 22 * meter);

        Node goalCreaseWest = createGoalCrease(goalLineInset, 270);
        Node goalCreaseEast = createGoalCrease(rinkLength - goalLineInset, 90);

        Scale scaleTransform = Transform.scale(1.0, 1.0);
        scaleTransform.xProperty().bind(scale);
        scaleTransform.yProperty().bind(scale);
        return GroupBuilder.create()
                .children(
                        ice,
                        centerLine,
                        centerSpot,
                        centerCircle,
                        westBlueLine,
                        eastBlueLine,
                        westGoalLine,
                        eastGoalLine,
                        nwFaceOff,
                        neFaceOff,
                        swFaceOff,
                        seFaceOff,
                        faceOffSpotNW,
                        faceOffSpotNE,
                        faceOffSpotSW,
                        faceOffSpotSE,
                        goalCreaseWest,
                        goalCreaseEast
                )
                .transforms(
                        scaleTransform
                )
                .build();
    }

    private Node createGoalCrease(double offsetX, int startAngle) {
        return ArcBuilder.create()
                    .centerX(offsetX)
                    .centerY(rinkWidth / 2)
                    .radiusX(goalCreaseRadius)
                    .radiusY(goalCreaseRadius)
                    .startAngle(startAngle)
                    .length(180)
                    .fill(Color.TRANSPARENT)
                    .stroke(Color.RED)
                    .strokeWidth(goalLineWidth)
                    .build();
    }

    private Group createFaceOff(double offsetX, double offsetY) {
        Circle faceOffSpot = createFaceOffSpot(offsetX, offsetY);
        Circle faceOffCircle = CircleBuilder.create()
                .centerX(offsetX)
                .centerY(offsetY)
                .radius(centerCircleRadius)
                .fill(Color.TRANSPARENT)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffEdgeNorthLeft = LineBuilder.create()
                .startX(offsetX - (1.7 * meter / 2))
                .endX(offsetX - (1.7 * meter / 2))
                .startY(offsetY - centerCircleRadius)
                .endY(offsetY - centerCircleRadius - 0.6 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffEdgeNorthRight = LineBuilder.create()
                .startX(offsetX + (1.7 * meter / 2))
                .endX(offsetX + (1.7 * meter / 2))
                .startY(offsetY - centerCircleRadius)
                .endY(offsetY - centerCircleRadius - 0.6 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffEdgeSouthLeft = LineBuilder.create()
                .startX(offsetX - (1.7 * meter / 2))
                .endX(offsetX - (1.7 * meter / 2))
                .startY(offsetY + centerCircleRadius)
                .endY(offsetY + centerCircleRadius + 0.6 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffEdgeSouthRight = LineBuilder.create()
                .startX(offsetX + (1.7 * meter / 2))
                .endX(offsetX + (1.7 * meter / 2))
                .startY(offsetY + centerCircleRadius)
                .endY(offsetY + centerCircleRadius + 0.6 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffNorthVerticalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * meter)
                .endX(offsetX - 0.6 * meter)
                .startY(offsetY - 1.125 * meter)
                .endY(offsetY - 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffNorthHorizontalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * meter)
                .endX(offsetX - 0.6 * meter - 1.2 * meter)
                .startY(offsetY - 0.225 * meter)
                .endY(offsetY - 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffNorthVerticalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * meter)
                .endX(offsetX + 0.6 * meter)
                .startY(offsetY - 1.125 * meter)
                .endY(offsetY - 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffNorthHorizontalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * meter)
                .endX(offsetX + 0.6 * meter + 1.2 * meter)
                .startY(offsetY - 0.225 * meter)
                .endY(offsetY - 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffSouthVerticalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * meter)
                .endX(offsetX - 0.6 * meter)
                .startY(offsetY + 1.125 * meter)
                .endY(offsetY + 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffSouthHorizontalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * meter)
                .endX(offsetX - 0.6 * meter - 1.2 * meter)
                .startY(offsetY + 0.225 * meter)
                .endY(offsetY + 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffSouthVerticalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * meter)
                .endX(offsetX + 0.6 * meter)
                .startY(offsetY + 1.125 * meter)
                .endY(offsetY + 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        Line faceOffSouthHorizontalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * meter)
                .endX(offsetX + 0.6 * meter + 1.2 * meter)
                .startY(offsetY + 0.225 * meter)
                .endY(offsetY + 0.225 * meter)
                .stroke(Color.RED)
                .strokeWidth(circleStrokeWidth)
                .build();
        return GroupBuilder.create()
                .children(
                        faceOffCircle,
                        faceOffSpot,
                        faceOffEdgeNorthLeft,
                        faceOffEdgeNorthRight,
                        faceOffEdgeSouthLeft,
                        faceOffEdgeSouthRight,
                        faceOffNorthVerticalLeft,
                        faceOffNorthHorizontalLeft,
                        faceOffNorthHorizontalRight,
                        faceOffNorthVerticalRight,
                        faceOffSouthVerticalLeft,
                        faceOffSouthHorizontalLeft,
                        faceOffSouthVerticalRight,
                        faceOffSouthHorizontalRight
                )
                .build();
    }

    private Circle createFaceOffSpot(double offsetX, double offsetY) {
        return CircleBuilder.create()
                    .centerX(offsetX)
                    .centerY(offsetY)
                    .radius(faceOffSpotRadius)
                    .fill(Color.RED)
                    .build();
    }

    private Line createLineAcross(double offset, Color color, double lineWidth) {
        return LineBuilder.create()
                .startX(offset)
                .endX(offset)
                .startY(0 + lineWidth / 2)
                .endY(rinkWidth - lineWidth / 2)
                .stroke(color)
                .strokeWidth(lineWidth)
                .strokeLineCap(StrokeLineCap.SQUARE)
                .build();
    }

}