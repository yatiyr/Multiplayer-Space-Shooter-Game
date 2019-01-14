package gui.controllers;

import gui.GameValues;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Registration {

    @FXML
    private Label txt_status;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_rp;


    //We open login page again
    public void Back() throws IOException {

        StageLoader stage = new StageLoader(GameValues.LOGIN_FXML_PATH,"LOGIN_GAME",GameValues.LOGIN_REGISTER_PANE_HEIGHT,GameValues.LOGIN_REGISTER_PANE_WIDTH);
        stage.loadStage();

        Stage registration_stage = (Stage) txt_status.getScene().getWindow();
        registration_stage.close();

    }

    //We take the entered values and call registration
    //function at server.
    public void Register() throws IOException {

        if(txt_username.getText().equals("")) {

            txt_status.setText("You Should Enter a Username!");

        }
        else if(txt_password.getText().equals(txt_rp.getText())) {

            if(txt_password.getText().equals("")) {

                txt_status.setText("Please Enter a password!");
            }
            else {

                String payload = "{" +
                        "\"name\": \"" + txt_username.getText() + "\", " +
                        "\"password\": \"" + txt_password.getText() + "\"" +
                        "}";
                StringEntity entity = new StringEntity(payload,
                        ContentType.APPLICATION_JSON);

                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost(GameValues.REGISTRATION_URL);
                request.setEntity(entity);

                HttpResponse response = httpClient.execute(request);

                if(response.getStatusLine().getStatusCode() == 200) {

                    txt_status.setText("User Successfully Added!");

                }
                else if(response.getStatusLine().getStatusCode() == 400) {

                    if(txt_password.getText().length() < GameValues.MAX_PASS_LENGTH) {

                        txt_status.setText("Password Should be >=6!");
                    }
                    else {

                        txt_status.setText("This User Exists!");
                    }
                }
            }

        }
        else {

            txt_status.setText("Passwords Should Match!");

        }

    }


}
