package hockey;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.RectangleBuilder;

/**
 * Created with IntelliJ IDEA.
 * User: perty
 * Date: 2013-04-06
 * Time: 17:58
 */
public class PlayerPanel extends HBox {
    static final double meter = Hockey.meter;

    public PlayerPanel() {
        getChildren().add(
                RectangleBuilder.create()
                        .height(4 * meter)
                        .width(5 * meter)
                        .arcHeight(meter)
                        .arcWidth(meter)
                        .stroke(Color.BLACK)
                        .fill(Color.TRANSPARENT)
                        .build()
        );

    }
}