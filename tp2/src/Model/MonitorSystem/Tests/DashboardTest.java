package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.Dashboard;

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

    }
}