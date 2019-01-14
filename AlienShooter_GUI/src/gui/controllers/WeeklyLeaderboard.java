package gui.controllers;

import gui.scoreboarduser.User;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

//This is the controller of the table of
//weekly leaderboard
public class WeeklyLeaderboard {

    @FXML
    public TableView<User> table;

    @FXML
    public TableColumn<User,String> id;

    @FXML
    public TableColumn<User,String> name;

    @FXML
    public TableColumn<User,String> score;

    void sort_by_score() {
        score.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(score);
    }

}
