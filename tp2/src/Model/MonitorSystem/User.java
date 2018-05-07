package tp2.src.Model.MonitorSystem;

import java.util.List;

public abstract class User {
    protected String name;
    protected List<Dashboard> dashboards;
    protected MonitorSystem monitorSystem;

    public User(MonitorSystem monitorSystem) {
        this.monitorSystem = monitorSystem;
    }

    public void addDashboard(Dashboard dashboard){
        this.dashboards.add(dashboard);
    }
}
