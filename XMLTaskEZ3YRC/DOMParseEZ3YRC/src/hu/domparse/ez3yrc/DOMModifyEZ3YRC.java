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
            NodeList pizzazoList = doc.getElementsByTagName("Pizzazo");
            for (int i = 0; i < pizzazoList.getLength(); i++) {
                Node pizzazo = pizzazoList.item(i);
                Element pizzazoElement = (Element) pizzazo;
                // Az első Pizzazo elem esetén
                if ("1".equals(pizzazoElement.getAttribute("pizzazo_id"))) {
                    pizzazoElement.getElementsByTagName("nev").item(0).setTextContent("Fortuna Pizzéria");
                }
            }

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

            NodeList beszallitasList = doc.getElementsByTagName("Beszallitas");
            for (int i = 0; i < beszallitasList.getLength(); i++) {
                Node beszallitas = beszallitasList.item(i);
                Element beszallitasElement = (Element) beszallitas;

                if ("2".equals(beszallitasElement.getAttribute("beszallito")) &&
                        "2".equals(beszallitasElement.getAttribute("pizzazo"))) {
                    beszallitasElement.getElementsByTagName("hozzavalo").item(0).setTextContent("olaj");
                }
            }

            NodeList vevoList = doc.getElementsByTagName("Vevo");
            for (int i = 0; i < vevoList.getLength(); i++) {
                Node vevo = vevoList.item(i);
                Element vevoElement = (Element) vevo;

                if ("3".equals(vevoElement.getAttribute("vevo_id"))) {
                    vevoElement.getElementsByTagName("telefonszam").item(0).setTextContent("408883091");
                }
            }

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
