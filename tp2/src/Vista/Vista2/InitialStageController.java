package tp2.src.Vista.Vista2;

public class InitialStageController extends Controller{

    public void openAssociatedStage() {
        try {
            this.main.replaceSceneContent("AssociatedStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openAdminStage() {
        try {
            this.main.replaceSceneContent("LoginStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
