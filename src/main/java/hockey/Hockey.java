package hockey;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Hockey extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hockey!");
        BorderPane root = new BorderPane();
        root.setCenter(new Rink());
        primaryStage.setScene(new Scene(root, 100 * Rink.meter, 50 * Rink.meter));
        primaryStage.show();
    }

}