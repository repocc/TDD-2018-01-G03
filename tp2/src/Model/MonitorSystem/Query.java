package tp2.src.Model.MonitorSystem;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Query {
    private String name;
    private Duration interval;
    private Rule rule;
    private List<Result> results;
    private QueryState state;

    public Query(String name, Duration interval, Rule rule){
        this.name = name;
        this.interval = interval;
        this.rule = rule;
        this.results = new ArrayList<Result>();
        this.state = QueryState.ENABLE;
    }

    public List<Result> getResults() {
        return results;
    }

    public Rule getRule() {
        return rule;
    }

    public Duration getInterval() {
        return interval;
    }

    public boolean equals(String queryName) {
        return (queryName == this.name);
    }

    public void enable() {
        this.state = QueryState.ENABLE;
    }

    public void disable() { this.state = QueryState.DISABLE;}

    public boolean isEnable(){
        return (this.state == QueryState.ENABLE);
    }

    public void updateResult(float ruleValue) {
        Result newResult = new Result(ruleValue);
        this.results.add(newResult);
    }
}
