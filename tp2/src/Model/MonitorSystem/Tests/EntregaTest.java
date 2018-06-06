package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.*;
import tp2.src.Model.MonitorSystem.Exceptions.NotFoundException;

import java.time.Duration;
import java.util.List;

public class EntregaTest extends TestCase{
    private MonitorSystem monitorSystem;
    private Engine engine;

    public void setUp() throws Exception {
        super.setUp();
        this.monitorSystem = new MonitorSystem();
        this.engine = new Engine(monitorSystem);
    }

    public void testMonitorSystem() { //TODO puntual test
        Admin admin = new Admin("U1", this.monitorSystem);
        this.monitorSystem.addUser(admin);
        admin.addDashboard(new Dashboard("D1"));

        try {

            Query query0 = new Query("open-count", Duration.ofSeconds(10), "open-count");
            Query query1 = new Query("close-count", Duration.ofSeconds(10), "close-count");
            Query query2 = new Query("open-fraction", Duration.ofSeconds(10), "open-fraction");

            admin.defineQuery(query0, "D1");
            admin.defineQuery(query1, "D1");
            admin.defineQuery(query2, "D1");


            this.engine.updateQueries((new Ticket( new TicketState("OPEN"))));
            assertEquals(1.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("open-fraction"), 0);

            this.engine.updateQueries((new Ticket( new TicketState("CLOSE"))));
            assertEquals(1.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0, this.engine.getRuleValue("open-fraction"), 0);

            this.engine.updateQueries((new Ticket( new TicketState("OPEN"))));
            assertEquals(2.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.5, this.engine.getRuleValue("open-fraction"), 0);

            this.engine.updateQueries((new Ticket( new TicketState("OPEN"))));
            assertEquals(3.0, this.engine.getRuleValue("open-count"), 0);
            assertEquals(1.0, this.engine.getRuleValue("close-count"), 0);
            assertEquals(0.7, this.engine.getRuleValue("open-fraction"), 0.1);


            //RESULTS
            List<Dashboard> dashboards = admin.getOwnDashboards();

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
