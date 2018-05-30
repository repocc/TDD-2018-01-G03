package tp2.src.Model.MonitorSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


public class xmlParser {

    public String getElementByTagName(String tagName, Element eElement){
        return eElement
                .getElementsByTagName(tagName)
                .item(0)
                .getTextContent();

    }

    public List<Rule> buildRules() {
        List<Rule> rules = new ArrayList<Rule>();
        try {
            File inputFile = new File("tp2/src/Model/MonitorSystem/rules.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Rule");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nBuilding " + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Rule rule = new Rule(
                            getElementByTagName("type",eElement),
                            getElementByTagName("name",eElement),
                            getElementByTagName("condition",eElement),
                            getElementByTagName("params",eElement));

                    rules.add(rule);

                    System.out.println("name : "
                            + rule.name);
                    System.out.println("condition : "
                            +  rule.condition);
                    System.out.println("Params : "
                            + rule.params);
                    System.out.println("Type : "
                            + rule.type);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rules;
    }
}
