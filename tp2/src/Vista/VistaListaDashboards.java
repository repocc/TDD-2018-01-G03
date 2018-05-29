package tp2.src.Vista;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tp2.src.Controlador.MostrarDashboard;
import tp2.src.Model.MonitorSystem.Admin;

public class VistaListaDashboards extends VBox {

    private Admin admin;
    private VistaAdmin vistaAdmin;

    public VistaListaDashboards(Admin admin,VistaAdmin vistaAdmin){
        this.admin = admin;
        this.vistaAdmin = vistaAdmin;
        this.setMinSize(100,650);
        this.setSpacing(5);
        this.actualizar();
    }

    public void actualizar() {
        this.getChildren().clear();
        for(int i=0;i< this.admin.getDashboards().size();i++){
            String dashboardName = this.admin.getDashboards().get(i).getName();
            Button btnDashboard = new Button(dashboardName);
            btnDashboard.setOnAction(new MostrarDashboard(this.admin,dashboardName,this.vistaAdmin));
            this.getChildren().add(btnDashboard);
        }

    }
}
