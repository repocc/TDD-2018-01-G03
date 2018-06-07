package tp2.src.Controller;

import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.View.Main;

public class AssociatedStageController extends UserController {

    private Associated assoc;

    @Override
    public void setMain(Main main) {
        super.setMain(main);
        updateCheckBoxList();
        assoc = (Associated) main.actualUser;
        dashboardSelected = null;
        getDashboards();

    }



}
