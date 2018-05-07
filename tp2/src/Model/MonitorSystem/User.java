package tp2.src.Model.MonitorSystem;

public abstract class User {
    protected String name;
    protected MonitorSystem monitorSystem;

    public User(String name, MonitorSystem monitorSystem) {
        this.monitorSystem = monitorSystem;
        this.name = name;
    }

    public abstract Dashboard getDashboard(String dashboardName);
}
