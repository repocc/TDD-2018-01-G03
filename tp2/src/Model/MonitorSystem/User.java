package tp2.src.Model.MonitorSystem;

import java.util.List;

public abstract class User {
    protected String name;
    protected MonitorSystem monitorSystem;

    public User(String name, MonitorSystem monitorSystem) {
        this.monitorSystem = monitorSystem;
        this.name = name;
    }

    public abstract Dashboard getDashboard(String dashboardName);

    public abstract List<Dashboard> getDashboards();
}
