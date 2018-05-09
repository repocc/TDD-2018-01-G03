package tp2.src.Model.MonitorSystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Engine {
    private MonitorSystem monitorSystem;

    public Engine(MonitorSystem monitorSystem) {
        this.monitorSystem = monitorSystem;
    }

    public ArrayList<Rule> getRulesFromQueries(ArrayList<Query> queries){
        ArrayList<Rule> rules = new ArrayList<Rule>();
        Iterator<Query> it = queries.iterator();
        while (it.hasNext()){
            rules.add(it.next().getRule());
        }
        return rules;
    }

    public void sendRules(ArrayList<Rule> rules){
        Iterator<Rule> it = rules.iterator();
        while (it.hasNext()){
//            url(parseRule(it.next()));
        }
    }

    public String parseRule(Rule rule){
        //TODO
        return "";
    }

    public void updateQueries(List<Ticket> tickets){
        ArrayList<Query> queries = this.monitorSystem.getQueries();
//        ArrayList<JSONObject> rules = this.getRulesFromQueries(queries);
    }
}
