package tp2.src.Model.MonitorSystem;

import tp2.src.Model.MonitorSystem.Exceptions.DashboardNotFoundException;
import tp2.src.Model.MonitorSystem.Exceptions.RuleNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MonitorSystem {
    private List<User> users;
    private Engine engine;
    private List<Rule> availableRules;

    public MonitorSystem() {
        this.users = new ArrayList<User>();
        this.engine = new Engine(this);
        this.availableRules =  this.buildRulesFromXML();
        this.engine.sendRules((ArrayList<Rule>) availableRules);
        this.engine.conector.initializeProcessor();
    }

    private List<Rule> buildRulesFromXML() {
        return (new xmlParser()).buildRules();
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public Dashboard getDashboard(String dashboardName) throws DashboardNotFoundException {
        for (User user : this.users) {
            Dashboard dashboard = user.getDashboard(dashboardName);
            if(dashboard != null){
               return  dashboard;
            }
        }
        throw new DashboardNotFoundException(dashboardName);
    }

    public ArrayList<Dashboard> getDashboards(){
        ArrayList<Dashboard> dashboards = new ArrayList<Dashboard>();
        Iterator<User> it = this.users.iterator();
        while (it.hasNext()) {
            dashboards.addAll(it.next().getDashboards());
        }
        return dashboards;
    }

    public ArrayList<Query> getQueries() {

        ArrayList<Query> queries = new ArrayList<Query>();
        Iterator<Dashboard> it = this.getDashboards().iterator();
        while (it.hasNext()){
            queries.addAll(it.next().getQueries());
        }
        return queries;
    }

    public Rule getRule(String ruleName) throws RuleNotFoundException {
        for (Rule rule : this.availableRules) {
            if(rule.name.equalsIgnoreCase(ruleName)){
                return  rule;
            }
        }
        throw new RuleNotFoundException(ruleName);
    }
}
