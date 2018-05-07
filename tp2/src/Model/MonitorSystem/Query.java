package tp2.src.Model.MonitorSystem;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Query {
    private String name;
    private Duration interval;
    private Rule rule;
    private List<String> results;

    public Query(String name, Duration interval, Rule rule){
        this.name = name;
        this.interval = interval;
        this.rule = rule;
        this.results = new ArrayList<String>();
    }

    public List<String> getResults() {
        return results;
    }

    public Rule getRule() {
        return rule;
    }

    public Duration getInterval() {
        return interval;
    }
}
