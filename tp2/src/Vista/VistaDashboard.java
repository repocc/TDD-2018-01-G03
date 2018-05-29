package tp2.src.Vista;

import javafx.scene.layout.VBox;
import tp2.src.Model.MonitorSystem.Admin;

public class VistaDashboard extends VBox {

    private Admin admin;
    private String name;

    public VistaDashboard(Admin admin, String name) {
       this.admin=admin;
       this.name=name;
    }
}
