package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Admin extends User {

    private List<Dashboard> dashboards;

    public Admin(String name, MonitorSystem monitorSystem) {
        super(name, monitorSystem);
        this.dashboards = new ArrayList<Dashboard>();
    }


    @Override
    public Dashboard getDashboard(String dashboardName) {
        for (Dashboard dashboard : this.dashboards) {
            if(dashboard.equals(dashboardName)){
                return dashboard;
            }
        }
        return null;
    }

    @Override
    public List<Dashboard> getDashboards() {
        return this.dashboards;
    }

    public void addDashboard(Dashboard dashboard){
        this.dashboards.add(dashboard);
    }

    public void defineQuery(Query query, String dashboardName){
        for (Dashboard dashboard : this.dashboards) {
            if(dashboard.equals(dashboardName)){
                dashboard.addQuery(query);
            }
        }
    }
    public void enableQuery(String queryName){
        Iterator<Dashboard> it = this.dashboards.iterator();
        while (it.hasNext()) {
            it.next().enableQuery(queryName);
            break;
        }
    }

    public void disableQuery(String queryName){
        for (Dashboard dashboard : this.dashboards) {
            dashboard.disableQuery(queryName);
        }
    }
    public void newDashboard(String name){
        this.dashboards.add(new Dashboard(name));
    }

    public void removeDashboard(String name){
        Iterator<Dashboard> it = this.dashboards.iterator();
        while (it.hasNext()) {
            if(it.next().equals(name)){
                it.remove();
                break;
            }
        }
    }
    public void disableDashboard(String dashboardName) {
        for (Dashboard dashboard : this.dashboards) {
            if(dashboard.equals(dashboardName)){
                dashboard.disable();
            }
        }
    }

    public void addDashboard(String name) {
        Dashboard dashboard = new Dashboard(name);
        this.addDashboard(dashboard);
    }
}
