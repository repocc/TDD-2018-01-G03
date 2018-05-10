package tp2.src.Model.MonitorSystem;

import org.json.simple.JSONObject;

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

    public ArrayList<Rule> getRulesFromQueries(ArrayList<Query> queries){
        ArrayList<Rule> rules = new ArrayList<Rule>();
        Iterator<Query> it = queries.iterator();
        while (it.hasNext()){
            rules.add(it.next().getRule());
        }
        return rules;
    }

    public JSONObject JsonRule(Rule rule){
        JSONObject ruleJson = new JSONObject();
        ruleJson.put("name",rule.name);
        ruleJson.put("condition",rule.condition);
        ruleJson.put("params",rule.params);
        ruleJson.put("type",rule.type);
        return ruleJson;
    }

    public ArrayList<String> sendRules(ArrayList<Rule> rules){
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

    public void sendTickets(List<Ticket> tickets) {
        Iterator<Ticket> it = tickets.iterator();
        while (it.hasNext()) {
            this.conector.proccessTicket(JsonTicket(it.next()));
        }
    }

    public float getCounterValue(String counterName){
        JSONObject json = new JSONObject();
        json.put("counter-name", counterName);
        return this.conector.getCounterValue(json);
    }

    public void updateQueries(List<Ticket> tickets){
        ArrayList<Query> queries = this.monitorSystem.getQueries();
        ArrayList<Rule> rules = this.getRulesFromQueries(queries);
        this.sendRules(rules);
        this.conector.initializeProcessor();
        this.sendTickets(tickets);
        this.conector.calculateLastSignal();



    }

    public float getRuleValue(String ruleName) {
        JSONObject json = new JSONObject();
        json.put("rule-name", ruleName);
        return this.conector.getRuleValue(json);
    }
}
