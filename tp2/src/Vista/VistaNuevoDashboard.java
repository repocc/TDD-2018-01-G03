package tp2.src.Vista;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tp2.src.Controlador.CrearDashboard;

public class VistaNuevoDashboard extends VBox {

    private VistaAdmin vistaAdmin;

    public VistaNuevoDashboard(VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;

        TextField name = new TextField();
        name.setPromptText("Nombre del dashboard...");
        name.setPrefColumnCount(10);
        name.getText();

        Label lblQueries = new Label("QUERIES:");
        lblQueries.setFont(Font.font("Cambria", 15));
        CheckBox totaltickets = new CheckBox("Total de tickets creados");
        CheckBox closeTickets = new CheckBox("Total de tickets cerrados");
        CheckBox openTickets = new CheckBox("Total de tickets abiertos actualmente");
        CheckBox chartOpen = new CheckBox("Grafico de tickets abiertos");

        Button btnCrear = new Button("Crear Dashboard");
        btnCrear.setOnAction(new CrearDashboard(vistaAdmin));
        this.setSpacing(10);
        this.setMinSize(800,700);
        this.getChildren().addAll(name,lblQueries,totaltickets,closeTickets,openTickets,chartOpen,btnCrear);

    }
}
