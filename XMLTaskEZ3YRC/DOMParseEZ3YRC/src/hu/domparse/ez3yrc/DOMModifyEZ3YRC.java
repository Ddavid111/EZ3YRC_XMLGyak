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

            // Módosítások a main függvényben
            // Módosítás 1: Pizzazo nevének módosítása
            Element firstPizzazo = (Element) doc.getElementsByTagName("Pizzazo").item(0);
            firstPizzazo.getElementsByTagName("nev").item(0).setTextContent("Fortuna Pizzéria");

            // Módosítás 2: Pizza méreteinek átállítása
            NodeList pizzaList = doc.getElementsByTagName("Pizza");
            for (int i = 0; i < pizzaList.getLength(); i++) {
                Element pizzaElement = (Element) pizzaList.item(i);
                NodeList meretList = pizzaElement.getElementsByTagName("meret");
                meretList.item(0).setTextContent("25");
                meretList.item(1).setTextContent("32");
                meretList.item(2).setTextContent("50");
            }

            // Módosítás 3: Beszállítás hozzávaló módosítása
            Element beszallitasElement = (Element) doc.getElementsByTagName("Beszallitas").item(1);
            beszallitasElement.getElementsByTagName("hozzavalo").item(0).setTextContent("olaj");

            // Módosítás 4: Vevő telefonszámának módosítása
            Element thirdVevo = (Element) doc.getElementsByTagName("Vevo").item(2);
            thirdVevo.getElementsByTagName("telefonszam").item(0).setTextContent("408883091");

            // Módosítás 5: Bankkártya adatainak módosítása
            Element thirdBankkartya = (Element) doc.getElementsByTagName("Bankkartya").item(2);
            thirdBankkartya.getElementsByTagName("bank").item(0).setTextContent("OTP");
            thirdBankkartya.getElementsByTagName("tipus").item(0).setTextContent("hitelkartya");
            thirdBankkartya.getElementsByTagName("lejaratidatum").item(0).setTextContent("2028-12");

            // Kimenet kiírása
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
}
