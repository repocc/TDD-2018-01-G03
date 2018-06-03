package tp2.src.Vista.Controller2;

import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Vista.Vista2.Main2;
import tp2.src.Vista.Vista2.ViewDashboard;

public class AssociatedStageController extends UserController {

    private Associated assoc;

    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        assoc = (Associated) main.actualUser;
        getDashboards();

    }


    public void getDashboards() {

        if(main.actualUser.getViewDashboards().size() > 0){
            showDashboards(main.actualUser.getViewDashboards());
        }
    }


    public void setDashboardPage(ViewDashboard viewDashboard){
        this.dashboard.getChildren().clear();
        this.dashboard.getChildren().add(viewDashboard);
    }

    public double getDashboardWidth() {
        return this.dashboard.getWidth();
    }

    public double getDashboardHeight() {
        return this.dashboard.getWidth();
    }



    public void selectDashboard(Dashboard dashboard){
        this.dashboardSelected = dashboard;
        this.setDashboardPage(new ViewDashboard(this,dashboard));
        this.dashboardTittle.setText(dashboard.getName());
    }
}
