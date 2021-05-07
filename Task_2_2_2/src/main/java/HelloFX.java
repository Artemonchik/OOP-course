import Controllers.Controller;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layout.fxml"));
        Controller controller = new Controller();
        VBox root = (VBox) loader.load();
        loader.setController(controller);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Controllers.Snake. Pro version. Pay me 300$");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}