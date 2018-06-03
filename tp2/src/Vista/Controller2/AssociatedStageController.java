package tp2.src.Vista.Controller2;

import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.Vista.Vista2.Main2;

public class AssociatedStageController extends UserController {

    private Associated assoc;

    @Override
    public void setMain(Main2 main) {
        super.setMain(main);
        updateCheckBoxList();
        assoc = (Associated) main.actualUser;
        dashboardSelected = null;
        getDashboards();

    }



}
