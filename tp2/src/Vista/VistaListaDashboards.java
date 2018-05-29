package tp2.src.Vista;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import tp2.src.Controlador.MostrarDashboard;
import tp2.src.Model.MonitorSystem.User;

public class VistaListaDashboards extends VBox {

    private User user;
    private VistaUser vistaUser;

    public VistaListaDashboards(User user, VistaUser vistaUser){
        this.user = user;
        this.vistaUser = vistaUser;
        this.setMinSize(100,650);
        this.setSpacing(5);
        this.actualizar();
    }

    public void actualizar() {
        this.getChildren().clear();
        for(int i=0;i< this.user.getViewDashboards().size();i++){
            String dashboardName = this.user.getViewDashboards().get(i).getName();
            Button btnDashboard = new Button(dashboardName);
            btnDashboard.setOnAction(new MostrarDashboard(this.user,dashboardName,this.vistaUser));
            this.getChildren().add(btnDashboard);
        }

    }
}
