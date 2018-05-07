package tp2.src.Model.TicketSystem;
import java.time.Duration;

public class Query {
    private String name;
    private Duration interval;
    private Rule rule;

    public Query(String name, Duration interval, Rule rule){
        this.name = name;
        this.interval = interval;
        this.rule = rule;
    }
    public float evalQuery(){
        return 0;
    }
}
