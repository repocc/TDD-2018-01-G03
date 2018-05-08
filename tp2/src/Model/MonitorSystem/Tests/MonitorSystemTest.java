package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.Admin;
import tp2.src.Model.MonitorSystem.MonitorSystem;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Model.MonitorSystem.Rule;

import java.time.Duration;

public class MonitorSystemTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        MonitorSystem monitorSystem = new MonitorSystem();
        Admin admin = new Admin("U1",monitorSystem);
        admin.addDashboard("D1");
        Query q1 = new Query("ticketsOpened", Duration.ofSeconds(1),new Rule());
        admin.defineQuery(q1, "D1");
    }

    public void testAddUser() {
    }

    public void testGetDashboard() {
    }
}