package tp2.src.Model.MonitorSystem;

import org.json.simple.JSONObject;
import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Engine {
    private MonitorSystem monitorSystem;
    public Conector conector;

    public Engine(MonitorSystem monitorSystem) {
        this.conector = new Conector();
        this.monitorSystem = monitorSystem;
    }

    public JSONObject JsonRule(Rule rule){
        JSONObject ruleJson = new JSONObject();
        ruleJson.put("name",rule.name);
        ruleJson.put("condition",rule.condition);
        ruleJson.put("params",rule.params);
        ruleJson.put("type",rule.type);
        return ruleJson;
    }

    public List<String> sendRules(ArrayList<Rule> rules){
        ArrayList<String> serverAnswers = new ArrayList<String>();
        Iterator<Rule> it = rules.iterator();
        while (it.hasNext()){
            serverAnswers.add(this.conector.setRule(JsonRule(it.next())));
        }
        return serverAnswers;
    }

    private JSONObject JsonTicket(Ticket ticket) {
        JSONObject ticketJson = new JSONObject();
        ticketJson.put(ticket.getActualState().getTittle(), true);
        return ticketJson;
    }

    public float getRuleValue(String ruleName) {
        JSONObject json = new JSONObject();
        json.put("rule-name", ruleName);
        return this.conector.getRuleValue(json);
    }


    public void updateQueriesResult() {
        ArrayList<Query> queries = this.monitorSystem.getQueries();
        for (Query query : queries) {
            query.updateResult(getRuleValue(query.getRule()));
        }
    }

    public void updateQueries(Ticket ticketModified) throws RuleNotFoundException {

        this.conector.proccessTicket(JsonTicket(ticketModified));
        this.conector.calculateLastSignal();

        this.updateQueriesResult();

    }



}
