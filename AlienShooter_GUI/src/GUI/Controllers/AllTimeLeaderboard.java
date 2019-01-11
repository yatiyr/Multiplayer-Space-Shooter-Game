package GUI.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import GUI.ScoreboardUser.User;

//This controller has the table to show
//AllTimeLeaderboard
public class AllTimeLeaderboard {

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
