package tp2.src.Vista;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Controlador.AgregarDashboard;
import tp2.src.Controlador.Logout;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.MonitorSystem;
import tp2.src.Model.MonitorSystem.Query;

import java.util.ArrayList;
import java.util.HashMap;

public class VistaAdmin extends VistaUser {

    private MonitorSystem monitorSystem;
    private Main mainApp;
    private Admin admin;
    private HashMap<String, Query> queries;
    private VistaListaDashboards vistaListaDashboards;


    public VistaAdmin(Admin admin, HashMap<String, Query> queries, Main mainApp, MonitorSystem monitorSystem){
        this.admin = admin;
        this.mainApp = mainApp;
        this.queries=queries;
        Label label = new Label("G3 - TDD Monitor System");
        label.setFont(Font.font("Cambria", FontWeight.BOLD, 35));
        label.setAlignment(Pos.TOP_CENTER);
        label.setMinHeight(100);
        label.setMinWidth(1100);

        Button btnAdd = new Button("Agregar Dashboard");
        btnAdd.setOnAction(new AgregarDashboard(this.mainApp,this));
        this.vistaListaDashboards = new VistaListaDashboards(this.admin,this);
        VBox vboxDashboardList = new VBox(10);
        vboxDashboardList.getChildren().add(new Label("DASHBOARDS"));
        vboxDashboardList.getChildren().add(btnAdd);
        vboxDashboardList.getChildren().add(vistaListaDashboards);
        this.vboxDashboardList = vboxDashboardList;

        VBox vboxDashboard = new VBox(5);
        vboxDashboard.setMinSize(900,700);
        this.vBoxMedio = vboxDashboard;

        VBox vboxwelcome = new VBox(10);
        Label welcome = new Label("Administrador");
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(new Logout(mainApp));

        vboxwelcome.getChildren().add(welcome);
        vboxwelcome.getChildren().add(btnLogout);

        HBox hboxUp = new HBox(label,vboxwelcome);
        //HBox hboxdown = new HBox(vboxDashboardList,this.vBoxMedio);
        this.hboxdown = new HBox(this.vboxDashboardList,this.vBoxMedio);
        this.hboxdown.setSpacing(30);

        this.setSpacing(10);
        this.getChildren().add(hboxUp);
        this.getChildren().add(this.hboxdown);

    }


    public void agregarDashboardVista(){
        this.vBoxMedio = new VistaNuevoDashboard(this.mainApp,this);
    }


    public void actualizarListaDeDashboards() {
        this.vistaListaDashboards.actualizar();
    }




    public void crearNuevoDashboard(String name, ArrayList<CheckBox> CBqueries){
        this.admin.addDashboard(new Dashboard(name));
        for(int i=0;i<CBqueries.size();i++){
            if(CBqueries.get(i).isSelected()){
                Query query = this.queries.get(CBqueries.get(i).getId());
                this.admin.defineQuery(query,name);
            }
        }

    }

}
