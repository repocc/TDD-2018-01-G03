package tp2.src.Vista.Controller2;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Vista.Vista2.ViewDashboard;

public class ShowDashboard implements EventHandler<ActionEvent> {

    private UserController userController;

    public ShowDashboard(UserController userController) {
        this.userController= userController;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.userController.setDashboardPage(new ViewDashboard(this.userController));

    }
}
