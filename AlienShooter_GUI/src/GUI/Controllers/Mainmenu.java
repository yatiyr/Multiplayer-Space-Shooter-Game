package GUI.Controllers;

import GUI.Game.Engine;
import GUI.GameValues;
import GUI.ScoreboardUser.User;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.geometry.Bounds;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Mainmenu {

    private int gamerid;

    private String name;

    @FXML
    public AnchorPane anchor_pane;

    @FXML
    private StackPane stack_pane;

    @FXML
    private Circle ball1;

    @FXML
    private Circle ball2;

    @FXML
    private Label welcome;

    void setgamerid(int id) {

        this.gamerid = id;

    }

    void setname(String name) {

        this.name = name;

    }

    void setwelcome() {

        String message = "Welcome " + this.name;

        welcome.setText(message);

    }


    //I did that for experiment when I was learning how to move balls with a timeline
    //There are two balls here and they move inside the mainmenu
    final Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(5), new EventHandler<ActionEvent>() {

        double deltaX = 1;
        double deltaY = 1;

        @Override
        public void handle(final ActionEvent t) {
            ball1.setLayoutX(ball1.getLayoutX() + deltaX);
            ball1.setLayoutY(ball1.getLayoutY() + deltaY);

            final Bounds bounds = stack_pane.getLayoutBounds();
            final boolean atRightBorder = ball1.getLayoutX() >= (bounds.getMaxX() - ball1.getRadius());
            final boolean atLeftBorder = ball1.getLayoutX() <= (bounds.getMinX() + ball1.getRadius());
            final boolean atBottomBorder = ball1.getLayoutY() >= (bounds.getMaxY() - ball1.getRadius());
            final boolean atTopBorder = ball1.getLayoutY() <= (bounds.getMinY() + ball1.getRadius());

            if (atRightBorder || atLeftBorder) {
                deltaX *= -1;
            }
            if (atBottomBorder || atTopBorder) {
                deltaY *= -1;
            }
        }

    }));

    final Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(5), new EventHandler<ActionEvent> () {

        double deltaX = -1;
        double deltaY = -1;

        @Override
        public void handle(final ActionEvent t) {
            ball2.setLayoutX(ball2.getLayoutX() + deltaX);
            ball2.setLayoutY(ball2.getLayoutY() + deltaY);

            final Bounds bounds = stack_pane.getLayoutBounds();

            final boolean atRightBorder = ball2.getLayoutX() > (bounds.getMaxX() - ball2.getRadius());
            final boolean atLeftBorder = ball2.getLayoutX() < (bounds.getMinX() + ball2.getRadius());
            final boolean atBottomBorder = ball2.getLayoutY() > (bounds.getMaxY() - ball2.getRadius());
            final boolean atTopBorder = ball2.getLayoutY() < (bounds.getMinY() + ball2.getRadius());

            if (atRightBorder || atLeftBorder) {
                deltaX *= -1;
            }
            if (atBottomBorder || atTopBorder) {
                deltaY *= -1;
            }
        }

    }));


    public void start_playing()  {


        //This sets up the game engine and sets our
        //user for saving score during the game
        Engine engine = new Engine();
        User user = new User(gamerid,name,0);
        engine.SetUser(user);

    }

    //We request a json array from server and put the members
    //of array to the table
    public void weekly_leaderboard() throws IOException {



        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GameValues.Weekly_Leaderboard_Request_Url);

        HttpResponse response = httpClient.execute(request);


        HttpEntity responseEntity = response.getEntity();

        String json = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

        JSONArray jsonarray_weekly = new JSONArray(json);

        ArrayList<User> list = new ArrayList<>();

        if(jsonarray_weekly.length() > 0) {

            for (int i = 0; i < jsonarray_weekly.length() ; i++) {

                JSONObject o = new JSONObject(jsonarray_weekly.get(i ).toString());
                String username_weekly = o.getString("username");
                int userid_weekly = o.getInt("userId");
                int score_weekly = o.getInt("score");

                User user = new User(userid_weekly, username_weekly, score_weekly);

                list.add(user);

            }

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameValues.Weekly_Leaderboard_FXML_Path));

            Stage stage = new Stage();
            Parent root = fxmlLoader.load();
            WeeklyLeaderboard controller = fxmlLoader.getController();

            controller.id.setCellValueFactory(new PropertyValueFactory<>("id"));
            controller.name.setCellValueFactory(new PropertyValueFactory<>("name"));
            controller.score.setCellValueFactory(new PropertyValueFactory<>("score"));

            controller.table.getItems().addAll(list);

            controller.sort_by_score();

            Scene scene = new Scene(root);

            stage.setResizable(false);

            stage.setScene(scene);

            stage.show();
        }


    }

    //We request a json array from server and put the members
    //of array to the table
    public void alltime_leaderboard() throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(GameValues.All_Time_Leaderboard_Request_Url);

        HttpResponse response = httpClient.execute(request);

        HttpEntity responseEntity = response.getEntity();

        String json = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

        JSONArray jsonarray = new JSONArray(json);

        ArrayList<User> list = new ArrayList<>();


        for(int i=0;i<jsonarray.length();i++) {

            JSONObject o = new JSONObject(jsonarray.get(i).toString());
            String username = o.getString("username");
            int userid = o.getInt("userId");
            int score = o.getInt("score");

            User user = new User(userid,username,score);

            list.add(user);

        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameValues.All_Time_Leaderboard_FXML_Path));

        Stage stage = new Stage();
        Parent root = fxmlLoader.load();
        AllTimeLeaderboard controller = fxmlLoader.getController();

        controller.id.setCellValueFactory(new PropertyValueFactory<>("id"));
        controller.name.setCellValueFactory(new PropertyValueFactory<>("name"));
        controller.score.setCellValueFactory(new PropertyValueFactory<>("score"));

        controller.table.getItems().addAll(list);

        controller.sort_by_score();

        Scene scene = new Scene(root);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.show();

    }
}
