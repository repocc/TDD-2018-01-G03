package tp2.src.Vista.Vista2.images;

import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Vista.Vista2.Controller;

import java.util.ArrayList;

public abstract class UserStageController extends Controller {


    public FlowPane dashboardsPane;

    public Button logoutButton;
    public Label dashboardTittle, queryTittle;
    public Button addDashboardButton, addQueryButton;
    public FlowPane queriesPane;
    public LineChart lineChart;


    public abstract void initialize();

    protected void showQueries(ArrayList<Query> queries) {

        for (Query query : queries) {
            Button button = new Button(query.getRule());
            button.setFocusTraversable(false);
            button.setPrefWidth(addQueryButton.getPrefWidth());
            button.setPrefHeight(addQueryButton.getPrefHeight());
            button.setStyle(addQueryButton.getStyle());

            queriesPane.getChildren().add(button);

        }

    }

    protected void showDashboards(ArrayList<Dashboard> dashboards) {

        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            button.setFocusTraversable(false);
            button.setPrefWidth(addDashboardButton.getPrefWidth());
            button.setPrefHeight(addDashboardButton.getPrefHeight());
            button.setStyle(addDashboardButton.getStyle());

            dashboardsPane.getChildren().add(button);

        }

    }
}
