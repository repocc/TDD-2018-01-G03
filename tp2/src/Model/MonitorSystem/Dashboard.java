package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.List;

public class Dashboard {
    private String name;
    private List<Query> queries;
    private DashboardState state;

    public Dashboard(String name) {
        this.name = name;
        this.queries = new ArrayList<Query>();
        this.state =  DashboardState.ENABLE;
    }
    public boolean equals(String name) {
        if (this.name == name) return true;
        return false;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void addQuery(Query query) {
        this.queries.add(query);
    }
    public void enableQuery(String queryName) {
        for (Query query : this.queries) {
            if(query.equals(queryName)){
                query.enable();
            }
        }
    }
    public void disableQuery(String queryName) {
        for (Query query : this.queries) {
            if(query.equals(queryName)){
                query.disable();
            }
        }
    }
    public void enable() {
        this.state = DashboardState.ENABLE;
    }
    public void disable() {
        this.state = DashboardState.DISABLE;
    }
}
