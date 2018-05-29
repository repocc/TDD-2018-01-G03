package tp2.src.Vista;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import tp2.src.Controlador.CrearDashboard;

import java.util.*;

public class VistaNuevoDashboard extends VBox {

    private VistaAdmin vistaAdmin;
    private Main mainApp;

    public VistaNuevoDashboard(Main mainApp, VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;
        this.mainApp = mainApp;
        TextField name = new TextField();
        name.setPromptText("Nombre del dashboard...");
        name.setPrefColumnCount(10);
        name.getText();

        Label lblQueries = new Label("QUERIES:");
        lblQueries.setFont(Font.font("Cambria", 15));
        CheckBox totaltickets = new CheckBox("Total de tickets creados");
        totaltickets.setId("open-count");
        CheckBox closeTickets = new CheckBox("Total de tickets cerrados");
        closeTickets.setId("close-count");
        CheckBox openTickets = new CheckBox("Total de tickets abiertos actualmente");
        openTickets.setId("open-actual-count");
        CheckBox chartOpen = new CheckBox("Grafico de tickets abiertos");
        chartOpen.setId("open-fraction");
        ArrayList<CheckBox> queries = new ArrayList<CheckBox>();
        queries.add(totaltickets);
        queries.add(closeTickets);
        queries.add(openTickets);
        queries.add(chartOpen);

        Button btnCrear = new Button("Crear Dashboard");
        btnCrear.setOnAction(new CrearDashboard(this.mainApp,this.vistaAdmin,name,queries));
        this.setSpacing(10);
        this.setMinSize(800,700);
        this.getChildren().addAll(name,lblQueries,totaltickets,closeTickets,openTickets,chartOpen,btnCrear);

    }
}
