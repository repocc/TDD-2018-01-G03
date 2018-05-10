package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.*;

import java.time.Duration;
import java.util.ArrayList;

public class EntregaTest extends TestCase{
    private MonitorSystem monitorSystem;
    private Engine engine;
    private TicketsDealer ticketsDealer;
    private TicketTranslator tickeySysyemG3Traslator;
    private ArrayList<Ticket> ticketsMock;
    public void setUp() throws Exception {
        super.setUp();
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
        this.ticketsDealer = new TicketsDealer(engine);
        this.tickeySysyemG3Traslator = new TicketSystemG3Translator(ticketsDealer);

    }


    public void testMonitorSystem(){
        Admin admin = new Admin("U1", this.monitorSystem );
        this.monitorSystem.addUser(admin);
        admin.addDashboard(new Dashboard("D1"));

        Rule rule0 = new Rule("define-counter","open-count","(current \"OPEN\")","[]");
        Rule rule1 = new Rule("define-counter","close-count","(current \"CLOSE\")","[]");
        Rule rule2 = new Rule("define-signal","open-fraction","true", "(/ (counter-value \"open-count\" []) (counter-value \"tickets-count\" []))");


        Query query0 = new Query("open-count", Duration.ofSeconds(10), rule0);
        Query query1 = new Query("close-count", Duration.ofSeconds(10), rule1);
        Query query2 = new Query("open-fraction", Duration.ofSeconds(10), rule2);

        admin.defineQuery(query0, "D1");
        admin.defineQuery(query1, "D1");
        admin.defineQuery(query2, "D1");

        //ADD TICKETS
        this.tickeySysyemG3Traslator.addTicket(new Ticket(0,new TicketState("OPEN")));
        assertEquals(1.0, this.engine.getRuleValue("open-count"),0);
        assertEquals(0, this.engine.getRuleValue("close-count"),0);
        assertEquals(1.0, this.engine.getRuleValue("open-fraction"), 0);

        this.tickeySysyemG3Traslator.addTicket(new Ticket(1,new TicketState("CLOSE")));
        assertEquals(1.0, this.engine.getRuleValue("open-count"),0);
        assertEquals(1.0, this.engine.getRuleValue("close-count"),0);
        assertEquals(0.5, this.engine.getRuleValue("open-fraction"), 0);

        this.tickeySysyemG3Traslator.addTicket(new Ticket(2,new TicketState("OPEN")));
        assertEquals(2.0, this.engine.getRuleValue("open-count"),0);
        assertEquals(1.0, this.engine.getRuleValue("close-count"),0);
        assertEquals(0.6, this.engine.getRuleValue("open-fraction"), 0.1);

        this.tickeySysyemG3Traslator.addTicket(new Ticket(3,new TicketState("OPEN")));
        assertEquals(3.0, this.engine.getRuleValue("open-count"),0);
        assertEquals(1.0, this.engine.getRuleValue("close-count"),0);
        assertEquals(0.75, this.engine.getRuleValue("open-fraction"), 0);

        //REMOVE TICKET
        this.tickeySysyemG3Traslator.removeTicket(1);
        assertEquals(3.0, this.engine.getRuleValue("open-count"),0);
        assertEquals(0, this.engine.getRuleValue("close-count"),0);
        assertEquals(1, this.engine.getRuleValue("open-fraction"), 0);

    }



}
