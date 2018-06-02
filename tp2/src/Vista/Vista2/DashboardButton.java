package tp2.src.Vista.Vista2;

import javafx.scene.control.Button;
import tp2.src.Model.MonitorSystem.Dashboard;

public class DashboardButton {
    private Button button;
    private Dashboard dashboard;


    public DashboardButton(javafx.scene.control.Button button, Dashboard dashboard) {
        this.button = button;
        this.dashboard = dashboard;
    }
}
