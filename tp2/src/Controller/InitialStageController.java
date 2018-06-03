package tp2.src.Controller;

import tp2.src.View.AdminView;
import tp2.src.View.AssociatedView;

public class InitialStageController extends Controller {


    public void openAssociatedStage() {
        try {
            this.main.setUserView( new AssociatedView(this.main));
            this.main.replaceSceneContent("LoginStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAdminStage() {
        try {
            this.main.setUserView(new AdminView(this.main));
            this.main.replaceSceneContent("LoginStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
