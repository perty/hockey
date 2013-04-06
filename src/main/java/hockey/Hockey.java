package hockey;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.ArithmeticPoint;

public class Hockey extends Application {
    static final double meter = 20;
    private static final Color HOME_TEAM_COLOR = Color.GREEN;
    private static final Color AWAY_TEAM_COLOR = Color.BLUE;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hockey!");
        BorderPane root = new BorderPane();
        Rink rink = new Rink();

        addPlayersToRink(rink);
        root.setCenter(rink);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void addPlayersToRink(Rink rink) {
        double centerLine = 0.9 * meter;
        Group awayTeam = createTeam(AWAY_TEAM_COLOR, centerLine, centerLine + 5 * meter, Rink.eastGoalLine());
        rink.getChildren().add(awayTeam);
        rink.getChildren().add(createTeam(HOME_TEAM_COLOR, -centerLine, -centerLine - 5 * meter, Rink.westGoalLine()));
        final HockeyPlayer hockeyPlayer = (HockeyPlayer) awayTeam.getChildren().get(0);
        rink.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hockeyPlayer.skateTo(new ArithmeticPoint(mouseEvent.getX(), mouseEvent.getY()));
            }
        });
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

}