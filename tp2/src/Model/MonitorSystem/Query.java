package tp2.src.Model.MonitorSystem;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Query {
    private String name;
    private String ruleName;
    private Duration interval;
    private List<Result> results;
    private QueryState state;

    public Query(String name, Duration interval, String ruleName){
        this.name = name;
        this.ruleName = ruleName;
        this.interval = interval;
        this.results = new ArrayList<Result>();
        this.state = QueryState.ENABLE;
    }

    public List<Result> getResults() {
        return results;
    }

    public String getRule() {
        return ruleName;
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

    public String getName(){return this.name;}

    public float getLastResult(){
        if(!results.isEmpty()){
            return results.get(results.size()-1).value;

        }
        return 0;
    }
}
