package tp2.src.Controlador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Model.MonitorSystem.User;
import tp2.src.Vista.VistaDashboard;
import tp2.src.Vista.VistaUser;

public class MostrarDashboard implements EventHandler<ActionEvent> {

    private User user;
    private String name;
    private VistaUser vistaUser;

    public MostrarDashboard(User user, String dashboardName,VistaUser vistaUser) {
        this.user = user;
        this.name = dashboardName;
        this.vistaUser = vistaUser;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.vistaUser.setearPaginaDashboard(new VistaDashboard(this.vistaUser,this.name));

    }
}
