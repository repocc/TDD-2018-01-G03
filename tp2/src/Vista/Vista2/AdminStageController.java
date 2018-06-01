package tp2.src.Vista.Vista2;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import java.util.ArrayList;
import java.util.List;

public class AdminStageController extends Controller{

    @FXML
    FlowPane dashboardsPane;
    @FXML
    Button logoutButton;
    @FXML
    Label dashboardTittle, queryTittle;
    @FXML
    Button addDashboardButton, addQueryButton;
    @FXML
    FlowPane queriesPane;
    @FXML
    LineChart lineChart;


    public void initialize(){

        showDashboards();
        showQueries();

    }

    private void showQueries() {
        List<String> list = new ArrayList<>();
        list.add("DASHBOARD 1");
        list.add("DASHBOARD 2");
        list.add("DASHBOARD 3");

        for (String dashboardName : list) {
            Button button = new Button(dashboardName);
            button.setFocusTraversable(false);
            button.setPrefWidth(addDashboardButton.getPrefWidth());
            button.setPrefHeight(addDashboardButton.getPrefHeight());
            button.setStyle(addDashboardButton.getStyle());


            dashboardsPane.getChildren().add(button);

        }

    }

    private void showDashboards() {
        List<String> list = new ArrayList<>();
        list.add("q 1");
        list.add("q 2");
        list.add("q 3");

        for (String dashboardName : list) {
            Button button = new Button(dashboardName);
            button.setFocusTraversable(false);
            button.setPrefWidth(addQueryButton.getPrefWidth());
            button.setPrefHeight(addQueryButton.getPrefHeight());
            button.setStyle(addQueryButton.getStyle());


            queriesPane.getChildren().add(button);

        }

    }


}
