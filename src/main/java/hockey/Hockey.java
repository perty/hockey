package hockey;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Hockey extends Application {
    static final double meter = 15;

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
        primaryStage.setScene(new Scene(root, 100 * meter, 50 * meter));
        primaryStage.show();
    }

    private void addPlayersToRink(Rink rink) {
        HockeyPlayer hockeyPlayer = new HockeyPlayer();
        rink.getChildren().add(hockeyPlayer);
        hockeyPlayer.skateTo(Rink.rinkLength / 2.2, Rink.rinkWidth / 2);

    }

}