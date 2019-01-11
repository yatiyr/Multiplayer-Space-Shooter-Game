package GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource(GameValues.Login_FXML_Path));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Term Project");
        primaryStage.setScene(new Scene(root, GameValues.Login_Register_Pane_Width,GameValues.Login_Register_Pane_Height));
        primaryStage.show();
        


    }


    public static void main(String[] args) {
        launch(args);
    }
}
