package eccox9;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class eccox9DomRead {
    public static void main(String[] args) {
        try {
            File inputFile = fileKereses();

            if (inputFile == null) {
                System.out.println("Az XML file nem található.");
                return;
            }

            System.out.println("XML fájl:");
            System.out.println(inputFile.getAbsolutePath());
            System.out.println();

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            System.out.println("Gyökérelem: " + document.getDocumentElement().getNodeName());
            System.out.println();

            etteremKiiras(document);
            foszakacsKiiras(document);
            szakacsKiiras(document);
            gyakornokKiiras(document);
            vendegKiiras(document);
            rendelesKiiras(document);

            File outputFile = new File(inputFile.getParentFile(), "XMLeccox91.xml");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(outputFile);

            transformer.transform(source, result);

            System.out.println("Sikeres mentés ide:");
            System.out.println(outputFile.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Hiba történt az XML feldolgozása közben.");
            e.printStackTrace();
        }
    }

    private static File fileKereses() {
        String[] fajlok = {
                "XMLEccox9.xml",
                "XMLEccox9",
                "src/eccox9/XMLEccox9.xml",
                "src/eccox9/XMLEccox9",
                "../XMLEccox9.xml",
                "../XMLEccox9"
        };

        for (String fajlNev : fajlok) {
            File fajl = new File(fajlNev);

            if (fajl.exists() && fajl.isFile()) {
                return fajl;
            }
        }

        return null;
    }

    private static void etteremKiiras(Document document){
        NodeList lista = document.getElementsByTagName("Etterem");

        System.out.println("-= Éttermek =-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Étterem azonosító: " + elem.getAttribute("Ekod"));
                System.out.println("Neve: " + getText(elem, "Nev"));
                System.out.println("Cím: " + getText(elem, "Varos") + " " + getText(elem, "Utca") + " " + getText(elem, "Hazszam"));
                System.out.println("Csillag: " + getText(elem, "Csillag"));
                System.out.println();
            }
        }
    }

    private static void foszakacsKiiras(Document document){
        NodeList lista = document.getElementsByTagName("Foszakacs");

        System.out.println("-== Főszakácsok ==-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Főszakács azonosító: " + elem.getAttribute("Fkod"));
                System.out.println("Étterem kapcsolat: " + elem.getAttribute("E_F"));
                System.out.println("Neve: " + getText(elem, "Nev"));
                System.out.println("Életkor: " + getText(elem, "Eletkor"));
                List<String> vegzettsegek = getTextList(elem, "Vegzettseg");
                System.out.println("Végzettségek: " + String.join(", ", vegzettsegek));
                System.out.println();
            }
        }
    }

    private static void szakacsKiiras(Document document){
        NodeList lista = document.getElementsByTagName("Szakacs");

        System.out.println("-== Szakácsok ==-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Szakács azonosító: " + elem.getAttribute("SZkod"));
                System.out.println("Étterem kapcsolat: " + elem.getAttribute("E_SZ"));
                System.out.println("Neve: " + getText(elem, "Nev"));
                System.out.println("Részleg: " + getText(elem, "Reszleg"));
                List<String> vegzettsegek = getTextList(elem, "Vegzettseg");
                System.out.println("Végzettségek: " + String.join(", ", vegzettsegek));
                System.out.println();
            }
        }
    }

    private static void gyakornokKiiras(Document document) {
        NodeList lista = document.getElementsByTagName("Gyakornok");

        System.out.println("-== Gyakornokok ==-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Gyakornok azonosító: " + elem.getAttribute("GYkod"));
                System.out.println("Étterem kapcsolat: " + elem.getAttribute("E_GY"));
                System.out.println("Neve: " + getText(elem, "Nev"));
                System.out.println("Gyakorlat kezdete: " + getText(elem, "Kezdete"));
                System.out.println("Gyakorlat időtartama: " + getText(elem, "Idotartam"));
                List<String> muszakok = getTextList(elem, "Muszak");
                System.out.println("Műszakok: " + String.join(", ", muszakok));
                System.out.println();
            }
        }
    }

    private static void vendegKiiras(Document document) {
        NodeList lista = document.getElementsByTagName("Vendeg");

        System.out.println("-== Vendégek ==-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Vendég azonosító: " + elem.getAttribute("Vkod"));
                System.out.println("Neve: " + getText(elem, "Nev"));
                System.out.println("Életkor: " + getText(elem, "Eletkor"));
                System.out.println("Cím: " + getText(elem, "Varos") + " " + getText(elem, "Utca") + " " + getText(elem, "Hazszam"));
                System.out.println();
            }
        }
    }

    private static void rendelesKiiras(Document document) {
        NodeList lista = document.getElementsByTagName("Rendeles");

        System.out.println("-== Rendelések ==-");

        for (int i = 0; i < lista.getLength(); i++) {
            Node node = lista.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) node;

                System.out.println("Étterem azonosító: " + elem.getAttribute("E_V_E"));
                System.out.println("Vendég azonosító: " + elem.getAttribute("E_V_V"));
                System.out.println("Étel: " + getText(elem, "Etel"));
                System.out.println("Összeg: " + getText(elem, "Osszeg") + " Ft");
                System.out.println();
            }
        }
    }

    private static String getText(Element elem, String tag) {
        NodeList nl = elem.getElementsByTagName(tag);
        if (nl != null && nl.getLength() > 0) {
            return nl.item(0).getTextContent();
        }
        return "";
    }

    private static List<String> getTextList(Element input, String my_string) {
        List<String> my_list = new ArrayList<>();
        NodeList lista = input.getElementsByTagName(my_string);

        for (int i = 0; i < lista.getLength(); i++) {
            my_list.add(lista.item(i).getTextContent());
        }

        return my_list;
    }
}
