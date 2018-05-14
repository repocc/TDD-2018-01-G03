package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.Dashboard;
import tp2.src.Model.MonitorSystem.Query;
import tp2.src.Model.MonitorSystem.Rule;

import java.time.Duration;

public class DashboardTest extends TestCase {
    private Dashboard dummyDashboard;
    public void setUp() throws Exception {
        super.setUp();
        this.dummyDashboard = new Dashboard("d1");
    }

    public void testEquals() {
        assertTrue(dummyDashboard.equals("d1"));
    }

    public void testAddQuery() {
        Duration duration =  Duration.ofHours(1);
        Query query = new Query("countTickets", duration, "open-count" );
        this.dummyDashboard.addQuery(query);
        assertEquals(1, this.dummyDashboard.getQueries().size());
    }

    public void testInitiallyEnable(){
        assertTrue(dummyDashboard.isEnable());
    }

    public void testDisable(){
        dummyDashboard.disable();
        assertFalse(dummyDashboard.isEnable());
    }

    public void testEnable(){
        dummyDashboard.disable();
        assertFalse(dummyDashboard.isEnable());
        dummyDashboard.enable();
        assertTrue(dummyDashboard.isEnable());
    }
}