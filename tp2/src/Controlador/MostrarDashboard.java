package tp2.src.Controlador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Vista.VistaAdmin;
import tp2.src.Vista.VistaDashboard;
import tp2.src.Vista.VistaNuevoDashboard;

public class MostrarDashboard implements EventHandler<ActionEvent> {

    private Admin admin;
    private String name;
    private VistaAdmin vistaAdmin;

    public MostrarDashboard(Admin admin, String dashboardName,VistaAdmin vistaAdmin) {
        this.admin = admin;
        this.name = dashboardName;
        this.vistaAdmin = vistaAdmin;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.vistaAdmin.setearPaginaDashboard(new VistaDashboard(this.admin,this.name));

    }
}
