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
            if(query.getName().equals(queryName)){
                query.enable();
            }
        }
    }
    public void disableQuery(String queryName) {
        for (Query query : this.queries) {
            if(query.getName().equals(queryName)){
                query.disable();
            }
        }
    }

    public void removeQuery(Query query){
       if(hasQuery(query.getName())){
           this.queries.remove(query);
        }
    }

    public String getName(){
        return this.name;
    }
    public void enable() {
        this.state = DashboardState.ENABLE;
    }
    public void disable() {
        this.state = DashboardState.DISABLE;
    }

    public boolean isEnable() {
        return (this.state == DashboardState.ENABLE);
    }

    public boolean hasQuery(String name) {
        for(int i=0;i<queries.size();i++){
            if(queries.get(i).getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
