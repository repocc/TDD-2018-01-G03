package tp2.src.Model.MonitorSystem;

import javax.xml.transform.sax.SAXResult;
import java.util.Iterator;

public class Admin extends User {

    public Admin(MonitorSystem monitorSystem) {
        super(monitorSystem);
    }

    public void defineQuery(Query query, String dashboardName){
        for (Dashboard dashboard : this.dashboards) {
            if(dashboard.equals(dashboardName)){
                dashboard.addQuery(query);
            }
        }
    }
    public void enableQuery(String queryName){
        for (Dashboard dashboard : this.dashboards) {
            dashboard.enableQuery(queryName);
        }
    }

    public void disableQuery(String queryName){
        for (Dashboard dashboard : this.dashboards) {
            dashboard.disable(queryName);
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
}
