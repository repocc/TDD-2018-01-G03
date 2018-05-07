package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.DashboardNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MonitorSystem {
    private List<User> users;
    private Engine engine;

    public MonitorSystem() {
        this.engine = new Engine();
        this.users = new ArrayList<User>();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Dashboard getDashboard(String dashboardName) throws DashboardNotFoundException {
        for (User user : this.users) {
            Dashboard dashboard = user.getDashboard(dashboardName);
            if(dashboard != null){
               return  dashboard;
            }
        }
        throw new DashboardNotFoundException(dashboardName);
    }
}
