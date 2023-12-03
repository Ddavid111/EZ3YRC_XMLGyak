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

public class DOMQueryEZ3YRC {

    public static void main(String[] argv) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File("XMLEZ3YRC.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        StringBuilder outputBuilder = new StringBuilder();

        // 2024 utáni lejáratú bankkártyák kiírása
        NodeList bankCardList = doc.getElementsByTagName("Bankkartya");
        outputBuilder.append("\n<ExpiringAfter2024>\n");
        for (int i = 0; i < bankCardList.getLength(); i++) {
            Node node = bankCardList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String lejaratidatum = element.getElementsByTagName("lejaratidatum").item(0).getTextContent();
                if (lejaratidatum.compareTo("2024-12") > 0) {
                    String kartyaszam = element.getAttribute("kartyaszam");
                    String bank = element.getElementsByTagName("bank").item(0).getTextContent();
                    String tipus = element.getElementsByTagName("tipus").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Bankkartya kartyaszam=\"%s\">\n", kartyaszam));
                    outputBuilder.append(String.format("    <Bank>%s</Bank>\n", bank));
                    outputBuilder.append(String.format("    <Tipus>%s</Tipus>\n", tipus));
                    outputBuilder.append(String.format("    <LejaratIdatum>%s</LejaratIdatum>\n", lejaratidatum));
                    outputBuilder.append("  </Bankkartya>\n");
                }
            }
        }
        outputBuilder.append("</ExpiringAfter2024>\n");

        //Kilistázza azokat a pizzákat, amelyek teljes ára 2600-nál olcsóbb.
        NodeList pizzaList = doc.getElementsByTagName("Pizza");
        outputBuilder.append("\n<AffordablePizzas>\n");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Node node = pizzaList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String teljesAr = element.getElementsByTagName("teljes_ar").item(0).getTextContent();
                int teljesArInt = Integer.parseInt(teljesAr);
                if (teljesArInt <= 2600) {
                    String pizzaId = element.getAttribute("pizza_id");
                    String pizzaneve = element.getElementsByTagName("pizzaneve").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Pizza pizza_id=\"%s\">\n", pizzaId));
                    outputBuilder.append(String.format("    <Nev>%s</Nev>\n", pizzaneve));
                    outputBuilder.append(String.format("    <TeljesAr>%s</TeljesAr>\n", teljesAr));
                    outputBuilder.append("  </Pizza>\n");
                }
            }
        }
        outputBuilder.append("</AffordablePizzas>\n");

        // Miskolci beszállítók kilistázása
        NodeList beszallitoList = doc.getElementsByTagName("Beszallito");
        outputBuilder.append("\n<MiskolciBeszallitok>\n");
        for (int i = 0; i < beszallitoList.getLength(); i++) {
            Node node = beszallitoList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NodeList varosList = element.getElementsByTagName("varos");
                if (varosList.getLength() > 0) {
                    String varos = varosList.item(0).getTextContent();
                    if ("Miskolc".equals(varos)) {
                        String beszallitoId = element.getAttribute("beszallito_id");
                        String nev = element.getElementsByTagName("nev").item(0).getTextContent();
                        outputBuilder.append(String.format("  <Beszallito beszallito_id=\"%s\">\n", beszallitoId));
                        outputBuilder.append(String.format("    <Nev>%s</Nev>\n", nev));
                        outputBuilder.append(String.format("    <Varos>%s</Varos>\n", varos));
                        outputBuilder.append("  </Beszallito>\n");
                    }
                }
            }
        }
        outputBuilder.append("</MiskolciBeszallitok>\n");


        // Fortuna pizzéria hozzávalóinak és beszállítójának kilistázása
        NodeList pizzazoList = doc.getElementsByTagName("Pizzazo");
        outputBuilder.append("\n<FortunaPizzaIngredientsAndSupplier>\n");
        String fortunaPizzazoId = "";
        for (int i = 0; i < pizzazoList.getLength(); i++) {
            Node node = pizzazoList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String nev = element.getElementsByTagName("nev").item(0).getTextContent();
                if (nev.equals("Fortuna Pizzéria")) {
                    fortunaPizzazoId = element.getAttribute("pizzazo_id");
                    break;
                }
            }
        }

        NodeList beszallitasList = doc.getElementsByTagName("Beszallitas");
        for (int i = 0; i < beszallitasList.getLength(); i++) {
            Node node = beszallitasList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String pizzazo = element.getAttribute("pizzazo");
                if (pizzazo.equals(fortunaPizzazoId)) {
                    String datum = element.getElementsByTagName("datum").item(0).getTextContent();
                    String hozzavalo = element.getElementsByTagName("hozzavalo").item(0).getTextContent();
                    String beszallito = element.getAttribute("beszallito");
                    outputBuilder.append(String.format("  <Beszallitas datum=\"%s\" hozzavalo=\"%s\" beszallito=\"%s\">\n", datum, hozzavalo, beszallito));
                    outputBuilder.append("  </Beszallitas>\n");
                }
            }
        }
        outputBuilder.append("</FortunaPizzaIngredientsAndSupplier>\n");

        // Don Pepe pizzéria pizzáinak és árainak kilistázása
        outputBuilder.append("\n<DonPepePizzasAndPrices>\n");
        String donPepePizzazoId = "";
        for (int i = 0; i < pizzazoList.getLength(); i++) {
            Node node = pizzazoList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String nev = element.getElementsByTagName("nev").item(0).getTextContent();
                if (nev.equals("Don Pepe")) {
                    donPepePizzazoId = element.getAttribute("pizzazo_id");
                    break;
                }
            }
        }


        for (int i = 0; i < pizzaList.getLength(); i++) {
            Node node = pizzaList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String pizzazo = element.getAttribute("pizzazo");
                if (pizzazo.equals(donPepePizzazoId)) {
                    String pizzaId = element.getAttribute("pizza_id");
                    String pizzaneve = element.getElementsByTagName("pizzaneve").item(0).getTextContent();
                    String teljesAr = element.getElementsByTagName("teljes_ar").item(0).getTextContent();
                    outputBuilder.append(String.format("  <Pizza pizza_id=\"%s\">\n", pizzaId));
                    outputBuilder.append(String.format("    <Nev>%s</Nev>\n", pizzaneve));
                    outputBuilder.append(String.format("    <TeljesAr>%s</TeljesAr>\n", teljesAr));
                    outputBuilder.append("  </Pizza>\n");
                }
            }
        }
        outputBuilder.append("</DonPepePizzasAndPrices>\n");

        System.out.println(outputBuilder);
    }
}
