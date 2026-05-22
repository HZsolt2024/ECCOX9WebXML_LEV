package eccox9;

import org.w3c.dom.Document;

import java.awt.image.MultiResolutionImage;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.xml.sax.SAXException;

public class XPathEccox9 {

    public static void Main(String[] args) {
        String file = "hallgato.xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(file);

        document.getDocumentElement().normalize();

        XPath xPath = XPathFactory.newInstance().newXPath();

        String neptunkod = "class";

        for(int i = 0; i < neptunkod.getLength(); i++){

        }
    }
}
