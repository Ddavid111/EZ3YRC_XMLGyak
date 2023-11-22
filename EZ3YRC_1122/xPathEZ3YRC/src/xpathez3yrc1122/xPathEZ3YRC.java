package xpathez3yrc1122;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;
import java.io.*;

public class xPathEZ3YRC {

    public static void main(String[] args) {
        try {
            // Betöltjük az XML dokumentumot
            File inputFile = new File("kurzusfelvetelEZ3YRC.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // XPath objektum létrehozása
            XPath xPath = XPathFactory.newInstance().newXPath();

            // Módosítások végrehajtása
            addNewCourse(doc, xPath);
            deleteCourse(doc, xPath);
            modifyCourse(doc, xPath);

            // Eredmények kiírása a konzolra
            printToConsole(doc);

            // Eredmények kiírása egy fájlba
            saveToFile(doc, "kurzusfelvetelEZ3YRC1.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Új kurzus hozzáadása
    private static void addNewCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Element kurzusokElement = (Element) xPath.compile("/EZ3YRC_kurzusfelvetel/kurzusok").evaluate(doc, XPathConstants.NODE);
        Element newCourse = doc.createElement("kurzus");

        newCourse.setAttribute("id", "NEW123");
        Element kurzusnev = doc.createElement("kurzusnev");
        kurzusnev.appendChild(doc.createTextNode("Új kurzus"));
        newCourse.appendChild(kurzusnev);

        Element kredit = doc.createElement("kredit");
        kredit.appendChild(doc.createTextNode("3"));
        newCourse.appendChild(kredit);

        Element hely = doc.createElement("hely");
        hely.appendChild(doc.createTextNode("YYY. előadó"));
        newCourse.appendChild(hely);

        Element idopont = doc.createElement("idopont");
        idopont.appendChild(doc.createTextNode("Csütörtök 16:00-18:00"));
        newCourse.appendChild(idopont);

        Element oktato = doc.createElement("oktato");
        oktato.appendChild(doc.createTextNode("Nagy Péter"));
        newCourse.appendChild(oktato);

        kurzusokElement.appendChild(newCourse);
    }

    // Egy kurzus törlése
    private static void deleteCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Node courseToDelete = (Node) xPath.compile("/EZ3YRC_kurzusfelvetel/kurzusok/kurzus[@id='GEIAL120-B']").evaluate(doc, XPathConstants.NODE);
        courseToDelete.getParentNode().removeChild(courseToDelete);
    }

    // Egy kurzus adatainak módosítása
    private static void modifyCourse(Document doc, XPath xPath) throws XPathExpressionException {
        Node courseToModify = (Node) xPath.compile("/EZ3YRC_kurzusfelvetel/kurzusok/kurzus[@id='GEMAK234-B']").evaluate(doc, XPathConstants.NODE);
        Element courseElement = (Element) courseToModify;

        // Módosítások
        courseElement.getElementsByTagName("kredit").item(0).setTextContent("4");
        courseElement.getElementsByTagName("hely").item(0).setTextContent("321. előadó");
        courseElement.getElementsByTagName("idopont").item(0).setTextContent("Szerda 14:00-18:00");
    }

    // Eredmények kiírása a konzolra
    private static void printToConsole(Document doc) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult consoleResult = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, consoleResult);

        System.out.println("Eredmény a konzolon:");
        System.out.println(consoleResult.getWriter().toString());
    }

    // Eredmények kiírása fájlba
    private static void saveToFile(Document doc, String fileName) throws TransformerException {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult fileResult = new StreamResult(new File(fileName));
        DOMSource source = new DOMSource(doc);
        transformer.transform(source, fileResult);

        System.out.println("Eredmény fájlba kiírva: " + fileName);
    }
}
