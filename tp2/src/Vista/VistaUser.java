package tp2.src.Vista;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class VistaUser extends VBox {

    VBox vboxDashboardList;
    VBox vBoxMedio;
    HBox hboxdown;

    public void setearPaginaDashboard(VBox vistaDashboard) {
        this.vBoxMedio = vistaDashboard;
        this.hboxdown.getChildren().clear();
        this.hboxdown.getChildren().addAll(vboxDashboardList,this.vBoxMedio);
    }
}
