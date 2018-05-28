package tp2.src.Vista;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tp2.src.Controlador.AgregarDashboard;
import tp2.src.Controlador.Logout;
import tp2.src.Model.MonitorSystem.MonitorSystem;

public class VistaAdmin extends VBox {

    private MonitorSystem monitorSystem;
    private Main mainApp;
    private VistaListaDashboards vistaListaDashboards;
    private VBox vboxDashboardList;
    private VBox vBoxMedio;
    private HBox hboxdown;

    public VistaAdmin(Main mainApp, MonitorSystem monitorSystem){

        this.mainApp = mainApp;
        Label label = new Label("G3 - TDD Monitor System");
        label.setFont(Font.font("Cambria", FontWeight.BOLD, 35));
        label.setAlignment(Pos.TOP_CENTER);
        label.setMinHeight(100);
        label.setMinWidth(1100);

        Button btnAdd = new Button("Agregar Dashboard");
        btnAdd.setOnAction(new AgregarDashboard(this));
        this.vistaListaDashboards = new VistaListaDashboards();
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
        this.vBoxMedio = new VistaNuevoDashboard(this);
    }


    public void actualizarListaDeDashboards() {

    }

    public void setearPaginaDashboard(VistaNuevoDashboard vistaNuevoDashboard) {
        this.vBoxMedio = vistaNuevoDashboard;
        this.hboxdown.getChildren().clear();
        this.hboxdown.getChildren().addAll(vboxDashboardList,this.vBoxMedio);
        System.out.println("Estoy aca");

    }


}
