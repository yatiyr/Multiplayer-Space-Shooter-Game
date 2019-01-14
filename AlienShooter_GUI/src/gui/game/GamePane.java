package gui.game;


import gui.GameValues;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



//This is our game field
//Every level, its image changes
public class GamePane extends Pane {

    private Label score;
    private Engine engine;


    private ImageView iv1;

    public GamePane(int gameLevel, int scoreFromPrevLevel) {

        score = new Label();
        score.setText(String.valueOf(scoreFromPrevLevel));
        score.setMinWidth(GameValues.GAME_PANE_WIDTH);
        score.setTextFill(Color.WHITE);

        if(gameLevel == 1)
            iv1 = new ImageView(new Image(GameValues.LEVEL1_GAME_PANE_IMAGE_PATH));
        else if(gameLevel == 2)
            iv1 = new ImageView(new Image(GameValues.LEVEL2_GAME_PANE_IMAGE_PATH));
        else if(gameLevel == 3)
            iv1 = new ImageView(new Image(GameValues.LEVEL3_GAME_PANE_IMAGE_PATH));
        else if(gameLevel == 4)
            iv1 = new ImageView(new Image(GameValues.LEVEL4_GAME_PANE_IMAGE_PATH));

        iv1.setFitHeight(GameValues.GAME_PANE_HEIGHT);
        iv1.setFitWidth(GameValues.GAME_PANE_WIDTH);

        this.getChildren().add(iv1);
        this.getChildren().add(score);

    }



    void bindScore(ReadOnlyIntegerProperty score) {

        this.score.textProperty().bind(score.asString());

    }

    public Engine getEngine() {

        return this.engine;

    }

    void setEngine(Engine engine) {

        this.engine = engine;

    }


}



