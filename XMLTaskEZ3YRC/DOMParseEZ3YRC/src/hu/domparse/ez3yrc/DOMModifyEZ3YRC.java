package hu.domparse.ez3yrc;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class DOMModifyEZ3YRC {

    public static void main(String[] args) {
        try {
            // Betöltjük az XML dokumentumot
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse("XMLEZ3YRC.xml");

            // Példa: Módosítás a Pizzazo nevében
            modifyPizzazoName(document, "1", "Don Pepe Pizzázó");

            // Példa: Módosítás a Pizza méretében
            modifyPizzaSize(document, "1", "17 cm", "20 cm");

            // Példa: Módosítás a Beszallitas hozzávalójában
            modifyBeszallitasHozzavalo(document, "1", "hagyma", "paprika");

            // Példa: Módosítás a Vevo telefonszámában
            modifyVevoTelefonszam(document, "1", "306000000");

            // Példa: Módosítás a Bankkártya adataiban
            modifyPaymentMethod(document, "1", "1", "MBH", "hitelkartya");

            // Kiírjuk a módosított dokumentumot a konzolra
            printXmlDocument(document);

        } catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // Példa: Módosítás a Pizzazo nevében
    private static void modifyPizzazoName(Document document, String pizzazoId, String newName) {
        NodeList pizzazoList = document.getElementsByTagName("Pizzazo");
        for (int i = 0; i < pizzazoList.getLength(); i++) {
            Element pizzazo = (Element) pizzazoList.item(i);
            if (pizzazo.getAttribute("pizzazo_id").equals(pizzazoId)) {
                Element nevElement = (Element) pizzazo.getElementsByTagName("nev").item(0);
                nevElement.setTextContent(newName);
                System.out.println("Pizzazo név módosítva: " + newName);
                break;
            }
        }
    }

    // Példa: Módosítás a Pizza méretében
    private static void modifyPizzaSize(Document document, String pizzaId, String oldSize, String newSize) {
        NodeList pizzaList = document.getElementsByTagName("Pizza");
        for (int i = 0; i < pizzaList.getLength(); i++) {
            Element pizza = (Element) pizzaList.item(i);
            if (pizza.getAttribute("pizza_id").equals(pizzaId)) {
                NodeList meretList = pizza.getElementsByTagName("meret");
                for (int j = 0; j < meretList.getLength(); j++) {
                    Element meretElement = (Element) meretList.item(j);
                    if (meretElement.getTextContent().equals(oldSize)) {
                        meretElement.setTextContent(newSize);
                        System.out.println("Pizza méret módosítva: " + newSize);
                        break;
                    }
                }
                break;
            }
        }
    }

    // Példa: Módosítás a Beszallitas hozzávalójában
    private static void modifyBeszallitasHozzavalo(Document document, String beszallitasId, String oldHozzavalo, String newHozzavalo) {
        NodeList beszallitasList = document.getElementsByTagName("Beszallitas");
        for (int i = 0; i < beszallitasList.getLength(); i++) {
            Element beszallitas = (Element) beszallitasList.item(i);
            if (beszallitas.getAttribute("beszallito").equals(beszallitasId)) {
                Element hozzavaloElement = (Element) beszallitas.getElementsByTagName("hozzavalo").item(0);
                if (hozzavaloElement.getTextContent().equals(oldHozzavalo)) {
                    hozzavaloElement.setTextContent(newHozzavalo);
                    System.out.println("Beszállítás hozzávaló módosítva: " + newHozzavalo);
                    break;
                }
            }
        }
    }

    // Példa: Módosítás a Vevo telefonszámában
    private static void modifyVevoTelefonszam(Document document, String vevoId, String newTelefonszam) {
        NodeList vevoList = document.getElementsByTagName("Vevo");
        for (int i = 0; i < vevoList.getLength(); i++) {
            Element vevo = (Element) vevoList.item(i);
            if (vevo.getAttribute("vevo_id").equals(vevoId)) {
                Element telefonszamElement = (Element) vevo.getElementsByTagName("telefonszam").item(0);
                telefonszamElement.setTextContent(newTelefonszam);
                System.out.println("Vevő telefonszáma módosítva: " + newTelefonszam);
                break;
            }
        }
    }

    // Példa: Módosítás a Bankkártya adataiban
    private static void modifyPaymentMethod(Document document, String vevoId, String kartyaszam, String newBank, String newTipus) {
        NodeList bankkartyaList = document.getElementsByTagName("Bankkartya");
        for (int i = 0; i < bankkartyaList.getLength(); i++) {
            Element bankkartya = (Element) bankkartyaList.item(i);
            if (bankkartya.getAttribute("vevo").equals(vevoId) && bankkartya.getAttribute("kartyaszam").equals(kartyaszam)) {
                Element bankElement = (Element) bankkartya.getElementsByTagName("bank").item(0);
                Element tipusElement = (Element) bankkartya.getElementsByTagName("tipus").item(0);

                bankElement.setTextContent(newBank);
                tipusElement.setTextContent(newTipus);

                System.out.println("Bankkártya adatai módosítva: Bank: " + newBank + ", Típus: " + newTipus);
                break;
            }
        }
    }

    // Kiírja az XML dokumentumot a konzolra
    private static void printXmlDocument(Document document) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        // Kiírjuk a DOM struktúrát a konzolra
        StreamResult result = new StreamResult(System.out);
        DOMSource source = new DOMSource(document);
        transformer.transform(source, result);
    }
}
