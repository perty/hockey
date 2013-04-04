package hockey;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Hockey extends Application {
    static final double meter = 20;

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
        final HockeyPlayer hockeyPlayer = new HockeyPlayer(4 * meter, 15 * meter);
        rink.getChildren().add(hockeyPlayer);
        rink.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                hockeyPlayer.skateTo(mouseEvent.getX(), mouseEvent.getY());
            }
        });
    }

}