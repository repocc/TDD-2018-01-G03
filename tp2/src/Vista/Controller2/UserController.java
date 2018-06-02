package tp2.src.Vista.Controller2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Vista2.ViewDashboard;

import java.util.List;

public abstract class UserController extends Controller {

    //public Button logoutButton;

    @FXML
    public Label dashboardTittle;
    @FXML
    public VBox listDashboard;
    @FXML
    public Pane dashboard;

    public abstract void showDashboards(List<Dashboard> dashboards);

    public abstract void setDashboardPage(ViewDashboard viewDashboard);

    public abstract double getDashboardWidth();

    public abstract double getDashboardHeight();

    public abstract void logout();
}
