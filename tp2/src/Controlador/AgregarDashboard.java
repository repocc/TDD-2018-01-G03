package tp2.src.Controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Vista.VistaAdmin;
import tp2.src.Vista.VistaNuevoDashboard;


public class AgregarDashboard implements EventHandler<ActionEvent> {

    private VistaAdmin vistaAdmin;

    public AgregarDashboard(VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;
    }


    @Override
    public void handle(ActionEvent actionEvent) {
        this.vistaAdmin.setearPaginaDashboard(new VistaNuevoDashboard(this.vistaAdmin));

    }

}
