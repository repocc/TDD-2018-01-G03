package tp2.src.Vista.Vista2;

import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;

import java.util.List;

public class AdminStageController extends UserController{
    public FlowPane dashboardsPane;
    public Button logoutButton;
    public Label dashboardTittle, queryTittle;
    public Button addDashboardButton, addQueryButton;
    public FlowPane queriesPane;
    public LineChart lineChart;

    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        Admin admin = (Admin) main.actualUser;

        if(admin.getOwnDashboards() != null){
            showDashboards(admin.getOwnDashboards());
        }
    }


    public void showDashboards(List<Dashboard> dashboards) {
    }

    @Override
    public void showQueries(Dashboard dashboard) {

    }

    public void addDashboard(){

    }
}
