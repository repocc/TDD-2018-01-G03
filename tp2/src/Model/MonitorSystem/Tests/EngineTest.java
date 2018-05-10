package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.*;

import java.util.ArrayList;

public class EngineTest extends TestCase {

    private Engine engine;
    private MonitorSystem monitorSystem;
    public void setUp() throws Exception {
        super.setUp();
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
    }

    public void testUpdateQueries() {
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

    public void testSendTickets(){
        engine.conector.initializeProcessor();
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        engine.sendTickets(tickets);
        System.out.println(engine.conector.getLastState());
//        TODO: checkear
    }

    public void testGetLastSignal(){
        engine.conector.initializeProcessor();
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        engine.sendTickets(tickets);

        System.out.println(engine.conector.getLastSignal());
//        TODO: checkear
    }


    public void testGetCounterValue(){
        engine.conector.initializeProcessor();
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        engine.sendTickets(tickets);
        assertEquals(3.0, engine.getCounterValue("open-count"), 0);
    }

    public void testCalculateLastSignal(){
        engine.conector.initializeProcessor();
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        engine.sendTickets(tickets);
        assertEquals("[{\"open-fraction\":0.75}]" , this.engine.conector.calculateLastSignal());
    }

    public void testRuleValue(){
        engine.conector.initializeProcessor();
        Ticket t0 = new Ticket(0,new TicketState("OPEN"));
        Ticket t1 = new Ticket(1,new TicketState("CLOSE"));
        Ticket t2 = new Ticket(2,new TicketState("OPEN"));
        Ticket t3 = new Ticket(3,new TicketState("OPEN"));
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        tickets.add(t0);
        tickets.add(t1);
        tickets.add(t2);
        tickets.add(t3);
        engine.sendTickets(tickets);
        this.engine.conector.calculateLastSignal();
        assertEquals(0.75 , this.engine.getRuleValue("open-fraction"), 0);
    }




}
