import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class DomQueryEZ3YRC {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("C:\\Users\\Laptop\\Downloads\\EZ3YRC_XMLGyak\\EZ3YRC_1115\\DomQueryEZ3YRC\\domez3yrc1115\\EZ3YRC_kurzusfelvetel.xml");

        // Felvett kurzusok nevének lekérdezése
        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzusnev");
        System.out.print("Kurzusnév: [");
        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
            Node kurzusNode = kurzusokNodeList.item(i);
            System.out.print(kurzusNode.getTextContent());
            if (i < kurzusokNodeList.getLength() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // Első hallgató adatainak kiírása konzolra
        NodeList hallgatokNodeList = doc.getElementsByTagName("hallgato");
        Node elsoHallgatoNode = hallgatokNodeList.item(0);
        Element elsoHallgatoElem = (Element) elsoHallgatoNode;

        System.out.println("Hallgató neve: " + elsoHallgatoElem.getElementsByTagName("hnev").item(0).getTextContent());
        System.out.println("Születési év: " + elsoHallgatoElem.getElementsByTagName("szulev").item(0).getTextContent());
        System.out.println("Szak: " + elsoHallgatoElem.getElementsByTagName("szak").item(0).getTextContent());
        System.out.println();

        // Első hallgató adatainak kiírása fájlba
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("EZ3YRC_lekerdezes.txt"))) {
            writer.write("Hallgató neve: " + elsoHallgatoElem.getElementsByTagName("hnev").item(0).getTextContent() + "\n");
            writer.write("Születési év: " + elsoHallgatoElem.getElementsByTagName("szulev").item(0).getTextContent() + "\n");
            writer.write("Szak: " + elsoHallgatoElem.getElementsByTagName("szak").item(0).getTextContent() + "\n\n");
        }

        // Kurzusokat oktatók nevének lekérdezése
        NodeList oktatokNodeList = doc.getElementsByTagName("oktato");
        HashSet<String> oktatokNeve = new HashSet<>();

        for (int i = 0; i < oktatokNodeList.getLength(); i++) {
            Node oktatoNode = oktatokNodeList.item(i);
            oktatokNeve.add(oktatoNode.getTextContent());
        }

        System.out.println("Oktatók nevei: " + oktatokNeve);

        // Összetett lekérdezés a kurzusokról és azok adatairól, melyeket egy adott oktató oktat
        String oktatoNeve = "Dr. Sasvári Péter";

        NodeList kurzusokNodeList2 = doc.getElementsByTagName("kurzus");

        for (int i = 0; i < kurzusokNodeList2.getLength(); i++) {
            Node kurzusNode = kurzusokNodeList2.item(i);
            Element kurzusElem = (Element) kurzusNode;

            NodeList kurzusOktatokNodeList = kurzusElem.getElementsByTagName("oktato");

            for (int j = 0; j < kurzusOktatokNodeList.getLength(); j++) {
                Node oktatoNode = kurzusOktatokNodeList.item(j);

                if (oktatoNode.getTextContent().equals(oktatoNeve)) {
                    System.out.println("Oktató neve: " + oktatoNeve);
                    System.out.println("Kurzusnév: " + kurzusElem.getElementsByTagName("kurzusnev").item(0).getTextContent());
                    System.out.println("Kredit: " + kurzusElem.getElementsByTagName("kredit").item(0).getTextContent());
                    System.out.println("Hely: " + kurzusElem.getElementsByTagName("hely").item(0).getTextContent());
                    System.out.println("Időpont: " + kurzusElem.getElementsByTagName("idopont").item(0).getTextContent());
                    System.out.println();
                    // Csak az első találatot vesszük figyelembe, ha egy kurzust több oktató is oktat
                    break;
                }
            }
        }
    }
}
