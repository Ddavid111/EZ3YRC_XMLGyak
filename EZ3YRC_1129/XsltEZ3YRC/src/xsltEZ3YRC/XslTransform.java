package xsltEZ3YRC;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XslTransform {


	    public static void main(String[] args) {
	        try {
	        	//1. feladat
	            String xmlInput = "hallgatoEZ3YRC.xml";
	            String xsltInputHTML = "hallgatoEZ3YRC.xsl";
	            String xsltInputXML = "hallgatoEZ3YRCxml.xsl";
	            String outputResult = "hallgatoEZ3YRC.html";
	            String outputResultXML = "hallgatoEZ3YRC.out.xml";

	            TransformerFactory transformerFactory = TransformerFactory.newInstance();

	            Transformer transformer = transformerFactory.newTransformer(new StreamSource(xsltInputHTML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResult));
	            
	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputXML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResultXML));
	            
	            //2. feladat
	            xmlInput = "orarendEZ3YRC.xml";
	            xsltInputHTML = "orarendEZ3YRC.xsl";
	            xsltInputXML = "orarendEZ3YRCLxml.xsl";
	            outputResult = "orarendEZ3YRC.html";
	            outputResultXML = "orarendEZ3YRC.out.xml";

	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputHTML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResult));
	            
	            transformer = transformerFactory.newTransformer(new StreamSource(xsltInputXML));
	            transformer.transform(new StreamSource(xmlInput), new StreamResult(outputResultXML));

	            System.out.println("Sikeres XSLT transzformáció, eredmény mentve.");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
