package tp2.src.Vista.Vista2;

import tp2.src.Vista.Vista2.images.UserStageController;

public class AdminStageController extends UserStageController {


    @Override
    public void initialize() {
        showDashboards(main.monitorSystem.getDashboards());

    }
}
