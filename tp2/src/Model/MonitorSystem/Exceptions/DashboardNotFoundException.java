package tp2.src.Model.MonitorSystem.Exceptions;

public class DashboardNotFoundException extends Throwable {
    public DashboardNotFoundException(String dashboardName) {
        System.out.println("There is not any dashboard called: " + dashboardName);
    }
}
