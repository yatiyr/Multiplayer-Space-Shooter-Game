package gui.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

//This class is for loading stages and it is for
//reducing the complexity of code
class StageLoader {

    private String name;

    private String title;

    private int height;

    private int width;

    StageLoader(String name,String title,int height,int width) {

        this.name = name;
        this.title = title;
        this.height = height;
        this.width = width;
    }

    void loadStage() throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(this.name));
        primaryStage.setResizable(false);
        primaryStage.setTitle(this.title);
        primaryStage.setScene(new Scene(root, this.width, this.height));
        primaryStage.show();
    }

}
