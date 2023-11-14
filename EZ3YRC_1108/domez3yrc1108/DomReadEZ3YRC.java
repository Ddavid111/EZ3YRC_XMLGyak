import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
 
public class DomReadEZ3YRC
{
    public static void main(String[] args)
    {
        try
        {
            File inputFile = new File("C:\\Users\\drig6\\OneDrive\\Képek\\EZ3YRC_1108\\EZ3YRC_kurzusfelvetel.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
 
            System.out.println("Gyökérelem: " + doc.getDocumentElement().getNodeName());
 
            NodeList kurzusokList = doc.getElementsByTagName("kurzus");
 
            for (int temp = 0; temp < kurzusokList.getLength(); temp++)
            {
                Node kurzusNode = kurzusokList.item(temp);
 
 
                System.out.println("\nAktuális elem: " + kurzusNode.getNodeName());
 
                if (kurzusNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element kurzusElement = (Element) kurzusNode;
 
                    System.out.println("Id: " + kurzusElement.getAttribute("id"));
                    System.out.println("Nyelv: " + kurzusElement.getAttribute("nyelv"));
 
 
                    System.out.println(
                            "Kurzusnév: " + kurzusElement.getElementsByTagName("kurzusnev").item(0).getTextContent());
                    System.out.println(
                            "Kredit: " + kurzusElement.getElementsByTagName("kredit").item(0).getTextContent());
 
 
                    System.out.println("Hely: " + kurzusElement.getElementsByTagName("hely").item(0).getTextContent());
                    System.out.println(
                            "Időpont: " + kurzusElement.getElementsByTagName("idopont").item(0).getTextContent());
                    System.out.println(
                            "Oktató: " + kurzusElement.getElementsByTagName("oktato").item(0).getTextContent());
 
                    if (kurzusElement.hasAttribute("jovahagyas"))
                        System.out.println("Jóváhagyás: " + kurzusElement.getAttribute("jovahagyas"));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}