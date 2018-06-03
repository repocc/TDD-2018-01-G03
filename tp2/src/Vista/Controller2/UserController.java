package tp2.src.Vista.Controller2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Dashboard;

import java.io.IOException;
import java.util.List;

public abstract class UserController extends Controller {

    //public Button logoutButton;
    public Dashboard dashboardSelected;
    @FXML
    public Label dashboardTittle;
    @FXML
    public VBox listDashboard;
    @FXML
    public Pane dashboard;



    public abstract double getDashboardWidth();

    public abstract double getDashboardHeight();

    public abstract void selectDashboard(Dashboard dashboard);

    public void showDashboards(List<Dashboard> dashboards) {
        listDashboard.getChildren().clear();
        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            button.setPrefSize(207,47);
            button.setMnemonicParsing(false);
            button.setStyle("-fx-background-color: #d3d3d3;");
            button.setOnAction(new ShowDashboard(this,dashboard));
            listDashboard.getChildren().add(button);
        }

    }

    public void logout()  {
        try {
            this.main.showInitialStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
