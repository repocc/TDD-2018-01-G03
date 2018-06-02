package tp2.src.Vista.Vista2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ShowDashboard implements EventHandler<ActionEvent> {

    private AdminStageController adminStageController;

    public ShowDashboard(AdminStageController adminStageController) {
        this.adminStageController= adminStageController;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.adminStageController.setDashboardPage(new ViewDashboard(this.adminStageController));

    }
}
