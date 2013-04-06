package hockey;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Stage;
import util.ArithmeticPoint;

public class Hockey extends Application {
    static final double meter = 20;
    private static HockeyPlayer selectedPlayer;
    private HockeyTeam homeTeam = new HockeyTeam(true);
    private HockeyTeam awayTeam = new HockeyTeam(false);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hockey!");
        BorderPane borderPane = new BorderPane();
        Rink rink = new Rink();
        rink.setOnMouseClicked(commandSelectedPlayer());

        addPlayersToRink(rink);

        borderPane.setCenter(rink);
        borderPane.setRight(controlPanel());
        borderPane.setLeft(controlPanel());
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.show();
    }

    private EventHandler<? super MouseEvent> commandSelectedPlayer() {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (Hockey.selectedPlayer != null) {
                    selectedPlayer.skateTo(new ArithmeticPoint(mouseEvent.getX(), mouseEvent.getY()));
                }
            }
        };
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
        rink.getChildren().add(homeTeam);
        rink.getChildren().add(awayTeam);
    }

    public static void select(HockeyPlayer player) {
        if (selectedPlayer != null) {
            selectedPlayer.setSelected(false);
        }
        selectedPlayer = player;
        if (player != null) {
            player.setSelected(true);
        }
    }
}