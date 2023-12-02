package hu.domparse.ez3yrc;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMReadEZ3YRC {

    public static void main(String[] args) {
        try {
            // XML fájl beolvasása és DOM objektum létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("XMLEZ3YRC.xml"));

            document.getDocumentElement().normalize();
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<Pizzazo_EZ3YRC xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"XMLSchemaEZ3YRC.xsd\">\n");

            // Különböző részek feldolgozása külön metódusokon keresztül
            readPizzazos(document);
            printEmptyLine();

            readBeszallitasok(document);
            printEmptyLine();

            readBeszallitok(document);
            printEmptyLine();

            readFutarok(document);
            printEmptyLine();

            readVevok(document);
            printEmptyLine();

            readRendelesek(document);
            printEmptyLine();

            readPizzak(document);
            printEmptyLine();

            readBankkartyak(document);
            printEmptyLine();

            System.out.println("\n</Pizzazo_EZ3YRC>");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // Pizzázók adatainak beolvasása és kiírása
    private static void readPizzazos(Document document) {
        NodeList pizzazoList = document.getElementsByTagName("Pizzazo");
        for (int temp = 0; temp < pizzazoList.getLength(); temp++) {
            Node node = pizzazoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String pizzazoId = eElement.getAttribute("pizzazo_id");
                String nev = eElement.getElementsByTagName("nev").item(0).getTextContent();

                System.out.println("    <Pizzazo pizzazo_id=\"" + pizzazoId + "\">");
                printElement("nev", nev);

                NodeList elerhetosegNodeList = eElement.getElementsByTagName("elerhetoseg");
                if (elerhetosegNodeList.getLength() > 0) {
                    Element elerhetosegElement = (Element) elerhetosegNodeList.item(0);
                    printElement("weboldal", elerhetosegElement.getElementsByTagName("weboldal").item(0).getTextContent());
                    printElement("telefonszam", elerhetosegElement.getElementsByTagName("telefonszam").item(0).getTextContent());
                }

                System.out.println("    </Pizzazo>");
            }
        }
    }

    // Beszállítások adatainak beolvasása és kiírása
    private static void readBeszallitasok(Document document) {
        NodeList beszallitasList = document.getElementsByTagName("Beszallitas");
        for (int temp = 0; temp < beszallitasList.getLength(); temp++) {
            Node node = beszallitasList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String beszallito = eElement.getAttribute("beszallito");
                String pizzazo = eElement.getAttribute("pizzazo");
                String datum = eElement.getElementsByTagName("datum").item(0).getTextContent();
                String hozzavalo = eElement.getElementsByTagName("hozzavalo").item(0).getTextContent();

                System.out.println("    <Beszallitas beszallito=\"" + beszallito + "\" pizzazo=\"" + pizzazo + "\">");
                printElement("datum", datum);
                printElement("hozzavalo", hozzavalo);
                System.out.println("    </Beszallitas>");
            }
        }
    }

    // Beszállítók adatainak beolvasása és kiírása
    private static void readBeszallitok(Document document) {
        NodeList beszallitoList = document.getElementsByTagName("Beszallito");
        for (int temp = 0; temp < beszallitoList.getLength(); temp++) {
            Node node = beszallitoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String beszallitoId = eElement.getAttribute("beszallito_id");
                String nev = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String email = eElement.getElementsByTagName("email").item(0).getTextContent();

                System.out.println("    <Beszallito beszallito_id=\"" + beszallitoId + "\">");
                printElement("nev", nev);
                printElement("email", email);

                NodeList cimNodeList = eElement.getElementsByTagName("cim");
                if (cimNodeList.getLength() > 0) {
                    Element cimElement = (Element) cimNodeList.item(0);
                    printElement("varos", cimElement.getElementsByTagName("varos").item(0).getTextContent());
                    printElement("hazszam", cimElement.getElementsByTagName("hazszam").item(0).getTextContent());
                    printElement("utca", cimElement.getElementsByTagName("utca").item(0).getTextContent());
                    printElement("iranyitoszam", cimElement.getElementsByTagName("iranyitoszam").item(0).getTextContent());
                }

                System.out.println("    </Beszallito>");
            }
        }
    }

    // Futárok adatainak beolvasása és kiírása
    private static void readFutarok(Document document) {
        NodeList futarList = document.getElementsByTagName("Futar");
        for (int temp = 0; temp < futarList.getLength(); temp++) {
            Node node = futarList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String futarId = eElement.getAttribute("futar_id");
                String pizzazoId = eElement.getAttribute("pizzazo");
                String nev = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String telefonszam = eElement.getElementsByTagName("telefonszam").item(0).getTextContent();

                System.out.println("    <Futar futar_id=\"" + futarId + "\" pizzazo=\"" + pizzazoId + "\">");
                printElement("nev", nev);
                printElement("telefonszam", telefonszam);
                System.out.println("    </Futar>");
            }
        }
    }

    // Vevők adatainak beolvasása és kiírása
    private static void readVevok(Document document) {
        NodeList vevoList = document.getElementsByTagName("Vevo");
        for (int temp = 0; temp < vevoList.getLength(); temp++) {
            Node node = vevoList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String vevoId = eElement.getAttribute("vevo_id");
                String nev = eElement.getElementsByTagName("nev").item(0).getTextContent();
                String telefonszam = eElement.getElementsByTagName("telefonszam").item(0).getTextContent();

                System.out.println("    <Vevo vevo_id=\"" + vevoId + "\">");
                printElement("nev", nev);
                printElement("telefonszam", telefonszam);

                NodeList cimNodeList = eElement.getElementsByTagName("cim");
                if (cimNodeList.getLength() > 0) {
                    Element cimElement = (Element) cimNodeList.item(0);
                    printElement("varos", cimElement.getElementsByTagName("varos").item(0).getTextContent());
                    printElement("hazszam", cimElement.getElementsByTagName("hazszam").item(0).getTextContent());
                    printElement("utca", cimElement.getElementsByTagName("utca").item(0).getTextContent());
                    printElement("iranyitoszam", cimElement.getElementsByTagName("iranyitoszam").item(0).getTextContent());
                }

                System.out.println("    </Vevo>");
            }
        }
    }

    // Rendelések adatainak beolvasása és kiírása
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

    // Pizzák adatainak beolvasása és kiírása
    private static void readPizzak(Document document) {
        NodeList pizzaList = document.getElementsByTagName("Pizza");
        for (int temp = 0; temp < pizzaList.getLength(); temp++) {
            Node node = pizzaList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                String pizzaId = eElement.getAttribute("pizza_id");
                String pizzazoId = eElement.getAttribute("pizzazo");
                String pizzaneve = eElement.getElementsByTagName("pizzaneve").item(0).getTextContent();
                String teljes_ar = eElement.getElementsByTagName("teljes_ar").item(0).getTextContent();

                System.out.println("    <Pizza pizza_id=\"" + pizzaId + "\" pizzazo=\"" + pizzazoId + "\">");
                printElement("pizzaneve", pizzaneve);
                printElement("teljes_ar", teljes_ar);

                NodeList feltetNodeList = eElement.getElementsByTagName("feltet");
                for (int i = 0; i < feltetNodeList.getLength(); i++) {
                    System.out.println("        <feltet>" + feltetNodeList.item(i).getTextContent() + "</feltet>");
                }

                NodeList meretNodeList = eElement.getElementsByTagName("meret");
                for (int i = 0; i < meretNodeList.getLength(); i++) {
                    System.out.println("        <meret>" + meretNodeList.item(i).getTextContent() + "</meret>");
                }

                System.out.println("    </Pizza>");
            }
        }
    }

    // Bankkártyák adatainak beolvasása és kiírása
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
                String lejaratidatum = eElement.getElementsByTagName("lejaratidatum").item(0).getTextContent();

                System.out.println("    <Bankkartya kartyaszam=\"" + kartyaszam + "\" vevo=\"" + vevoId + "\">");
                printElement("bank", bank);
                printElement("tipus", tipus);
                printElement("lejaratidatum", lejaratidatum);
                System.out.println("    </Bankkartya>");
            }
        }
    }

    // Segédfüggvény az XML elemek kiírásához
    private static void printElement(String name, String value) {
        System.out.println("        <" + name + ">" + value + "</" + name + ">");
    }

    // Segédfüggvény egy üres sor kiírásához a konzolon
    private static void printEmptyLine() {
        System.out.println();
    }
}
