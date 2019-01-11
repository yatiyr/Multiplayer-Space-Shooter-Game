package GUI.Game;


import GUI.GameValues;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



//This is our game field
//Every level, its image changes
public class Game_Pane extends Pane {

    private Label score;
    private Engine engine;


    private ImageView iv1;

    public Game_Pane(int game_level,int score_from_prev_level) {

        score = new Label();
        score.setText(String.valueOf(score_from_prev_level));
        score.setMinWidth(GameValues.Game_Pane_Width);
        score.setTextFill(Color.WHITE);

        if(game_level == 1)
            iv1 = new ImageView(new Image(GameValues.Level1_GamePane_Image_Path));
        else if(game_level == 2)
            iv1 = new ImageView(new Image(GameValues.Level2_GamePane_Image_Path));
        else if(game_level == 3)
            iv1 = new ImageView(new Image(GameValues.Level3_GamePane_Image_Path));
        else if(game_level == 4)
            iv1 = new ImageView(new Image(GameValues.Level4_GamePane_Image_Path));

        iv1.setFitHeight(GameValues.Game_Pane_Height);
        iv1.setFitWidth(GameValues.Game_Pane_Width);

        this.getChildren().add(iv1);
        this.getChildren().add(score);

    }



    void bindScore(ReadOnlyIntegerProperty score) {

        this.score.textProperty().bind(score.asString());

    }

    public Engine getEngine() {

        return this.engine;

    }

    void SetEngine(Engine engine) {

        this.engine = engine;

    }


}



