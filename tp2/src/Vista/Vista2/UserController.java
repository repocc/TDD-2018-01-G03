package tp2.src.Vista.Vista2;

import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import tp2.src.Model.MonitorSystem.Dashboard;

import java.util.List;

public abstract class UserController extends Controller {
    public FlowPane dashboardsPane;
    public Button logoutButton;
    public Label dashboardTittle;
    public FlowPane queriesPane;
    public LineChart lineChart;

    public abstract void showDashboards(List<Dashboard> dashboards);
}
