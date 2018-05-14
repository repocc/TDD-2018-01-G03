package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.*;
import tp2.src.Model.MonitorSystem.Exceptions.NotFoundException;

import java.time.Duration;
import java.util.List;

public class EntregaTest extends TestCase{
    private MonitorSystem monitorSystem;
    private Engine engine;
    private TicketsDealer ticketsDealer;
    private TicketTranslator tickeySysyemG3Traslator;

    public void setUp() throws Exception {
        super.setUp();
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
        this.ticketsDealer = new TicketsDealer(engine);
        this.tickeySysyemG3Traslator = new TicketSystemG3Translator(ticketsDealer);
    }

    public void testMonitorSystem() {
        Admin admin = new Admin("U1", this.monitorSystem);
        this.monitorSystem.addUser(admin);
        admin.addDashboard(new Dashboard("D1"));

        try {
            Rule rule0 = this.monitorSystem.getRule("open-count");
            Rule rule1 = this.monitorSystem.getRule("close-count");
            Rule rule2 = this.monitorSystem.getRule("open-fraction");


            Query query0 = new Query("open-count", Duration.ofSeconds(10), "open-count");
            Query query1 = new Query("close-count", Duration.ofSeconds(10), "close-count");
            Query query2 = new Query("open-fraction", Duration.ofSeconds(10), "open-fraction");

            admin.defineQuery(query0, "D1");
            admin.defineQuery(query1, "D1");
            admin.defineQuery(query2, "D1");

            //ADD TICKETS
            this.tickeySysyemG3Traslator.addTicket(new Ticket(0, new TicketState("OPEN")));
            assertEquals(1.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("open-fraction"), 0);

            this.tickeySysyemG3Traslator.addTicket(new Ticket(1, new TicketState("CLOSE")));
            assertEquals(1.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0, this.engine.getRuleValue("open-fraction"), 0);

            this.tickeySysyemG3Traslator.addTicket(new Ticket(2, new TicketState("OPEN")));
            assertEquals(2.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.5, this.engine.getRuleValue("open-fraction"), 0);

            Ticket t = new Ticket(3, new TicketState("OPEN"));
            this.tickeySysyemG3Traslator.addTicket(t);
            assertEquals(3.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.7, this.engine.getRuleValue("open-fraction"), 0.1);

            //REMOVE TICKET
            t.remove();
            this.tickeySysyemG3Traslator.ticketUpdate(t);
            assertEquals(3.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1, this.engine.getRuleValue("remove-count"), 0);
            assertEquals(1, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.7, this.engine.getRuleValue("open-fraction"), 0);

            //UPDATE TICKET
            Ticket ticket4 = new Ticket(4, new TicketState("OPEN"));
            this.tickeySysyemG3Traslator.addTicket(ticket4);
            assertEquals(4.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.7, this.engine.getRuleValue("open-fraction"), 0);

            ticket4.changeState(new TicketState("CLOSE"));
            this.tickeySysyemG3Traslator.ticketUpdate(ticket4);
            assertEquals(4.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(2, this.engine.getRuleValue("close-count"), 0);
            assertEquals(3, this.engine.getRuleValue("open-actual-count"), 0);
            assertEquals(0.75, this.engine.getRuleValue("open-fraction"), 0);


            //RESULTS
            List<Dashboard> dashboards = admin.getDashboards();

            for (Dashboard dashboard : dashboards) {

                for (Query query : dashboard.getQueries()) {
                    System.out.println(query.getRule());
                    for (Result result : query.getResults()) {
                        System.out.print(result.value);
                        System.out.print("   ");
                        System.out.println(result.dateTimeRecorded);
                    }
                }
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
