package tp2.src.Vista.Controller2;

import javafx.scene.control.Button;
import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.ViewDashboard;

import java.io.IOException;
import java.util.List;

public class AssociatedStageController extends UserController {

    private Associated assoc;

    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        assoc = (Associated) main.actualUser;
        getDashboards();

    }

    @Override
    public void showDashboards(List<Dashboard> dashboards) {
        listDashboard.getChildren().clear();
        for (Dashboard dashboard : dashboards) {
            Button button = new Button(dashboard.getName());
            button.prefWidth(200);
            button.setMinWidth(200);
            button.setMnemonicParsing(false);
            button.setStyle("-fx-background-color: #d3d3d3;");
            button.setOnAction(new ShowDashboard(this,dashboard));
            listDashboard.getChildren().add(button);
        }

    }

    public void getDashboards() {

        if(main.actualUser.getViewDashboards().size() > 0){
            showDashboards(main.actualUser.getViewDashboards());
        }
    }


    public void setDashboardPage(ViewDashboard viewDashboard){
        this.dashboard.getChildren().add(viewDashboard);
    }

    public double getDashboardWidth() {
        return this.dashboard.getWidth();
    }

    public double getDashboardHeight() {
        return this.dashboard.getWidth();
    }

    @Override
    public void logout()  {
        try {
            this.main.showInitialStage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void selectDashboard(Dashboard dashboard){
        this.dashboardSelected = dashboard;
        this.setDashboardPage(new ViewDashboard(this,dashboard));
    }
}
