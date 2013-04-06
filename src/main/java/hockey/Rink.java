package hockey;

import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import util.ArithmeticPoint;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-04
 * Time: 19:31
 */
public class Rink extends Group {
    static final double meter = Hockey.meter;
    static final double rinkLength = 60 * meter;
    static final double CENTER_SPOT_X = rinkLength / 2;
    static final double rinkWidth = 30 * meter;
    static final double CENTER_SPOT_Y = rinkWidth / 2;
    static final ArithmeticPoint centerSpot = new ArithmeticPoint(CENTER_SPOT_X, CENTER_SPOT_Y);
    static final double goalCreaseRadius = 1.8 * meter;
    static final double circleStrokeWidth = 0.05 * meter;
    static final double goalLineWidth = circleStrokeWidth;
    static final double goalLineInset = 4 * meter;
    static final double goalLineDistance = rinkLength - 2 * goalLineInset;
    static final double zoneLength = goalLineDistance / 3;
    static final double mainLineWidth = 0.3 * meter;
    static final double centerCircleRadius = 4.5 * meter;
    static final double faceOffSpotRadius = 0.3 * meter;
    static final double centerSpotRadius = 0.3 * meter;

    public Rink() {
        getChildren().add(createRink());
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
        Circle centerSpotMark = CircleBuilder.create()
                .centerX(CENTER_SPOT_X)
                .centerY(CENTER_SPOT_Y)
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

        return GroupBuilder.create()
                .children(
                        ice,
                        centerLine,
                        centerSpotMark,
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
                .build();
    }

    private Node createGoalCrease(double offsetX, int startAngle) {
        return ArcBuilder.create()
                .centerX(offsetX)
                .centerY(Rink.rinkWidth / 2)
                .radiusX(Rink.goalCreaseRadius)
                .radiusY(Rink.goalCreaseRadius)
                .startAngle(startAngle)
                .length(180)
                .fill(Color.TRANSPARENT)
                .stroke(Color.RED)
                .strokeWidth(Rink.goalLineWidth)
                .build();
    }

    private Group createFaceOff(double offsetX, double offsetY) {
        Circle faceOffSpot = createFaceOffSpot(offsetX, offsetY);
        Circle faceOffCircle = CircleBuilder.create()
                .centerX(offsetX)
                .centerY(offsetY)
                .radius(Rink.centerCircleRadius)
                .fill(Color.TRANSPARENT)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffEdgeNorthLeft = LineBuilder.create()
                .startX(offsetX - (1.7 * Rink.meter / 2))
                .endX(offsetX - (1.7 * Rink.meter / 2))
                .startY(offsetY - Rink.centerCircleRadius)
                .endY(offsetY - Rink.centerCircleRadius - 0.6 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffEdgeNorthRight = LineBuilder.create()
                .startX(offsetX + (1.7 * Rink.meter / 2))
                .endX(offsetX + (1.7 * Rink.meter / 2))
                .startY(offsetY - Rink.centerCircleRadius)
                .endY(offsetY - Rink.centerCircleRadius - 0.6 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffEdgeSouthLeft = LineBuilder.create()
                .startX(offsetX - (1.7 * Rink.meter / 2))
                .endX(offsetX - (1.7 * Rink.meter / 2))
                .startY(offsetY + Rink.centerCircleRadius)
                .endY(offsetY + Rink.centerCircleRadius + 0.6 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffEdgeSouthRight = LineBuilder.create()
                .startX(offsetX + (1.7 * Rink.meter / 2))
                .endX(offsetX + (1.7 * Rink.meter / 2))
                .startY(offsetY + Rink.centerCircleRadius)
                .endY(offsetY + Rink.centerCircleRadius + 0.6 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffNorthVerticalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * Rink.meter)
                .endX(offsetX - 0.6 * Rink.meter)
                .startY(offsetY - 1.125 * Rink.meter)
                .endY(offsetY - 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffNorthHorizontalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * Rink.meter)
                .endX(offsetX - 0.6 * Rink.meter - 1.2 * Rink.meter)
                .startY(offsetY - 0.225 * Rink.meter)
                .endY(offsetY - 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffNorthVerticalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * Rink.meter)
                .endX(offsetX + 0.6 * Rink.meter)
                .startY(offsetY - 1.125 * Rink.meter)
                .endY(offsetY - 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffNorthHorizontalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * Rink.meter)
                .endX(offsetX + 0.6 * Rink.meter + 1.2 * Rink.meter)
                .startY(offsetY - 0.225 * Rink.meter)
                .endY(offsetY - 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffSouthVerticalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * Rink.meter)
                .endX(offsetX - 0.6 * Rink.meter)
                .startY(offsetY + 1.125 * Rink.meter)
                .endY(offsetY + 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffSouthHorizontalLeft = LineBuilder.create()
                .startX(offsetX - 0.6 * Rink.meter)
                .endX(offsetX - 0.6 * Rink.meter - 1.2 * Rink.meter)
                .startY(offsetY + 0.225 * Rink.meter)
                .endY(offsetY + 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffSouthVerticalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * Rink.meter)
                .endX(offsetX + 0.6 * Rink.meter)
                .startY(offsetY + 1.125 * Rink.meter)
                .endY(offsetY + 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
                .build();
        Line faceOffSouthHorizontalRight = LineBuilder.create()
                .startX(offsetX + 0.6 * Rink.meter)
                .endX(offsetX + 0.6 * Rink.meter + 1.2 * Rink.meter)
                .startY(offsetY + 0.225 * Rink.meter)
                .endY(offsetY + 0.225 * Rink.meter)
                .stroke(Color.RED)
                .strokeWidth(Rink.circleStrokeWidth)
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
                .radius(Rink.faceOffSpotRadius)
                .fill(Color.RED)
                .build();
    }

    private Line createLineAcross(double offset, Color color, double lineWidth) {
        return LineBuilder.create()
                .startX(offset)
                .endX(offset)
                .startY(0 + lineWidth / 2)
                .endY(Rink.rinkWidth - lineWidth / 2)
                .stroke(color)
                .strokeWidth(lineWidth)
                .strokeLineCap(StrokeLineCap.SQUARE)
                .build();
    }

    public static ArithmeticPoint centerSpot() {
        return centerSpot;
    }

    public static double centerRadius() {
        return centerCircleRadius;
    }

    public static double eastGoalLine() {
        return rinkLength - goalLineInset;
    }

    public static double westGoalLine() {
        return goalLineInset;
    }
}
