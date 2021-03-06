import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            Parent content = FXMLLoader.load(getClass().getResource("GraphicalInterface.fxml"));
            primaryStage.setTitle("Gloal Warming 3D");
            primaryStage.setScene(new Scene(content));
            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
