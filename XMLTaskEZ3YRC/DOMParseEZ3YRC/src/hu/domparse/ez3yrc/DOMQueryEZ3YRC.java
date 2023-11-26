package hu.domparse.ez3yrc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DOMQueryEZ3YRC {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse("XMLEZ3YRC.xml");

            // Példa: 2024 utáni lejárattal rendelkező bankkártyák kiírása
            query2024UtanLejaroBankkartyak(document);

            // Példa: 2024 utáni lejárattal rendelkező bankkártyák kiírása
            query3000NelOlcsobbPizzak(document);

            // Példa: Kilistázza a miskolci pizzériákat
            queryMiskolciPizzeriak(document);

            // Példa: Kilistázza a Fortuna pizzéria hozzávalóit és a beszállítót
            queryFortunaPizzeriaHozzavaloi(document);

            // Példa: Kilistázza a Don Pepe pizzéria pizzáit és azok teljes árait
            queryDonPepeRendeltPizzak(document);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2024 utáni lejáratú bankkártyák kiírása
    private static void query2024UtanLejaroBankkartyak(Document document) {
        System.out.println("\n");
        System.out.println("=== 2024 utáni lejárattal rendelkező bankkártyák ===");

        NodeList bankkartyaList = document.getElementsByTagName("Bankkartya");

        for (int i = 0; i < bankkartyaList.getLength(); i++) {
            Node bankkartyaNode = bankkartyaList.item(i);

            if (bankkartyaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element bankkartyaElement = (Element) bankkartyaNode;

                String lejaratDatumString = bankkartyaElement.getElementsByTagName("lejaratidatum").item(0).getTextContent();

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    Date lejaratDatum = sdf.parse(lejaratDatumString);

                    // Ellenőrizzük, hogy a lejárati dátum 2024 után jár-e le
                    if (lejaratDatum.after(sdf.parse("2025-01"))) {
                        // Ha igen, akkor kiírjuk az adatokat
                        String kartyaszam = bankkartyaElement.getAttribute("kartyaszam");
                        String bank = bankkartyaElement.getElementsByTagName("bank").item(0).getTextContent();
                        String tipus = bankkartyaElement.getElementsByTagName("tipus").item(0).getTextContent();

                        System.out.println("  <Bankkartya>");
                        System.out.println("    <Kartyaszam>" + kartyaszam + "</Kartyaszam>");
                        System.out.println("    <Bank>" + bank + "</Bank>");
                        System.out.println("    <Tipus>" + tipus + "</Tipus>");
                        System.out.println("    <Lejarat>" + lejaratDatumString + "</Lejarat>");
                        System.out.println("  </Bankkartya>");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\n");
    }

    // 3000-nél olcsóbb pizzák kiírása
    private static void query3000NelOlcsobbPizzak(Document document) {
        System.out.println("=== 2600-nál olcsóbb pizzák ===");

        NodeList pizzaList = document.getElementsByTagName("Pizza");

        for (int i = 0; i < pizzaList.getLength(); i++) {
            Node pizzaNode = pizzaList.item(i);

            if (pizzaNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pizzaElement = (Element) pizzaNode;

                // Olvassuk be a teljes árat és ellenőrizzük, hogy 2600-nál olcsóbb
                int teljesAr = Integer.parseInt(pizzaElement.getElementsByTagName("teljes_ar").item(0).getTextContent());

                if (teljesAr < 2600) {
                    // Ha a pizza olcsóbb, akkor kiírjuk az adatokat
                    String pizzaId = pizzaElement.getAttribute("pizza_id");
                    String pizzazoId = pizzaElement.getAttribute("pizzazo");
                    String pizzaneve = pizzaElement.getElementsByTagName("pizzaneve").item(0).getTextContent();

                    System.out.println("  <Pizza>");
                    System.out.println("    <PizzaID>" + pizzaId + "</PizzaID>");
                    System.out.println("    <PizzazoID>" + pizzazoId + "</PizzazoID>");
                    System.out.println("    <Pizzaneve>" + pizzaneve + "</Pizzaneve>");
                    System.out.println("    <TeljesAr>" + teljesAr + "</TeljesAr>");
                    System.out.println("  </Pizza>");
                }
            }
        }

        System.out.println("\n");
    }

    private static void queryMiskolciPizzeriak(Document document) {
        System.out.println("=== Miskolci pizzériák neve és elérhetősége ===");

        NodeList pizzazoList = document.getElementsByTagName("Pizzazo");

        for (int i = 0; i < pizzazoList.getLength(); i++) {
            Node pizzazoNode = pizzazoList.item(i);

            if (pizzazoNode.getNodeType() == Node.ELEMENT_NODE) {
                Element pizzazoElement = (Element) pizzazoNode;

                String pizzazoId = pizzazoElement.getAttribute("pizzazo_id");
                String nev = pizzazoElement.getElementsByTagName("nev").item(0).getTextContent();
                String weboldal = pizzazoElement.getElementsByTagName("weboldal").item(0).getTextContent();
                String telefonszam = pizzazoElement.getElementsByTagName("telefonszam").item(0).getTextContent();

                System.out.println("  <Pizzazo>");
                System.out.println("    <PizzazoID>" + pizzazoId + "</PizzazoID>");
                System.out.println("    <Nev>" + nev + "</Nev>");
                System.out.println("    <Weboldal>" + weboldal + "</Weboldal>");
                System.out.println("    <Telefonszam>" + telefonszam + "</Telefonszam>");
                System.out.println("  </Pizzazo>");
            }
        }

        System.out.println("\n");
    }

    private static void queryFortunaPizzeriaHozzavaloi(Document document) {
        System.out.println("=== Fortuna Pizzéria által rendelt hozzávalók és beszállítóik ===");

        NodeList beszallitasList = document.getElementsByTagName("Beszallitas");

        for (int i = 0; i < beszallitasList.getLength(); i++) {
            Node beszallitasNode = beszallitasList.item(i);

            if (beszallitasNode.getNodeType() == Node.ELEMENT_NODE) {
                Element beszallitasElement = (Element) beszallitasNode;

                String pizzazoId = beszallitasElement.getAttribute("pizzazo");
                if (pizzazoId.equals("3")) {
                    String datum = beszallitasElement.getElementsByTagName("datum").item(0).getTextContent();
                    String hozzavalo = beszallitasElement.getElementsByTagName("hozzavalo").item(0).getTextContent();
                    String beszallitoId = beszallitasElement.getAttribute("beszallito");

                    // Beszállító adatainak lekérdezése
                    NodeList beszallitoList = document.getElementsByTagName("Beszallito");
                    for (int j = 0; j < beszallitoList.getLength(); j++) {
                        Node beszallitoNode = beszallitoList.item(j);

                        if (beszallitoNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element beszallitoElement = (Element) beszallitoNode;

                            if (beszallitoElement.getAttribute("beszallito_id").equals(beszallitoId)) {
                                String beszallitoNev = beszallitoElement.getElementsByTagName("nev").item(0).getTextContent();

                                System.out.println("  <Beszallitas>");
                                System.out.println("    <Datum>" + datum + "</Datum>");
                                System.out.println("    <Hozzavalo>" + hozzavalo + "</Hozzavalo>");
                                System.out.println("    <BeszallitoID>" + beszallitoId + "</BeszallitoID>");
                                System.out.println("    <BeszallitoNev>" + beszallitoNev + "</BeszallitoNev>");
                                System.out.println("  </Beszallitas>");
                            }
                        }
                    }
                }
            }
        }

        System.out.println("\n");
    }

    private static void queryDonPepeRendeltPizzak(Document document) {
        System.out.println("=== Don Pepe által rendelt pizzák neve és teljes ára ===");

        NodeList rendelesList = document.getElementsByTagName("Rendeles");

        for (int i = 0; i < rendelesList.getLength(); i++) {
            Node rendelesNode = rendelesList.item(i);

            if (rendelesNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rendelesElement = (Element) rendelesNode;

                String pizzazoId = rendelesElement.getAttribute("pizza");
                if (pizzazoId.equals("1")) {
                    // Pizza adatainak lekérdezése
                    NodeList pizzaList = document.getElementsByTagName("Pizza");
                    for (int j = 0; j < pizzaList.getLength(); j++) {
                        Node pizzaNode = pizzaList.item(j);

                        if (pizzaNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element pizzaElement = (Element) pizzaNode;

                            if (pizzaElement.getAttribute("pizza_id").equals(pizzazoId)) {
                                String pizzanev = pizzaElement.getElementsByTagName("pizzaneve").item(0).getTextContent();
                                String teljesAr = pizzaElement.getElementsByTagName("teljes_ar").item(0).getTextContent();

                                System.out.println("  <Rendeles>");
                                System.out.println("    <Pizzanev>" + pizzanev + "</Pizzanev>");
                                System.out.println("    <TeljesAr>" + teljesAr + "</TeljesAr>");
                                System.out.println("  </Rendeles>");
                            }
                        }
                    }
                }
            }
        }

    }
}
