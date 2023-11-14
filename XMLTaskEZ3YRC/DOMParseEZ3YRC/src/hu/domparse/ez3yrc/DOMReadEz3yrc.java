package hu.domparse.ez3yrc;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class DOMReadEz3yrc {
    public static void main(String[] args) {
        try {
            File xmlFile = new File("XMLEZ3YRC.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            String[] sub_roots = { "Pizzazo", "Beszallitas", "Beszallito", "Futar", "Vevo", "Rendeles", "Pizza", "Bankkartya" };

            for (String element : sub_roots) {
                NodeList nList = doc.getElementsByTagName(element);
                for (int i = 0; i < nList.getLength(); i++) {
                    Element elem = (Element) nList.item(i);
                    System.out.println("\nCurrent Element: " + elem.getNodeName());

                    // Handling IDs
                    if (!element.equals("Beszallitas") && !element.equals("Rendeles")) {
                        System.out.println("ID: " + elem.getAttribute(element.toLowerCase() + "_id"));
                    }

                    NodeList childNodes = elem.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        if (childNodes.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                            Element childElement = (Element) childNodes.item(j);
                            String nodeName = childElement.getNodeName();
                            String textContent = childElement.getTextContent();

                            if (nodeName.equals("elerhetoseg") || nodeName.equals("cim")) {
                                System.out.println(nodeName + ":");
                                printSubElements(childElement, "\t");
                            } else {
                                System.out.println("\t" + nodeName + ": " + textContent);
                            }
                        }
                    }
                }

                if (element.equals("Bankkartya")) {
                    // Handle Bankkartya fields
                    NodeList nListt = doc.getElementsByTagName("Bankkartya");
                    for (int i = 0; i < nListt.getLength(); i++) {
                        Element bankkartya = (Element) nListt.item(i);
                        System.out.println("\nCurrent Element: " + bankkartya.getNodeName());
                        System.out.println("ID: " + bankkartya.getAttribute("kartyaszam"));

                        NodeList childNodes = bankkartya.getChildNodes();
                        for (int j = 0; j < childNodes.getLength(); j++) {
                            if (childNodes.item(j).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                                Element childElement = (Element) childNodes.item(j);
                                System.out.println("\t" + childElement.getNodeName() + ": " + childElement.getTextContent());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printSubElements(Element element, String indent) {
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element childElement = (Element) childNodes.item(i);
                System.out.println(indent + childElement.getNodeName() + ": " + childElement.getTextContent());
            }
        }
    }
}
