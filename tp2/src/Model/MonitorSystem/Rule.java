package tp2.src.Model.MonitorSystem;

public class Rule {
    public String type;
    public String name;
    public String condition;
    public String params;

    public Rule(String type, String name, String condition, String params) {
        this.type = type;
        this.name = name;
        this.condition = condition;
        this.params = params;
    }

    public String getName() {
        return this.name;
    }
}
