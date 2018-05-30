package tp2.src.Model.MonitorSystem.Tests;

import junit.framework.TestCase;
import tp2.src.Model.MonitorSystem.xmlParser;

public class xmlParserTest extends TestCase {
    private xmlParser xmlParser = new xmlParser();

    public void testDecodeRules() {
        xmlParser.buildRules();

        //TODO: sacar prints y testear
    }
}