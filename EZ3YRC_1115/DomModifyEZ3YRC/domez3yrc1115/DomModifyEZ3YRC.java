import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class DomModifyEZ3YRC {

    public static void main(String[] args) {
        try {
            // Specify the path to your XML file
            String filePath = "C:\\Users\\Laptop\\Downloads\\EZ3YRC_XMLGyak\\EZ3YRC_1115\\DomModifyEZ3YRC\\EZ3YRC_kurzusfelvetel.xml";

            // Parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // Modify at least two instances by adding the <oraado> element
            modifyCourse(document, "1", "Fazekas Levente");
            modifyCourse(document, "GEIAL322-B", "Fazekas Levente");

            // Modify the language attribute of all courses from English to German
            modifyLanguage(document, "angol", "német");

            // Add a new attribute "szint" with value "haladó" to all courses
            addAttributeToAllCourses(document, "szint", "haladó");

            // Print the modified XML to the console
            printXmlToConsole(document);

            // Save the modified XML to a new file
            saveXmlToFile(document, "kurzusfelvetelModify1Neptunkod.xml");
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }

    }

    private static void modifyCourse(Document document, String courseId, String oraadoContent) {
        NodeList courses = document.getElementsByTagName("kurzus");
        for (int i = 0; i < courses.getLength(); i++) {
            Element course = (Element) courses.item(i);
            String id = course.getAttribute("id");
            if (id.equals(courseId)) {
                // Check if <oraado> element already exists
                if (course.getElementsByTagName("oraado").getLength() == 0) {
                    // Add <oraado> element
                    Element oraado = document.createElement("oraado");
                    oraado.appendChild(document.createTextNode(oraadoContent));
                    course.appendChild(oraado);
                }
            }
        }
    }

    private static void modifyLanguage(Document document, String oldLanguage, String newLanguage) {
        NodeList courses = document.getElementsByTagName("kurzus");
        for (int i = 0; i < courses.getLength(); i++) {
            Element course = (Element) courses.item(i);
            Node languageNode = course.getAttributes().getNamedItem("nyelv");
            if (languageNode != null && oldLanguage.equals(languageNode.getNodeValue())) {
                languageNode.setNodeValue(newLanguage);
            }
        }
    }

    private static void addAttributeToAllCourses(Document document, String attributeName, String attributeValue) {
        NodeList courses = document.getElementsByTagName("kurzus");
        for (int i = 0; i < courses.getLength(); i++) {
            Element course = (Element) courses.item(i);
            course.setAttribute(attributeName, attributeValue);
        }
    }

    private static void printXmlToConsole(Document document) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult consoleResult = new StreamResult(System.out);

        transformer.transform(source, consoleResult);
    }

    private static void saveXmlToFile(Document document, String fileName) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        DOMSource source = new DOMSource(document);
        StreamResult fileResult = new StreamResult(new File(fileName));

        transformer.transform(source, fileResult);
    }
}
