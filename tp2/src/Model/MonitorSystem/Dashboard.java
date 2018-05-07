package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dashboard {
    private String name;
    private List<Query> queries;

    public Dashboard(String name) {
        this.name = name;
        this.queries = new ArrayList<Query>();
    }

    public boolean equals(String name) {
        if (this.name == name) return true;
        return false;
    }


    public void addQuery(Query query) {
    }
}
