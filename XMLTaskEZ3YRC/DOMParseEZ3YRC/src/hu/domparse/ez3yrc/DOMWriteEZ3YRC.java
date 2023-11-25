package hu.domparse.ez3yrc;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class DOMWriteEZ3YRC {

    public static void main(String[] args) {
        try {
            File inputFile = new File("XMLEZ3YRC.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            printNode(doc.getDocumentElement(), "");
            writeDocumentToFile(doc, "XMLEZ3YRC1.xml");

            System.out.println("The content has been written to the output file successfully.");
        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static void printNode(Node node, String indent) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            System.out.print("\n" + indent + "<" + node.getNodeName());
            if (node.hasAttributes()) {
                NamedNodeMap nodeMap = node.getAttributes();
                for (int i = 0; i < nodeMap.getLength(); i++) {
                    Node attr = nodeMap.item(i);
                    System.out.print(" " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\"");
                }
            }

            NodeList children = node.getChildNodes();
            if (children.getLength() == 1 && children.item(0).getNodeType() == Node.TEXT_NODE) {
                System.out.print(">" + children.item(0).getTextContent().trim());
                System.out.println("</" + node.getNodeName() + ">");
            } else {
                System.out.println(">");
                for (int i = 0; i < children.getLength(); i++)
                    printNode(children.item(i), indent + "    ");
                System.out.println(indent + "</" + node.getNodeName() + ">");
            }
        } else if (node.getNodeType() == Node.TEXT_NODE) {
            String content = node.getTextContent().trim();
            if (!content.isEmpty())
                System.out.print(content);
        }
    }


    private static void writeDocumentToFile(Document doc, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
}
