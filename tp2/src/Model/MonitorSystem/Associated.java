package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.List;

public class Associated extends User{

    public Associated(String name, MonitorSystem monitorSystem) {
        super(name, monitorSystem);
    }

    @Override
    public Dashboard getDashboard(String dashboardName) {
        return null;
    }

    @Override
    public List<Dashboard> getOwnDashboards() {
        return new ArrayList<>();
    }

    @Override
    public List<Dashboard> getViewDashboards() {
        return this.monitorSystem.getDashboards();
    }

    public boolean logAsAdmin(){
        return false;
    }

}
