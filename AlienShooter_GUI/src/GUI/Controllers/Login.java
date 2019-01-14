package GUI.Controllers;

import GUI.GameValues;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Login {

    @FXML
    private Label status;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    public void login() throws IOException {



        //This takes username and password, and sends
        //the needed request for login
        String payload = "{" +
                "\"name\": \"" + txt_username.getText() + "\", " +
                "\"password\": \"" + txt_password.getText() + "\"" +
                "}";
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_JSON);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(GameValues.Sign_In_Request_Url);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);

        if(response.getStatusLine().getStatusCode() == 400) {

            status.setText("Login not Successful!");
        }
        else if(response.getStatusLine().getStatusCode() == 200) {

            status.setText("Login Successful!");

            //If login is successful, we take the json response as
            //player object's attributes and send it to the controller
            //of mainmenu and opens the mainmenu
            HttpEntity responseEntity = response.getEntity();

            String json = EntityUtils.toString(responseEntity, StandardCharsets.UTF_8);

            JSONObject o = new JSONObject(json);

            String name = o.getString("name");
            int id = o.getInt("id");

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(GameValues.Mainmenu_FXML_Path));

            Stage stage = new Stage();
            Parent root = fxmlLoader.load();
            Mainmenu controller = fxmlLoader.getController();
            controller.setgamerid(id);
            controller.setname(name);
            controller.setwelcome();
            Scene scene = new Scene(root);

            controller.timeline1.setCycleCount(Timeline.INDEFINITE);
            controller.timeline1.play();

            controller.timeline2.setCycleCount(Timeline.INDEFINITE);
            controller.timeline2.play();

            stage.setResizable(false);

            stage.setScene(scene);

            stage.show();

            Stage login_stage = (Stage) status.getScene().getWindow();

            login_stage.close();
        }
    }

    public void Register() throws IOException {

        StageLoader stage = new StageLoader(GameValues.Registration_FXML_Path,"REGISTER",400,300);
        stage.LoadStage();

        Stage login_stage = (Stage) status.getScene().getWindow();
        login_stage.close();

    }


}