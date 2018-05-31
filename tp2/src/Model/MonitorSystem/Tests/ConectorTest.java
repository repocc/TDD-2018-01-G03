package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import org.json.simple.JSONObject;
import tp2.src.Model.MonitorSystem.Conector;

public class ConectorTest extends TestCase {
    Conector conector;
    public void setUp() throws Exception {
        super.setUp();
        conector = new Conector();
    }

    public void testEcho() {
        JSONObject json = new JSONObject();

        json.put("rule-name", "sdasd");
        this.conector.echo(json);
    }
}