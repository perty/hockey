package hockey;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import util.ArithmeticPoint;

public class Hockey extends Application {
    static final double meter = 20;
    private static final Color HOME_TEAM_COLOR = Color.GREEN;
    private static final Color AWAY_TEAM_COLOR = Color.BLUE;
    private static HockeyPlayer selectedPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hockey!");
        BorderPane borderPane = new BorderPane();
        Rink rink = new Rink();

        addPlayersToRink(rink);

        borderPane.setCenter(rink);
        borderPane.setRight(controlPanel());
        borderPane.setLeft(controlPanel());
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    private Node controlPanel() {
        return VBoxBuilder.create()
                .children(
                        new PlayerPanel(),
                        new PlayerPanel(),
                        new PlayerPanel(),
                        new PlayerPanel(),
                        new PlayerPanel(),
                        new PlayerPanel()
                )
                .spacing(0.1 * meter)
                .build();
    }

    private void addPlayersToRink(Rink rink) {
        double centerLine = 0.9 * meter;
        rink.getChildren().add(createTeam(AWAY_TEAM_COLOR, centerLine, centerLine + 5 * meter, Rink.eastGoalLine()));
        rink.getChildren().add(createTeam(HOME_TEAM_COLOR, -centerLine, -centerLine - 5 * meter, Rink.westGoalLine()));
    }

    private Group createTeam(Color teamColor, double centerLine, double defenceLine, double goalLine) {
        ArithmeticPoint startPoint = relativeToCenterSpot(centerLine, 0);
        HockeyPlayer center = playerFacingCenterSpot(startPoint, teamColor);
        HockeyPlayer leftForward = playerFacingCenterSpot(relativeToCenterSpot(centerLine, Rink.centerRadius()), teamColor);
        HockeyPlayer rightForward = playerFacingCenterSpot(relativeToCenterSpot(centerLine, -Rink.centerRadius()), teamColor);
        HockeyPlayer leftDefence = playerFacingCenterSpot(relativeToCenterSpot(defenceLine, Rink.centerRadius()), teamColor);
        HockeyPlayer rightDefence = playerFacingCenterSpot(relativeToCenterSpot(defenceLine, -Rink.centerRadius()), teamColor);
        HockeyPlayer goalie = playerFacingCenterSpot(new ArithmeticPoint(goalLine, Rink.rinkWidth / 2), teamColor);

        return GroupBuilder.create()
                .children(
                        center,
                        leftForward,
                        rightForward,
                        leftDefence,
                        rightDefence,
                        goalie
                )
                .build();
    }

    private HockeyPlayer playerFacingCenterSpot(ArithmeticPoint startPoint, Color teamColor) {
        HockeyPlayer center = new HockeyPlayer(startPoint, teamColor);
        center.faceTowardsPoint(Rink.centerSpot);
        return center;
    }

    private ArithmeticPoint relativeToCenterSpot(double deltaX, double deltaY) {
        return Rink.centerSpot().add(new ArithmeticPoint(deltaX, deltaY));
    }

    public static void select(HockeyPlayer player) {
        if(selectedPlayer != null) {
            selectedPlayer.setSelected(false);
        }
        selectedPlayer = player;
        player.setSelected(true);
    }
}