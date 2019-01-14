package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource(GameValues.LOGIN_FXML_PATH));
        primaryStage.setResizable(false);
        primaryStage.setTitle("Term Project");
        primaryStage.setScene(new Scene(root, GameValues.LOGIN_REGISTER_PANE_WIDTH,GameValues.LOGIN_REGISTER_PANE_HEIGHT));
        primaryStage.show();
        


    }


    public static void main(String[] args) {
        launch(args);
    }
}
