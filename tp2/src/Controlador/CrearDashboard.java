package tp2.src.Controlador;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import tp2.src.Vista.VistaAdmin;
import tp2.src.Vista.Main;
import java.util.ArrayList;

public class CrearDashboard implements EventHandler<ActionEvent> {
    private Main mainApp;
    private VistaAdmin vistaAdmin;
    private TextField name;
    private ArrayList<CheckBox> queries;

    public CrearDashboard(Main mainApp,VistaAdmin vistaAdmin, TextField name, ArrayList<CheckBox> queries){
        this.mainApp = mainApp;
        this.vistaAdmin= vistaAdmin;
        this.name = name;
        this.queries = queries;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        this.vistaAdmin.crearNuevoDashboard(name.getText(),queries);
        this.vistaAdmin.actualizarListaDeDashboards();

    }
}
