package tp2.src.Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Model.MonitorSystem.MonitorSystem;

public class VistaAdmin extends VBox {

    private MonitorSystem monitorSystem;
    private Main mainApp;

    public VistaAdmin(Main mainApp, MonitorSystem monitorSystem){

        this.mainApp = mainApp;
        Label label = new Label("G3 - TDD Monitor System");
        label.setFont(Font.font("Cambria", FontWeight.BOLD, 35));
        label.setAlignment(Pos.CENTER);
        label.setMinHeight(50);
        label.setMinWidth(1200);

        ListView listViewDashboards = new ListView();
        listViewDashboards.setMinSize(100,700);
        VBox vboxDashboardList = new VBox(5);
        vboxDashboardList.getChildren().add(new Label("DASHBOARDS"));
        vboxDashboardList.getChildren().add(listViewDashboards);

        VBox vboxDashboard = new VBox(5);
        vboxDashboard.setMinSize(700,700);

        ListView listViewQueries = new ListView();
        listViewQueries.setMinSize(100,650);
        VBox vboxQueries = new VBox(5);
        Button btnLogout = new Button("Logout");
        vboxQueries.getChildren().add(btnLogout);
        vboxQueries.getChildren().add(new Label("QUERIES"));
        vboxQueries.getChildren().add(listViewQueries);


        HBox hbox = new HBox(vboxDashboardList,vboxDashboard,vboxQueries);


        this.setSpacing(10);
        this.getChildren().add(label);
        this.getChildren().add(hbox);



    }



}
