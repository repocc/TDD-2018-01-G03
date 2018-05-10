package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.*;

import java.time.Duration;
import java.util.ArrayList;

public class EngineTest extends TestCase {

    private Engine engine;
    private MonitorSystem monitorSystem;
    public void setUp() throws Exception {
        super.setUp();
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
    }

    public void testSendRules() {

        Rule rule0 = new Rule("define-counter","open-count","(current \"OPEN\")","[]");
        Rule rule1 = new Rule("define-counter","close-count","(current \"CLOSE\")","[]");
        Rule rule2 = new Rule("define-signal","open-fraction","true", "(/ (counter-value \"open-count\" []) (counter-value \"tickets-count\" []))");
        ArrayList<Rule> rules = new ArrayList<Rule>();
        rules.add(rule0);
        rules.add(rule1);
        rules.add(rule2);
        System.out.println(engine.sendRules(rules));
    }

    public ArrayList<Ticket> getListTicketsMock(){
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        return tickets;
    }

    public void sendTicketsMock(){
        engine.conector.initializeProcessor();
        engine.sendTickets(this.getListTicketsMock());
    }

    public void testSendTickets(){
        this.sendTicketsMock();
        System.out.println(engine.conector.getLastState());
//        TODO: checkear
    }

    public void testGetLastSignal(){
       this.sendTicketsMock();
        System.out.println(engine.conector.getLastSignal());
//        TODO: checkear
    }


    public void testGetCounterValue(){
        this.sendTicketsMock();
        assertEquals(3.0, engine.getCounterValue("open-count"), 0);
    }

    public void testCalculateLastSignal(){
       this.sendTicketsMock();
        assertEquals("[{\"open-fraction\":0.75}]" , this.engine.conector.calculateLastSignal());
    }

    public void testRuleValue(){
        this.sendTicketsMock();
        this.engine.conector.calculateLastSignal();

        assertEquals(1.0 , this.engine.getRuleValue("close-count"), 0);
        assertEquals(3.0 , this.engine.getRuleValue("open-count"), 0);
        assertEquals(0.75 , this.engine.getRuleValue("open-fraction"), 0);
    }


    public void testUpdateQueriesOneDashboard() {
        Admin admin = new Admin("ADMIN", this.monitorSystem);
        admin.addDashboard(new Dashboard("DASH1"));
        this.monitorSystem.addUser(admin);
        Query query0 = new Query("open-count", Duration.ofSeconds(10), new Rule("define-counter","open-count","(current \"OPEN\")","[]"));
        Query query1 = new Query("open-count", Duration.ofSeconds(10), new Rule("define-counter","close-count","(current \"CLOSE\")","[]"));
        admin.defineQuery(query0,"DASH1");
        admin.defineQuery(query1,"DASH1");
        this.engine.updateQueries(this.getListTicketsMock());
        assertEquals(3.0, query0.getResults().get(0).value, 0);
        assertEquals(1.0, query1.getResults().get(0).value, 0);
    }


    public void testUpdateQueriesMultipleDashboards() {
        Admin admin = new Admin("ADMIN", this.monitorSystem);
        admin.addDashboard(new Dashboard("DASH1"));
        admin.addDashboard(new Dashboard("DASH2"));
        this.monitorSystem.addUser(admin);
        Query query0 = new Query("open-count", Duration.ofSeconds(10), new Rule("define-counter","open-count","(current \"OPEN\")","[]"));
        Query query1 = new Query("open-count", Duration.ofSeconds(10), new Rule("define-counter","close-count","(current \"CLOSE\")","[]"));
        admin.defineQuery(query0,"DASH1");
        admin.defineQuery(query1,"DASH2");
        this.engine.updateQueries(this.getListTicketsMock());
        assertEquals(3.0, query0.getResults().get(0).value, 0);
        assertEquals(1.0, query1.getResults().get(0).value, 0);
    }



}
