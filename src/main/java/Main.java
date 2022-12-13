import connection.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        try {
            Connection.makeConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 389, 422);
        stage.setTitle("Showroom");
        stage.setScene(scene);
        stage.show();
    }
}
