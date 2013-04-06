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
        Group awayTeam = createAwayTeam(rink);
        rink.getChildren().add(awayTeam);
        Group homeTeam = createHomeTeam(rink);
        rink.getChildren().add(homeTeam);
        final HockeyPlayer hockeyPlayer = (HockeyPlayer) awayTeam.getChildren().get(0);
        rink.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hockeyPlayer.skateTo(new ArithmeticPoint(mouseEvent.getX(), mouseEvent.getY()));
            }
        });
    }

    private Group createHomeTeam(Rink rink) {
        double deltaX = -0.5 * meter;
        HockeyPlayer center = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, 0), HOME_TEAM_COLOR);
        HockeyPlayer leftForward = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, rink.centerRadius()), HOME_TEAM_COLOR);
        HockeyPlayer rightForward = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, -rink.centerRadius()), HOME_TEAM_COLOR);
        double defenceDeltaX = deltaX - 5 * meter;
        HockeyPlayer leftDefence = new HockeyPlayer(relativeToCenterSpot(rink, defenceDeltaX, rink.centerRadius()), HOME_TEAM_COLOR);
        HockeyPlayer rightDefence = new HockeyPlayer(relativeToCenterSpot(rink, defenceDeltaX, -rink.centerRadius()), HOME_TEAM_COLOR);
        HockeyPlayer goalie = new HockeyPlayer(new ArithmeticPoint(rink.westGoalLine(), Rink.rinkWidth / 2), HOME_TEAM_COLOR);

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

    private Group createAwayTeam(Rink rink) {
        double deltaX = 0.5 * meter;
        HockeyPlayer center = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, 0), AWAY_TEAM_COLOR);
        HockeyPlayer leftForward = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, rink.centerRadius()), AWAY_TEAM_COLOR);
        HockeyPlayer rightForward = new HockeyPlayer(relativeToCenterSpot(rink, deltaX, -rink.centerRadius()), AWAY_TEAM_COLOR);
        double defenceDeltaX = deltaX + 5 * meter;
        HockeyPlayer leftDefence = new HockeyPlayer(relativeToCenterSpot(rink, defenceDeltaX, rink.centerRadius()), AWAY_TEAM_COLOR);
        HockeyPlayer rightDefence = new HockeyPlayer(relativeToCenterSpot(rink, defenceDeltaX, -rink.centerRadius()), AWAY_TEAM_COLOR);
        HockeyPlayer goalie = new HockeyPlayer(new ArithmeticPoint(rink.eastGoalLine(), rink.rinkWidth / 2), AWAY_TEAM_COLOR);

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

    private ArithmeticPoint relativeToCenterSpot(Rink rink, double deltaX, double deltaY) {
        return rink.centerSpot().add(new ArithmeticPoint(deltaX, deltaY));
    }

}