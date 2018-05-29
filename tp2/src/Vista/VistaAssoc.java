package tp2.src.Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Controlador.Logout;
import tp2.src.Model.MonitorSystem.Associated;
import tp2.src.Model.MonitorSystem.MonitorSystem;

public class VistaAssoc extends VistaUser {

    private Associated assoc;
    private Main mainApp;
    private MonitorSystem monitorSystem;
    private VistaListaDashboards vistaListaDashboards;


    public VistaAssoc(Associated assoc, Main mainApp, MonitorSystem monitorSystem) {
        this.assoc = assoc;
        this.mainApp = mainApp;
        this.monitorSystem = monitorSystem;
        Label label = new Label("G3 - TDD Monitor System");
        label.setFont(Font.font("Cambria", FontWeight.BOLD, 35));
        label.setAlignment(Pos.TOP_CENTER);
        label.setMinHeight(100);
        label.setMinWidth(1100);

        this.vistaListaDashboards = new VistaListaDashboards(this.assoc, this);
        VBox vboxDashboardList = new VBox(10);
        vboxDashboardList.getChildren().add(new Label("DASHBOARDS"));
        vboxDashboardList.getChildren().add(vistaListaDashboards);
        this.vboxDashboardList = vboxDashboardList;

        VBox vboxDashboard = new VBox(5);
        vboxDashboard.setMinSize(900, 700);
        this.vBoxMedio = vboxDashboard;

        VBox vboxwelcome = new VBox(10);
        Label welcome = new Label("Administrador");
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(new Logout(mainApp));

        vboxwelcome.getChildren().add(welcome);
        vboxwelcome.getChildren().add(btnLogout);

        HBox hboxUp = new HBox(label, vboxwelcome);
        HBox hboxdown = new HBox(vboxDashboardList, this.vBoxMedio);
        this.hboxdown = new HBox(this.vboxDashboardList, this.vBoxMedio);
        this.hboxdown.setSpacing(30);

        this.setSpacing(10);
        this.getChildren().add(hboxUp);
        this.getChildren().add(this.hboxdown);
    }


}
