package tp2.src.Vista.Controller2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Model.MonitorSystem.Dashboard;

public class ShowDashboard implements EventHandler<ActionEvent> {

    private UserController userController;
    private Dashboard dashboard;

    public ShowDashboard(UserController userController, Dashboard dashboard) {
        this.userController= userController;
        this.dashboard = dashboard;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.userController.selectDashboard(dashboard);

    }
}
