package tp2.src.Model.MonitorSystem;

public class Associated extends User{

    public Associated(String name, MonitorSystem monitorSystem) {
        super(name, monitorSystem);
    }

    @Override
    public Dashboard getDashboard(String dashboardName) {
        return null;
    }


}
