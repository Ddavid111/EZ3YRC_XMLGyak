package hu.domparse.ez3yrc;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DOMWriteEZ3YRC {

    public static void main(String[] args) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();


            // Gyökér elem létrehozása
            Element rootElement = doc.createElement("Pizzazo_EZ3YRC");
            doc.appendChild(rootElement);

            // Namespace attribútumok hozzáadása
            rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaEZ3YRC.xsd");

            // Pizzazo elemek hozzáadása
            addPizzazo(doc, rootElement, "1", "Don Pepe", "https://www.donpepe.hu/hu", "309082091");
            addPizzazo(doc, rootElement, "2", "Pizza Tábor", "https://www.pizzatabor.hu/", "204716655");
            addPizzazo(doc, rootElement, "3", "Fortuna Pizzéria", "https://pizzafortuna.hu/", "302415505");

            // Beszallitas elemek hozzáadása
            addBeszallitas(doc, rootElement, "1", "1", "2023-11-04", "hagyma");
            addBeszallitas(doc, rootElement, "2", "2", "2023-11-19", "cukor");
            addBeszallitas(doc, rootElement, "3", "3", "2023-11-25", "olaj");

            // Beszallito elemek hozzáadása
            addBeszallito(doc, rootElement, "1", "Kovács Lajos", "mintalajos@gmail.com", "Miskolc", "5", "Klapka György", "3524");
            addBeszallito(doc, rootElement, "2", "Nehéz István", "mintaistvan@gmail.com", "Miskolc", "5", "Petőfi Sándor", "3527");
            addBeszallito(doc, rootElement, "3", "Kiss István", "mintakiss@gmail.com", "Miskolc", "4", "József Attila", "3531");

            // Futar elemek hozzáadása
            addFutar(doc, rootElement, "1", "1", "Dudás Lajos", "309562179");
            addFutar(doc, rootElement, "2", "2", "Lajos Imre", "309652179");
            addFutar(doc, rootElement, "3", "3", "Nagy Márkó", "209852179");

            // Vevo elemek hozzáadása
            addVevo(doc, rootElement, "1", "Nehéz Gábor", "306153384", "Miskolc", "6", "Hajós Alfréd", "3524");
            addVevo(doc, rootElement, "2", "Kiss Lajos", "206453384", "Miskolc", "4", "Vörösmarty Mihály", "3532");
            addVevo(doc, rootElement, "3", "Elek János", "206158484", "Miskolc", "6", "Árok utca", "3531");

            // Rendeles elemek hozzáadása
            addRendeles(doc, rootElement, "1", "1", "1");
            addRendeles(doc, rootElement, "2", "2", "2");
            addRendeles(doc, rootElement, "3", "3", "3");

            // Pizza elemek hozzáadása
            addPizza(doc, rootElement, "1", "1", "Sonkás Pizza", "paradicsom,mozzarella,sonka", "2000", "17 cm,25 cm,30 cm");
            addPizza(doc, rootElement, "2", "2", "Pizza Mexikói", "paradicsom,chili,sajt", "2550", "25 cm,32 cm,50 cm");
            addPizza(doc, rootElement, "3", "3", "Aladdin Pizza", "aszalt paradicsom,ananász,csirkemell", "2750", "17 cm,25 cm,30 cm");

            // Bankkartya elemek hozzáadása
            addBankkartya(doc, rootElement, "1", "1", "OTP", "bankkartya", "2024-05");
            addBankkartya(doc, rootElement, "2", "2", "ERSTE", "hitelkartya", "2025-06");
            addBankkartya(doc, rootElement, "3", "3", "UNICREDIT", "bankkartya", "2026-05");

            // XML fájl írása
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            StreamResult fileResult = new StreamResult(new File("XMLEZ3YRC1.xml"));
            transformer.transform(source, fileResult);

            // Konzolra írás
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            System.out.println("The content has been written to the output file successfully.");
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Valami baj van: " + e);
        }
    }

    // Segédfüggvények az elemek hozzáadásához
    private static void addPizzazo(Document doc, Element root, String pizzazoId, String nev, String weboldal, String telefonszam) {
        Element pizzazo = doc.createElement("Pizzazo");
        pizzazo.setAttribute("pizzazo_id", pizzazoId);
        root.appendChild(pizzazo);

        Element nevElement = doc.createElement("nev");
        nevElement.appendChild(doc.createTextNode(nev));
        pizzazo.appendChild(nevElement);

        Element elerhetoseg = doc.createElement("elerhetoseg");
        pizzazo.appendChild(elerhetoseg);

        Element weboldalElement = doc.createElement("weboldal");
        weboldalElement.appendChild(doc.createTextNode(weboldal));
        elerhetoseg.appendChild(weboldalElement);

        Element telefonszamElement = doc.createElement("telefonszam");
        telefonszamElement.appendChild(doc.createTextNode(telefonszam));
        elerhetoseg.appendChild(telefonszamElement);
    }

    private static void addBeszallitas(Document doc, Element root, String beszallito, String pizzazo, String datum, String hozzavalo) {
        Element beszallitas = doc.createElement("Beszallitas");
        beszallitas.setAttribute("beszallito", beszallito);
        beszallitas.setAttribute("pizzazo", pizzazo);
        root.appendChild(beszallitas);

        Element datumElement = doc.createElement("datum");
        datumElement.appendChild(doc.createTextNode(datum));
        beszallitas.appendChild(datumElement);

        Element hozzavaloElement = doc.createElement("hozzavalo");
        hozzavaloElement.appendChild(doc.createTextNode(hozzavalo));
        beszallitas.appendChild(hozzavaloElement);
    }

    private static void addBeszallito(Document doc, Element root, String beszallitoId, String nev, String email, String varos, String hazszam, String utca, String iranyitoszam) {
        Element beszallitoElem = doc.createElement("Beszallito");
        beszallitoElem.setAttribute("beszallito_id", beszallitoId);
        root.appendChild(beszallitoElem);

        Element nevElement = doc.createElement("nev");
        nevElement.appendChild(doc.createTextNode(nev));
        beszallitoElem.appendChild(nevElement);

        Element emailElement = doc.createElement("email");
        emailElement.appendChild(doc.createTextNode(email));
        beszallitoElem.appendChild(emailElement);

        Element cimElem = doc.createElement("cim");
        beszallitoElem.appendChild(cimElem);

        Element varosElement = doc.createElement("varos");
        varosElement.appendChild(doc.createTextNode(varos));
        cimElem.appendChild(varosElement);

        Element hazszamElement = doc.createElement("hazszam");
        hazszamElement.appendChild(doc.createTextNode(hazszam));
        cimElem.appendChild(hazszamElement);

        Element utcaElement = doc.createElement("utca");
        utcaElement.appendChild(doc.createTextNode(utca));
        cimElem.appendChild(utcaElement);

        Element iranyitoszamElement = doc.createElement("iranyitoszam");
        iranyitoszamElement.appendChild(doc.createTextNode(iranyitoszam));
        cimElem.appendChild(iranyitoszamElement);
    }

    private static void addFutar(Document doc, Element root, String futarId, String pizzazo, String nev, String telefonszam) {
        Element futarElem = doc.createElement("Futar");
        futarElem.setAttribute("futar_id", futarId);
        futarElem.setAttribute("pizzazo", pizzazo);
        root.appendChild(futarElem);

        Element nevElement = doc.createElement("nev");
        nevElement.appendChild(doc.createTextNode(nev));
        futarElem.appendChild(nevElement);

        Element telefonszamElement = doc.createElement("telefonszam");
        telefonszamElement.appendChild(doc.createTextNode(telefonszam));
        futarElem.appendChild(telefonszamElement);
    }

    private static void addVevo(Document doc, Element root, String vevoId, String nev, String telefonszam, String varos, String hazszam, String utca, String iranyitoszam) {
        Element vevoElem = doc.createElement("Vevo");
        vevoElem.setAttribute("vevo_id", vevoId);
        root.appendChild(vevoElem);

        Element nevElement = doc.createElement("nev");
        nevElement.appendChild(doc.createTextNode(nev));
        vevoElem.appendChild(nevElement);

        Element telefonszamElement = doc.createElement("telefonszam");
        telefonszamElement.appendChild(doc.createTextNode(telefonszam));
        vevoElem.appendChild(telefonszamElement);

        Element cimElem = doc.createElement("cim");
        vevoElem.appendChild(cimElem);

        Element varosElement = doc.createElement("varos");
        varosElement.appendChild(doc.createTextNode(varos));
        cimElem.appendChild(varosElement);

        Element hazszamElement = doc.createElement("hazszam");
        hazszamElement.appendChild(doc.createTextNode(hazszam));
        cimElem.appendChild(hazszamElement);

        Element utcaElement = doc.createElement("utca");
        utcaElement.appendChild(doc.createTextNode(utca));
        cimElem.appendChild(utcaElement);

        Element iranyitoszamElement = doc.createElement("iranyitoszam");
        iranyitoszamElement.appendChild(doc.createTextNode(iranyitoszam));
        cimElem.appendChild(iranyitoszamElement);
    }

    private static void addRendeles(Document doc, Element root, String pizza, String vevo, String rendelesId) {
        Element rendelesElem = doc.createElement("Rendeles");
        rendelesElem.setAttribute("pizza", pizza);
        rendelesElem.setAttribute("vevo", vevo);
        root.appendChild(rendelesElem);
    }

    private static void addPizza(Document doc, Element root, String pizzaId, String pizzazo, String pizzaneve, String feltetek, String teljesAr, String meretek) {
        Element pizzaElem = doc.createElement("Pizza");
        pizzaElem.setAttribute("pizza_id", pizzaId);
        pizzaElem.setAttribute("pizzazo", pizzazo);
        root.appendChild(pizzaElem);

        Element pizzaneveElement = doc.createElement("pizzaneve");
        pizzaneveElement.appendChild(doc.createTextNode(pizzaneve));
        pizzaElem.appendChild(pizzaneveElement);

        String[] feltetArray = feltetek.split(",");
        for (String feltet : feltetArray) {
            Element feltetElement = doc.createElement("feltet");
            feltetElement.appendChild(doc.createTextNode(feltet.trim()));
            pizzaElem.appendChild(feltetElement);
        }

        Element teljesArElement = doc.createElement("teljes_ar");
        teljesArElement.appendChild(doc.createTextNode(teljesAr));
        pizzaElem.appendChild(teljesArElement);

        String[] meretArray = meretek.split(",");
        for (String meret : meretArray) {
            Element meretElement = doc.createElement("meret");
            meretElement.appendChild(doc.createTextNode(meret.trim()));
            pizzaElem.appendChild(meretElement);
        }
    }

    private static void addBankkartya(Document doc, Element root, String kartyaszam, String vevo, String bank, String tipus, String lejaratidatum) {
        Element bankkartyaElem = doc.createElement("Bankkartya");
        bankkartyaElem.setAttribute("kartyaszam", kartyaszam);
        bankkartyaElem.setAttribute("vevo", vevo);
        root.appendChild(bankkartyaElem);

        Element bankElem = doc.createElement("bank");
        bankElem.appendChild(doc.createTextNode(bank));
        bankkartyaElem.appendChild(bankElem);

        Element tipusElem = doc.createElement("tipus");
        tipusElem.appendChild(doc.createTextNode(tipus));
        bankkartyaElem.appendChild(tipusElem);

        Element lejaratElem = doc.createElement("lejaratidatum");
        lejaratElem.appendChild(doc.createTextNode(lejaratidatum));
        bankkartyaElem.appendChild(lejaratElem);
    }


    public static void addPizzaElem(Document doc, Element root, String pizzaId, String pizzazo, String pizzaneve, String feltetek, String teljesAr, String meretek) {
        Element pizzaElem = doc.createElement("Pizza");
        pizzaElem.setAttribute("pizza_id", pizzaId);
        pizzaElem.setAttribute("pizzazo", pizzazo);
        root.appendChild(pizzaElem);

        Element pizzaneveElem = doc.createElement("pizzaneve");
        pizzaneveElem.appendChild(doc.createTextNode(pizzaneve));
        pizzaElem.appendChild(pizzaneveElem);

        String[] feltetArray = feltetek.split(",");
        for (String feltet : feltetArray) {
            Element feltetElem = doc.createElement("feltet");
            feltetElem.appendChild(doc.createTextNode(feltet.trim()));
            pizzaElem.appendChild(feltetElem);
        }

        Element teljesArElem = doc.createElement("teljes_ar");
        teljesArElem.appendChild(doc.createTextNode(teljesAr));
        pizzaElem.appendChild(teljesArElem);

        String[] meretArray = meretek.split(",");
        for (String meret : meretArray) {
            Element meretElem = doc.createElement("meret");
            meretElem.appendChild(doc.createTextNode(meret.trim()));
            pizzaElem.appendChild(meretElem);
        }
    }
}
