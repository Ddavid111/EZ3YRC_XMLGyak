package xpathez3yrc;

import org.w3c.dom.*;
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.xpath.*;

public class xPathEZ3YRC {

    public static void main(String[] args) {
        try {
            File inputFile = new File("studentEZ3YRC.xml");
            // Dokumentum elkészítése
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // XPath készítése
            XPath xPath = XPathFactory.newInstance().newXPath();

            System.out.println("1.");
            String IN1BLK = "class/student";
            NodeList nodeList1 = (NodeList) xPath.compile(IN1BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList1);

            System.out.println("\n2.");
            String IN2BLK = "class/student[@id='02']";
            NodeList nodeList2 = (NodeList) xPath.compile(IN2BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList2);

            System.out.println("\n3.");
            String IN3BLK = "//student";
            NodeList nodeList3 = (NodeList) xPath.compile(IN3BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList3);

            System.out.println("\n4.");
            String IN4BLK = "class/student[2]";
            NodeList nodeList4 = (NodeList) xPath.compile(IN4BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList4);

            System.out.println("\n5.");
            String IN5BLK = "class/student[last()]";
            NodeList nodeList5 = (NodeList) xPath.compile(IN5BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList5);

            System.out.println("\n6.");
            String IN6BLK = "class/student[last()-1]";
            NodeList nodeList6 = (NodeList) xPath.compile(IN6BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList6);

            System.out.println("\n7.");
            String IN7BLK = "class/student[position() <= 2]";
            NodeList nodeList7 = (NodeList) xPath.compile(IN7BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList7);

            System.out.println("\n8.");
            String IN8BLK = "class/*";
            NodeList nodeList8 = (NodeList) xPath.compile(IN8BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList8);

            System.out.println("\n9.");
            String IN9BLK = "//student[@*]";
            NodeList nodeList9 = (NodeList) xPath.compile(IN9BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList9);

            System.out.println("\n10.");
            String IN10BLK = "//*";
            NodeList nodeList10 = (NodeList) xPath.compile(IN10BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList10);

            System.out.println("\n11.");
            String IN11BLK = "class/student[kor > 20]";
            NodeList nodeList11 = (NodeList) xPath.compile(IN11BLK).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList11);

            System.out.println("\n12.");
            String EZ3YRC = "//student/keresztnev | //student/vezeteknev";
            NodeList nodeList12 = (NodeList) xPath.compile(EZ3YRC).evaluate(doc, XPathConstants.NODESET);
            writeData(nodeList12);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeData(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            System.out.println("\nAktuális elem: " + node.getNodeName());

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals("student")) {
                Element element = (Element) node;

                System.out.println("Hallgató ID: " + element.getAttribute("id"));

                System.out.println("Vezetéknév: " + element.getElementsByTagName("vezeteknev").item(0).getTextContent());
                System.out.println("Keresztnév: " + element.getElementsByTagName("keresztnev").item(0).getTextContent());

                System.out.println("Becenév : " + element.getElementsByTagName("becenev").item(0).getTextContent());

                System.out.println("Kor : " + element.getElementsByTagName("kor").item(0).getTextContent());
            }
        }
    }
}
