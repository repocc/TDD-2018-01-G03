package tp2.src.Vista.Vista2;

import tp2.src.Model.MonitorSystem.Dashboard;

import java.util.List;

public class AssociatedStageController extends UserController {


    public void initialize() {
        super.setMain(main);
        this.getDashboards();
    }

    @Override
    public void showDashboards(List<Dashboard> dashboards) {

    }

    public void getDashboards() {

        if(main.actualUser.getOwnDashboards() != null){
            showDashboards(main.actualUser.getOwnDashboards());
        }
    }

    @Override
    public void showQueries(Dashboard dashboard) {

    }
}
