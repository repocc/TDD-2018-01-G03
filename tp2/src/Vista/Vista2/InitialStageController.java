package tp2.src.Vista.Vista2;

public class InitialStageController extends Controller{

    public void openAssociatedStage() {
        System.out.println("ASOC");
    }

    public void openAdminStage() {
        try {
            this.main.replaceSceneContent("AdminStage.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
