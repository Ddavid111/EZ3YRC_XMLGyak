package hu.domparse.ez3yrc;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.File;

public class DOMModifyEZ3YRC {
    // Main metódus
    public static void main(String argv[]) {
        try {
            File inputFile = new File("XMLEZ3YRC.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);

            modifyPizzazos(doc);
            modifyPizzas(doc);
            modifyBeszallitasIngredients(doc);
            modifyVevoPhoneNumbers(doc);
            modifyBankkartyaData(doc);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 1-es id-val rendelkező Pizzazo nevének módosítása Fortuna Pizzériára
    private static void modifyPizzazos(Document doc) {
        NodeList pizzazoList = doc.getElementsByTagName("Pizzazo");
        for (int i = 0; i < pizzazoList.getLength(); i++) {
            Node pizzazo = pizzazoList.item(i);
            Element pizzazoElement = (Element) pizzazo;
            // Az első Pizzazo elem esetén
            if ("1".equals(pizzazoElement.getAttribute("pizzazo_id"))) {
                pizzazoElement.getElementsByTagName("nev").item(0).setTextContent("Fortuna Pizzéria");
            }
        }
    }

    // Pizza méretének módosítása minden pizzánál 25,32,50 cm-re
    private static void modifyPizzas(Document doc) {
        NodeList pizzaList = doc.getElementsByTagName("Pizza");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Node pizza = pizzaList.item(i);
            Element pizzaElement = (Element) pizza;
            NodeList meretList = pizzaElement.getElementsByTagName("meret");
            // Az összes méretet átállítjuk 25, 32, 50-re
            for (int j = 0; j < meretList.getLength(); j++) {
                Node meretNode = meretList.item(j);
                meretNode.setTextContent("25");
                if (j == 1) {
                    meretNode.setTextContent("32");
                } else if (j == 2) {
                    meretNode.setTextContent("50");
                }
            }
        }
    }

    // Beszallitas hozzávalójának módosítása a 2-es id-val rendelkezőnél olajra
    private static void modifyBeszallitasIngredients(Document doc) {
        NodeList beszallitasList = doc.getElementsByTagName("Beszallitas");
        for (int i = 0; i < beszallitasList.getLength(); i++) {
            Node beszallitas = beszallitasList.item(i);
            Element beszallitasElement = (Element) beszallitas;

            if ("2".equals(beszallitasElement.getAttribute("beszallito")) &&
                    "2".equals(beszallitasElement.getAttribute("pizzazo"))) {
                beszallitasElement.getElementsByTagName("hozzavalo").item(0).setTextContent("olaj");
            }
        }
    }

    // A 3-as id-val rendelkező Vevő telefonszámának módosítása  408883091-re
    private static void modifyVevoPhoneNumbers(Document doc) {
        NodeList vevoList = doc.getElementsByTagName("Vevo");
        for (int i = 0; i < vevoList.getLength(); i++) {
            Node vevo = vevoList.item(i);
            Element vevoElement = (Element) vevo;

            if ("3".equals(vevoElement.getAttribute("vevo_id"))) {
                vevoElement.getElementsByTagName("telefonszam").item(0).setTextContent("408883091");
            }
        }
    }

    // Bankkártya adatainak módosítása a 3-as id-val rendelkezőnél a bank neve: OTP a kártya típusa: hitelkártya a lejárati dátum: 2028-12
    private static void modifyBankkartyaData(Document doc) {
        NodeList bankkartyaList = doc.getElementsByTagName("Bankkartya");
        for (int i = 0; i < bankkartyaList.getLength(); i++) {
            Node bankkartya = bankkartyaList.item(i);
            Element bankkartyaElement = (Element) bankkartya;

            if ("3".equals(bankkartyaElement.getAttribute("kartyaszam")) &&
                    "3".equals(bankkartyaElement.getAttribute("vevo"))) {
                bankkartyaElement.getElementsByTagName("bank").item(0).setTextContent("OTP");
                bankkartyaElement.getElementsByTagName("tipus").item(0).setTextContent("hitelkartya");
                bankkartyaElement.getElementsByTagName("lejaratidatum").item(0).setTextContent("2028-12");
            }
        }
    }
}
