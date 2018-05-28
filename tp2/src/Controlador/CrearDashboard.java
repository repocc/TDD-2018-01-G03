package tp2.src.Controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import tp2.src.Vista.VistaAdmin;

public class CrearDashboard implements EventHandler<ActionEvent> {

    private VistaAdmin vistaAdmin;

    public CrearDashboard(VistaAdmin vistaAdmin){
        this.vistaAdmin= vistaAdmin;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.vistaAdmin.actualizarListaDeDashboards();

    }
}
