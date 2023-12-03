package hu.domparse.ez3yrc;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.*;

public class DOMReadEZ3YRC {

    public static void main(String[] args) {
        try {
            // XML dokumentum feldolgozásához szükséges objektumok létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("XMLEZ3YRC.xml"));

            // XML struktúra kiíratása
            document.getDocumentElement().normalize();
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<Pizzazo_EZ3YRC xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaEZ3YRC.xsd\">\n");

            readPizzazos(document);
            readBeszallitasok(document);
            readBeszallitok(document);
            readFutarok(document);
            readVevok(document);
            readRendelesek(document);
            readPizzak(document);
            readBankkartyak(document);

            System.out.println("\n</Pizzazo_EZ3YRC>");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            System.out.println("Valami baj van: " + e);
        }
    }

    // Pizzázók adatainak kiolvasása és kiíratása
    private static void readPizzazos(Document document) {
        NodeList pizzazoList = document.getElementsByTagName("Pizzazo");
        for (int temp = 0; temp < pizzazoList.getLength(); temp++) {
            Node node = pizzazoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String pizzazoId = eElement.getAttribute("pizzazo_id");
                String pizzazoName = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String pizzazoWebsite = eElement.getElementsByTagName("weboldal").item(0).getTextContent();
                String pizzazoPhoneNumber = eElement.getElementsByTagName("telefonszam").item(0).getTextContent();

                System.out.println("    <Pizzazo pizzazo_id=\"" + pizzazoId + "\">");
                printElement("nev", pizzazoName);
                System.out.println("        <elerhetoseg>");
                printElement("weboldal", pizzazoWebsite);
                printElement("telefonszam", pizzazoPhoneNumber);
                System.out.println("        </elerhetoseg>");
                System.out.println("    </Pizzazo>");
            }
        }
    }

    // Beszállítások adatainak kiolvasása és kiíratása
    private static void readBeszallitasok(Document document) {
        NodeList beszallitasList = document.getElementsByTagName("Beszallitas");
        for (int temp = 0; temp < beszallitasList.getLength(); temp++) {
            Node node = beszallitasList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String beszallitoId = eElement.getAttribute("beszallito");
                String pizzazoId = eElement.getAttribute("pizzazo");
                String beszallitasDate = eElement.getElementsByTagName("datum").item(0).getTextContent();
                String hozzavalo = eElement.getElementsByTagName("hozzavalo").item(0).getTextContent();

                System.out.println("    <Beszallitas beszallito=\"" + beszallitoId + "\" pizzazo=\"" + pizzazoId + "\">");
                printElement("datum", beszallitasDate);
                printElement("hozzavalo", hozzavalo);
                System.out.println("    </Beszallitas>");
            }
        }
    }

    // Beszállítók adatainak kiolvasása és kiíratása
    private static void readBeszallitok(Document document) {
        NodeList beszallitoList = document.getElementsByTagName("Beszallito");
        for (int temp = 0; temp < beszallitoList.getLength(); temp++) {
            Node node = beszallitoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String beszallitoId = eElement.getAttribute("beszallito_id");
                String beszallitoName = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String beszallitoEmail = eElement.getElementsByTagName("email").item(0).getTextContent();
                String varos = eElement.getElementsByTagName("varos").item(0).getTextContent();
                String hazszam = eElement.getElementsByTagName("hazszam").item(0).getTextContent();
                String utca = eElement.getElementsByTagName("utca").item(0).getTextContent();
                String iranyitoszam = eElement.getElementsByTagName("iranyitoszam").item(0).getTextContent();

                System.out.println("    <Beszallito beszallito_id=\"" + beszallitoId + "\">");
                printElement("nev", beszallitoName);
                printElement("email", beszallitoEmail);
                System.out.println("        <cim>");
                printElement("varos", varos);
                printElement("hazszam", hazszam);
                printElement("utca", utca);
                printElement("iranyitoszam", iranyitoszam);
                System.out.println("        </cim>");
                System.out.println("    </Beszallito>");
            }
        }
    }

    // Futárok adatainak kiolvasása és kiíratása
    private static void readFutarok(Document document) {
        NodeList futarList = document.getElementsByTagName("Futar");
        for (int temp = 0; temp < futarList.getLength(); temp++) {
            Node node = futarList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String futarId = eElement.getAttribute("futar_id");
                String pizzazoId = eElement.getAttribute("pizzazo");
                String futarName = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String futarPhoneNumber = eElement.getElementsByTagName("telefonszam").item(0).getTextContent();

                System.out.println("    <Futar futar_id=\"" + futarId + "\" pizzazo=\"" + pizzazoId + "\">");
                printElement("nev", futarName);
                printElement("telefonszam", futarPhoneNumber);
                System.out.println("    </Futar>");
            }
        }
    }

    // Vevők adatainak kiolvasása és kiíratása
    private static void readVevok(Document document) {
        NodeList vevoList = document.getElementsByTagName("Vevo");
        for (int temp = 0; temp < vevoList.getLength(); temp++) {
            Node node = vevoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String vevoId = eElement.getAttribute("vevo_id");
                String vevoName = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String vevoPhoneNumber = eElement.getElementsByTagName("telefonszam").item(0).getTextContent();
                String varos = eElement.getElementsByTagName("varos").item(0).getTextContent();
                String hazszam = eElement.getElementsByTagName("hazszam").item(0).getTextContent();
                String utca = eElement.getElementsByTagName("utca").item(0).getTextContent();
                String iranyitoszam = eElement.getElementsByTagName("iranyitoszam").item(0).getTextContent();

                System.out.println("    <Vevo vevo_id=\"" + vevoId + "\">");
                printElement("nev", vevoName);
                printElement("telefonszam", vevoPhoneNumber);
                System.out.println("        <cim>");
                printElement("varos", varos);
                printElement("hazszam", hazszam);
                printElement("utca", utca);
                printElement("iranyitoszam", iranyitoszam);
                System.out.println("        </cim>");
                System.out.println("    </Vevo>");
            }
        }
    }

    // Rendelések adatainak kiolvasása és kiíratása
    private static void readRendelesek(Document document) {
        NodeList rendelesList = document.getElementsByTagName("Rendeles");
        for (int temp = 0; temp < rendelesList.getLength(); temp++) {
            Node node = rendelesList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String pizzaId = eElement.getAttribute("pizza");
                String vevoId = eElement.getAttribute("vevo");

                System.out.println("    <Rendeles pizza=\"" + pizzaId + "\" vevo=\"" + vevoId + "\"></Rendeles>");
            }
        }
    }

    // Pizzák adatainak kiolvasása és kiíratása
    private static void readPizzak(Document document) {
        NodeList pizzaList = document.getElementsByTagName("Pizza");
        for (int temp = 0; temp < pizzaList.getLength(); temp++) {
            Node node = pizzaList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String pizzaId = eElement.getAttribute("pizza_id");
                String pizzazoId = eElement.getAttribute("pizzazo");
                String pizzaName = eElement.getElementsByTagName("pizzaneve").item(0).getTextContent();
                NodeList feltetList = eElement.getElementsByTagName("feltet");
                NodeList meretList = eElement.getElementsByTagName("meret");
                String teljesAr = eElement.getElementsByTagName("teljes_ar").item(0).getTextContent();

                System.out.println("    <Pizza pizza_id=\"" + pizzaId + "\" pizzazo=\"" + pizzazoId + "\">");
                printElement("pizzaneve", pizzaName);
                for (int i = 0; i < feltetList.getLength(); i++) {
                    printElement("feltet", feltetList.item(i).getTextContent());
                }
                for (int i = 0; i < meretList.getLength(); i++) {
                    printElement("meret", meretList.item(i).getTextContent());
                }
                printElement("teljes_ar", teljesAr);
                System.out.println("    </Pizza>");
            }
        }
    }

    // Bankkártyák adatainak kiolvasása és kiíratása
    private static void readBankkartyak(Document document) {
        NodeList bankkartyaList = document.getElementsByTagName("Bankkartya");
        for (int temp = 0; temp < bankkartyaList.getLength(); temp++) {
            Node node = bankkartyaList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String kartyaszam = eElement.getAttribute("kartyaszam");
                String vevoId = eElement.getAttribute("vevo");
                String bank = eElement.getElementsByTagName("bank").item(0).getTextContent();
                String tipus = eElement.getElementsByTagName("tipus").item(0).getTextContent();
                String lejaratiDatum = eElement.getElementsByTagName("lejaratidatum").item(0).getTextContent();

                System.out.println("    <Bankkartya kartyaszam=\"" + kartyaszam + "\" vevo=\"" + vevoId + "\">");
                printElement("bank", bank);
                printElement("tipus", tipus);
                printElement("lejaratidatum", lejaratiDatum);
                System.out.println("    </Bankkartya>");
            }
        }
    }

    // Segédfüggvény az XML elemek kiíratásához
    private static void printElement(String elementName, String content) {
        System.out.println("        <" + elementName + ">" + content + "</" + elementName + ">");
    }
    // Dokumentum írásához használt függvény
    public static void writeDocument(Document document, StreamResult result) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}
